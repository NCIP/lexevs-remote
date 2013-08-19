/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
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
