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
package org.LexGrid.LexBIG.caCore.applicationservice.impl;

import gov.nih.nci.evs.security.SecurityToken;
import gov.nih.nci.system.applicationservice.ApplicationException;
import gov.nih.nci.system.applicationservice.impl.ApplicationServiceImpl;
import gov.nih.nci.system.dao.DAO;
import gov.nih.nci.system.dao.Request;
import gov.nih.nci.system.dao.Response;
import gov.nih.nci.system.query.cql.CQLQuery;
import gov.nih.nci.system.query.hibernate.HQLCriteria;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.LexGrid.LexBIG.DataModel.Collections.CodingSchemeRenderingList;
import org.LexGrid.LexBIG.DataModel.Collections.ExtensionDescriptionList;
import org.LexGrid.LexBIG.DataModel.Collections.LocalNameList;
import org.LexGrid.LexBIG.DataModel.Collections.ModuleDescriptionList;
import org.LexGrid.LexBIG.DataModel.Collections.SortDescriptionList;
import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.DataModel.InterfaceElements.types.SortContext;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Exceptions.LBInvocationException;
import org.LexGrid.LexBIG.Extensions.Generic.GenericExtension;
import org.LexGrid.LexBIG.Extensions.Query.Filter;
import org.LexGrid.LexBIG.Extensions.Query.Sort;
import org.LexGrid.LexBIG.History.HistoryService;
import org.LexGrid.LexBIG.Impl.LexBIGServiceImpl;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeGraph;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.LexBIGService.LexBIGServiceManager;
import org.LexGrid.LexBIG.LexBIGService.LexBIGServiceMetadata;
import org.LexGrid.LexBIG.caCore.applicationservice.QueryOptions;
import org.LexGrid.LexBIG.caCore.applicationservice.RemoteExecutionResults;
import org.LexGrid.LexBIG.caCore.applicationservice.annotations.DataServiceSecurityTokenRequired;
import org.LexGrid.LexBIG.caCore.applicationservice.annotations.LexEVSSecurityTokenRequired;
import org.LexGrid.LexBIG.caCore.applicationservice.annotations.LexEVSSecurityTokenRequiredForParameter;
import org.LexGrid.LexBIG.caCore.client.proxy.LexEVSListProxy;
import org.LexGrid.LexBIG.caCore.connection.orm.utils.LexEVSClassCache;
import org.LexGrid.LexBIG.caCore.dao.orm.LexEVSDAO;
import org.LexGrid.LexBIG.caCore.dao.orm.selectionStrategy.exceptions.SelectionStrategyException;
import org.LexGrid.LexBIG.caCore.dao.orm.translators.GridCQLToDetachedCriteria;
import org.LexGrid.LexBIG.caCore.dao.orm.translators.NestedObjectToCriteria;
import org.LexGrid.LexBIG.caCore.dao.orm.translators.QBEPathToDetachedCriteria;
import org.LexGrid.LexBIG.caCore.dao.orm.translators.SDKCQLToDetachedCriteria;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.caCore.security.Validator;
import org.LexGrid.LexBIG.caCore.utils.LexEVSCaCoreUtils;
import org.LexGrid.codingSchemes.CodingScheme;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.impl.CriteriaImpl;
import org.lexevs.locator.LexEvsServiceLocator;
import org.lexevs.system.utility.MyClassLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ClassUtils;

/**
 * Main implementation class of LexEVSAPI. This class implements but the DataService
 * and the Distributed portions of the LexEVSAPI.
 * 
 * @author <a href="mailto:kevin.peterson@mayo.edu">Kevin Peterson</a>
 */
public class LexEVSApplicationServiceImpl extends ApplicationServiceImpl implements LexEVSApplicationService {
	
	private LexEVSClassCache classCache;
	private static Logger log = Logger.getLogger(LexEVSApplicationServiceImpl.class.getName());
	protected ApplicationContext appContext;

	private Validator validator;
	
	  /** The lbs. */
    private final LexBIGService lbs;
    
    private boolean updateClientProxyTarget = false;
    
    private NestedObjectToCriteria nestedObjectToCriteriaTranslator;
    private GridCQLToDetachedCriteria gridCQLToDetachedCriteriaTranslator;
    private QBEPathToDetachedCriteria qbePathToDetachedCriteriaTranslator;
    private SDKCQLToDetachedCriteria sdkCQLToDetachedCriteriaTranslator;
	
    private PaginationHelper paginationHelper;
    
	
    /**
     * Annotation class used to mark LexBig classes and methods as
     * requiring security token for execution on a client without the LexBig environment.
     */
    private static final Class TOKEN_REQUIRED = LexEVSSecurityTokenRequired.class;
    
    /**
     * Annotation to indicate that this Data Service method requires a Security Token
     */
    private static final Class DATA_SERVICE_TOKEN_REQUIRED = DataServiceSecurityTokenRequired.class;
	  
	public LexEVSApplicationServiceImpl(LexEVSClassCache classCache, Validator validator) throws ApplicationException {		
		super(classCache);
		this.classCache = classCache;
		this.validator = validator;
	
		try {
			this.lbs = new LexBIGServiceImpl();
		} catch (LBInvocationException e) {
			throw new ApplicationException("Error initializing LexBIG Service.", e);
		}	
	}
	
	public LexEVSApplicationServiceImpl(Validator validator) throws ApplicationException {	
		this(new LexEVSClassCache(), validator);
		log.warn("LexEVSAPI has been started without the caCORE SDK Data Services.");	
	}
	
    /**
     * Execute the given method on the specified LexBig object.
     *
     * @param object - Object
     * @param methodName - String
     * @param parameterClasses -String[]
     * @param args - Object[]
     *
     * @return the object
     *
     * @throws ApplicationException the application exception
     */
    public Object executeRemotely(Object object, String methodName,
            String[] parameterClasses, Object[] args) throws ApplicationException {
        if ( !LexEVSCaCoreUtils.isLexBigClass(object.getClass() )) {  
            throw new SecurityException(
                    "Cannot execute method on non-LexBig object");
        }

        try {
            int i = 0;
            Class[] parameterTypes = new Class[parameterClasses.length];
            for (String paramClass : parameterClasses) {
                parameterTypes[i++] = ClassUtils.forName(paramClass, LexEvsServiceLocator.getInstance().getSystemResourceService().getClassLoader());
            }
            Method objMethod = object.getClass().getMethod(methodName,
                parameterTypes);
            Object result = objMethod.invoke(object, args);
            
            //Wrap up the result and the current state of the object
            //and return it. We will use the object on the client side
            //to update the proxy state.
            if(updateClientProxyTarget){
            	return new RemoteExecutionResults(object, result);
            } else {
            	return result;
            }
        }
        catch (Exception e) {
            throw new ApplicationException(
                "Failed to execute LexBig method remotely",e);
        }
    }
      
    /**
     * Execute securely. (Note: currently the annotations parameter is used only on
     * the client side)
     *
     * @param object the object
     * @param methodName the method name
     * @param annotations annotations used by this method
     * @param parameterClasses the parameter classes
     * @param args the args
     *
     * @return the object
     *
     * @throws Exception the exception
     */
    public Object executeSecurely(String methodName, Annotation[] annotations,
    		String[] parameterClasses, Object[] args, HashMap tokens)
    throws Exception {
    	try {   	
    		Class[] parameterTypes = new Class[parameterClasses.length];

    		int i = 0;
    		for (String paramClass : parameterClasses) {
    			parameterTypes[i++] = ClassUtils.forName(paramClass, MyClassLoader.instance());
    		}
    		Method objMethod = this.getClass().getMethod(methodName, parameterTypes);

    		//First check if this is a Data Service method -- if so
    		//process it with the SecurityToken map.
    		if(isMethodDataServiceSecured(objMethod)){
    			//If there were no QueryOptions passed in, but there are SecurityTokens
    			//in the HashMap, we want to account for those.
    			boolean foundQueryOptions = false;
    			for(int j=0; j<parameterClasses.length; j++){
    				String param = parameterClasses[j];
    				if(param.equals(QueryOptions.class.getName())){
    					foundQueryOptions = true;
    					//We found the QueryOptions -- see if the SecurityTokens are null
    					QueryOptions queryOptions = (QueryOptions)args[j];
    					if(queryOptions.getSecurityTokens() == null){
    						queryOptions.setSecurityTokens(tokens);
    						args[j] = queryOptions;
    					}
    				}
    			}
    			//If there were no QueryOptions -- we can build some with the Tokens passed
    			//in from the client, and adjust the Method that will be called.
    			if(!foundQueryOptions){
    				QueryOptions queryOptions = new QueryOptions();
    				queryOptions.setSecurityTokens(tokens);
    				Class[] adjustedParameters = (Class[])ArrayUtils.add(parameterTypes, QueryOptions.class);
    				
    				//adjust the method to be called
    				objMethod = this.getClass().getMethod(methodName, adjustedParameters);
    				
    				//adjust the args of the method to include the QueryOptions
    				args = ArrayUtils.add(args, queryOptions);
    			}
    			
    		} else if(isMethodSecured(objMethod)) {        
    			int index = -1;
    			index = isMethodArgumentSecured(objMethod);	          
    			if(index >= 0) {
    				String requestedVocabName = args[index].toString();           	       

    				if(validator.isSecured(requestedVocabName)) {
    					SecurityToken securityToken = (SecurityToken)tokens.get(requestedVocabName);
    					if(securityToken == null){
    						log.error("Security token is null -- a valid token is required. ");
    						throw new IllegalArgumentException("Security token is null -- a valid token is required.");
    					}
    					boolean isValid = validator.validate(requestedVocabName, securityToken);
    					if(!isValid){
    						log.error("Security was present, but is invalid");
    						throw new IllegalArgumentException("Security was present, but is invalid");
    					}              
    				}
    			}	       
    		}	       
    		return objMethod.invoke(this, args);
    	}
    	catch (Exception e) {
    		throw new ApplicationException(
    				"Failed to execute LexBig method securely", e);
    	}
    }
    
    /**
     * Returns true if the given method or class is marked TOKEN_REQUIRED.
     *
     * @param object the object
     *
     * @return true, if checks if is client safe
     */@SuppressWarnings("unchecked")
    private boolean isMethodSecured(Object object) {
        if (object instanceof Method) {
            return ((Method)object).isAnnotationPresent(TOKEN_REQUIRED);
        }
        else {
            return ((Class)object).isAnnotationPresent(TOKEN_REQUIRED);
        }
    }
     
     /**
      * Returns true if the given method or class is marked TOKEN_REQUIRED.
      *
      * @param object the object
      *
      * @return true, if checks if is client safe
      */@SuppressWarnings("unchecked")
     private boolean isMethodDataServiceSecured(Object object) {
         if (object instanceof Method) {
             return ((Method)object).isAnnotationPresent(DATA_SERVICE_TOKEN_REQUIRED);
         }
         else {
             return ((Class)object).isAnnotationPresent(DATA_SERVICE_TOKEN_REQUIRED);
         }
     }
     
     /**
      * Returns true if the given method or class is marked TOKEN_REQUIRED.
      *
      * @param object the object
      *
      * @return true, if checks if is client safe
      */@SuppressWarnings("unchecked")
     private int isMethodArgumentSecured(Method object) {

	 	 Annotation methodArgs[][] = object.getParameterAnnotations();
    	 for(int i=0; i < methodArgs.length; i++) {
    		 for(int j=0; j < methodArgs[i].length; j++) {
    			 if(methodArgs[i][j] instanceof LexEVSSecurityTokenRequiredForParameter) {
    				 return i;
    			 }
    		 }
    	 }
         return -1;
     }
     
     
    /**
     * Register Security Token
     *
     * @param vocabulary the String
     * @param token the SecurityToken
     *
     * @return boolean the Boolean
     *
     * @throws Exception the exception
     */
    public Boolean registerSecurityToken(String vocabulary, SecurityToken token) throws Exception {
		// Intentionally left blank. It should be intercepted at the client Proxy
    	// Throw an exception if this method gets called here on server
    	throw new IllegalStateException();
	}
    
    @DataServiceSecurityTokenRequired public List<Object> getAssociation(Object source, String associationName, QueryOptions queryOptions) throws ApplicationException {	
    	List<Object> returnList = new ArrayList();

    	List<Object> searchReturnList = this.search(source.getClass(), source, associationName, queryOptions);
    	if(searchReturnList.size() > 1){
    		log.warn("More than one association returned -- example object is ambiguous");
    	}

    	for (Object obj : searchReturnList){
    		Class searchClass = obj.getClass();

    		while(searchClass != null){
    			try {
    				Field[] fields = searchClass.getDeclaredFields();
    				for(Field field : fields){
    					if(field.getName().equals(associationName)){
    						field.setAccessible(true); 
    						Object fieldValue = field.get(obj);
    						returnList.add(fieldValue);
    					}
    				}
    			} catch (Exception e) {
    				throw new ApplicationException(e);
    			}			
    			searchClass = searchClass.getSuperclass();
    		}
    	}   	   	
    	return returnList;
    }
	
    @DataServiceSecurityTokenRequired public List<Object> getAssociation(Object source, String associationName) throws ApplicationException {		
    	return getAssociation(source, associationName, new QueryOptions());
    }

    @DataServiceSecurityTokenRequired public <E> List<E> search(Class targetClass, Object obj, String eagerFetchAssociation, QueryOptions queryOptions) throws ApplicationException {
    	DetachedCriteria crit = nestedObjectToCriteriaTranslator.translate(targetClass, obj, eagerFetchAssociation);
		return query(crit, queryOptions);
    }   
       
    @DataServiceSecurityTokenRequired public <E> List<E> search(Class targetClass, Object obj) throws ApplicationException {
		return search(targetClass, obj, null);
	}
    
    @DataServiceSecurityTokenRequired public <E> List<E> search(Class targetClass, Object obj, QueryOptions queryOptions) throws ApplicationException {
    	DetachedCriteria crit = nestedObjectToCriteriaTranslator.translate(targetClass, obj);
    	return query(crit, queryOptions);
	}
    
    @DataServiceSecurityTokenRequired public <E> List<E> search(Class targetClass, List objList) throws ApplicationException {
    	return search(targetClass, objList, null);
    }
    
    @DataServiceSecurityTokenRequired public <E> List<E> search(Class targetClass, List objList, QueryOptions queryOptions) throws ApplicationException {
    	DetachedCriteria crit = nestedObjectToCriteriaTranslator.translate(targetClass, objList);
    	return query(crit, queryOptions);
    }

    @DataServiceSecurityTokenRequired public <E> List<E> search(String path, Object obj, QueryOptions queryOptions) throws ApplicationException {
		DetachedCriteria crit = qbePathToDetachedCriteriaTranslator.translate(path, obj);
		return query(crit, queryOptions);
	}
		
	@DataServiceSecurityTokenRequired public <E> List<E> search(String path, Object obj) throws ApplicationException {
		return search(path, obj, new QueryOptions());
	}

	@DataServiceSecurityTokenRequired public <E> List<E> query(CQLQuery cqlQuery, String targetClassName, QueryOptions queryOptions)
			throws ApplicationException {
		//NOTE: 'targetClassName' is NOT USED!!!!
		return query(cqlQuery, queryOptions);		
	}
	
	@DataServiceSecurityTokenRequired public <E> List<E> query(CQLQuery cqlQuery, String targetClassName)
			throws ApplicationException {
		//NOTE: 'targetClassName' is NOT USED!!!!
		return query(cqlQuery);
		
	}

	@DataServiceSecurityTokenRequired public <E> List<E> query(CQLQuery cqlQuery, QueryOptions queryOptions) throws ApplicationException {
		DetachedCriteria crit = sdkCQLToDetachedCriteriaTranslator.translate(cqlQuery);
		return query(crit, queryOptions);
	}
	
	@DataServiceSecurityTokenRequired public <E> List<E> query(CQLQuery cqlQuery) throws ApplicationException {
		return query(cqlQuery, new QueryOptions());
	}

	@DataServiceSecurityTokenRequired public <E> List<E> query(gov.nih.nci.cagrid.cqlquery.CQLQuery cqlQuery)
			throws ApplicationException {
		return query(cqlQuery, new QueryOptions());	
	}
	
	@DataServiceSecurityTokenRequired public <E> List<E> query(gov.nih.nci.cagrid.cqlquery.CQLQuery cqlQuery, QueryOptions queryOptions)
			throws ApplicationException {
		DetachedCriteria crit = gridCQLToDetachedCriteriaTranslator.translate(cqlQuery);	
		return query(crit, queryOptions);	
	}
	
	@DataServiceSecurityTokenRequired public <E> List<E> query(DetachedCriteria detachedCriteria, String targetClassName) throws ApplicationException {
		return query(detachedCriteria, targetClassName, new QueryOptions());
	}
	
	@DataServiceSecurityTokenRequired public <E> List<E> query(DetachedCriteria detachedCriteria, String targetClassName, QueryOptions queryOptions) throws ApplicationException {
		return privateQuery(detachedCriteria, targetClassName, queryOptions);
	}
	
	
	@DataServiceSecurityTokenRequired public <E> List<E> query(DetachedCriteria detachedCriteria) throws ApplicationException {
		return query(detachedCriteria, new QueryOptions());
	}
	
	@DataServiceSecurityTokenRequired public <E> List<E> query(DetachedCriteria detachedCriteria, QueryOptions queryOptions) throws ApplicationException {
		CriteriaImpl crit = (CriteriaImpl)detachedCriteria.getExecutableCriteria(null);
		String targetClassName = crit.getEntityOrClassName();
		return privateQuery(detachedCriteria, targetClassName, queryOptions);
	}
	
	@DataServiceSecurityTokenRequired public <E> List<E> query(HQLCriteria hqlCriteria, String targetClassName) throws ApplicationException {
		return query(hqlCriteria, targetClassName, new QueryOptions());
	}	
	
	@DataServiceSecurityTokenRequired public <E> List<E> query(HQLCriteria hqlCriteria, String targetClassName, QueryOptions queryOptions) throws ApplicationException {
		return privateQuery(hqlCriteria, targetClassName, queryOptions);
	}	

	@DataServiceSecurityTokenRequired public <E> List<E> query(HQLCriteria hqlCriteria) throws ApplicationException {
		return query(hqlCriteria, new QueryOptions());
	}
	
	@DataServiceSecurityTokenRequired public <E> List<E> query(HQLCriteria hqlCriteria, QueryOptions queryOptions) throws ApplicationException {
		String hql = hqlCriteria.getHqlString();
		
		String upperHQL = hql.toUpperCase();
		int index = upperHQL.indexOf(" FROM ");
		
		hql = hql.substring(index + " FROM ".length()).trim()+" ";
		String targetClassName = hql.substring(0,hql.indexOf(' ')).trim();
		return privateQuery(hqlCriteria, targetClassName, queryOptions);
	}
	
	@DataServiceSecurityTokenRequired public <E> List<E> query(Object criteria, Integer firstRow, String targetClassName) throws ApplicationException {
		return query(criteria, firstRow, targetClassName, new QueryOptions());
	}
	
	@DataServiceSecurityTokenRequired public <E> List<E> query(Object criteria, Integer firstRow, String targetClassName, QueryOptions queryOptions) throws ApplicationException {
		Request request = new Request(criteria);
		
		request.setIsCount(Boolean.valueOf(false));
		request.setFirstRow(firstRow);
		request.setDomainObjectName(targetClassName);

		Response response = query(request, queryOptions);
		List results = (List) response.getResponse();

		return results;
	}
	
	@DataServiceSecurityTokenRequired public <E> List<E> search(String path, List objList) throws ApplicationException {
		return search(path, objList, new QueryOptions());
	}

	@DataServiceSecurityTokenRequired public <E> List<E> search(String path, List objList, QueryOptions queryOptions) throws ApplicationException {
		DetachedCriteria crit = qbePathToDetachedCriteriaTranslator.translate(path, objList);
		return query(crit, queryOptions);
	}
	
	@DataServiceSecurityTokenRequired public Integer getQueryRowCount(Object criteria, String targetClassName) throws ApplicationException {
		return getQueryRowCount(criteria, targetClassName, new QueryOptions());
	}

	@DataServiceSecurityTokenRequired public Integer getQueryRowCount(Object criteria, String targetClassName, QueryOptions queryOptions) throws ApplicationException {
		Request request = new Request(criteria);
		request.setIsCount(Boolean.TRUE);
		request.setDomainObjectName(targetClassName);

		try {
			int totalCount = 0;
			
			List<LexEVSDAO> daoList = getDOAListForQuery(request, queryOptions);
			for (LexEVSDAO dao : daoList) {
				Response queryResponse = query(request, dao);
				totalCount += queryResponse.getRowCount();
			}
			return totalCount;
		} catch (Exception e) {
			throw new ApplicationException(e);
		} 
	}	
	
	protected <E> List<E> convertToListProxy(Collection originalList, Object query, String classname, Integer start, QueryOptions options)
	{
		int maxRecordsPerQuery = getMaxRecordsCount();
		if(options != null && options.getResultPageSize() > 0){
			maxRecordsPerQuery = options.getResultPageSize();
		}
		
		LexEVSListProxy resultList = new LexEVSListProxy();
		resultList.setAppService(this);

		// Set the value for ListProxy
		if (originalList != null) {
			resultList.addAll(originalList);
		}
		
		resultList.setQueryOptions(options);
		resultList.setOriginalStart(start);
		resultList.setMaxRecordsPerQuery(maxRecordsPerQuery);
		resultList.setOriginalCriteria(query);
		resultList.setTargetClassName(classname);
		resultList.calculateRealSize();
		
		return resultList;
	}
	
	protected <E> List<E> privateQuery(Object criteria, String targetClassName, QueryOptions queryOptions) throws ApplicationException 
	{
			Request request = new Request(criteria);
			request.setIsCount(Boolean.FALSE);
			request.setFirstRow(0);
			request.setDomainObjectName(targetClassName);

			Response response = query(request, queryOptions);
			List results = (List) response.getResponse();

			List resultList = convertToListProxy(results,criteria, targetClassName, 0, queryOptions);
			log.debug("response.getRowCount(): " + response.getRowCount());

			return resultList;		
	}	
		
	protected Response query(Request request, QueryOptions options) throws ApplicationException {
		List<LexEVSDAO> daoList;
		try {
			daoList = getDOAListForQuery(request, options);
		} catch (SelectionStrategyException e) {
			throw new ApplicationException(e);
		}
		if(daoList == null || daoList.size() == 0){
			throw new ApplicationException("No information could be retrieved. Either the information you" +
					" have requested is not available in any loaded Coding Scheme on the system, or you have" +
					" restricted your query to a Coding Scheme that is not applicable for this query.");
		}
		//If this was narrowed to one CodingScheme -- we can set this up the normal way
		if(daoList.size() == 1){
			try {
				return query(request, daoList.get(0), options.isLazyLoad(), options.getResultPageSize());
			} catch (Exception e) {
				throw new ApplicationException("Error querying DAO.", e);
			}
		} else {
			//We can't Lazy Load from Multiple Coding Schemes -- throw an error if trying to.
			if(options !=  null && options.isLazyLoad()){
				throw new ApplicationException("Cannot Lazy Load without narrowing down to a single Coding Scheme. " +
						"Please either set Lazy Load to 'false' or specify a Coding Scheme and Version in the Query Options");
			}
			try {		
				return paginationHelper.getResponseFromMultipleCodingSchemeQuery(request, daoList, options.getResultPageSize());
			} catch (Exception e) {
				throw new ApplicationException(e);
			}
		}
	}


	private List<LexEVSDAO> getDOAListForQuery(Request request, QueryOptions queryOptions) throws SelectionStrategyException {
		List<LexEVSDAO> daoList = classCache.getDaoList(request, queryOptions);
		return daoList;
	}
	
	protected Response query(Request request, LexEVSDAO dao) throws Exception {
		return query(request, dao, true, -1);
	}
	
	protected Response query(Request request, LexEVSDAO dao, boolean lazyLoad, int resultPageSize) throws Exception {
		LexEVSClassCache classCache = this.getClassCache();
		request.setClassCache(classCache);	
		Response response = dao.query(request, lazyLoad, resultPageSize);		
		return response;
	}
	
	  /**
     * Gets the coding scheme concepts.
     *
     * @param codingScheme the coding scheme String
     * @param versionOrTag the version or tag String
     * @param activeOnly the active only boolean
     *
     * @return the coding scheme concepts as a CodedNodeSet
     *
     * @throws LBException the LB exception
     *
     * @deprecated Not implemented here since it is deprecated in the
     * LexBIGService interface.
     */@Deprecated
     @LexEVSSecurityTokenRequired public CodedNodeSet getCodingSchemeConcepts(
    		 @LexEVSSecurityTokenRequiredForParameter @SuppressWarnings("unused")String codingScheme,
    		@SuppressWarnings("unused")CodingSchemeVersionOrTag versionOrTag,
    		@SuppressWarnings("unused")boolean activeOnly)
            throws LBException {
        throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see org.LexGrid.LexBIG.LexBIGService.LexBIGService#getCodingSchemeConcepts(java.lang.String, org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag)
     */
     @LexEVSSecurityTokenRequired public CodedNodeSet getCodingSchemeConcepts(@LexEVSSecurityTokenRequiredForParameter String codingScheme,
            CodingSchemeVersionOrTag versionOrTag) throws LBException {
        return lbs.getCodingSchemeConcepts(codingScheme, versionOrTag);
    }


    /* (non-Javadoc)
     * @see org.LexGrid.LexBIG.LexBIGService.LexBIGService#getFilter(java.lang.String)
     */
    public Filter getFilter(String name) throws LBException {
        return lbs.getFilter(name);
    }

    /* (non-Javadoc)
     * @see org.LexGrid.LexBIG.LexBIGService.LexBIGService#getFilterExtensions()
     */
    public ExtensionDescriptionList getFilterExtensions() {
        return lbs.getFilterExtensions();
    }

    /* (non-Javadoc)
     * @see org.LexGrid.LexBIG.LexBIGService.LexBIGService#getGenericExtension(java.lang.String)
     */
    public GenericExtension getGenericExtension(String name) throws LBException {
        return lbs.getGenericExtension(name);
    }

    /* (non-Javadoc)
     * @see org.LexGrid.LexBIG.LexBIGService.LexBIGService#getGenericExtensions()
     */
    public ExtensionDescriptionList getGenericExtensions() {
        return lbs.getGenericExtensions();
    }

    /* (non-Javadoc)
     * @see org.LexGrid.LexBIG.LexBIGService.LexBIGService#getHistoryService(java.lang.String)
     */
    @LexEVSSecurityTokenRequired public HistoryService getHistoryService(@LexEVSSecurityTokenRequiredForParameter String codingScheme)
            throws LBException {
        return lbs.getHistoryService(codingScheme);
    }

    /* (non-Javadoc)
     * @see org.LexGrid.LexBIG.LexBIGService.LexBIGService#getLastUpdateTime()
     */
    public Date getLastUpdateTime() throws LBInvocationException {
        return lbs.getLastUpdateTime();
    }

    /* (non-Javadoc)
     * @see org.LexGrid.LexBIG.LexBIGService.LexBIGService#getMatchAlgorithms()
     */
    public ModuleDescriptionList getMatchAlgorithms() {
        return lbs.getMatchAlgorithms();
    }

    /* (non-Javadoc)
     * @see org.LexGrid.LexBIG.LexBIGService.LexBIGService#getNodeGraph(java.lang.String, org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag, java.lang.String)
     */
    @LexEVSSecurityTokenRequired public CodedNodeGraph getNodeGraph(@LexEVSSecurityTokenRequiredForParameter String codingScheme,
            CodingSchemeVersionOrTag versionOrTag, String relationsName)
            throws LBException {
        return lbs.getNodeGraph(codingScheme, versionOrTag, relationsName);
    }

    /* (non-Javadoc)
     * @see org.LexGrid.LexBIG.LexBIGService.LexBIGService#getServiceManager(java.lang.Object)
     */
    public LexBIGServiceManager getServiceManager(Object credentials)
            throws LBException {
        return lbs.getServiceManager(credentials);
    }

    /* (non-Javadoc)
     * @see org.LexGrid.LexBIG.LexBIGService.LexBIGService#getServiceMetadata()
     */
    public LexBIGServiceMetadata getServiceMetadata() throws LBException {
        return lbs.getServiceMetadata();
    }

    /* (non-Javadoc)
     * @see org.LexGrid.LexBIG.LexBIGService.LexBIGService#getSortAlgorithm(java.lang.String)
     */
    public Sort getSortAlgorithm(String name) throws LBException {
        return lbs.getSortAlgorithm(name);
    }

    /* (non-Javadoc)
     * @see org.LexGrid.LexBIG.LexBIGService.LexBIGService#getSortAlgorithms(org.LexGrid.LexBIG.DataModel.InterfaceElements.types.SortContext)
     */
    public SortDescriptionList getSortAlgorithms(SortContext context) {
        return lbs.getSortAlgorithms(context);
    }

    /* (non-Javadoc)
     * @see org.LexGrid.LexBIG.LexBIGService.LexBIGService#getSupportedCodingSchemes()
     */
    public CodingSchemeRenderingList getSupportedCodingSchemes()
            throws LBInvocationException {
        return lbs.getSupportedCodingSchemes();
    }

    /* (non-Javadoc)
     * @see org.LexGrid.LexBIG.LexBIGService.LexBIGService#resolveCodingScheme(java.lang.String, org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag)
     */
    @LexEVSSecurityTokenRequired public CodingScheme resolveCodingScheme(@LexEVSSecurityTokenRequiredForParameter String codingScheme,
            CodingSchemeVersionOrTag versionOrTag) throws LBException {
        return lbs.resolveCodingScheme(codingScheme, versionOrTag);
    }
 
    /* (non-Javadoc)
     * @see org.LexGrid.LexBIG.LexBIGService.LexBIGService#resolveCodingSchemeCopyright(java.lang.String, org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag)
     */
    public java.lang.String resolveCodingSchemeCopyright(java.lang.String codingScheme,
                                                         CodingSchemeVersionOrTag versionOrTag)
                                                         throws LBException {
        return lbs.resolveCodingSchemeCopyright(codingScheme, versionOrTag);
    }

	/* (non-Javadoc)
	 * @see org.LexGrid.LexBIG.LexBIGService.LexBIGService#getNodeSet(java.lang.String, org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag, org.LexGrid.LexBIG.DataModel.Collections.LocalNameList)
	 */
    @LexEVSSecurityTokenRequired public CodedNodeSet getNodeSet(@LexEVSSecurityTokenRequiredForParameter String codingScheme, CodingSchemeVersionOrTag versionOrTag,
			LocalNameList localNameList) throws LBException {
		return lbs.getNodeSet(codingScheme, versionOrTag, localNameList);
	}

	@Override
	protected LexEVSClassCache getClassCache() {
		return this.classCache;
	}

	@Override
	protected DAO getDAO(String classname) throws ApplicationException {
		throw new RuntimeException("Not supported for LexEVS DataService");
	}

	public NestedObjectToCriteria getNestedObjectToCriteriaTranslator() {
		return nestedObjectToCriteriaTranslator;
	}

	public void setNestedObjectToCriteriaTranslator(
			NestedObjectToCriteria nestedObjectToCriteriaTranslator) {
		this.nestedObjectToCriteriaTranslator = nestedObjectToCriteriaTranslator;
	}

	public GridCQLToDetachedCriteria getGridCQLToDetachedCriteriaTranslator() {
		return gridCQLToDetachedCriteriaTranslator;
	}

	public void setGridCQLToDetachedCriteriaTranslator(
			GridCQLToDetachedCriteria gridCQLToDetachedCriteriaTranslator) {
		this.gridCQLToDetachedCriteriaTranslator = gridCQLToDetachedCriteriaTranslator;
	}

	public QBEPathToDetachedCriteria getQbePathToDetachedCriteriaTranslator() {
		return qbePathToDetachedCriteriaTranslator;
	}

	public void setQbePathToDetachedCriteriaTranslator(
			QBEPathToDetachedCriteria qbePathToDetachedCriteriaTranslator) {
		this.qbePathToDetachedCriteriaTranslator = qbePathToDetachedCriteriaTranslator;
	}

	public SDKCQLToDetachedCriteria getSdkCQLToDetachedCriteriaTranslator() {
		return sdkCQLToDetachedCriteriaTranslator;
	}

	public void setSdkCQLToDetachedCriteriaTranslator(
			SDKCQLToDetachedCriteria sdkCQLToDetachedCriteriaTranslator) {
		this.sdkCQLToDetachedCriteriaTranslator = sdkCQLToDetachedCriteriaTranslator;
	}

	public PaginationHelper getPaginationHelper() {
		return paginationHelper;
	}

	public void setPaginationHelper(PaginationHelper paginationHelper) {
		this.paginationHelper = paginationHelper;
	}

	public boolean isUpdateClientProxyTarget() {
		return updateClientProxyTarget;
	}

	public void setUpdateClientProxyTarget(boolean updateClientProxyTarget) {
		this.updateClientProxyTarget = updateClientProxyTarget;
	}
}
