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

import java.util.HashSet;
import java.util.Set;

import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Extensions.Generic.CodingSchemeReference;
import org.LexGrid.LexBIG.Extensions.Generic.SearchExtension;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.Utility.Iterators.ResolvedConceptReferencesIterator;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.apache.commons.lang.StringUtils;

public class SearchExtensionTest extends ServiceTestCase {
	
	private SearchExtension searchExtension;

	public void setUp(){
		LexBIGService lbs = LexEVSServiceHolder.instance().getLexEVSAppService();
		
		try {
			searchExtension = (SearchExtension)lbs.getGenericExtension("SearchExtension");
		} catch (LBException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	public void testConnect(){
		assertNotNull(searchExtension);	
	}

	public void testSimpleSearch() throws LBException {
		ResolvedConceptReferencesIterator itr = searchExtension.search("boxing");
		assertTrue(itr.hasNext());
		assertTrue(StringUtils.equalsIgnoreCase("boxing", itr.next().getEntityDescription().getContent()));
	}
	
	public void testSimpleSearchWithCodingSchemeRestriction() throws LBException {
		CodingSchemeReference ref = new CodingSchemeReference();
		ref.setCodingScheme(SNOMED_SCHEME);
		
		Set<CodingSchemeReference> codingSchemes = new HashSet<CodingSchemeReference>();
		codingSchemes.add(ref);
		
		ResolvedConceptReferencesIterator itr = searchExtension.search("boxing", codingSchemes);
		assertTrue(itr.hasNext());
		assertTrue(StringUtils.equalsIgnoreCase("boxing", itr.next().getEntityDescription().getContent()));
	}
	
	public void testSimpleSearchNumberRemaining() throws LBException {
		ResolvedConceptReferencesIterator itr = searchExtension.search("boxing");
		assertTrue(itr.numberRemaining() > 0);
	}
	
	public void testSimpleSearchGetMaxToReturn() throws LBException {
		ResolvedConceptReferencesIterator itr = searchExtension.search("boxing");
		assertTrue(itr.hasNext());
		assertEquals(5, itr.next(5));
	}
}
