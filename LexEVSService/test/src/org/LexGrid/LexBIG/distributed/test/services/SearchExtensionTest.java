/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.distributed.test.services;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Extensions.Generic.CodingSchemeReference;
import org.LexGrid.LexBIG.Extensions.Generic.SearchExtension;
import org.LexGrid.LexBIG.Extensions.Generic.SearchExtension.MatchAlgorithm;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.Utility.Iterators.ResolvedConceptReferencesIterator;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.codingSchemes.CodingScheme;
import org.LexGrid.util.assertedvaluesets.AssertedValueSetParameters;
import org.apache.commons.lang.StringUtils;
import org.lexgrid.resolvedvalueset.LexEVSResolvedValueSetService;
import org.lexgrid.resolvedvalueset.impl.LexEVSResolvedValueSetServiceImpl;

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
		ResolvedConceptReferencesIterator itr = searchExtension.search("boxing", MatchAlgorithm.LUCENE);
		assertTrue(itr.hasNext());
		assertTrue(StringUtils.equalsIgnoreCase("Boxing (sports activity)", itr.next().getEntityDescription().getContent()));
	}
	
	public void testSimpleSearchContains() throws LBException {
		ResolvedConceptReferencesIterator itr = searchExtension.search("genome", MatchAlgorithm.PRESENTATION_CONTAINS);
		assertTrue(itr.hasNext());
		assertTrue(StringUtils.equalsIgnoreCase("genome", itr.next().getEntityDescription().getContent()));
	}
	
	public void testSimpleSearchWithCodingSchemeRestriction() throws LBException {
		CodingSchemeReference ref = new CodingSchemeReference();
		ref.setCodingScheme(SNOMED_SCHEME);
		
		Set<CodingSchemeReference> codingSchemes = new HashSet<CodingSchemeReference>();
		codingSchemes.add(ref);
		
		ResolvedConceptReferencesIterator itr = searchExtension.search("boxing", codingSchemes, MatchAlgorithm.LUCENE);
		assertTrue(itr.hasNext());
		ResolvedConceptReference entity = itr.next();
		assertTrue(StringUtils.containsIgnoreCase(entity.getEntityDescription().getContent(), "boxing"));
	}
	
	public void testSimpleSearchNumberRemaining() throws LBException {
		ResolvedConceptReferencesIterator itr = searchExtension.search("boxing", MatchAlgorithm.LUCENE);
		assertTrue(itr.numberRemaining() > 0);
	}
	
	public void testSimpleSearchGetMaxToReturn() throws LBException {
		ResolvedConceptReferencesIterator itr = searchExtension.search("boxing", MatchAlgorithm.LUCENE);
		assertTrue(itr.hasNext());
		assertEquals(5, itr.next(5).getResolvedConceptReferenceCount());
	}
	
	public void testSimpleSearchContainsPerformance() throws LBException {
		for(String term : Arrays.asList("boxing", "gene", "heart attack", "single cell", "cancer", "cure")){
			long start = System.currentTimeMillis();
			ResolvedConceptReferencesIterator itr = searchExtension.search(term, MatchAlgorithm.PRESENTATION_CONTAINS);
			assertTrue(itr.hasNext());
			System.out.println(System.currentTimeMillis() - start);
		}
	}
	
	public void testSimpleSearchContainsPerformanceResolvedValueSets() throws LBException {
		LexEVSResolvedValueSetService rss = LexEVSServiceHolder.instance().getLexEVSAppService().
				getLexEVSResolvedVSService(new AssertedValueSetParameters.Builder(THES_VERSION).build());
		Set<CodingSchemeReference> valueSets = new HashSet<CodingSchemeReference>();
		
		for(CodingScheme cs : rss.listAllResolvedValueSets()){
			CodingSchemeReference ref = new CodingSchemeReference();
			ref.setCodingScheme(cs.getCodingSchemeURI());
			ref.setVersionOrTag(Constructors.createCodingSchemeVersionOrTagFromVersion(cs.getRepresentsVersion()));
			
			valueSets.add(ref);
		}
		
		for(String term : Arrays.asList("year", "month", "day", "observation", "imputed")){
			long start = System.currentTimeMillis();
			ResolvedConceptReferencesIterator itr = searchExtension.search(term, valueSets, MatchAlgorithm.PRESENTATION_CONTAINS);
			assertTrue(itr.hasNext());
			System.out.println(System.currentTimeMillis() - start);
		}
	}
}
