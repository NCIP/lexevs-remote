/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
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