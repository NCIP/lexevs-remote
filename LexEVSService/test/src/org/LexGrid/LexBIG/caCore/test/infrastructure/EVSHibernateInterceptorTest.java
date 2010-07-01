/*
 * Copyright: (c) 2004-2006 Mayo Foundation for Medical Education and
 * Research (MFMER).  All rights reserved.  MAYO, MAYO CLINIC, and the
 * triple-shield Mayo logo are trademarks and service marks of MFMER.
 *
 * Except as contained in the copyright notice above, the trade names, 
 * trademarks, service marks, or product names of the copyright holder shall
 * not be used in advertising, promotion or otherwise in connection with
 * this Software without prior written authorization of the copyright holder.
 * 
 * Licensed under the Eclipse Public License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 * 
 * 		http://www.eclipse.org/legal/epl-v10.html
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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