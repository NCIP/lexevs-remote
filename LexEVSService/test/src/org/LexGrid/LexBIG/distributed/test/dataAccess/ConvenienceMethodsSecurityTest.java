/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.distributed.test.dataAccess;

import gov.nih.nci.evs.security.SecurityToken;
import gov.nih.nci.system.client.ApplicationServiceProvider;

import org.LexGrid.LexBIG.Extensions.Generic.LexBIGServiceConvenienceMethods;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSDistributed;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

public class ConvenienceMethodsSecurityTest extends ServiceTestCase {
    String testId = "DistributedSecurityTest";
	@Override
	protected String getTestID() {
		return testId;
	}
	public void testGetHierarchyRoots(){
		LexEVSDistributed lbs = null;
		LexBIGServiceConvenienceMethods lbscm = null;
		try {
			lbs = (LexEVSDistributed)ApplicationServiceProvider.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl, "EvsServiceInfo");
			lbscm = 
				(LexBIGServiceConvenienceMethods)lbs.getGenericExtension("LexBIGServiceConvenienceMethods");
			lbscm.setLexBIGService(lbs);
		} catch (Exception e1) {
			e1.printStackTrace();
			fail("Error Connecting to Distributed LexBIG");		
		}
		
		try {
			lbscm.getHierarchyRoots(THES_SCHEME, null, null);	
		} catch (Exception e) {
			e.printStackTrace();
			fail("error resolving NCI Thesaurus");	
		}
	}

	public void testGetHierarchyRootsWithToken(){
		SecurityToken token = new SecurityToken();
		token.setAccessToken(MEDDRA_TOKEN);
		
		LexEVSDistributed lbs = null;
		LexBIGServiceConvenienceMethods lbscm = null;
		try {
			lbs = (LexEVSDistributed)ApplicationServiceProvider.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl, "EvsServiceInfo");
			lbs.registerSecurityToken(MEDDRA_SCHEME, token);
			lbscm = 
				(LexBIGServiceConvenienceMethods)lbs.getGenericExtension("LexBIGServiceConvenienceMethods");
			lbscm.setLexBIGService(lbs);
		} catch (Exception e1) {
			e1.printStackTrace();
			fail("Error Connecting to Distributed LexBIG");		
		}
		
		try {
			lbscm.getHierarchyRoots(MEDDRA_SCHEME, Constructors.createCodingSchemeVersionOrTagFromVersion(MEDDRA_VERSION), null);	
		} catch (Exception e) {
			e.printStackTrace();
			fail("error resoloving MedDRA");	
		}
	}

	public void testGetHierarchyRootsWithoutToken(){
		LexEVSDistributed lbs = null;
		LexBIGServiceConvenienceMethods lbscm = null;
		try {
			lbs = (LexEVSDistributed)ApplicationServiceProvider.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl, "EvsServiceInfo");
			lbscm = 
				(LexBIGServiceConvenienceMethods)lbs.getGenericExtension("LexBIGServiceConvenienceMethods");
			lbscm.setLexBIGService(lbs);
		} catch (Exception e1) {
			e1.printStackTrace();
			fail("Error Connecting to Distributed LexBIG");				
		}
		
		try {
			lbscm.getHierarchyRoots(MEDDRA_SCHEME, null, null);	
			fail("Secure coding cheme was resolved without a token");
		} catch (Exception e) {
			//This is a good thing -- it should  throw an exception when trying
			//to access a secure coding scheme without a token
		}
	}	
}
