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
package org.LexGrid.LexBIG.distributed.test.dataAccess;

import gov.nih.nci.evs.security.SecurityToken;
import gov.nih.nci.system.client.ApplicationServiceProvider;

import org.LexGrid.LexBIG.Extensions.Generic.LexBIGServiceConvenienceMethods;
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
			lbscm.getHierarchyRoots(MEDDRA_SCHEME, null, null);	
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
