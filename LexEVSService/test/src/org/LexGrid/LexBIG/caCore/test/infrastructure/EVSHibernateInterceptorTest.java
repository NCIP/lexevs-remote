/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.test.infrastructure;

import org.LexGrid.LexBIG.caCore.connection.orm.interceptors.EVSHibernateInterceptor;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

public class EVSHibernateInterceptorTest extends ServiceTestCase
{
	private final String test_id = "Get Associations Tests";
	
	String searchCodingScheme;
	String searchPrefix;
	
	private EVSHibernateInterceptor interceptor;
	private String noChangeSQL;
	private String prefixChangeSQL;
	private String codingSchemeChangeSQL;
	private String csAndEntityNsChangeSQL;
	private String existingNs;
	
	
	@Override
	protected String getTestID() {
		return test_id;
		
	}
	
	public void setUp(){
		searchCodingScheme = "TestCodingScheme";
		searchPrefix = "myPrefix";
		interceptor = new EVSHibernateInterceptor();
		interceptor.setCodingSchemeName(searchCodingScheme);
		interceptor.setPrefix(searchPrefix);
				
		noChangeSQL = "SELECT * from dog";
		prefixChangeSQL = "SELECT * from @PREFIX@dog";
		codingSchemeChangeSQL = "SELECT * from dog WHERE codingSchemeName = @CODING_SCHEME_NAME@";
		csAndEntityNsChangeSQL = "SELECT * from dog WHERE codingSchemeName = @CODING_SCHEME_NAME@"
			+ " AND entityCodeNamespace @LIKE_OR_EQUALS@ @ENTITY_CODE_NAMESPACE@";
		existingNs = "SELECT * from dog WHERE entityCodeNamespace=? and codingSchemeName = @CODING_SCHEME_NAME@"
			+ " AND entityCodeNamespace @LIKE_OR_EQUALS@ @ENTITY_CODE_NAMESPACE@";
	}
	
	public void testNoChangeSQLQuery() throws Exception {
		assertTrue(noChangeSQL.
				equals(interceptor.onPrepareStatement(noChangeSQL)));
	}
	
	public void testChangePrefixes() throws Exception {
		String changedSQL = interceptor.onPrepareStatement(prefixChangeSQL);
		assertTrue(changedSQL.equals("SELECT * from myPrefixdog"));
	}
	
	public void testChangeCodingScheme() throws Exception {
		String changedSQL = interceptor.onPrepareStatement(codingSchemeChangeSQL);
		assertTrue(changedSQL.
				equals("SELECT * from dog WHERE codingSchemeName = TestCodingScheme"));
	}
}