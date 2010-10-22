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
package org.LexGrid.LexBIG.caCore.test.webservice;

import java.net.URL;

import org.LexGrid.LexBIG.caCore.webservice.client.LexEVSWSQueryImpl;
import org.LexGrid.LexBIG.caCore.webservice.client.LexEVSWSQueryImplServiceLocator;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.ws.codingSchemes.CodingScheme;
import org.LexGrid.ws.commonTypes.EntityDescription;
import org.LexGrid.ws.concepts.Entity;
import org.LexGrid.ws.naming.SupportedCodingScheme;

public class WebQueryTest extends ServiceTestCase {
	String testId = "LexEVS DataService Web Service Test (SOAP)";

	@Override
	protected String getTestID() {	
		return testId;		
	}

	public void testConnectToWebService() throws Exception {
		LexEVSWSQueryImplServiceLocator locator = new LexEVSWSQueryImplServiceLocator();
		LexEVSWSQueryImpl query = locator.getlexevsapi60Service(new URL(ServiceTestCase.endpointUrl));   
	}

	public void testQueryObject() throws Exception {	
		LexEVSWSQueryImplServiceLocator locator = new LexEVSWSQueryImplServiceLocator();
		LexEVSWSQueryImpl query = locator.getlexevsapi60Service(new URL(ServiceTestCase.endpointUrl));
		
		CodingScheme cs = new CodingScheme();
		Object[] results = query.queryObject(org.LexGrid.naming.SupportedCodingScheme.class.getName(), cs);
		
		assertTrue(results != null);
		assertTrue(results.length > 0);
	}

	public void testGetAssociation() throws Exception {	
		LexEVSWSQueryImplServiceLocator locator = new LexEVSWSQueryImplServiceLocator();
		LexEVSWSQueryImpl query = locator.getlexevsapi60Service(new URL(ServiceTestCase.endpointUrl));
		
		Entity concept = new Entity();
		concept.setEntityCodeNamespace(ServiceTestCase.SNOMED_SCHEME);
		concept.setEntityCode("29506000");
		
		Object[] results = query.getAssociation(concept, "_entityDescription", 0);
		
		assertTrue(results != null);
		assertTrue(results.length == 1);	
		assertTrue(results[0] instanceof EntityDescription);
		
		EntityDescription ed = (EntityDescription)results[0];
		assertTrue(ed.getContent().equals("Boxing"));		
	}
}
