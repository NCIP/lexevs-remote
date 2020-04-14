package org.LexGrid.LexBIG.distributed.test.testUtility;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Exceptions.LBInvocationException;
import org.LexGrid.LexBIG.Exceptions.LBParameterException;
import org.LexGrid.LexBIG.Exceptions.LBResourceUnavailableException;
import org.LexGrid.LexBIG.Extensions.Generic.CodingSchemeReference;
import org.LexGrid.LexBIG.Extensions.Generic.SearchExtension.MatchAlgorithm;
import org.LexGrid.LexBIG.Extensions.Generic.SourceAssertedValueSetSearchExtension;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.Utility.Iterators.ResolvedConceptReferencesIterator;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.util.assertedvaluesets.AssertedValueSetParameters;
import org.junit.BeforeClass;
import org.junit.Test;

public class DistributedAssertedVSSearchExtensionTest {
	
		static AssertedValueSetParameters params;
		static SourceAssertedValueSetSearchExtension service;
		static private LexBIGService lbs;
		
		@BeforeClass
		public static void setUp() throws LBException {
			lbs = LexEVSServiceHolder.instance().getLexEVSAppService();
			service = (SourceAssertedValueSetSearchExtension) lbs.getGenericExtension("AssertedValueSetSearchExtension");
			
		}
		

	@Test
	public void test() throws LBParameterException, LBResourceUnavailableException, LBInvocationException {
		ResolvedConceptReferencesIterator itr = service.search("Black", MatchAlgorithm.PROPERTY_EXACT);
		assertNotNull(itr);
		assertTrue(itr.hasNext());
		ResolvedConceptReference ref = itr.next();
		assertNotNull(ref);
		assertEquals(ref.getCode(), "C48323");
		assertEquals(ref.getEntityDescription().getContent(), "Black");
	}
	
	@Test
	public void testParams1() throws LBParameterException, LBResourceUnavailableException, LBInvocationException {
		CodingSchemeReference csRef = new CodingSchemeReference();
		csRef.setCodingScheme("http://evs.nci.nih.gov/valueset/FDA/C54453");
		csRef.setVersionOrTag(Constructors.createCodingSchemeVersionOrTagFromVersion("0.1.5"));
		ResolvedConceptReferencesIterator itr = service.search("Black", csRef,  MatchAlgorithm.PROPERTY_EXACT);
		assertNotNull(itr);
		assertTrue(itr.hasNext());
		ResolvedConceptReference ref = itr.next();
		assertNotNull(ref);
		assertEquals(ref.getCode(), "C48323");
		assertEquals(ref.getEntityDescription().getContent(), "Black");
		assertEquals(ref.getCodingSchemeName(), "owl2lexevs");
	}
	
	@Test
	public void testParams2() throws LBParameterException, LBResourceUnavailableException, LBInvocationException {
		CodingSchemeReference csRef = new CodingSchemeReference();
		csRef.setCodingScheme("lexevs2owl");
		csRef.setVersionOrTag(Constructors.createCodingSchemeVersionOrTagFromVersion("0.1.5"));
		Set<CodingSchemeReference> csRefs = new HashSet<CodingSchemeReference>();
		csRefs.add(csRef);
		ResolvedConceptReferencesIterator itr = service.search(
				"Black", null, csRefs, MatchAlgorithm.PROPERTY_EXACT, true);
		assertNotNull(itr);
		assertTrue(itr.hasNext());
		ResolvedConceptReference ref = itr.next();
		assertNotNull(ref);
		assertEquals(ref.getCode(), "C48323");
		assertEquals(ref.getEntityDescription().getContent(), "Black");
	}
	
	@Test
	public void testParams3() throws LBParameterException, LBResourceUnavailableException, LBInvocationException {
		CodingSchemeReference csRef = new CodingSchemeReference();
		csRef.setCodingScheme("http://evs.nci.nih.gov/valueset/TEST/C48323");
		csRef.setVersionOrTag(Constructors.createCodingSchemeVersionOrTagFromVersion("0.1.5"));
		Set<CodingSchemeReference> csRefs = new HashSet<CodingSchemeReference>();
		csRefs.add(csRef);
		ResolvedConceptReferencesIterator itr = service.search(
				"Black", null, csRefs, MatchAlgorithm.PROPERTY_EXACT, true, false);
		assertNotNull(itr);
		assertTrue(itr.hasNext());
		ResolvedConceptReference ref = itr.next();
		assertNotNull(ref);
		assertEquals(ref.getCode(), "C48323");
		assertEquals(ref.getEntityDescription().getContent(), "Black");
	}
	
	@Test
	public void testParams4() throws LBParameterException, LBResourceUnavailableException, LBInvocationException {
		CodingSchemeReference csRef = new CodingSchemeReference();
		csRef.setCodingScheme("http://evs.nci.nih.gov/valueset/TEST/C48323");
		csRef.setVersionOrTag(Constructors.createCodingSchemeVersionOrTagFromVersion("0.1.5"));
		Set<CodingSchemeReference> csRefs = new HashSet<CodingSchemeReference>();
		csRefs.add(csRef);
		ResolvedConceptReferencesIterator itr = service.search(
				"Color", null, csRefs, MatchAlgorithm.PROPERTY_CONTAINS, true, false);
		List<ResolvedConceptReference> refs = new ArrayList<ResolvedConceptReference>();
		assertNotNull(itr);
		while(itr.hasNext()) {
		refs.add(itr.next());
		}
		assertTrue(refs.size() > 0);
		assertEquals(refs.size(), 12);
		assertFalse(refs.stream().anyMatch(x -> x.getCode().equals("C54453")));
		assertTrue(refs.stream().anyMatch(x -> x.getCode().equals("C99996")));
	}
	
	

}
