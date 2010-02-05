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
package org.LexGrid.LexBIG.caCore.connection.orm.interceptors;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.hibernate.EmptyInterceptor;
import org.hibernate.EntityMode;
import org.hibernate.Session;

/**
 * Hibernate Interceptor used to modify the SQL query sent to the database. 
 * This interceptor changes the prefix, and also places some extra constraints
 * on the query to ensure that critical queries always use DB table indexes.
 * 
 * @author <a href="mailto:kevin.peterson@mayo.edu">Kevin Peterson</a>
 */
public class EVSHibernateInterceptor extends EmptyInterceptor {  

	private static Logger log = Logger.getLogger(EVSHibernateInterceptor.class.getName());
	protected Session session;
	private String prefix;
	private String codingSchemeName;
	
	private static final String PREFIX_PLACEHOLDER = "@PREFIX@";
	private static final String CODING_SCHEME_NAME_PLACEHOLDER = "@CODING_SCHEME_NAME@";
	private static final String ENTITY_CODE_NAMESPACE_PLACEHOLDER = "@ENTITY_CODE_NAMESPACE@";
	private static final String LIKE_OR_EQUALS_PLACEHOLDER = "@LIKE_OR_EQUALS@";
	private static final String WILDCARD = "%";
	private static final String LIKE = "LIKE";
	private static final String EQUALS = "=";
	
	
	/* (non-Javadoc)
	 * @see org.hibernate.EmptyInterceptor#onPrepareStatement(java.lang.String)
	 */
	public String onPrepareStatement(String sql) { 	
		log.info("Adjusting table names to prefix: " + prefix);
		sql = sql.replaceAll(PREFIX_PLACEHOLDER, prefix);
		
		log.info("Adjusting for Coding Scheme Name'" + codingSchemeName);
		sql = sql.replaceAll(CODING_SCHEME_NAME_PLACEHOLDER, codingSchemeName);
	
		return sql;			
	}
		
	public String getCodingSchemeName() {
		return codingSchemeName;
	}

	public void setCodingSchemeName(String codingSchemeName) {
		this.codingSchemeName = codingSchemeName;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}  