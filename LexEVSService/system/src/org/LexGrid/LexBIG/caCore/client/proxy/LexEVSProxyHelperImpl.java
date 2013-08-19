/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.client.proxy;

import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.client.proxy.BeanProxy;
import gov.nih.nci.system.client.proxy.ProxyHelperImpl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.LexGrid.LexBIG.caCore.applicationservice.RemoteExecutionResults;
import org.LexGrid.LexBIG.caCore.applicationservice.resource.RemoteShell;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.caCore.utils.LexEVSCaCoreUtils;
import org.LexGrid.annotations.LgAdminFunction;
import org.LexGrid.annotations.LgClientSideSafe;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.AopContext;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.target.SingletonTargetSource;

/**
 * Object proxy implementation for EVS. Certain methods are overridden to
 * provide EVS-specific proxying functionality.
 *
 * @author <a href="mailto:muhsins@mail.nih.gov">Shaziya Muhsin</a>
 * @author <a href="mailto:rokickik@mail.nih.gov">Konrad Rokicki</a>
 */
public class LexEVSProxyHelperImpl extends ProxyHelperImpl {

    private static final Logger log = Logger.getLogger(LexEVSProxyHelperImpl.class);
   
    /**
     * Annotation class used to mark LexBig classes and methods as
     * safe for execution on a client without the LexBig environment.
     */
    private static final Class CLIENT_SAFE = LgClientSideSafe.class;
    
    /**
     * Annotation class used to mark LexBig methods which are admin
     * functions and thus illegal for execution via the distributed API.
     */
    private static final Class ADMIN_FUNCTION = LgAdminFunction.class;

    @Override
    protected Object convertObjectToProxy(ApplicationService as, Object obj) {
        if(null == obj) return null;
        
        //Check to see if the returned object is an EVSRemoteExecutionResults.
        //If so, unwrap it and update the proxy target
        if(obj instanceof RemoteExecutionResults){
        	RemoteExecutionResults results = (RemoteExecutionResults)obj;
        	
        	//if the returned results are null, return null
        	if(results.getReturnValue() == null) return null;
        	
        	//Get the current proxy target and update it with the retuned value
        	//from the server
        	Advised advised = (Advised)AopContext.currentProxy();
        	advised.setTargetSource(new SingletonTargetSource(results.getObj()));
        	
        	obj = results.getReturnValue();      	
        }

        if(obj instanceof RemoteShell){
        	Class<?>[] targetInterfaces = ((RemoteShell)obj).getTargetInterfaces();
        	Class<?> targetClass = ((RemoteShell)obj).getTargetClass();
        	ProxyFactory pf = new ProxyFactory(targetInterfaces);
        	pf.addAdvice(new LexEVSBeanProxy(as, this));
        	pf.setProxyTargetClass(true);
        	pf.setTargetClass(targetClass);
        	pf.setTarget(obj);
        
        	return pf.getProxy();
        }
        
        if(obj instanceof Integer || obj instanceof Float || obj instanceof Double
                || obj instanceof Character || obj instanceof Long || obj instanceof Boolean
                || obj instanceof String || obj instanceof Date || obj instanceof LexEVSBeanProxy
                || obj instanceof BeanProxy)
            return obj;

        if( ! LexEVSCaCoreUtils.isLexBigClass(obj.getClass())){
        	return obj;
        }
        
        // Don't proxy client-safe LexBig objects
        if (isClientSafe(obj.getClass())) {
            return obj;
        } else {
        	return LexEVSCaCoreUtils.createProxy(obj, as, this);
        }
    }

    /**
     * Returns true if the object is initialized
     */@SuppressWarnings("unchecked")
    @Override
    public boolean isInitialized(MethodInvocation invocation) throws Throwable {
    	 if(invocation.getThis() == null || invocation.getThis() instanceof RemoteShell){
    		 return false;
    	 }
    	 
    	 Class implClass = invocation.getThis().getClass();

        // LexBig objects have methods that must be executed remotely
        if (LexEVSCaCoreUtils.isLexBigClass(implClass)) {
            Method method = invocation.getMethod();
            Method methodImpl = implClass.getMethod(method.getName(),
                method.getParameterTypes());

            if (methodImpl.isAnnotationPresent(ADMIN_FUNCTION)) {
                throw new UnsupportedOperationException(
                    "Admin functions cannot be executed using the distributed API");
            }

            if (isClientSafe(methodImpl)) {
                log.info("DLB calling locally: "+implClass.getName()+"."+methodImpl.getName());
                return true;
            }

            log.info("DLB calling remotely: "+implClass.getName()+"."+methodImpl.getName());
            return false;
        }

        return true;
    }
    /**
     * Implements the LazyLoading
     */@SuppressWarnings("unchecked")
    @Override
    public Object lazyLoad(ApplicationService as, MethodInvocation invocation)
            throws Throwable {

        Object bean = invocation.getThis();
        Method method = invocation.getMethod();
        String methodName = method.getName();
        
        Object args[] = invocation.getArguments();
        
        Class implClass = bean.getClass();

        // LexBig objects have methods that must be executed remotely
        if (LexEVSCaCoreUtils.isLexBigClass(implClass)) {
   
            LexEVSApplicationService eas = (LexEVSApplicationService)as;
            Object results = eas.executeRemotely(
            		bean,
            		methodName,  getParameterTypes(method), args);
          
            return results;
       }

       return null;
    }

    /**
     * Returns true if the given method or class is marked client-safe.
     *
     * @param object the object
     *
     * @return true, if checks if is client safe
     */@SuppressWarnings("unchecked")
    private boolean isClientSafe(Object object) {
        if (object instanceof Method) {
            return ((Method)object).isAnnotationPresent(CLIENT_SAFE);
        }
        else {
            return ((Class)object).isAnnotationPresent(CLIENT_SAFE);
        }
    }
     
    /**
     * Returns a list of class names that are parameters to the given method.
     * @param methodImpl
     * @return list of fully-qualified class names
     */
    private String[] getParameterTypes(Method methodImpl) {
        String[] paramClasses =
            new String[methodImpl.getParameterTypes().length];
        int i = 0;
        for(Class paramClass : methodImpl.getParameterTypes()) {
            if (paramClass == null) continue;
            paramClasses[i++] = paramClass.getName();
        }
        return paramClasses;
    }

    /**
     * Creates a serializable copy of a given object
     */
    protected Object createClone(Object source)
    {
        try
        {
            Object target = source.getClass().newInstance();
            List<Field> fieldList = new ArrayList<Field>();
            getAllFields(source.getClass(), fieldList);
            for(Field field:fieldList)
            {
                Object obj = field.get(source);

                if(obj instanceof Integer || obj instanceof Float || obj instanceof Double
                        || obj instanceof Character || obj instanceof Long || obj instanceof Boolean
                        || obj instanceof String)
                {
                    if(!Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers()))
                    {
                        field.setAccessible(true);
                        field.set(target, obj);
                    }
                }
            }
            return target;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    protected List<Field> getAnnotatedFields(Object obj, Class annotation){
    	List<Field> returnList = new ArrayList<Field>();
    	for(Field field : obj.getClass().getDeclaredFields()){
    		if(field.isAnnotationPresent(annotation)){
    			returnList.add(field);
    		}
    	}
    	return returnList;
    }
}
