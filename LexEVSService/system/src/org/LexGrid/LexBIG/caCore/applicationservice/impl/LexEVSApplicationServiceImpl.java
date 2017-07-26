/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.applicationservice.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
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
import org.LexGrid.LexBIG.caCore.applicationservice.annotations.LexEVSSecurityTokenRequired;
import org.LexGrid.LexBIG.caCore.applicationservice.annotations.LexEVSSecurityTokenRequiredForParameter;
import org.LexGrid.LexBIG.caCore.applicationservice.resource.RemoteResourceManager;
import org.LexGrid.LexBIG.caCore.client.proxy.LexEVSListProxy;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.caCore.security.Validator;
import org.LexGrid.LexBIG.caCore.security.properties.LexEVSProperties;
import org.LexGrid.LexBIG.caCore.utils.LexEVSCaCoreUtils;
import org.LexGrid.codingSchemes.CodingScheme;
import org.apache.log4j.Logger;
import org.lexevs.locator.LexEvsServiceLocator;
import org.lexevs.system.utility.MyClassLoader;
import org.lexgrid.conceptdomain.LexEVSConceptDomainServices;
import org.lexgrid.conceptdomain.impl.LexEVSConceptDomainServicesImpl;
import org.lexgrid.valuesets.LexEVSPickListDefinitionServices;
import org.lexgrid.valuesets.LexEVSValueSetDefinitionServices;
import org.lexgrid.valuesets.impl.LexEVSPickListDefinitionServicesImpl;
import org.lexgrid.valuesets.impl.LexEVSValueSetDefinitionServicesImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ClassUtils;

import gov.nih.nci.evs.security.SecurityToken;
import gov.nih.nci.system.applicationservice.ApplicationException;
import gov.nih.nci.system.applicationservice.impl.ApplicationServiceImpl;
import gov.nih.nci.system.util.ClassCache;

/**
 * Main implementation class of LexEVSAPI. This class implements but the DataService
 * and the Distributed portions of the LexEVSAPI.
 * 
 * @author <a href="mailto:kevin.peterson@mayo.edu">Kevin Peterson</a>
 */
public class LexEVSApplicationServiceImpl extends ApplicationServiceImpl implements LexEVSApplicationService {

	private static final long serialVersionUID = -6915753324402247212L;
	
	private RemoteResourceManager remoteResourceManager;
	
	private static Logger log = Logger.getLogger(LexEVSApplicationServiceImpl.class.getName());
	protected ApplicationContext appContext;

	private Validator validator;
	
	  /** The lbs. */
    private final LexBIGService lbs;
    
    private boolean updateClientProxyTarget = false;
    
	
    /**
     * Annotation class used to mark LexBig classes and methods as
     * requiring security token for execution on a client without the LexBig environment.
     */
    private static final Class TOKEN_REQUIRED = LexEVSSecurityTokenRequired.class;

    private static final String LEXBIG_SYSPROPERTY = "LG_CONFIG_FILE";
    
	  
	public LexEVSApplicationServiceImpl(ClassCache classCache, Validator validator, LexEVSProperties properties) throws Exception {		
		super(classCache);
		this.validator = validator;
		System.setProperty(LEXBIG_SYSPROPERTY, properties.getLexBigConfigFileLocation());
	
		try {
			this.lbs = LexBIGServiceImpl.defaultInstance();
		} catch (Exception e) {
			throw new ApplicationException("Error initializing LexBIG Service.", e);
		}	
	}
	
	public LexEVSApplicationServiceImpl(Validator validator, LexEVSProperties properties) throws Exception {	
		this(new ClassCache() , validator, properties);
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
            
            result = replaceWithShell(result);

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

	private Object replaceWithShell(Object result) {
		return this.remoteResourceManager.replaceWithShell(result);
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

    		if(isMethodSecured(objMethod)) {        
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
    		Object returnObj = objMethod.invoke(this, args);
    		
    		return replaceWithShell(returnObj);
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
	public LexEVSConceptDomainServices getLexEVSConceptDomainServices() {
		return LexEVSConceptDomainServicesImpl.defaultInstance();
	}

	@Override
	public LexEVSValueSetDefinitionServices getLexEVSValueSetDefinitionServices() {
		return LexEVSValueSetDefinitionServicesImpl.defaultInstance();
	}

	@Override
	public LexEVSPickListDefinitionServices getLexEVSPickListDefinitionServices() {
		return LexEVSPickListDefinitionServicesImpl.defaultInstance();
	}

	public boolean isUpdateClientProxyTarget() {
		return updateClientProxyTarget;
	}

	public void setUpdateClientProxyTarget(boolean updateClientProxyTarget) {
		this.updateClientProxyTarget = updateClientProxyTarget;
	}

	public RemoteResourceManager getRemoteResourceManager() {
		return remoteResourceManager;
	}

	public void setRemoteResourceManager(RemoteResourceManager remoteResourceManager) {
		this.remoteResourceManager = remoteResourceManager;
	}

	@Override
	public List<CodingScheme> getMinimalResolvedCodingSchemes() throws LBInvocationException {
		return lbs.getMinimalResolvedCodingSchemes();
	}
}
