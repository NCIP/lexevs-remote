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
package org.LexGrid.LexBIG.caCore.test.pagination;

import java.util.List;

import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.caCore.applicationservice.QueryOptions;
import org.LexGrid.LexBIG.caCore.client.proxy.LexEVSListProxy;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.codingSchemes.CodingScheme;
import org.LexGrid.concepts.Entity;

import edu.mayo.informatics.lexgrid.convert.directConversions.TextCommon.Concept;

public class PagedReturnsTest extends ServiceTestCase {
	String testId = "LexEVSDataServiceSecurityTest";

	@Override
	protected String getTestID() {
		// TODO Auto-generated method stub
		return testId;
	}

	public void testPageConceptsWithNoLazyLoad() throws Exception {
		QueryOptions qo = new QueryOptions();
		qo.setLazyLoad(false);
		qo.setResultPageSize(10);
		qo.setCodingScheme(ServiceTestCase.THES_SCHEME);
		CodingSchemeVersionOrTag csvt = new CodingSchemeVersionOrTag();
		csvt.setVersion(ServiceTestCase.THES_VERSION);
		qo.setCodingSchemeVersionOrTag(csvt);
		LexEVSApplicationService svc = LexEVSServiceHolder.instance().getLexEVSAppService();
		List<Entity> concepts = svc.search(Entity.class, new Entity(), qo);
		LexEVSListProxy listProxy = (LexEVSListProxy)concepts;
		assertTrue(listProxy.isHasAllRecords() == false);
		assertTrue(listProxy.getListChunk().size() == 10);
		
		assertTrue(concepts.size() > 0);
		
		//Step through 50 concepts
		int i=0;
		for (Entity c : concepts) {
			if(i == 50) break;
			LexEVSListProxy lp = (LexEVSListProxy)concepts;
			assertTrue(lp.isHasAllRecords() == false);
			assertTrue(lp.getListChunk().size() == 10);
			i++;
		}
	}
	
	public void testPageConceptsWithLazyLoad() throws Exception {
		QueryOptions qo = new QueryOptions();
		qo.setCodingScheme(ServiceTestCase.THES_SCHEME);
		qo.setLazyLoad(true);
		qo.setResultPageSize(50);
		CodingSchemeVersionOrTag csvt = new CodingSchemeVersionOrTag();
		csvt.setVersion(ServiceTestCase.THES_VERSION);
		qo.setCodingSchemeVersionOrTag(csvt);
		LexEVSApplicationService svc = LexEVSServiceHolder.instance().getLexEVSAppService();
		List<Entity> concepts = svc.search(Entity.class, new Entity(), qo);
		LexEVSListProxy listProxy = (LexEVSListProxy)concepts;
		assertTrue(listProxy.isHasAllRecords() == false);
		assertTrue(listProxy.getListChunk().size() == 50);
		
		assertTrue(concepts.size() > 0);
		
		//Step through 1000 concepts
		int i=0;
		for (Entity c : concepts) {
			if(i == 1000) break;
			LexEVSListProxy lp = (LexEVSListProxy)concepts;
			assertTrue(lp.isHasAllRecords() == false);
			assertTrue(lp.getListChunk().size() == 50);
			i++;
		}
	}
	
	public void testPageConceptsNotRestrictedByCodingScheme() throws Exception {
		QueryOptions qo = new QueryOptions();
		qo.setLazyLoad(false);
		qo.setResultPageSize(2);
		LexEVSApplicationService svc = LexEVSServiceHolder.instance().getLexEVSAppService();
		List<CodingScheme> codingSchemes = svc.search(CodingScheme.class, new CodingScheme(), qo);
		LexEVSListProxy listProxy = (LexEVSListProxy)codingSchemes;
		assertTrue(listProxy.isHasAllRecords() == false);
		assertTrue(listProxy.getListChunk().size() == 2);
		
		assertTrue(codingSchemes.size() > 0);
		
		//Step through 4 results
		int i=0;
		for (CodingScheme c : codingSchemes) {
			if(i == 3) break;
			LexEVSListProxy lp = (LexEVSListProxy)codingSchemes;
			assertTrue(lp.isHasAllRecords() == false);
			assertTrue(lp.getListChunk().size() == 2);
			i++;
		}
	}
	
	public void testPageThroughAllResults() throws Exception {
		QueryOptions qo = new QueryOptions();
		qo.setLazyLoad(false);
		qo.setResultPageSize(1);
		LexEVSApplicationService svc = LexEVSServiceHolder.instance().getLexEVSAppService();
		List<CodingScheme> codingSchemes = svc.search(CodingScheme.class, new CodingScheme(), qo);
		LexEVSListProxy listProxy = (LexEVSListProxy)codingSchemes;
		assertTrue(listProxy.isHasAllRecords() == false);
		assertTrue(listProxy.getListChunk().size() == 1);
		
		assertTrue(codingSchemes.size() > 0);
		
		int size = codingSchemes.size();
		
		int returnedSize = 0;
		for (CodingScheme c : codingSchemes) {
			LexEVSListProxy lp = (LexEVSListProxy)codingSchemes;
			assertTrue(lp.isHasAllRecords() == false);
			assertTrue(lp.getListChunk().size() == 1);
			returnedSize++;
		}
		
		assertTrue(size == returnedSize);
	}
	
	public void testPaginationAcrossCodingSchemes() throws Exception {
		LexEVSApplicationService svc = LexEVSServiceHolder.instance().getLexEVSAppService();
		List<CodingScheme> codingSchemes = svc.search(CodingScheme.class, new CodingScheme());
		LexEVSListProxy listProxy = (LexEVSListProxy)codingSchemes;
		assertTrue(listProxy.isHasAllRecords() == true);
		
		assertTrue(codingSchemes.size() > 0);
		
		//Step through an entire CodingScheme
		for (CodingScheme c : codingSchemes) {	
			//Step through....
		}
	}
	
	public void testPaginationStressTest() throws Exception {
		QueryOptions qo = new QueryOptions();
		qo.setLazyLoad(true);
		qo.setResultPageSize(50);
		CodingSchemeVersionOrTag csvt = new CodingSchemeVersionOrTag();
		csvt.setVersion(ServiceTestCase.ZEBRAFISH_VERSION);
		qo.setCodingSchemeVersionOrTag(csvt);
		qo.setCodingScheme(ServiceTestCase.ZEBRAFISH_SCHEME);
		LexEVSApplicationService svc = LexEVSServiceHolder.instance().getLexEVSAppService();
		List<Entity> concepts = svc.search(Entity.class, new Entity(), qo);
		LexEVSListProxy listProxy = (LexEVSListProxy)concepts;
		assertTrue(listProxy.isHasAllRecords() == false);
		assertTrue(listProxy.getListChunk().size() == 50);
		
		assertTrue(concepts.size() > 0);
		
		//Step through an entire CodingScheme
		for (Entity c : concepts) {	
			//Step through....
		}
	}
}
