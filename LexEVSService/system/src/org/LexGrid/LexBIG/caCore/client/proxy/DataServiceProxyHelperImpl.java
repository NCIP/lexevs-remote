/*******************************************************************************
 * Copyright: (c) 2004-2009 Mayo Foundation for Medical Education and 
 * Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
 * triple-shield Mayo logo are trademarks and service marks of MFMER.
 * 
 * Except as contained in the copyright notice above, or as used to identify 
 * MFMER as the author of this software, the trade names, trademarks, service
 * marks, or product names of the copyright holder shall not be used in
 * advertising, promotion or otherwise in connection with this software without
 * prior written authorization of the copyright holder.
 *   
 * Licensed under the Eclipse Public License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *   
 *  		http://www.eclipse.org/legal/epl-v10.html
 * 
 *  		
 *******************************************************************************/
package org.LexGrid.LexBIG.caCore.client.proxy;

import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.client.proxy.BeanProxy;
import gov.nih.nci.system.client.proxy.ListProxy;
import gov.nih.nci.system.client.proxy.ProxyHelperImpl;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import java.util.HashMap;
import java.util.Iterator;


import net.sf.cglib.proxy.Enhancer;

import org.LexGrid.LexBIG.Impl.logging.LoggerFactory;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.caCore.utils.LexEVSCaCoreUtils;
import org.LexGrid.LexBIG.caCore.applicationservice.RemoteExecutionResults;
import org.LexGrid.LexBIG.caCore.applicationservice.annotations.DataServiceLazyLoadable;
import org.LexGrid.annotations.LgAdminFunction;
import org.LexGrid.annotations.LgClientSideSafe;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.AopContext;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.target.SingletonTargetSource;


import gov.nih.nci.evs.security.SecurityToken;

/**
 * Object proxy implementation for EVS. Certain methods are overridden to
 * provide EVS-specific proxying functionality.
 *
 * @author <a href="mailto:muhsins@mail.nih.gov">Shaziya Muhsin</a>
 * @author <a href="mailto:rokickik@mail.nih.gov">Konrad Rokicki</a>
 */
public class DataServiceProxyHelperImpl extends ProxyHelperImpl {

    private static final Logger log = Logger.getLogger(LexEVSProxyHelperImpl.class);
    
    /*
     * Special methods to exclude from Lazy Loading
     */
    private static final String[] NON_LAZY_LOAD_METHODS = new String[]{"getAllProperties"};
    
    static {
        // must configure LexBig before attempting to create any proxies
        LoggerFactory.setLightweight(true);
    }
    
	public Object convertToProxy(ApplicationService as, Object obj) 
	{
		if(obj == null) return null;
		if(obj instanceof LexEVSListProxy)
			return convertLexEVSListProxyToProxy(as,(LexEVSListProxy)obj);
		if(obj instanceof ListProxy)
			return convertListProxyToProxy(as,(ListProxy)obj);
		if(obj instanceof java.util.Collection)
			return convertCollectionToProxy(as,(Collection)obj);
		else if(obj instanceof Object[])
			return convertArrayToProxy(as,(Object[])obj);
		else
		   	return convertObjectToProxy(as,obj);
	}

	protected Object convertLexEVSListProxyToProxy(ApplicationService as, LexEVSListProxy proxy) {
		proxy.setAppService(as);
		List chunk = proxy.getListChunk();
		
		List modifiedChunk = new ArrayList((Collection)convertToProxy(as,chunk));
		proxy.setListChunk(modifiedChunk);
		
		return proxy;
	}

    @Override
    protected Object convertObjectToProxy(ApplicationService as, Object obj) {
        if(null == obj) return null;
        
        if(obj instanceof Integer || obj instanceof Float || obj instanceof Double
                || obj instanceof Character || obj instanceof Long || obj instanceof Boolean
                || obj instanceof String || obj instanceof Date || obj instanceof LexEVSBeanProxy
                || obj instanceof BeanProxy)
            return obj;
    
        ProxyFactory pf = new ProxyFactory(obj);
        pf.setProxyTargetClass(true);
        pf.addAdvice(new LexEVSBeanProxy(as, this));
        pf.setExposeProxy(true);
        return pf.getProxy();
    }

    /**
     * Returns true if the object is initialized
     */@SuppressWarnings("unchecked")
    @Override
    public boolean isInitialized(MethodInvocation invocation) throws Exception {   
    	if(isLazyLoadableMethod(invocation.getMethod())){
    		return isMethodInitialized(invocation);
    	} else {
    		return true;
    	}
    }
     
     protected boolean isLazyLoadableMethod(Method method){
    	 if(!method.getDeclaringClass().getName().startsWith("org.LexGrid")){
    		 return false;
    	 }
    	 
    	 String methodName = method.getName();
    	 
    	 //if this method has been flagged to be skipped from Lazy Loading, skip it here
    	 if(ArrayUtils.contains(NON_LAZY_LOAD_METHODS, methodName)){
    		 return false;
    	 }
    	 
    	 if(methodName.startsWith("get") ||
    			 methodName.startsWith("iterate") ||
    			 methodName.startsWith("enumerate")){
    		 return true;
    	 }
    	 return false;
     }
     
     /**
     * Determine the property within the bean that is being referenced by the method called.
     * This is due to Castor's naming conventions -- for example:
     * 
     * The method 'getPresentationCount'
     * May reference '_presentationList' in the bean.
     * 
     * We need to know the specific name of the property associated with each method call so
     * that we know what property Hibernate needs to lazy load.
     * 
     * This also needs to take into account that Castor may assign multiple methods to a property,
     * for example, it may return a count of the property (getPresentationCount), or an Iterator
     * (IteratePresentation)... and so on. All these methods in the end reference the same property in
     * the bean, '_presentationList'.
     * 
     * @param invocation The Method Invocation.
     * @return The property name within the bean that will be referenced by this MethodInvocation.
     * @throws Exception
     */
    protected String getPropertyNameFromMethodName(MethodInvocation invocation) throws Exception {
    	 String methodName = invocation.getMethod().getName();
    	
    	 //Lets say we have the method 'getPresentationCount"
    	 //so now we know that its a getter -- chop off the 'get' and start looking
    	 methodName = methodName.replaceFirst("get", "");
    	 
    	 //Do the same for 'iterate' and 'enumerate'
    	 methodName = methodName.replaceFirst("enumerate", "");
    	 methodName = methodName.replaceFirst("iterate", "");
    	     	 
    	 //we now have 'PresentationCount'
    	 //make the first letter lower case and add the goofy Castor underscore
    	 methodName = makeFirstLetterLowerCase(methodName);
    	 methodName = "_"+methodName;
    	 
    	 //now we have _PresentationCount
    	 //if this is supposed to return a list, add the "List"
    	 if(invocation.getMethod().getReturnType() == List.class ||
    			 invocation.getMethod().getReturnType() == Iterator.class ||
    			 invocation.getMethod().getReturnType() == Enumeration.class ||
    			 invocation.getMethod().getReturnType().isArray()){
    		 //if this is a List, we must append the "List"
    		 methodName = methodName + "List";
    		 //now we have _presentationList, which is what we want!
    	 }
    	 
    	 //Account for the Castor get...(int) methods for Collections
    	 if(invocation.getMethod().getParameterTypes().length == 1 &&
    			 invocation.getMethod().getParameterTypes()[0] == int.class){
    		 methodName = methodName + "List";
    	 }
    	 
    	 //chop off the "Count" and replace with "List".
    	 if(methodName.endsWith("Count")){
    		 //if this is a List, we must append the "List"
    		 methodName = methodName.replace("Count", "List");
    		 //now we have _presentationList, which is what we want!
    	 }  	 
    	 return methodName;
     }
     
     protected String makeFirstLetterLowerCase(String searchString){
    	return searchString.substring(0, 1).toLowerCase() + searchString.substring(1);
     }
     
     /**
     * Determine whether or not this method call has been fully initialized by Hibernate. If
     * not, it will need to be lazy-loaded. 
     * 
     * @param invocation The MethodInvocation to check.
     * @return Whether or not it has been fully initialized by Hibernate.
     * @throws Exception
     */
    protected boolean isMethodInitialized(MethodInvocation invocation) throws Exception {
    	 String propertyName = getPropertyNameFromMethodName(invocation);
    	 return isFieldHibernateInit(invocation.getThis(), propertyName);
     }

     /**
     * Determine whether or not a Field has been initialized by Hibernate or not.
     * 
     * @param obj The object to check.
     * @param fieldName The field name.
     * @return If the given field has been initialized or not.
     * @throws Exception
     */
    protected boolean isFieldHibernateInit(Object obj, String fieldName) throws Exception {
    	 Class clazz = obj.getClass();
    	 Field field = LexEVSCaCoreUtils.getField(clazz, fieldName);
    	 field.setAccessible(true);
    	 return Hibernate.isInitialized(field.get(obj));
     }
  
    /**
     * Implements the LazyLoading
     */@SuppressWarnings("unchecked") 
    @Override
    public Object lazyLoad(ApplicationService as, MethodInvocation invocation)
            throws Throwable {
    	 Object source = invocation.getThis();

    	 LexEVSApplicationService eas = (LexEVSApplicationService)as;
    	 
    	 String associationName = getPropertyNameFromMethodName(invocation);
    	 
    	 List results = (List)eas.getAssociation(createClone(source), associationName);
    	 if(results.size() > 1){
    		 throw new Exception("LazyLoad returned more results than expected. Please set the QueryOption to initialize the query.");
    	 }
   	 
    	 Object result = results.get(0);
    	
    	 if(result instanceof List){
    		 return accountForCastorMethods((List)result, invocation);
    	 } else {
    		 return result;
    	 }
    }
      
     /**
     * Account for all the extra methods Castor puts in its beans. This needs to be done because
     * the properties themselves may not be initialized by Hibernate (lazy loaded), but calling 
     * one of these methods on a Castor bean will try to invoke the un-initialized property. So,
     * we have to determine what method is being called and process the result accordingly.
     *  
     * @param result The right result given the Method being called.
     * @param methodInvocation The method being invoked.
     * @return
     */
    protected Object accountForCastorMethods(List result, MethodInvocation methodInvocation){
    	 Method method = methodInvocation.getMethod();
    	 String methodName = method.getName();
    	 
    	 if(methodName.startsWith("get") && methodName.endsWith("Count")){
    		 return new Integer(result.size());
    	 }
    	 if(methodName.startsWith("enumerate")){
    		 return java.util.Collections.enumeration(result);
    	 }
    	 if(methodName.startsWith("iterate")){
    		 return result.iterator();
    	 }
    	 if(methodName.startsWith("get") && method.getReturnType().isArray()){	 
    		 Class returnType = method.getReturnType().getComponentType();
    		 Object[] array = (Object[])Array.newInstance(returnType, result.size()); 	
    		 return result.toArray(array);
    	 }
    	 if(methodName.startsWith("get") && method.getParameterTypes().length == 1 &&
    			 method.getParameterTypes()[0] == int.class){
    		 return result.get((Integer)methodInvocation.getArguments()[0]);
    	 }
    			
    	 return result;
     }
}
