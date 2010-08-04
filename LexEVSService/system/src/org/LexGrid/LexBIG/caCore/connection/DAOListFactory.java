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
package org.LexGrid.LexBIG.caCore.connection;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.LexGrid.LexBIG.DataModel.Collections.CodingSchemeTagList;
import org.LexGrid.LexBIG.caCore.connection.orm.interceptors.EVSHibernateInterceptor;
import org.LexGrid.LexBIG.caCore.connection.orm.utils.DBConnector;
import org.LexGrid.LexBIG.caCore.dao.orm.LexEVSDAO;
import org.LexGrid.LexBIG.caCore.dao.orm.LexEVSDAOImpl;
import org.LexGrid.LexBIG.caCore.dao.orm.LexEVSDAO.DAOType;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.lexevs.locator.LexEvsServiceLocator;
import org.lexevs.registry.model.RegistryEntry;
import org.lexevs.registry.service.Registry.ResourceType;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

/**
 * Builds the List of DAOs associated with the underlying LexBIG installation. Also, a prefix-changing interceptor
 * is added to each DAO to dynamically change the table prefix (to enable single or multi-db LexBIG mode).
 * 
 * @author <a href="mailto:kevin.peterson@mayo.edu">Kevin Peterson</a>
 */
public class DAOListFactory {
	private static Logger log = Logger.getLogger(DAOListFactory.class.getName());
	
	private DBConnector connector;
	private Resource configLocation;
	private int resultCountPerQuery;
	List<LexEVSDAO> daoList;
	
	public void buildDAOs() throws Exception {	
		daoList = new ArrayList<LexEVSDAO>();	
		
		List<RegistryEntry> codingSchemeEntries = LexEvsServiceLocator.getInstance().getRegistry().getAllRegistryEntriesOfType(ResourceType.CODING_SCHEME);

		log.warn("Using a Single Pooled Datasource.");
		DataSource datasource = this.createDataSource();

		for(RegistryEntry entry : codingSchemeEntries){
			daoList.add(
					buildDAO(
							buildSessionFactoryBean(datasource), 
							entry, DAOType.CODING_SCHEME));
		}
	}

	protected DataSource createDataSource() {
		return LexEvsServiceLocator.getInstance().getLexEvsDatabaseOperations().getDataSource();
	}

	protected LocalSessionFactoryBean buildSessionFactoryBean(DataSource datasource){
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setConfigLocation(configLocation);
		sessionFactory.setDataSource(datasource);
		return sessionFactory;
	}
	
	private LexEVSDAO buildDAO(LocalSessionFactoryBean sessionFactory, RegistryEntry connection, DAOType type) throws Exception {	
		String defaultPrefix = LexEvsServiceLocator.getInstance().
			getLexEvsDatabaseOperations().
			getPrefixResolver().
			resolveDefaultPrefix();
		
		String uri = connection.getResourceUri();
		String version = connection.getResourceVersion();
		String prefix = defaultPrefix + connection.getPrefix();
			
		EVSHibernateInterceptor interceptor = new EVSHibernateInterceptor();
		interceptor.setPrefix(prefix);	
		
		//Set the CodingScheme Name on the Interceptor
		//Don't do this for History DAOs
		if(type.equals(DAOType.CODING_SCHEME)){
			String codingSchemeName = connector.getLocalDBNameForURIAndVersion(uri, version);
			interceptor.setCodingSchemeName(codingSchemeName);
		}
		
		sessionFactory.setEntityInterceptor(interceptor);	
		sessionFactory.afterPropertiesSet();	
		SessionFactory factory = (SessionFactory)sessionFactory.getObject();
			
		LexEVSDAOImpl dao = new LexEVSDAOImpl();	
		dao.setResultCountPerQuery(resultCountPerQuery);
		dao.setSessionFactory(factory);
		dao.setUrn(uri);
		dao.setVersion(version);		
		dao.setDaoType(type);
		
		//Get the Tag List for this CodingScheme - Not for History Connections, though.
		//History Connections don't have Tag Lists.
		if(type.equals(DAOType.CODING_SCHEME)){
			CodingSchemeTagList cstl = connector.getTagList(uri, version);
			dao.setTagList(cstl);
		}
		
		return dao;
	}
	
	public List<LexEVSDAO> getDaoList(){
		return this.daoList;
	}
	
	public Resource getConfigLocation() {
		return configLocation;
	}

	public void setConfigLocation(Resource configLocation) {
		this.configLocation = configLocation;
	}

	public DBConnector getConnector() {
		return connector;
	}
	public void setConnector(DBConnector connector) {
		this.connector = connector;
	}

	public int getResultCountPerQuery() {
		return resultCountPerQuery;
	}

	public void setResultCountPerQuery(int resultCountPerQuery) {
		this.resultCountPerQuery = resultCountPerQuery;
	}
}

