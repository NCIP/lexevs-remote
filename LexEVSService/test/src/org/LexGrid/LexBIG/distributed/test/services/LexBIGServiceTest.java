/*******************************************************************************
 * Copyright: (c) 2004-2007 Mayo Foundation for Medical Education and 
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
package org.LexGrid.LexBIG.distributed.test.services;

import java.util.Arrays;
import java.util.Date;

import junit.framework.TestCase;

import org.LexGrid.LexBIG.DataModel.Collections.CodingSchemeRenderingList;
import org.LexGrid.LexBIG.DataModel.Collections.ExtensionDescriptionList;
import org.LexGrid.LexBIG.DataModel.Collections.ModuleDescriptionList;
import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.DataModel.InterfaceElements.CodingSchemeRendering;
import org.LexGrid.LexBIG.DataModel.InterfaceElements.ExtensionDescription;
import org.LexGrid.LexBIG.DataModel.InterfaceElements.ModuleDescription;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Extensions.Generic.LexBIGServiceConvenienceMethods;
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
}
