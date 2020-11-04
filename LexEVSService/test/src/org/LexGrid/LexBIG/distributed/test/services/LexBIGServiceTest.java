/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.distributed.test.services;

import java.util.Date;

import org.LexGrid.LexBIG.DataModel.Collections.CodingSchemeRenderingList;
import org.LexGrid.LexBIG.DataModel.Collections.ModuleDescriptionList;
import org.LexGrid.LexBIG.DataModel.InterfaceElements.CodingSchemeRendering;
import org.LexGrid.LexBIG.DataModel.InterfaceElements.ModuleDescription;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.codingSchemes.CodingScheme;

public class LexBIGServiceTest extends ServiceTestCase{
	LexBIGService lbs;
	
	public void setUp(){
		lbs = LexEVSServiceHolder.instance().getLexEVSAppService();
		/*
		try {
			lbs = new LexBIGCaGridServiceAdapter("http://localhost:8080/wsrf/services/cagrid/LexBIGCaGridServices");
		} catch (LBException e) {
			e.printStackTrace();
		}
		*/
	}
	public void testConnect(){
		assertNotNull(lbs);	
	}
	public void testGetSupportedCodingSchemes() throws Exception{
		CodingSchemeRenderingList csrl = lbs.getSupportedCodingSchemes();
		assertNotNull(csrl);
		CodingSchemeRendering[] csr = csrl.getCodingSchemeRendering();
		assertTrue(csr.length > 0);		
	}
	
	public void testGetLastUpdateTime() throws Exception{
		Date date = lbs.getLastUpdateTime();
		assertNotNull(date);
	}
	public void testGetMatchAlgorithms(){
		ModuleDescriptionList mdl = lbs.getMatchAlgorithms();
		ModuleDescription[] md = mdl.getModuleDescription();
		assertTrue(md.length > 0);
	}
	
	public void testResolveCodingScheme() throws Exception {
		CodingScheme cs = lbs.resolveCodingScheme(THES_SCHEME, null);
		
		assertTrue(cs.getCodingSchemeName().equals(THES_SCHEME));	
	}
	
	public void testGetCodingSchemeConcepts() throws Exception{
		CodedNodeSet cns = lbs.getCodingSchemeConcepts(THES_SCHEME, null);
		assertNotNull(cns);
	}
	
	public void testLexevsVersion() 
    {
		LexBIGService lbSvc = LexEVSServiceHolder.instance().getLexEVSAppService();
		
		String version = lbSvc.getLexEVSBuildVersion();
		String timestamp = lbSvc.getLexEVSBuildTimestamp();
		
		assertNotNull(version);
		assertNotNull(timestamp);
		
    	System.out.println("LexEVS Build Version:   " + version);
    	System.out.println("LexEVS Build Timestamp: " + timestamp);
    }
}
