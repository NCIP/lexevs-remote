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
package org.LexGrid.LexBIG.caCore.dao.orm;

import gov.nih.nci.system.dao.DAOException;
import gov.nih.nci.system.dao.Request;
import gov.nih.nci.system.dao.Response;
import gov.nih.nci.system.dao.orm.ORMDAOImpl;
import gov.nih.nci.system.dao.orm.translator.NestedCriteria2HQL;
import gov.nih.nci.system.query.cql.CQLQuery;
import gov.nih.nci.system.query.hibernate.HQLCriteria;
import gov.nih.nci.system.query.nestedcriteria.NestedCriteriaPath;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.LexGrid.LexBIG.DataModel.Collections.CodingSchemeTagList;
import org.apache.commons.lang.SerializationUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.JDBCException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.util.Assert;

/**

 *
 */
public class LexEVSDAOImpl extends ORMDAOImpl implements LexEVSDAO
{
	private Interceptor tablePrefixInterceptor = null;
	
	private String uri;
	private String version;
	private CodingSchemeTagList tagList;
	private DAOType daoType;
	
	public List<String> getAllClassNames(){	    	
		List<String> allClassNames = new ArrayList<String>();
		Map allClassMetadata = getSessionFactory().getAllClassMetadata();

		for (Iterator iter = allClassMetadata.keySet().iterator() ; iter.hasNext(); ){		
			String className = (String)iter.next();
			if(className.contains("$")){
				className = className.substring(0, className.indexOf("$"));
			}

			if(!allClassNames.contains(className)){
				allClassNames.add(className);
			}
		}    	
		return allClassNames;
	}
	
	public Response query(Request request) throws DAOException {
		return query(request, true, this.getResultCountPerQuery());
	}
	
	public Response query(Request request, boolean lazyLoad, int maxResults) throws DAOException 
	{
		Object obj = request.getRequest();
		try
		{
			log.debug("****** obj: " + obj.getClass());
			if (obj instanceof DetachedCriteria) 				
				return query(request, (DetachedCriteria) obj, lazyLoad, maxResults); 				
			else if (obj instanceof HQLCriteria)
				return query(request, (HQLCriteria) obj, lazyLoad, maxResults);		
			else
				throw new DAOException("Can not determine type of the query");
		} catch (JDBCException ex){
			log.error("JDBC Exception in ORMDAOImpl ", ex);
			throw new DAOException("JDBC Exception in ORMDAOImpl ", ex);
		} catch(org.hibernate.HibernateException hbmEx)	{
			log.error(hbmEx.getMessage());
			throw new DAOException("Hibernate problem ", hbmEx);
		} catch(Exception e) {
			log.error("Exception ", e);
			throw new DAOException("Exception in ORMDAOImpl ", e);
		}
	}
		
	protected Response query(Request request, DetachedCriteria obj, boolean lazyLoad, int maxResult) throws Exception
	{
		Response rsp = new Response();
		log.info("Detached Criteria Query :"+obj.toString());
		
	    if(request.getIsCount() != null && request.getIsCount())
	    {
	    	obj = (DetachedCriteria)SerializationUtils.clone(obj);
	    	
	    	HibernateCallback callBack = getExecuteCountCriteriaHibernateCallback(obj);
	        Integer rowCount = (Integer)getHibernateTemplate().execute(callBack);
			log.debug("DetachedCriteria ORMDAOImpl ===== count = " + rowCount);
			rsp.setRowCount(rowCount);
	    }
	    else 
	    {
	    	HibernateCallback callBack = getExecuteFindQueryCriteriaHibernateCallback(obj, lazyLoad, 
	    			request.getFirstRow() == null?-1:request.getFirstRow(), 
	    					maxResult > 0 ? maxResult:this.getResultCountPerQuery());
	    	List rs = (List)getHibernateTemplate().execute(callBack);
	    	rsp.setRowCount(rs.size());
	        rsp.setResponse(rs);
	    }
	    
		return rsp;
	}	
	
	/*
	 * Override because I want to reset the Original Detached Criteria to not include the Row Count Projection.
	 * 
	 * Used http://forum.hibernate.org/viewtopic.php?t=974802 as a guide.
	 * 
	 * (non-Javadoc)
	 * @see gov.nih.nci.system.dao.orm.ORMDAOImpl#getExecuteCountCriteriaHibernateCallback(org.hibernate.criterion.DetachedCriteria)
	 */
	protected HibernateCallback getExecuteCountCriteriaHibernateCallback(final DetachedCriteria criteria)
	{
		HibernateCallback callBack = new HibernateCallback(){

			public Object doInHibernate(Session session) throws HibernateException, SQLException {

				Criteria exeCriteria = criteria.getExecutableCriteria(session);
				
				// Cast to CriteriaImpl to get access to the Projection and ResultTransformer
			    CriteriaImpl cImpl = (CriteriaImpl) exeCriteria;
			    cImpl.setFirstResult(0);
			    cImpl.setFetchSize(0);

			    int totalElements = 0;
			    exeCriteria.setProjection(Projections.rowCount());
			    List resultList = exeCriteria.list();
			    for(Object object : resultList){
			    	Integer countResult = (Integer)object;
			    	totalElements += countResult;
			    }	

				return totalElements;
			}
		};
		return callBack;
	}	
	
	protected HibernateCallback getExecuteFindQueryCriteriaHibernateCallback(final DetachedCriteria criteria, final boolean lazyLoad, final int firstResult, final int maxResults)
	{
		HibernateCallback callBack = new HibernateCallback(){
			
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria query = criteria.getExecutableCriteria(session);
				query.setFirstResult(firstResult);				    		
	
				query.setMaxResults(maxResults);
					
				List returnList = query.list();				
				if(!lazyLoad){
					LexEVSDAOImpl.initializeAll(returnList);
				}			
				return returnList;
			}		
		};
		return callBack;
	}
	
	//if (obj instanceof HQLCriteria)
	protected Response query(Request request, HQLCriteria hqlCriteria, boolean lazyLoad, int maxResult) throws Exception
	{
		if(request.getIsCount() != null && request.getIsCount())
		{
			String countQ = hqlCriteria.getCountHqlString();
			if(countQ == null)
				countQ = getCountQuery(hqlCriteria.getHqlString());
			log.info("HQL Query :"+countQ);
			Response rsp = new Response();
	    	HibernateCallback callBack = getExecuteCountQueryHibernateCallback(countQ,hqlCriteria.getParameters());
			Integer rowCount = Integer.parseInt(getHibernateTemplate().execute(callBack)+"");
			log.debug("HQL Query : count = " + rowCount);		
			rsp.setRowCount(rowCount);
			return rsp;
		}
		else 
		{
			log.info("HQL Query :"+hqlCriteria.getHqlString());
			Response rsp = new Response();
	    	HibernateCallback callBack = getExecuteFindQueryHibernateCallback(hqlCriteria.getHqlString(),hqlCriteria.getParameters(), 
	    			lazyLoad, request.getFirstRow() == null?-1:request.getFirstRow(),
	    					maxResult > 0 ? maxResult:this.getResultCountPerQuery());
	    	List rs = (List)getHibernateTemplate().execute(callBack);
			
	    	rsp.setRowCount(rs.size());
	    	rsp.setResponse(rs);
			return rsp;
		}
	}
	
	protected HibernateCallback getExecuteFindQueryHibernateCallback(final String hql, final List params, final int firstResult, final int maxResult){
		return getExecuteFindQueryHibernateCallback(hql, params, false, firstResult, maxResult);
	}
	
	protected HibernateCallback getExecuteFindQueryHibernateCallback(final String hql, final List params, final boolean lazyLoad, final int firstResult, final int maxResult)
	{
		HibernateCallback callBack = new HibernateCallback(){

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setFirstResult(firstResult);				    		
				query.setMaxResults(maxResult);
				
				int count = 0;
				if(params!=null)
					for(Object param:params)
						query.setParameter(count++, param);
							
				List rs = (List)query.list();
				if(!lazyLoad){
					LexEVSDAOImpl.initializeAll(rs);
				}			
							
				return rs;
			}
		};
		return callBack;
	}
	
	protected static void initializeAll(List<Object> list){
		for(Object obj : list){
			Class resultClass = obj.getClass(); 
			while(resultClass != null){
				Field[] fields  = resultClass.getDeclaredFields();
				for(int f=0;f<fields.length; f++){
					fields[f].setAccessible(true);
					try {
						if(!Hibernate.isInitialized(fields[f].get(obj))){
							Hibernate.initialize(fields[f].get(obj));
						}
					} catch (Exception e) {
						throw new HibernateException("Error fully initializing.", e);
					}
				}
				resultClass = resultClass.getSuperclass();
			}
		}
	}
	
	private String getCountQuery(String hql)
	{
		return NestedCriteria2HQL.getCountQuery(hql);
	}
	
	public Interceptor getTablePrefixInterceptor() {
		return tablePrefixInterceptor;
	}

	public void setTablePrefixInterceptor(Interceptor tablePrefixInterceptor) {
		this.tablePrefixInterceptor = tablePrefixInterceptor;
	}	
	
	public HibernateTemplate[] getHibernateAllTemplates(){
		return null;
	}

	public String getUri() {
		return uri;
	}

	public void setUrn(String uri) {
		this.uri = uri;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public CodingSchemeTagList getTagList() {
		return tagList;
	}

	public void setTagList(CodingSchemeTagList tagList) {
		this.tagList = tagList;
	}

	public DAOType getDaoType() {
		return daoType;
	}

	public void setDaoType(DAOType daoType) {
		this.daoType = daoType;
	}
}