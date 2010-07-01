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
package org.LexGrid.LexBIG.caCore.test.bugs;

import java.util.List;

import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.caCore.applicationservice.QueryOptions;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSDataService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.concepts.Entity;

public class GForge17233 extends ServiceTestCase
{
	private final String test_id = "GForge17233";
	
	@Override
	protected String getTestID() {
		return test_id;
	}
	
	public void testGetEntityCodesPrefixedByNamespaceWithColon() throws Exception {
		LexEVSDataService service = LexEVSServiceHolder.instance().getLexEVSAppService();
		
		QueryOptions qo = new QueryOptions();
		qo.setCodingScheme(ServiceTestCase.THES_SCHEME);
		CodingSchemeVersionOrTag csvt = new CodingSchemeVersionOrTag();
		csvt.setVersion(ServiceTestCase.THES_VERSION);
		qo.setCodingSchemeVersionOrTag(csvt);
		
		Entity entity = new Entity();
		entity.setEntityCodeNamespace("Thesaurus");
		entity.setEntityCode("Thesaurus:*");
				
		List<Entity> results = service.search(Entity.class, entity, qo);
		assertTrue(results.size() == 0);
	}		
	
	public void testGetEntityCodesPrefixedByNamespaceWithHash() throws Exception {
		LexEVSDataService service = LexEVSServiceHolder.instance().getLexEVSAppService();
		
		QueryOptions qo = new QueryOptions();
		qo.setCodingScheme(ServiceTestCase.THES_SCHEME);
		CodingSchemeVersionOrTag csvt = new CodingSchemeVersionOrTag();
		csvt.setVersion(ServiceTestCase.THES_VERSION);
		qo.setCodingSchemeVersionOrTag(csvt);
		
		Entity entity = new Entity();
		entity.setEntityCodeNamespace("Thesaurus");
		entity.setEntityCode("Thesaurus#*");
				
		List<Entity> results = service.search(Entity.class, entity, qo);
		assertTrue(results.size() == 0);
	}		
}
