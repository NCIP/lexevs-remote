/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.test.queryOptions;

import java.util.List;

import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.caCore.applicationservice.QueryOptions;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSDataService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.codingSchemes.CodingScheme;

public class QueryOptionsCodingSchemeTest extends ServiceTestCase {
	String testId = "LexEVS DataService Web Service Test (SOAP)";
	
	protected String getTestID() {	
		return testId;
	}

	public void testQueryOptionGoodCodingSchemeRestriction() throws Exception {
		QueryOptions options = new QueryOptions();
		options.setCodingScheme(ServiceTestCase.GO_SCHEME);
		CodingSchemeVersionOrTag csvt = new CodingSchemeVersionOrTag();
		csvt.setVersion(ServiceTestCase.GO_VERSION);
		options.setCodingSchemeVersionOrTag(csvt);
		
		CodingScheme cs = new CodingScheme();
		LexEVSDataService svc = LexEVSServiceHolder.instance().getLexEVSAppService();
		List<CodingScheme> results = svc.search(CodingScheme.class, cs, options);
		assertTrue(results.size() == 1);
		assertTrue(results.get(0).getCodingSchemeName()
				.equals(ServiceTestCase.GO_SCHEME));
	}
	
	public void testQueryOptionBadCodingSchemeRestriction() {
		QueryOptions options = new QueryOptions();
		options.setCodingScheme("INVALID_CODING_SCHEME");
		CodingScheme cs = new CodingScheme();
		LexEVSDataService svc = LexEVSServiceHolder.instance().getLexEVSAppService();
		try{
			List<CodingScheme> results = svc.search(CodingScheme.class, cs, options);
			//if this resolves, fail
			fail("Should not resolve with an invalid QueryOption.");
		} catch (Exception e){
			//this is good -- should throw an exception.
		}
	}
}

