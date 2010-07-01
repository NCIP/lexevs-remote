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
package org.LexGrid.LexBIG.caCore.test.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;

import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

public class RESTTest extends ServiceTestCase {
	String testId = "LexEVS DataService Web Service Test (SOAP)";

	String serviceEndPoint;
	
	@Override
	protected String getTestID() {	
		return testId;
	}
	
	public void setUp(){
		String url = ServiceTestCase.serviceUrl;
		serviceEndPoint = url + "/";
	}
	
	public void testConnectToRESTService() throws Exception {	
		String result = callRestfulService("GetHTML?query=org.LexGrid.codingSchemes.CodingScheme&org.LexGrid.codingSchemes.CodingScheme");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
	}

	public void testConnectToRESTServiceXML() throws Exception {	
		String result = callRestfulService("GetXML?query=org.LexGrid.codingSchemes.CodingScheme&org.LexGrid.codingSchemes.CodingScheme");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
	}
	
	public void testConnectToRESTServiceHTML() throws Exception {	
		String result = callRestfulService("GetHTML?query=org.LexGrid.codingSchemes.CodingScheme&org.LexGrid.codingSchemes.CodingScheme");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
	}
	
	public void testRESTXMLSearch() throws Exception {	
		String result = callRestfulService("GetXML?query=org.LexGrid.codingSchemes.CodingScheme&org.LexGrid.codingSchemes.CodingScheme");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains(ServiceTestCase.SNOMED_SCHEME));
	}
	
	public void testRESTHTMLSearch() throws Exception {	
		String result = callRestfulService("GetHTML?query=org.LexGrid.codingSchemes.CodingScheme&org.LexGrid.codingSchemes.CodingScheme");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains(ServiceTestCase.SNOMED_SCHEME));
	}
	public void testRESTXMLSearchByCriteria() throws Exception {	
		String result = callRestfulService("GetXML?query=org.LexGrid.concepts.Concept&org.LexGrid.concepts.Concept[@_entityCode=29506000]");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains("29506000"));
	}
	
	public void testRESTHTMLSearchByCriteria() throws Exception {	
		String result = callRestfulService("GetHTML?query=org.LexGrid.concepts.Concept&org.LexGrid.concepts.Concept[@_entityCode=29506000]");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains("29506000"));
	}
	
	public void testRESTXMLSearchByPath() throws Exception {	
		String result = callRestfulService("GetXML?query=org.LexGrid.concepts.Concept,org.LexGrid.commonTypes.EntityDescription&org.LexGrid.commonTypes.EntityDescription[@_content=Boxing]");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains("29506000"));
	}
	
	public void testRESTHTMLSearchByPath() throws Exception {	
		String result = callRestfulService("GetHTML?query=org.LexGrid.concepts.Concept,org.LexGrid.commonTypes.EntityDescription&org.LexGrid.commonTypes.EntityDescription[@_content=Boxing]");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains("29506000"));
	}
	
	public void testRESTXMLSearchByRoleName() throws Exception {	
		String result = callRestfulService("GetXML?query=org.LexGrid.commonTypes.EntityDescription&org.LexGrid.concepts.Concept[@_entityCode=29506000]&roleName=_entityDescription");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains("Boxing"));
	}
	
	public void testRESTHTMLSearchByRoleName() throws Exception {	
		String result = callRestfulService("GetHTML?query=org.LexGrid.commonTypes.EntityDescription&org.LexGrid.concepts.Concept[@_entityCode=29506000]&roleName=_entityDescription");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains("Boxing"));
	}
	
	public void testRESTXMLSearchByMultipeCriteria() throws Exception {	
		String result = callRestfulService("GetXML?query=org.LexGrid.concepts.Concept&org.LexGrid.concepts.Concept[@_entityCode=29506000][@_entityCodeNamespace=SNOMED%20Clinical%20Terms]");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains("29506000"));
	}
	
	public void testRESTHTMLSearchByMultipeCriteria() throws Exception {	
		String result = callRestfulService("GetHTML?query=org.LexGrid.concepts.Concept&org.LexGrid.concepts.Concept[@_entityCode=29506000][@_entityCodeNamespace=SNOMED%20Clinical%20Terms]");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains("29506000"));
	}
	
	public void testRESTXMLInvalidURLParenthesis() throws Exception {	
		String result = callRestfulService("GetXML?query=org.LexGrid.concepts.Concept&org.LexGrid.concepts.Concept[@_entityCode=29506000][@_entityCodeNamespace=SNOMED%20Clinical%20Terms]]");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains("Invalid format: '[' parenthesis does not match number of ']' parenthesis"));
	}
	
	public void testRESTNonFullyQualifiedClassNamesPath() throws Exception {	
		String result = callRestfulService("GetHTML?query=Concept,EntityDescription&EntityDescription[@_content=Boxing]");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains("29506000"));
	}
	
	public void testRESTNonFullyQualifiedClassNamesCriteria() throws Exception {	
		String result = callRestfulService("GetHTML?query=Concept&Concept[@_entityCode=29506000]");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains("29506000"));
	}
	
	public void testRESTNonFullyQualifiedClassNamesRoleName() throws Exception {	
		String result = callRestfulService("GetHTML?query=EntityDescription&Concept[@_entityCode=29506000]&roleName=_entityDescription");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains("Boxing"));
	}
	
	public void testRESTHTMLInvalidURLParenthesis() throws Exception {	
		String result = callRestfulService("GetHTML?query=org.LexGrid.concepts.Concept&org.LexGrid.concepts.Concept[@_entityCode=29506000][@_entityCodeNamespace=SNOMED%20Clinical%20Terms]]");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains("Invalid format: '[' parenthesis does not match number of ']' parenthesis"));
	}
	
	public void testRESTXMLInvalidCriteriaObject() throws Exception {	
		String result = callRestfulService("GetXML?query=org.LexGrid.concepts.Concept&org.LexGrid.concepts.ConceptINVALID[@_entityCode=29506000]");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains("org.LexGrid.concepts.ConceptINVALID is an invalid query object. Check your spelling and make sure this is a valid object in this system."));
	}
	
	public void testRESTHTMLInvalidCriteriaObject() throws Exception {	
		String result = callRestfulService("GetHTML?query=org.LexGrid.concepts.Concept&org.LexGrid.concepts.ConceptINVALID[@_entityCode=29506000][@_entityCodeNamespace=SNOMED%20Clinical%20Terms]");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains("org.LexGrid.concepts.ConceptINVALID is an invalid query object. Check your spelling and make sure this is a valid object in this system."));
	}
	
	public void testRESTXMLInvalidAttribute() throws Exception {	
		String result = callRestfulService("GetXML?query=org.LexGrid.concepts.Concept&org.LexGrid.concepts.Concept[@_entityCodeINVALID=29506000]");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains("The String of Attributes passed in: [@_entityCodeINVALID=29506000] is invalid"));
		assertTrue(result.contains("Caused by: Invalid field name - _entityCodeINVALID"));
	}

	public void testRESTHTMLInvalidAttribute() throws Exception {	
		String result = callRestfulService("GetHTML?query=org.LexGrid.concepts.Concept&org.LexGrid.concepts.Concept[@_entityCodeINVALID=29506000]");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains("The String of Attributes passed in: [@_entityCodeINVALID=29506000] is invalid"));
		assertTrue(result.contains("Caused by: Invalid field name - _entityCodeINVALID"));
	}
	
	public void testRESTXMLWithCodingSchemeParameter() throws Exception {	
		String result = callRestfulService("GetXML?query=org.LexGrid.codingSchemes.CodingScheme&org.LexGrid.codingSchemes.CodingScheme&codingSchemeName=" + ServiceTestCase.THES_SCHEME  + "&codingSchemeVersion=" + ServiceTestCase.THES_VERSION);
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains(ServiceTestCase.THES_SCHEME));
		
		//Make sure a second Coding Scheme wasn't returned
		assertFalse(result.contains("2.Result Class Name:"));
	}
	
	public void testRESTHTMLWithCodingSchemeParameter() throws Exception {	
		String result = callRestfulService("GetHTML?query=org.LexGrid.codingSchemes.CodingScheme&org.LexGrid.codingSchemes.CodingScheme&codingSchemeName=" + ServiceTestCase.THES_SCHEME + "&codingSchemeVersion=" + ServiceTestCase.THES_VERSION);
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains(ServiceTestCase.THES_SCHEME));
		
		//Make sure a second Coding Scheme wasn't returned
		assertFalse(result.contains("2.Result Class Name:"));
	}
	
	public void testRESTXMLWithWrongCodingSchemeParameter() throws Exception {	
		String result = callRestfulService("GetXML?query=org.LexGrid.codingSchemes.CodingScheme&org.LexGrid.codingSchemes.CodingScheme&codingSchemeName=INVALID_CODING_SCHEME");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains("Didn't find CodingScheme/History for CodingScheme Name:"));		
	}
	
	public void testRESTHTMLWithWrongCodingSchemeParameter() throws Exception {	
		String result = callRestfulService("GetHTML?query=org.LexGrid.codingSchemes.CodingScheme&org.LexGrid.codingSchemes.CodingScheme&codingSchemeName=INVALID_CODING_SCHEME");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains("Didn't find CodingScheme/History for CodingScheme Name:"));		
	}
	
	public void testRESTXMLWithCodingSchemeAndVersionParameter() throws Exception {	
		String result = callRestfulService("GetXML?query=org.LexGrid.codingSchemes.CodingScheme&org.LexGrid.codingSchemes.CodingScheme&codingSchemeName=" + ServiceTestCase.THES_SCHEME + "&codingSchemeVersion=" + ServiceTestCase.THES_VERSION);
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains(ServiceTestCase.THES_SCHEME));
		
		//Make sure a second Coding Scheme wasn't returned
		assertFalse(result.contains("2.Result Class Name:"));	
	}
	
	public void testRESTHTMLWithCodingSchemeAndVersionParameter() throws Exception {	
		String result = callRestfulService("GetHTML?query=org.LexGrid.codingSchemes.CodingScheme&org.LexGrid.codingSchemes.CodingScheme&codingSchemeName=" + ServiceTestCase.THES_SCHEME + "&codingSchemeVersion=" + ServiceTestCase.THES_VERSION);
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains(ServiceTestCase.THES_SCHEME));
		
		//Make sure a second Coding Scheme wasn't returned
		assertFalse(result.contains("2.Result Class Name:"));;		
	}

	public void testRESTXMLWithWrongCodingSchemeAndVersionParameter() throws Exception {	
		String result = callRestfulService("GetXML?query=org.LexGrid.codingSchemes.CodingScheme&org.LexGrid.codingSchemes.CodingScheme&codingSchemeName=" + ServiceTestCase.THES_SCHEME + "&codingSchemeVersion=INVALID_VERSION");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains("Didn't find CodingScheme/History for CodingScheme Name:"));		
	}
	
	public void testRESTHTMLWithWrongCodingSchemeAndVersionParameter() throws Exception {	
		String result = callRestfulService("GetHTML?query=org.LexGrid.codingSchemes.CodingScheme&org.LexGrid.codingSchemes.CodingScheme&codingSchemeName=" + ServiceTestCase.THES_SCHEME + "&codingSchemeVersion=INVALID_VERSION");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains("Didn't find CodingScheme/History for CodingScheme Name:"));		
	}
	
	public void testRESTHTMLWithVersionParameterWithoutCodingScheme() throws Exception {	
		String result = callRestfulService("GetHTML?query=org.LexGrid.codingSchemes.CodingScheme&org.LexGrid.codingSchemes.CodingScheme&codingSchemeVersion=" + ServiceTestCase.THES_VERSION);
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		
		//Make sure a second Coding Scheme wasn't returned
		assertTrue(result.contains("Cannot Pass in a CodingScheme Version without a CodingScheme Name."));		
	}

	private String callRestfulService(String putString) throws Exception {
		URL url = new URL(serviceEndPoint + putString);		   
		URLConnection urlc = url.openConnection();
		urlc.setDoInput(true);
		urlc.setDoOutput(true);

		PrintStream ps = new PrintStream(urlc.getOutputStream());
		ps.close();

		//retrieve result
		BufferedReader br = new BufferedReader(new InputStreamReader(urlc
				.getInputStream()));
		String str;
		StringBuffer sb = new StringBuffer();
		while ((str = br.readLine()) != null) {
			sb.append(str);
			sb.append("\n");
		}
		br.close();
		return sb.toString();
	}
}

