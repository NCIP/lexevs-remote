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
import org.LexGrid.codingSchemes.CodingScheme;
import org.LexGrid.commonTypes.EntityDescription;
import org.LexGrid.concepts.Entity;


public class WebQueryTest extends ServiceTestCase {
	String testId = "LexEVS DataService Web Service Test (SOAP)";

	@Override
	protected String getTestID() {	
		return testId;		
	}

	public void testConnectToWebService() throws Exception {
		LexEVSWSQueryImplServiceLocator locator = new LexEVSWSQueryImplServiceLocator();
		LexEVSWSQueryImpl query = locator.getlexevsapi61Service(new URL(ServiceTestCase.endpointUrl));   
	}

	public void testQueryObject() throws Exception {	
		LexEVSWSQueryImplServiceLocator locator = new LexEVSWSQueryImplServiceLocator();
		LexEVSWSQueryImpl query = locator.getlexevsapi61Service(new URL(ServiceTestCase.endpointUrl));
		
		CodingScheme cs = new CodingScheme();
		Object[] results = query.queryObject(org.LexGrid.naming.SupportedCodingScheme.class.getName(), cs);
		
		assertTrue(results != null);
		assertTrue(results.length > 0);
	}

}
