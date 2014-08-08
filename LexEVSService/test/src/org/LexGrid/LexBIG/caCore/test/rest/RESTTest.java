/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.test.rest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;

import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.springframework.util.Assert;

public class RESTTest extends ServiceTestCase {
	String testId = "LexEVS DataService REST Test.";

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
		String result = callRestfulService("GetXML?query=org.LexGrid.concepts.Entity&org.LexGrid.concepts.Entity[@_entityCode=29506000]");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains("29506000"));
	}
	
	public void testRESTHTMLSearchByCriteria() throws Exception {	
		String result = callRestfulService("GetHTML?query=org.LexGrid.concepts.Entity&org.LexGrid.concepts.Entity[@_entityCode=29506000]");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains("29506000"));
	}
	
	public void testRESTXMLSearchByPath() throws Exception {	
		String result = callRestfulService("GetXML?query=org.LexGrid.concepts.Entity,org.LexGrid.commonTypes.EntityDescription&org.LexGrid.commonTypes.EntityDescription[@_content=Boxing]");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains("29506000"));
	}
	
	public void testRESTHTMLSearchByPath() throws Exception {	
		String result = callRestfulService("GetHTML?query=org.LexGrid.concepts.Entity,org.LexGrid.commonTypes.EntityDescription&org.LexGrid.commonTypes.EntityDescription[@_content=Boxing]");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains("29506000"));
	}
	
	public void testRESTXMLSearchByRoleName() throws Exception {	
		String result = callRestfulService("GetXML?query=org.LexGrid.commonTypes.EntityDescription&org.LexGrid.concepts.Entity[@_entityCode=29506000]&roleName=_entityDescription");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains("Boxing"));
	}
	
	public void testRESTHTMLSearchByRoleName() throws Exception {	
		String result = callRestfulService("GetHTML?query=org.LexGrid.commonTypes.EntityDescription&org.LexGrid.concepts.Entity[@_entityCode=29506000]&roleName=_entityDescription");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains("Boxing"));
	}
	
	public void testRESTXMLSearchByMultipeCriteria() throws Exception {	
		String result = callRestfulService("GetXML?query=org.LexGrid.concepts.Entity&org.LexGrid.concepts.Entity[@_entityCode=29506000][@_entityCodeNamespace=SNOMED%20Clinical%20Terms]");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains("29506000"));
	}
	
	public void testRESTHTMLSearchByMultipeCriteria() throws Exception {	
		String result = callRestfulService("GetHTML?query=org.LexGrid.concepts.Entity&org.LexGrid.concepts.Entity[@_entityCode=29506000][@_entityCodeNamespace=SNOMED%20Clinical%20Terms]");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains("29506000"));
	}
	
	public void testRESTXMLInvalidURLParenthesis() throws Exception {	
		try {
			callRestfulService("GetXML?query=org.LexGrid.concepts.Entity&org.LexGrid.concepts.Entity[@_entityCode=29506000][@_entityCodeNamespace=SNOMED%20Clinical%20Terms]]");
		} catch (Exception e) {
			Assert.isInstanceOf(IOException.class, e);
			assertTrue(e.getMessage().contains("HTTP response code: 400"));
			return;
		}
		fail();
	}
	
	public void testRESTNonFullyQualifiedClassNamesPath() throws Exception {	
		String result = callRestfulService("GetHTML?query=Entity,EntityDescription&EntityDescription[@_content=Boxing]");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains("29506000"));
	}
	
	public void testRESTNonFullyQualifiedClassNamesCriteria() throws Exception {	
		String result = callRestfulService("GetHTML?query=Entity&Entity[@_entityCode=29506000]");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains("29506000"));
	}
	
	public void testRESTNonFullyQualifiedClassNamesRoleName() throws Exception {	
		String result = callRestfulService("GetHTML?query=EntityDescription&Entity[@_entityCode=29506000]&roleName=_entityDescription");
		assertTrue(result != null);
		assertTrue(result.length() > 0);
		assertTrue(result.contains("Boxing"));
	}
	
	public void testRESTHTMLInvalidURLParenthesis() throws Exception {	
		try {
			callRestfulService("GetHTML?query=org.LexGrid.concepts.Entity&org.LexGrid.concepts.Entity[@_entityCode=29506000][@_entityCodeNamespace=SNOMED%20Clinical%20Terms]]");
		} catch (Exception e) {
			Assert.isInstanceOf(IOException.class, e);
			assertTrue(e.getMessage().contains("HTTP response code: 400"));
			return;
		}
		fail();
	}
	
	public void testRESTXMLInvalidCriteriaObject() throws Exception {	
		try {
			callRestfulService("GetXML?query=org.LexGrid.concepts.Entity&org.LexGrid.concepts.EntityINVALID[@_entityCode=29506000]");
		} catch (Exception e) {
			Assert.isInstanceOf(IOException.class, e);
			assertTrue(e.getMessage().contains("HTTP response code: 400"));
			return;
		}
		fail();
	}
	
	public void testRESTHTMLInvalidCriteriaObject() throws Exception {	
		try {
			callRestfulService("GetHTML?query=org.LexGrid.concepts.Entity&org.LexGrid.concepts.EntityINVALID[@_entityCode=29506000][@_entityCodeNamespace=SNOMED%20Clinical%20Terms]");
		} catch (Exception e) {
			Assert.isInstanceOf(IOException.class, e);
			assertTrue(e.getMessage().contains("HTTP response code: 400"));
			return;
		}	
		fail();
	}

	public void testRESTXMLInvalidAttribute() throws Exception {	
		try {
			callRestfulService("GetXML?query=org.LexGrid.concepts.Entity&org.LexGrid.concepts.Entity[@_entityCodeINVALID=29506000]");
		} catch (Exception e) {
			Assert.isInstanceOf(IOException.class, e);
			assertTrue(e.getMessage().contains("HTTP response code: 400"));
			return;
		}	
		fail();
	}

	public void testRESTHTMLInvalidAttribute() throws Exception {	
		try {
			callRestfulService("GetHTML?query=org.LexGrid.concepts.Entity&org.LexGrid.concepts.Entity[@_entityCodeINVALID=29506000]");
		} catch (Exception e) {
			Assert.isInstanceOf(IOException.class, e);
			assertTrue(e.getMessage().contains("HTTP response code: 400"));
			return;
		}	
		fail();
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
		try {
			callRestfulService("GetXML?query=org.LexGrid.codingSchemes.CodingScheme&org.LexGrid.codingSchemes.CodingScheme&codingSchemeName=INVALID_CODING_SCHEME");
		} catch (Exception e) {
			Assert.isInstanceOf(FileNotFoundException.class, e);
			return;
		}	
		fail();
	}
	
	public void testRESTHTMLWithWrongCodingSchemeParameter() throws Exception {	
		try {
			callRestfulService("GetHTML?query=org.LexGrid.codingSchemes.CodingScheme&org.LexGrid.codingSchemes.CodingScheme&codingSchemeName=INVALID_CODING_SCHEME");
		} catch (Exception e) {
			Assert.isInstanceOf(FileNotFoundException.class, e);
			
			return;
		}	
		fail();
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
		try {
			callRestfulService("GetXML?query=org.LexGrid.codingSchemes.CodingScheme&org.LexGrid.codingSchemes.CodingScheme&codingSchemeName=" + ServiceTestCase.THES_SCHEME + "&codingSchemeVersion=INVALID_VERSION");
		} catch (Exception e) {
			Assert.isInstanceOf(FileNotFoundException.class, e);
			return;
		}	
		fail();
	}

	public void testRESTHTMLWithWrongCodingSchemeAndVersionParameter() throws Exception {	
		try {
			callRestfulService("GetHTML?query=org.LexGrid.codingSchemes.CodingScheme&org.LexGrid.codingSchemes.CodingScheme&codingSchemeName=" + ServiceTestCase.THES_SCHEME + "&codingSchemeVersion=INVALID_VERSION");
		} catch (Exception e) {
			Assert.isInstanceOf(FileNotFoundException.class, e);
			return;
		}	
		fail();
	}

	public void testRESTHTMLWithVersionParameterWithoutCodingScheme() throws Exception {	
		try {

			callRestfulService("GetHTML?query=org.LexGrid.codingSchemes.CodingScheme&org.LexGrid.codingSchemes.CodingScheme&codingSchemeVersion=" + ServiceTestCase.THES_VERSION);
		} catch (Exception e) {
			Assert.isInstanceOf(IOException.class, e);
			assertTrue(e.getMessage().contains("HTTP response code: 400"));
			return;
		}	
		fail();
	}
	
	public void testXSSAttack() throws Exception {	
		try {

			callRestfulService("ShowDynamicTree.action;jsessionid=F9623CBA386D7757FD29EA965EB4AE7F/?--></script><script>alert(20787)</script>");
		} catch (Exception e) {
			Assert.isInstanceOf(IOException.class, e);
			assertTrue(e.toString().contains("FileNotFoundException") || e.getMessage().contains("Connection reset"));
			return;
		}	
		fail();
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

