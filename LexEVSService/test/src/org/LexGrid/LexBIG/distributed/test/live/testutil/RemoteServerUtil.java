package org.LexGrid.LexBIG.distributed.test.live.testutil;

import static org.junit.Assert.*;

import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.codingSchemes.CodingScheme;
import org.junit.Test;

import gov.nih.nci.evs.security.SecurityToken;
import gov.nih.nci.system.client.ApplicationServiceProvider;

public class RemoteServerUtil {

	public static final String SERVICEURL = "https://lexevsapi65.nci.nih.gov";
	public static String NCIT_SCHEME_NAME = "NCI Thesaurus";
	public static String NCIT_SCHEME_NAMESPACE = "NCI Thesaurus";
	public static String NCIM_SCHEME_NAME = "NCI Metathesaurus";

	public static LexBIGService createSecureLexBIGService(String codingScheme, String token) throws Exception {
		SecurityToken securityToken = new SecurityToken();
		securityToken.setAccessToken(token);
		LexEVSApplicationService lexevsService = (LexEVSApplicationService) ApplicationServiceProvider
				.getApplicationServiceFromUrl(SERVICEURL, "EvsServiceInfo");
		lexevsService.registerSecurityToken(codingScheme, securityToken);
		return lexevsService;
	}

	public static LexBIGService createLexBIGService() throws Exception {
		return (LexEVSApplicationService) ApplicationServiceProvider.getApplicationServiceFromUrl(SERVICEURL,
				"EvsServiceInfo");
	}
	
	@Test
	public void connectionTest() throws Exception{
		LexBIGService lbs = createLexBIGService();
		CodingScheme scheme = lbs.resolveCodingScheme(NCIT_SCHEME_NAME, null);
		assertNotNull(scheme);
		
	}

}
