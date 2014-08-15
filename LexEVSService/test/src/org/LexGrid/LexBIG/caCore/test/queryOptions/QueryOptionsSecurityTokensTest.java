/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.test.queryOptions;

import gov.nih.nci.evs.security.SecurityToken;

import java.util.HashMap;
import java.util.List;

import org.LexGrid.LexBIG.caCore.applicationservice.QueryOptions;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSDataService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.codingSchemes.CodingScheme;

public class QueryOptionsSecurityTokensTest extends ServiceTestCase {
	String testId = "LexEVS DataService Web Service Test (SOAP)";

	QueryOptions queryOptions;
	LexEVSDataService service;
	String secureVocab = ServiceTestCase.MEDDRA_SCHEME;
	String nonSecureVocab = ServiceTestCase.THES_SCHEME;
	String csClassName = CodingScheme.class.getName();
	SecurityToken goodSecureToken;
	SecurityToken badSecureToken;
	SecurityToken blankSecureToken;
	
	public void setUp(){
		queryOptions = new QueryOptions();
		service = LexEVSServiceHolder.instance().getLexEVSAppService();
		
		goodSecureToken = new SecurityToken();
		goodSecureToken.setAccessToken(ServiceTestCase.MEDDRA_TOKEN);
		
		badSecureToken = new SecurityToken();
		badSecureToken.setAccessToken("INVALID_TOKEN");
		
		blankSecureToken = new SecurityToken();
	}
	
	protected String getTestID() {	
		return testId;
	}

	public void testResolveSecureCodingSchemeNoTokensNoQueryOptions() throws Exception {
		List<CodingScheme> returnList = service.search(csClassName, new CodingScheme());
		assertFalse(checkForSecureVocab(returnList));		
	}
	
	public void testResolveSecureCodingSchemeNoTokensWithEmptyQueryOptions() throws Exception {
		refreshService();
		List<CodingScheme> returnList = service.search(csClassName, new CodingScheme(), queryOptions);
		assertFalse(checkForSecureVocab(returnList));		
	}
	
	public void testResolveSecureCodingSchemeWithBlankQueryOptions() throws Exception {
		refreshService();
		HashMap tokens = new HashMap();
		tokens.put(secureVocab, blankSecureToken);
		queryOptions.setSecurityTokens(tokens);
		List<CodingScheme> returnList = service.search(csClassName, new CodingScheme(), queryOptions);
		assertFalse(checkForSecureVocab(returnList));		
	}
	
	public void testResolveSecureCodingSchemeWithBadQueryOptions() throws Exception {
		refreshService();
		HashMap tokens = new HashMap();
		tokens.put(secureVocab, badSecureToken);
		queryOptions.setSecurityTokens(tokens);
		List<CodingScheme> returnList = service.search(csClassName, new CodingScheme(), queryOptions);
		assertFalse(checkForSecureVocab(returnList));		
	}
	
	public void testResolveSecureCodingSchemeWithGoodQueryOptions() throws Exception {
		refreshService();
		HashMap tokens = new HashMap();
		tokens.put(ServiceTestCase.MEDDRA_URN, goodSecureToken);
		queryOptions.setSecurityTokens(tokens);
		List<CodingScheme> returnList = service.search(csClassName, new CodingScheme(), queryOptions);
		assertTrue(checkForSecureVocab(returnList));		
	}
	
	public void testNoQueryOptionsWithGoodRegisteredTokens() throws Exception {
		refreshService();
		service.registerSecurityToken(ServiceTestCase.MEDDRA_URN, goodSecureToken);
		List<CodingScheme> returnList = service.search(csClassName, new CodingScheme());
		assertTrue(checkForSecureVocab(returnList));		
	}
	
	//Make sure our refresh service is working -- just to make SURE.
	public void testRefreshService() throws Exception {
		refreshService();	
		List<CodingScheme> returnList = service.search(csClassName, new CodingScheme());
		assertFalse(checkForSecureVocab(returnList));		
	}
	
	//This should work because the Token HashMap in the QueryOptions is empty - so it doesn't override
	//the registered Tokens
	public void testEmptyQueryOptionsWithGoodRegisteredTokens() throws Exception {
		refreshService();
		service.registerSecurityToken(ServiceTestCase.MEDDRA_URN, goodSecureToken);
		List<CodingScheme> returnList = service.search(csClassName, new CodingScheme(), queryOptions);
		assertTrue(checkForSecureVocab(returnList));		
	}
	
	//This is effectively overriding the 'Good' registered tokens with an empty set -- should not resolve.
	public void testBlankQueryOptionsWithGoodRegisteredTokens() throws Exception {
		refreshService();
		service.registerSecurityToken(secureVocab, goodSecureToken);
		
		HashMap tokens = new HashMap();
		queryOptions.setSecurityTokens(tokens);
		
		List<CodingScheme> returnList = service.search(csClassName, new CodingScheme(), queryOptions);
		assertFalse(checkForSecureVocab(returnList));		
	}
	
	public void testAddTokenWithQueryOptions() throws Exception {
		refreshService();
		service.registerSecurityToken(nonSecureVocab, badSecureToken);
		
		HashMap tokens = new HashMap();
		tokens.put(ServiceTestCase.MEDDRA_URN, goodSecureToken);
		queryOptions.setSecurityTokens(tokens);
		
		List<CodingScheme> returnList = service.search(csClassName, new CodingScheme(), queryOptions);
		assertTrue(checkForSecureVocab(returnList));		
	}
	
	public void refreshService(){
		service = LexEVSServiceHolder.instance().getFreshLexEVSAppService();
	}
	
	public boolean checkForSecureVocab(List<CodingScheme> schemes){
		for(int i = 0; i < schemes.size(); i++){
			if(schemes.get(i).getCodingSchemeName().equals(secureVocab)){
				return true;
			}
		}
		return false;
	}
	
		
}

