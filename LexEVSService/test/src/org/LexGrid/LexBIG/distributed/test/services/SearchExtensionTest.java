/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
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
