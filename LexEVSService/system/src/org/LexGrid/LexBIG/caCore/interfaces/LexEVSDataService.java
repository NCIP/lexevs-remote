/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.interfaces;

import gov.nih.nci.system.applicationservice.ApplicationException;
import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.query.cql.CQLQuery;
import gov.nih.nci.system.query.hibernate.HQLCriteria;

import java.util.List;

import org.LexGrid.LexBIG.caCore.applicationservice.QueryOptions;
import org.LexGrid.LexBIG.caCore.applicationservice.annotations.DataServiceLazyLoadable;
import org.LexGrid.LexBIG.caCore.security.interfaces.TokenSecurableApplicationService;
import org.hibernate.criterion.DetachedCriteria;

/**
 * The caCORE-SDK Data Service Portion of LexEVSAPI. This extends on the caCORE ApplicationService
 * to provide additional Query Options.
 * 
 * @author <a href="mailto:kevin.peterson@mayo.edu">Kevin Peterson</a>
 */
public interface LexEVSDataService extends ApplicationService, TokenSecurableApplicationService {

	  /**
	 * Retrieves the result from the data source using the CQL query. The CQL query structure is converted into the
	 * data source specific query language. For the Object Relational Mapping based persistence tier, the CQL query
	 * structure is converted in the Hibernate Query Language (HQL). Hibernate converts the HQL into SQL and executes
	 * it against the relational database. 
	 * 
	 * The retrieved results are converted into a list which may not be completely loaded. If the retrieved results 
	 * are more than the maximum number of supported records as indicated by {@link #getMaxRecordsCount()} then the
	 * result set will be partially loaded. The client framework will execute a subsequent query (transparent to the
	 * client application) against the <code>ApplicationService</code> to load the remaining results in the chunk 
	 * no greater than value specified by {@link #getMaxRecordsCount()}.
	 * 
	 * <B>Note:</B> The <code>targetClassName</code> parameter will not be interpreted by the system. This parameter
	 * will be determined from the CQLQuery. 
	 * 
	 * @param cqlQuery
	 * @param targetClassName
	 * @param queryOptions
	 * @return
	 * @throws ApplicationException
	 * @see {@link #query(CQLQuery)}
	 */
	@Deprecated
	@DataServiceLazyLoadable
	public <E> List<E> query(CQLQuery cqlQuery, String targetClassName, QueryOptions queryOptions) throws ApplicationException;
	
	/**
	 * Retrieves the result from the data source using the CQL query. The CQL query structure is converted into the
	 * data source specific query language. For the Object Relational Mapping based persistence tier, the CQL query
	 * structure is converted in the Hibernate Query Language (HQL). Hibernate converts the HQL into SQL and executes
	 * it against the relational database. 
	 * 
	 * The retrieved results are converted into a list which may not be completely loaded. If the retrieved results 
	 * are more than the maximum number of supported records as indicated by {@link #getMaxRecordsCount()} then the
	 * result set will be partially loaded. The client framework will execute a subsequent query (transparent to the
	 * client application) against the <code>ApplicationService</code> to load the remaining results in the chunk 
	 * no greater than value specified by {@link #getMaxRecordsCount()}.
	 * 
	 * @param cqlQuery
	 * @param queryOptions Specific Options to be used while processing this query.
	 * @return
	 * @throws ApplicationException
	 */
	@DataServiceLazyLoadable
	public <E> List<E> query(CQLQuery cqlQuery, QueryOptions queryOptions) throws ApplicationException;

	/**
	 * Retrieves the result from the data source using the DetachedCriteria query. The DetachedCriteria query structure 
	 * can be used only by the Object Relational Mapping based persistence tier. Hibernate executes it against the 
	 * relational database and fetches the results. 
	 * 
	 * The retrieved results are converted into a list which may not be completely loaded. If the retrieved results 
	 * are more than the maximum number of supported records as indicated by {@link #getMaxRecordsCount()} then the
	 * result set will be partially loaded. The client framework will execute a subsequent query (transparent to the
	 * client application) against the <code>ApplicationService</code> to load the remaining results in the chunk 
	 * no greater than value specified by {@link #getMaxRecordsCount()}.
	 * 
	 * <B>Note:</B> The <code>targetClassName</code> parameter will not be interpreted by the system. This parameter
	 * will be determined from the DetachedCriteria object. 
	 * 
	 * @param detachedCriteria
	 * @param targetClassName
	 * @param queryOptions Specific Options to be used while processing this query.
	 * @return
	 * @throws ApplicationException
	 * @see {@link #query(DetachedCriteria)}
	 */
	@Deprecated
	@DataServiceLazyLoadable
	public <E> List<E> query(DetachedCriteria detachedCriteria, String targetClassName, QueryOptions queryOptions) throws ApplicationException;
	
	/**
	 * Retrieves the result from the data source using the DetachedCriteria query. The DetachedCriteria query structure 
	 * can be used only by the Object Relational Mapping based persistence tier. Hibernate executes it against the 
	 * relational database and fetches the results. 
	 * 
	 * The retrieved results are converted into a list which may not be completely loaded. If the retrieved results 
	 * are more than the maximum number of supported records as indicated by {@link #getMaxRecordsCount()} then the
	 * result set will be partially loaded. The client framework will execute a subsequent query (transparent to the
	 * client application) against the <code>ApplicationService</code> to load the remaining results in the chunk 
	 * no greater than value specified by {@link #getMaxRecordsCount()}.
	 * 
	 * @param detachedCriteria
	 * @param queryOptions Specific Options to be used while processing this query.
	 * @return
	 * @throws ApplicationException
	 */
	@DataServiceLazyLoadable
	public <E> List<E> query(DetachedCriteria detachedCriteria, QueryOptions queryOptions) throws ApplicationException;
	
	/**
	 * Retrieves the result from the data source using the HQL query. The HQL query structure can be used only by 
	 * the Object Relational Mapping based persistence tier. Hibernate executes hql query against the relational 
	 * database and fetches the results. 
	 * 
	 * The retrieved results are converted into a list which may not be completely loaded. If the retrieved results 
	 * are more than the maximum number of supported records as indicated by {@link #getMaxRecordsCount()} then the
	 * result set will be partially loaded. The client framework will execute a subsequent query (transparent to the
	 * client application) against the <code>ApplicationService</code> to load the remaining results in the chunk 
	 * no greater than value specified by {@link #getMaxRecordsCount()}.
	 * 
	 * <B>Note:</B> The <code>targetClassName</code> parameter will not be interpreted by the system. This parameter
	 * will be determined from the hql query. 
	 * 
	 * @param hqlCriteria
	 * @param targetClassName
	 * @param queryOptions Specific Options to be used while processing this query.
	 * @return
	 * @throws ApplicationException
	 * @see {@link #query(HQLCriteria)}
	 */
	@Deprecated
	@DataServiceLazyLoadable
	public <E> List<E> query(HQLCriteria hqlCriteria, String targetClassName, QueryOptions queryOptions) throws ApplicationException;
	
	/**
	 * Retrieves the result from the data source using the HQL query. The HQL query structure can be used only by 
	 * the Object Relational Mapping based persistence tier. Hibernate executes hql query against the relational 
	 * database and fetches the results. 
	 * 
	 * The retrieved results are converted into a list which may not be completely loaded. If the retrieved results 
	 * are more than the maximum number of supported records as indicated by {@link #getMaxRecordsCount()} then the
	 * result set will be partially loaded. The client framework will execute a subsequent query (transparent to the
	 * client application) against the <code>ApplicationService</code> to load the remaining results in the chunk 
	 * no greater than value specified by {@link #getMaxRecordsCount()}.
	 * 
	 * @param hqlCriteria
	 * @param queryOptions Specific Options to be used while processing this query.
	 * @return
	 * @throws ApplicationException
	 */
	@DataServiceLazyLoadable
	public <E> List<E> query(HQLCriteria hqlCriteria, QueryOptions queryOptions) throws ApplicationException;

	/**
	 * Retrieves the result from the data source using the Query by Example. The <code>targetClass</code> specifies 
	 * the object that the user intends to fetch after executing the query. The <code>targetClass</code> should be 
	 * same as the object specified in the objList or associated object for the example object. Also, all the objects 
	 * in the <code>objList</code> has to be of the same type. The example query is converted into the data source 
	 * specific query language. For the Object Relational Mapping based persistence tier, the example query structure 
	 * is converted in the Hibernate Query Language (HQL). Hibernate converts the HQL into SQL and executes it against
	 * the relational database.
	 *  
	 * The retrieved results are converted into a list which may not be completely loaded. If the retrieved results 
	 * are more than the maximum number of supported records as indicated by {@link #getMaxRecordsCount()} then the
	 * result set will be partially loaded. The client framework will execute a subsequent query (transparent to the
	 * client application) against the <code>ApplicationService</code> to load the remaining results in the chunk 
	 * no greater than value specified by {@link #getMaxRecordsCount()}.
	 * 
	 * @param targetClass
	 * @param objList
	 * @param queryOptions Specific Options to be used while processing this query.
	 * @return
	 * @throws ApplicationException
	 */
	@DataServiceLazyLoadable
	public <E> List<E> search(Class targetClass, List objList, QueryOptions queryOptions) throws ApplicationException;
	
	/**
	 * Retrieves the result from the data source using the Query by Example. The <code>targetClass</code> specifies 
	 * the object that the user intends to fetch after executing the query. The <code>targetClass</code> should be 
	 * same as the example object or associated object for the example object. The example query is converted into 
	 * the data source specific query language. For the Object Relational Mapping based persistence tier, the example 
	 * query structure is converted in the Hibernate Query Language (HQL). Hibernate converts the HQL into SQL and 
	 * executes it against the relational database.
	 *  
	 * The retrieved results are converted into a list which may not be completely loaded. If the retrieved results 
	 * are more than the maximum number of supported records as indicated by {@link #getMaxRecordsCount()} then the
	 * result set will be partially loaded. The client framework will execute a subsequent query (transparent to the
	 * client application) against the <code>ApplicationService</code> to load the remaining results in the chunk 
	 * no greater than value specified by {@link #getMaxRecordsCount()}.
	 * 
	 * @param targetClass
	 * @param obj
	 * @param queryOptions Specific Options to be used while processing this query.
	 * @return
	 * @throws ApplicationException
	 */
	@DataServiceLazyLoadable
	public <E> List<E> search(Class targetClass, Object obj, QueryOptions queryOptions) throws ApplicationException;
	
	/**
	 * Retrieves the result from the data source using a Nested Search Criteria. The <code>path</code> specifies 
	 * the list of objects (separated by commas), which should be used to reach the target object from the example objects 
	 * passed in the <code>objList</code> or associated object for the example object. The Nested Search Criteria 
	 * is converted into the data source specific query language. For the Object Relational Mapping based persistence 
	 * tier, the query structure is first converted into the Hibernate Query Language (HQL). Hibernate then converts the HQL into 
	 * SQL and executes it against the relational database.
	 *  
	 * The retrieved results are converted into a list which may not be completely loaded. If the retrieved results 
	 * are more than the maximum number of supported records as indicated by {@link #getMaxRecordsCount()} then the
	 * result set will be partially loaded. The client framework will execute a subsequent query (transparent to the
	 * client application) against the <code>ApplicationService</code> to load the remaining results in the chunk 
	 * no greater than value specified by {@link #getMaxRecordsCount()}.
	 * 
	 * @param path
	 * @param objList
	 * @param queryOptions Specific Options to be used while processing this query.
	 * @return
	 * @throws ApplicationException
	 */
	@DataServiceLazyLoadable
	public <E> List<E> search(String path, List objList, QueryOptions queryOptions) throws ApplicationException;
	
	/**
	 * Retrieves the result from the data source using the Nested Search Criteria. The <code>path</code> specifies 
	 * the list of objects (separated by commas) which should be used to reach the target object from the example object 
	 * passed as <code>obj</code>, or the associated object for the example object. Internally, the Nested Search Criteria 
	 * is converted into the data source specific query language. For the Object Relational Mapping based persistence 
	 * tier, the query structure is first converted into the Hibernate Query Language (HQL). Hibernate then converts the HQL into 
	 * SQL and executes it against the relational database.
	 *  
	 * The retrieved results are converted into a list which may not be completely loaded. If the retrieved results 
	 * are more than the maximum number of supported records as indicated by {@link #getMaxRecordsCount()} then the
	 * result set will be partially loaded. The client framework will execute a subsequent query (transparent to the
	 * client application) against the <code>ApplicationService</code> to load the remaining results in the chunk 
	 * no greater than value specified by {@link #getMaxRecordsCount()}.
	 * 
	 * @param path
	 * @param obj
	 * @param queryOptions Specific Options to be used while processing this query.
	 * @return
	 * @throws ApplicationException
	 */
	@DataServiceLazyLoadable
	public <E> List<E> search(String path, Object obj, QueryOptions queryOptions) throws ApplicationException;	

	/**
	 * Used by the infrastructure to get next chunk of records in the result set. Use this method in conjunction with the 
	 * {@link #getMaxRecordsCount()} to determine what should be the start of next chunk.
	 *  
	 * @param criteria
	 * @param firstRow
	 * @param targetClassName
	 * @return
	 * @throws ApplicationException
	 */
	@DataServiceLazyLoadable
	public <E> List<E> query(Object criteria, Integer firstRow, String targetClassName, QueryOptions queryOptions) throws ApplicationException;
	
	/**
	 * Returns the number of records that meet the search criteria. The method is used by the client framework to determine 
	 * the number of chunk of results. Use this method in conjunction with the {@link #getMaxRecordsCount()}
	 * 
	 * @param criteria
	 * @param targetClassName
	 * @param queryOptions Specific Options to be used while processing this query.
	 * @return
	 * @throws ApplicationException
	 */
	@DataServiceLazyLoadable
	public Integer getQueryRowCount(Object criteria, String targetClassName, QueryOptions queryOptions) throws ApplicationException;
	
	/**
	 * Retrieves an associated object for the example object specified by the <code>source</code> parameter.
	 * 
	 * @param source
	 * @param associationName
	 * @param queryOptions Specific Options to be used while processing this query.
	 * @return
	 * @throws ApplicationException
	 */
	@DataServiceLazyLoadable
	public <E> List<E> getAssociation(Object source, String associationName, QueryOptions queryOptions) throws ApplicationException;


	/**
	 * Retrieves the result from the data source using the caGrid's CQL query. The CQL query structure is converted into the
	 * data source specific query language. For the Object Relational Mapping based persistence tier, the CQL query
	 * structure is converted in the Hibernate Query Language (HQL). Hibernate converts the HQL into SQL and executes
	 * it against the relational database. 
	 * 
	 * The retrieved results are converted into a list which may not be completely loaded. If the retrieved results 
	 * are more than the maximum number of supported records as indicated by {@link #getMaxRecordsCount()} then the
	 * result set will be partially loaded. The client framework will execute a subsequent query (transparent to the
	 * client application) against the <code>ApplicationService</code> to load the remaining results in the chunk 
	 * no greater than value specified by {@link #getMaxRecordsCount()}.
	 * 
	 * @param cqlQuery
	 * @param queryOptions Specific Options to be used while processing this query.
	 * @return
	 * @throws ApplicationException
	 */
	@DataServiceLazyLoadable
	public <E> List<E> query(gov.nih.nci.cagrid.cqlquery.CQLQuery cqlQuery, QueryOptions queryOptions) throws ApplicationException;
	
    
}
