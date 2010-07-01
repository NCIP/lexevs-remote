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
package org.LexGrid.LexBIG.caCore.test.queryOptions;

import java.util.List;

import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.caCore.applicationservice.QueryOptions;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSDataService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.codingSchemes.CodingScheme;

public class QueryOptionsVersionTest extends ServiceTestCase {
	String testId = "LexEVS DataService QueryOptions Tests";
	
	protected String getTestID() {	
		return testId;
	}

	public void testQueryOptionGoodVersionRestriction() throws Exception {
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
		assertTrue(results.get(0).getRepresentsVersion()
				.equals(ServiceTestCase.GO_VERSION));
	}
	
	public void testQueryOptionBadVersionRestriction() throws Exception {
		QueryOptions options = new QueryOptions();
		options.setCodingScheme(ServiceTestCase.GO_SCHEME);
		
		CodingSchemeVersionOrTag csvt = new CodingSchemeVersionOrTag();
		csvt.setVersion("INVALID_VERSION");
		
		options.setCodingSchemeVersionOrTag(csvt);
		
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

