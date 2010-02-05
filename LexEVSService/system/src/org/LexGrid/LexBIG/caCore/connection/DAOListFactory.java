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

import gov.nih.nci.system.dao.DAO;
import gov.nih.nci.system.dao.DAOException;
import gov.nih.nci.system.dao.orm.HibernateConfigurationHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.LexGrid.LexBIG.DataModel.Collections.CodingSchemeTagList;
import org.LexGrid.LexBIG.Impl.dataAccess.ResourceManager;
import org.LexGrid.LexBIG.Impl.helpers.SQLConnectionInfo;
import org.LexGrid.LexBIG.caCore.connection.orm.ConnectionConstants;
import org.LexGrid.LexBIG.caCore.connection.orm.LexEVSSessionFactoryBean;
import org.LexGrid.LexBIG.caCore.connection.orm.interceptors.EVSHibernateInterceptor;
import org.LexGrid.LexBIG.caCore.connection.orm.utils.DBConnector;
import org.LexGrid.LexBIG.caCore.connection.orm.utils.LexEVSClassCache;
import org.LexGrid.LexBIG.caCore.dao.orm.LexEVSDAO;
import org.LexGrid.LexBIG.caCore.dao.orm.LexEVSDAOImpl;
import org.LexGrid.LexBIG.caCore.dao.orm.LexEVSDAO.DAOType;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.mchange.v2.c3p0.ComboPooledDataSource;

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
		
		//Get the CodingScheme DAOs
		SQLConnectionInfo[] csConnections = connector.getCodingSchemeConnections();
		List<SQLConnectionInfo> csConList = Arrays.asList(csConnections);
		
		//Get the History DAOs
		SQLConnectionInfo[] historyConnections = connector.getHistoryConnections();
		List<SQLConnectionInfo> histConList = Arrays.asList(historyConnections);
		
		List<SQLConnectionInfo> allConnections = new ArrayList<SQLConnectionInfo>();
		allConnections.addAll(csConList);
		allConnections.addAll(histConList);
		
		boolean useOneDatasource = this.canUseSingleDatasource(allConnections);
		
		if(useOneDatasource){
			log.warn("Using a Single Pooled Datasource.");
			DataSource datasource = this.createDataSource(csConnections[0]);
			for (int i = 0; i < csConnections.length; i++) {
				daoList.add(
						buildDAO(
								buildSessionFactoryBean(datasource), 
								csConnections[i], DAOType.CODING_SCHEME));
			}	

			for (int i = 0; i < historyConnections.length; i++) {
				daoList.add(
						buildDAO(
								buildSessionFactoryBean(datasource), 
								historyConnections[i], DAOType.HISTORY));
			}	
		} else {
			log.warn("Using ONE Datasource per Ontology. This uses alot of database connections -- " +
					"please configure your LexEVS System for Single Database mode.");
			for (int i = 0; i < csConnections.length; i++) {
				daoList.add(
						buildDAO(
								buildSessionFactoryBean(csConnections[i]), 
								csConnections[i], DAOType.CODING_SCHEME));
			}	

			for (int i = 0; i < historyConnections.length; i++) {
				daoList.add(
						buildDAO(
								buildSessionFactoryBean(historyConnections[i]), 
								historyConnections[i], DAOType.HISTORY));
			}	
		}
	}
	
	protected LocalSessionFactoryBean buildSessionFactoryBean(DataSource datasource){
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setConfigLocation(configLocation);
		sessionFactory.setDataSource(datasource);
		return sessionFactory;
	}
	
	protected LocalSessionFactoryBean buildSessionFactoryBean(SQLConnectionInfo connection){
		LexEVSSessionFactoryBean sessionFactory = new LexEVSSessionFactoryBean();	
		sessionFactory.setConfigLocation(configLocation);	
		sessionFactory.setConnection(connection);
		return sessionFactory;
	}
	
	private LexEVSDAO buildDAO(LocalSessionFactoryBean sessionFactory, SQLConnectionInfo connection, DAOType type) throws Exception {	
		String uri = connection.urn;
		String version = connection.version;
		String prefix = connection.prefix;
			
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
	
	protected boolean canUseSingleDatasource(List<SQLConnectionInfo> connections){
		Set<String> urls = new HashSet<String>();
		for(SQLConnectionInfo conInfo : connections){
			if(StringUtils.isNotBlank(conInfo.dbName)){
				return false;
			}
			urls.add(conInfo.server);
		}
		return urls.size() == 1;
	}
	
	protected DataSource createDataSource(SQLConnectionInfo connection) throws Exception {	
		log.warn("Creating common datasource.");
		ComboPooledDataSource cpds = new ComboPooledDataSource(); 
		cpds.setDriverClass(connection.driver); 
		cpds.setJdbcUrl(connection.server); 
		cpds.setUser(connection.username); 
		cpds.setPassword(connection.password); 
		cpds.setAcquireIncrement(1); 
		cpds.setMaxPoolSize(10); 
		cpds.setIdleConnectionTestPeriod(5000);
		cpds.setMaxIdleTime(5000);
		return cpds;
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

