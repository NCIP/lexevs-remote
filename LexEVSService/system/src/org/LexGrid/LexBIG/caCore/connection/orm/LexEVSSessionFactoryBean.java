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
package org.LexGrid.LexBIG.caCore.connection.orm;

import gov.nih.nci.system.dao.orm.HibernateConfigurationHolder;
import gov.nih.nci.system.security.helper.SecurityInitializationHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.LexGrid.LexBIG.Impl.helpers.SQLConnectionInfo;
import org.LexGrid.LexBIG.caCore.connection.orm.utils.DBConnector;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

/**
 * LexEVS implementation of a LocalSessionFactoryBean. This will be dynamically injected with
 * LexBIG connection info to build DAOs for the various CodingSchemes loaded into the local
 * LexBIG installation.
 * 
 * @author <a href="mailto:kevin.peterson@mayo.edu">Kevin Peterson</a>
 *
 */
public class LexEVSSessionFactoryBean extends LocalSessionFactoryBean {
	
	public static HashMap<String,String> ShardPrefixToFactoryNameMap;
	
	private HibernateConfigurationHolder configHolder;
	private SecurityInitializationHelper securityHelper;
	
	private SQLConnectionInfo connection;
	
	/* (non-Javadoc)
	 * @see org.springframework.orm.hibernate3.LocalSessionFactoryBean#newSessionFactory(org.hibernate.cfg.Configuration)
	 */
	protected SessionFactory newSessionFactory(Configuration config) throws HibernateException {
		config.setProperty(ConnectionConstants.CONNECTION_URL, connection.server);
		config.setProperty(ConnectionConstants.CONNECTION_USERNAME, connection.username);
		config.setProperty(ConnectionConstants.CONNECTION_PASSWORD, connection.password);				
		config.setProperty(ConnectionConstants.DRIVER_CLASS, connection.driver);
		
		return super.newSessionFactory(config);	
	}
	
	public HibernateConfigurationHolder getConfigHolder() {
		return configHolder;
	}
	public void setConfigHolder(HibernateConfigurationHolder configHolder) {
		this.configHolder = configHolder;
	}

	public SecurityInitializationHelper getSecurityHelper() {
		return securityHelper;
	}

	public void setSecurityHelper(SecurityInitializationHelper securityHelper) {
		this.securityHelper = securityHelper;
	}

	public SQLConnectionInfo getConnection() {
		return connection;
	}

	public void setConnection(SQLConnectionInfo connection) {
		this.connection = connection;
	}
}
