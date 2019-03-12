package org.LexGrid.LexBIG.distributed.test.testUtility;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.LexGrid.LexBIG.DataModel.Collections.ResolvedConceptReferenceList;
import org.LexGrid.LexBIG.DataModel.Core.AbsoluteCodingSchemeVersionReference;
import org.LexGrid.LexBIG.DataModel.Core.ConceptReference;
import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Exceptions.LBInvocationException;
import org.LexGrid.LexBIG.Exceptions.LBParameterException;
import org.LexGrid.LexBIG.Exceptions.LBResourceUnavailableException;
import org.LexGrid.LexBIG.Extensions.Generic.SearchExtension.MatchAlgorithm;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.Utility.RemoveFromDistributedTests;
import org.LexGrid.LexBIG.Utility.Iterators.ResolvedConceptReferencesIterator;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.codingSchemes.CodingScheme;
import org.LexGrid.commonTypes.Property;
import org.LexGrid.commonTypes.PropertyQualifier;
import org.LexGrid.util.assertedvaluesets.AssertedValueSetParameters;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.lexgrid.resolvedvalueset.LexEVSResolvedValueSetService;
import org.lexgrid.resolvedvalueset.impl.LexEVSResolvedValueSetServiceImpl;
import org.lexgrid.valuesets.LexEVSValueSetDefinitionServices;
import org.lexgrid.valuesets.sourceasserted.impl.AssertedValueSetResolvedConceptReferenceIterator;

public class DistributedResolvedValueSetTests {
	static AssertedValueSetParameters params;
	static LexEVSResolvedValueSetService service;
	static private LexBIGService lbs;
	
	@BeforeClass
	public static void setUp() {
		lbs = LexEVSServiceHolder.instance().getLexEVSAppService();
		params =
		new AssertedValueSetParameters.Builder("0.1.5").
		assertedDefaultHierarchyVSRelation("Concept_In_Subset").
		codingSchemeName("owl2lexevs").
		codingSchemeURI("http://ncicb.nci.nih.gov/xml/owl/EVS/owl2lexevs.owl").
		rootConcept("C54453")
		.build();
		service = LexEVSServiceHolder.instance().getLexEVSAppService().getLexEVSResolvedVSService(params);
		((LexEVSResolvedValueSetServiceImpl)service).initParams(params);
	}

	@Test
	public void testListAllResolvedValueSets() throws Exception {
		long start = System.currentTimeMillis();
		List<CodingScheme> list = service.listAllResolvedValueSets();
		long end = System.currentTimeMillis();
		System.out.println("Retrieving full scheme value sets: " + (end - start) + " mseconds");
		assertTrue(list.size() > 0);
		assertEquals(list.size(), 8);
		assertEquals(list.stream().
		filter(scheme -> scheme.getProperties().getPropertyAsReference().
			stream().filter(
			prop -> prop.getPropertyName().
			equals(LexEVSValueSetDefinitionServices.RESOLVED_AGAINST_CODING_SCHEME_VERSION)).
				findAny().isPresent()).count(), 8);
		//Source asserted value set
		assertTrue(list.stream().filter(scheme -> scheme.getCodingSchemeName().equals("Black")).findAny().isPresent());
		//Resolved value set coding scheme
		assertTrue(list.stream().filter(scheme -> scheme.getCodingSchemeName().equals("All Domestic Autos But GM")).findAny().isPresent());
		
		ResolvedConceptReferenceList refList = service.getValueSetEntitiesForURI(list.get(0).getCodingSchemeURI());

		@SuppressWarnings("unchecked")
		Iterator<ResolvedConceptReference> refs = (Iterator<ResolvedConceptReference>) refList.iterateResolvedConceptReference();
		while (refs.hasNext()) {

			ResolvedConceptReference ref = refs.next();
			System.out.println("Namespace: " + ref.getEntity().getEntityCodeNamespace());
			System.out.println("Code: " + ref.getCode());
			System.out.println("Description: " + ref.getEntityDescription().getContent());
		}
	}
	
	@Test
	public void testListAllResolvedValueSetsWithNoAssertedScheme() throws Exception {
		long start = System.currentTimeMillis();
		LexEVSResolvedValueSetService nullVsService = LexEVSServiceHolder.instance().getLexEVSAppService().getLexEVSResolvedVSService(null);
		List<CodingScheme> list = nullVsService .listAllResolvedValueSets();
		long end = System.currentTimeMillis();
		System.out.println("Retrieving full scheme value sets: " + (end - start) + " mseconds");
		assertTrue(list.size() > 0);
		assertEquals(list.size(), 3);
		assertEquals(list.stream().
		filter(scheme -> scheme.getProperties().getPropertyAsReference().
			stream().filter(
			prop -> prop.getPropertyName().
			equals(LexEVSValueSetDefinitionServices.RESOLVED_AGAINST_CODING_SCHEME_VERSION)).
				findAny().isPresent()).count(), 3);
		//Source asserted value set list should be empty
		assertFalse(list.stream().filter(scheme -> scheme.getCodingSchemeName().equals("Black")).findAny().isPresent());
		//Resolved value set coding scheme
		assertTrue(list.stream().filter(scheme -> scheme.getCodingSchemeName().equals("All Domestic Autos But GM")).findAny().isPresent());
	}
	
	@Test
	public void testListAllResolvedValueSetsWithMiniScheme() throws Exception {
		long start = System.currentTimeMillis();
		List<CodingScheme> schemes = service.getMinimalResolvedValueSetSchemes();
		long end = System.currentTimeMillis();
		System.out.println("Retrieving mini scheme value sets: " + (end - start) + " mseconds");
		assertTrue(schemes.size() > 0);
		assertEquals(schemes.size(), 8);
		//Resolved value set coding schemes
		assertTrue(schemes.stream().anyMatch(x -> x.getFormalName().equals("All Domestic Autos But GM")));
		assertTrue(schemes.stream().anyMatch(x -> x.getFormalName().equals("All Domestic Autos But GM  and "
				+ "as many characters as it takes to exceed 50 chars but not 250 chars and that "
				+ "should about do it")));
		assertTrue(schemes.stream().anyMatch(x -> x.getFormalName().equals("One Child Value Set")));
		assertTrue(schemes.stream().anyMatch(x -> x.getCodingSchemeURI().equals("SRITEST:AUTO:AllDomesticButGM")));
		assertTrue(schemes.stream().anyMatch(x -> x.getCodingSchemeURI().equals("SRITEST:AUTO:AllDomesticButGMWithlt250charName")));
		assertTrue(schemes.stream().anyMatch(x -> x.getCodingSchemeURI().equals("XTEST:One.Node.ValueSet")));
		assertTrue(schemes.stream().anyMatch(x -> x.getRepresentsVersion().equals("12.03test")));
		assertTrue(schemes.stream().anyMatch(x -> x.getRepresentsVersion().equals("1.0")));
		assertTrue(schemes.stream().anyMatch(x -> x.isIsActive()));
		
		assertTrue(schemes.stream().anyMatch(x -> x.getFormalName().equals("Black")));
		assertTrue(schemes.stream().anyMatch(x -> x.getRepresentsVersion().equals("0.1.5")));
//		assertTrue(schemes.stream().anyMatch(x -> x.getCodingSchemeURI().equals("http://evs.nci.nih.gov/valueset/FDA/C48323")));
		assertTrue(schemes.stream().anyMatch(x -> x.getCodingSchemeURI().equals("http://evs.nci.nih.gov/valueset/TEST/C48323")));
		final int count[] = {0};
		schemes.forEach(x ->{ count[0]++; System.out.println(x.getFormalName() + " count: " +  count[0]);});
	}
	
	
	@Test
	public void testListAllResolvedValueSetsWithMiniSchemeAndNoAssertedScheme() throws Exception {
		long start = System.currentTimeMillis();
		LexEVSResolvedValueSetService nullVsService = LexEVSServiceHolder.instance().getLexEVSAppService().getLexEVSResolvedVSService(null);
		List<CodingScheme> schemes = nullVsService.getMinimalResolvedValueSetSchemes();
		long end = System.currentTimeMillis();
		System.out.println("Retrieving mini scheme value sets: " + (end - start) + " mseconds");
		assertTrue(schemes.size() > 0);
		assertEquals(schemes.size(), 3);
		//Resolved value set coding schemes
		assertTrue(schemes.stream().anyMatch(x -> x.getFormalName().equals("All Domestic Autos But GM")));
		assertTrue(schemes.stream().anyMatch(x -> x.getFormalName().equals("All Domestic Autos But GM  and "
				+ "as many characters as it takes to exceed 50 chars but not 250 chars and that "
				+ "should about do it")));
		assertTrue(schemes.stream().anyMatch(x -> x.getFormalName().equals("One Child Value Set")));
		assertTrue(schemes.stream().anyMatch(x -> x.getCodingSchemeURI().equals("SRITEST:AUTO:AllDomesticButGM")));
		assertTrue(schemes.stream().anyMatch(x -> x.getCodingSchemeURI().equals("SRITEST:AUTO:AllDomesticButGMWithlt250charName")));
		assertTrue(schemes.stream().anyMatch(x -> x.getCodingSchemeURI().equals("XTEST:One.Node.ValueSet")));
		assertTrue(schemes.stream().anyMatch(x -> x.getRepresentsVersion().equals("12.03test")));
		assertTrue(schemes.stream().anyMatch(x -> x.getRepresentsVersion().equals("1.0")));
		assertTrue(schemes.stream().anyMatch(x -> x.isIsActive()));
		
		assertFalse(schemes.stream().anyMatch(x -> x.getFormalName().equals("Black")));
		assertFalse(schemes.stream().anyMatch(x -> x.getRepresentsVersion().equals("0.1.5")));
		assertFalse(schemes.stream().anyMatch(x -> x.getCodingSchemeURI().equals("http://evs.nci.nih.gov/valueset/TEST/C48323")));
		final int count[] = {0};
		schemes.forEach(x ->{ count[0]++; System.out.println(x.getFormalName() + " count: " +  count[0]);});
	}
	
	@Test
	public void testResolveDuplicateValueSetsWithTestSource() throws Exception {
		URI uri = new URI("http://evs.nci.nih.gov/valueset/TEST/C48323");
		CodingScheme ref = service.getResolvedValueSetForValueSetURI(uri);
		assertNotNull(ref);
		ResolvedConceptReferenceList refs = service.getValueSetEntitiesForURI(uri.toString());
		assertNotNull(refs);
		assertTrue(refs.getResolvedConceptReferenceCount() > 0);
	}
	
	@Test
	public void testResolveDuplicateValueSetsWithFDASource() throws Exception {
		URI uri = new URI("http://evs.nci.nih.gov/valueset/FDA/C48323");
		CodingScheme ref = service.getResolvedValueSetForValueSetURI(uri);
		assertNotNull(ref);
		ResolvedConceptReferenceList refs = service.getValueSetEntitiesForURI(uri.toString());
		assertNotNull(refs);
		assertTrue(refs.getResolvedConceptReferenceCount() > 0);
	}
	
	@Test
	public void getValueSetEntities() throws Exception {
		URI uri = new URI("http://evs.nci.nih.gov/valueset/TEST/C48323");
		ResolvedConceptReferenceList refs = service.getValueSetEntitiesForURI(uri.toString());
		assertNotNull(refs);
		assertTrue(refs.getResolvedConceptReferenceCount() > 0);
	}
	
	@Test(expected = RuntimeException.class)
	public void getValueSetEntitiesWithNoAssertedScheme() throws Exception {
		LexEVSResolvedValueSetService nullVsService = LexEVSServiceHolder.instance().getLexEVSAppService().getLexEVSResolvedVSService(null);
		URI uri = new URI("http://evs.nci.nih.gov/valueset/TEST/C48323");
		ResolvedConceptReferenceList refs = nullVsService.getValueSetEntitiesForURI(uri.toString());
		assertNotNull(refs);
		assertTrue(refs.getResolvedConceptReferenceCount() == 0);
	}
	
	@Test
	public void getValueSetEntitiesFromIterator() throws Exception {
		URI uri = new URI("http://evs.nci.nih.gov/valueset/TEST/C48323");
		ResolvedConceptReferencesIterator refs = service.getValueSetIteratorForURI(uri.toString());
		assertNotNull(refs);
		assertTrue(refs.numberRemaining() > 0);
		assertNotNull(refs.next());
	}
	
	@Test(expected=RuntimeException.class)
	public void getValueSetEntitiesWithNoAssertedSchemeFromIterator() throws Exception {
		LexEVSResolvedValueSetServiceImpl nullVsService = new LexEVSResolvedValueSetServiceImpl();
		URI uri = new URI("http://evs.nci.nih.gov/valueset/TEST/C48323");
		ResolvedConceptReferencesIterator refs = nullVsService.getValueSetIteratorForURI(uri.toString());
		assertNotNull(refs);
		assertTrue(refs.numberRemaining() == 0);
		assertNull(refs.next());
	}
	
	@Test
	public void testPage() throws Exception {

		URI uri = new URI("http://evs.nci.nih.gov/valueset/TEST/C48323");

		ResolvedConceptReferencesIterator itr = service.getValueSetIteratorForURI(uri.toString());
		
		ResolvedConceptReferenceList list = itr.next(2);
		assertNotNull(list);
		assertTrue(list.getResolvedConceptReferenceCount() > 0);
		assertNotNull(list.getResolvedConceptReference()[0]);
		assertEquals(2, list.getResolvedConceptReferenceCount());
		assertEquals(0, itr.numberRemaining());
		
		ResolvedConceptReferenceList list2 = itr.next(1);
		assertNotNull(list2);
		assertEquals(0, list2.getResolvedConceptReferenceCount());
		assertEquals(0, itr.numberRemaining());
	}
	
	@Test
	public void testPageByOne() throws Exception {

		URI uri = new URI("http://evs.nci.nih.gov/valueset/TEST/C48323");

		ResolvedConceptReferencesIterator itr = service.getValueSetIteratorForURI(uri.toString());
		
		ResolvedConceptReferenceList list = itr.next(1);
		assertNotNull(list);
		assertTrue(list.getResolvedConceptReferenceCount() > 0);
		assertNotNull(list.getResolvedConceptReference()[0]);
		assertEquals(1, list.getResolvedConceptReferenceCount());
		assertEquals(1, itr.numberRemaining());
		
		ResolvedConceptReferenceList list2 = itr.next(1);
		assertNotNull(list2);
		assertEquals(1, list2.getResolvedConceptReferenceCount());
		assertEquals(0, itr.numberRemaining());
		
		ResolvedConceptReferenceList list3 = itr.next(1);
		assertNotNull(list3);
		assertEquals(0, list3.getResolvedConceptReferenceCount());
		assertEquals(0, itr.numberRemaining());
	}
	
	@Test
	public void testPageByNone() throws Exception {

		URI uri = new URI("http://evs.nci.nih.gov/valueset/TEST/C48323");

		ResolvedConceptReferencesIterator itr = service.getValueSetIteratorForURI(uri.toString());
		
		ResolvedConceptReferenceList list = itr.next(0);
		assertNotNull(list);
		assertEquals(0, list.getResolvedConceptReferenceCount());
		assertEquals(2, itr.numberRemaining());
	}
	
	@Test
	public void testNext() throws Exception {
		URI uri = new URI("http://evs.nci.nih.gov/valueset/TEST/C48323");
		ResolvedConceptReferencesIterator itr = service.getValueSetIteratorForURI(uri.toString());		
		ResolvedConceptReference ref = itr.next();
		assertNotNull(ref);
		assertEquals(1, itr.numberRemaining());		
	}
	
	@Test
	public void testNextAndPage() throws Exception {
		URI uri = new URI("http://evs.nci.nih.gov/valueset/TEST/C48323");
		ResolvedConceptReferencesIterator itr = service.getValueSetIteratorForURI(uri.toString());		
		ResolvedConceptReferenceList ref = itr.next(1);
		assertNotNull(ref);
		assertEquals(1, itr.numberRemaining());		
		
		ResolvedConceptReference ref1 = itr.next();
		assertNotNull(ref1);
		assertEquals(0, itr.numberRemaining());		
	}
	
	@Test
	public void testPageAndNext() throws Exception {
		URI uri = new URI("http://evs.nci.nih.gov/valueset/TEST/C48323");
		ResolvedConceptReferencesIterator itr = service.getValueSetIteratorForURI(uri.toString());			
		
		ResolvedConceptReference ref1 = itr.next();
		assertNotNull(ref1);
		assertEquals(1, itr.numberRemaining());	
		
		ResolvedConceptReferenceList ref = itr.next(1);
		assertNotNull(ref);
		assertEquals(0, itr.numberRemaining());	
	}


@Test
public void testHasNext() 
		throws LBException, URISyntaxException {
	URI uri = new URI("http://evs.nci.nih.gov/valueset/TEST/C48323");
	ResolvedConceptReferencesIterator itr = service.getValueSetIteratorForURI(uri.toString());
	List<ResolvedConceptReference> list = new ArrayList<ResolvedConceptReference>();
	while(itr.hasNext()) {
		list.add(itr.next());
	}
	assertTrue(list.size() > 0);
	assertEquals(list.size(), 2);
}

@Test
public void testHasNextPage()
		throws LBException, URISyntaxException {
	URI uri = new URI("http://evs.nci.nih.gov/valueset/TEST/C48323");
	ResolvedConceptReferencesIterator itr = service.getValueSetIteratorForURI(uri.toString());
	List<ResolvedConceptReference> list = new ArrayList<ResolvedConceptReference>();
	while(itr.hasNext()) {
		list.addAll(Arrays.asList(itr.next(2).getResolvedConceptReference()));
	}
	assertTrue(list.size() > 0);
	assertEquals(list.size(), 2);
}

@Test
public void testHasNextPageLowerBoundary() 
		throws LBException, URISyntaxException {
	URI uri = new URI("http://evs.nci.nih.gov/valueset/TEST/C48323");
	ResolvedConceptReferencesIterator itr = service.getValueSetIteratorForURI(uri.toString());
	List<ResolvedConceptReference> list = new ArrayList<ResolvedConceptReference>();
	while(itr.hasNext()) {
		list.addAll(Arrays.asList(itr.next(1).getResolvedConceptReference()));
	}
	assertTrue(list.size() > 0);
	assertEquals(list.size(), 2);
}

@Test
public void testHasNextPageLowerBoundaryBadRequest()
		throws LBException, URISyntaxException{
	URI uri = new URI("http://evs.nci.nih.gov/valueset/TEST/C48323");
	ResolvedConceptReferencesIterator itr = service.getValueSetIteratorForURI(uri.toString());
	List<ResolvedConceptReference> list = new ArrayList<ResolvedConceptReference>();
	while(itr.hasNext()) {
		list.addAll(Arrays.asList(itr.next(0).getResolvedConceptReference()));
	}
	assertEquals(list.size(), 0);
}

@Test
public void testHasNextPageUpperBoundaryOverFlow()
		throws LBException, URISyntaxException {
	URI uri = new URI("http://evs.nci.nih.gov/valueset/TEST/C48323");
	ResolvedConceptReferencesIterator itr = service.getValueSetIteratorForURI(uri.toString());
	List<ResolvedConceptReference> list = new ArrayList<ResolvedConceptReference>();
	while(itr.hasNext()) {
		list.addAll(Arrays.asList(itr.next(6).getResolvedConceptReference()));
	}
	assertTrue(list.size() > 0);
	assertEquals(list.size(), 2);
}
	@Test
	public void testGetResolvedValueSetsforConceptReference() {
		//Resolved value set coding scheme
		ConceptReference ref = new ConceptReference();
		ref.setCode("005");
		ref.setCodeNamespace("Automobiles");
		ref.setCodingSchemeName("Automobiles");
		List<CodingScheme> schemes = service.getResolvedValueSetsForConceptReference(ref);
		assertTrue(schemes.size() > 0);
		
		//Resolved value set coding scheme
		ConceptReference asVSref = new ConceptReference();
		asVSref.setCode("C48323");
		asVSref.setCodeNamespace("owl2lexevs");
		asVSref.setCodingSchemeName("Black");
		List<CodingScheme> asVsSchemes = service.getResolvedValueSetsForConceptReference(ref);
		assertTrue(asVsSchemes.size() > 0);
	}
	
	@Test
	public void testGetResolvedValueSetsforConceptReferenceWithNoAssertedScheme() {
		
		LexEVSResolvedValueSetService nullVsService = LexEVSServiceHolder.instance().getLexEVSAppService().getLexEVSResolvedVSService(null);
		//Resolved value set coding scheme
		ConceptReference ref = new ConceptReference();
		ref.setCode("005");
		ref.setCodeNamespace("Automobiles");
		ref.setCodingSchemeName("Automobiles");
		List<CodingScheme> schemes = nullVsService.getResolvedValueSetsForConceptReference(ref);
		assertTrue(schemes.size() > 0);
		
		//Resolved value set coding scheme
		ConceptReference asVSref = new ConceptReference();
		asVSref.setCode("C48323");
		asVSref.setCodeNamespace("owl2lexevs");
		asVSref.setCodingSchemeName("Black");
		List<CodingScheme> asVsSchemes = nullVsService.getResolvedValueSetsForConceptReference(asVSref);
		assertFalse(asVsSchemes.size() > 0);
	}

	@Test
    @Category(RemoveFromDistributedTests.class)
	public void testGetCodingSchemeMetadataForResolvedValueSetURI() throws URISyntaxException {
		
		// No coding scheme version or tag defined.  This will resolve against RPODCUTION tag of automobiles.
		URI uri = new URI("SRITEST:AUTO:AllDomesticButGM");
		CodingScheme scheme = service.getResolvedValueSetForValueSetURI(uri);
		for (Property prop : scheme.getProperties().getPropertyAsReference()) {
			if (prop.getPropertyName().equals(LexEVSValueSetDefinitionServices.RESOLVED_AGAINST_CODING_SCHEME_VERSION)) {
				assertTrue(getPropertyQualifierValue(LexEVSValueSetDefinitionServices.CS_NAME, prop).equals(
						"Automobiles"));
				assertTrue(getPropertyQualifierValue(LexEVSValueSetDefinitionServices.VERSION, prop).equals("1.0"));
			}
		}
		
		URI asVSuri = new URI("http://evs.nci.nih.gov/valueset/FDA/C48323");
		CodingScheme asVSscheme = service.getResolvedValueSetForValueSetURI(asVSuri);
		for (Property prop : asVSscheme.getProperties().getPropertyAsReference()) {
			if (prop.getPropertyName().equals(LexEVSValueSetDefinitionServices.RESOLVED_AGAINST_CODING_SCHEME_VERSION)) {
				assertTrue(getPropertyQualifierValue(LexEVSValueSetDefinitionServices.CS_NAME, prop).equals(
						"Black"));
				assertTrue(getPropertyQualifierValue(LexEVSValueSetDefinitionServices.VERSION, prop).equals("0.1.5"));
			}
		}
	}
	
	@Test(expected = RuntimeException.class)
    @Category(RemoveFromDistributedTests.class)
	public void testGetValueSEtForResolvedValueSetURIWithNoAssertedScheme() throws URISyntaxException {
		LexEVSResolvedValueSetService nullVsService = LexEVSServiceHolder.instance().getLexEVSAppService().getLexEVSResolvedVSService(null);
		URI uri = new URI("SRITEST:AUTO:AllDomesticButGM");
		CodingScheme scheme = nullVsService.getResolvedValueSetForValueSetURI(uri);
		for (Property prop : scheme.getProperties().getPropertyAsReference()) {
			if (prop.getPropertyName().equals(LexEVSValueSetDefinitionServices.RESOLVED_AGAINST_CODING_SCHEME_VERSION)) {
				assertTrue(getPropertyQualifierValue(LexEVSValueSetDefinitionServices.CS_NAME, prop).equals(
						"Automobiles"));
				assertTrue(getPropertyQualifierValue(LexEVSValueSetDefinitionServices.VERSION, prop).equals("1.0"));
			}
		}
		
		// Expected to have a runtime exception when attempting to resolve as coding scheme
		URI asVSuri = new URI("http://evs.nci.nih.gov/valueset/FDA/C48323");
		nullVsService.getResolvedValueSetForValueSetURI(asVSuri);
	}
	
	@Test
	public void testCorrectTruncationForFormalNameJIRA_594() throws URISyntaxException {
		URI uri = new URI("SRITEST:AUTO:AllDomesticButGMWithlt250charName");
		CodingScheme scheme = service.getResolvedValueSetForValueSetURI(uri);
		for (Property prop : scheme.getProperties().getPropertyAsReference()) {
			if (prop.getPropertyName().equals("formalName")) {
				assertTrue(scheme.getFormalName().length() > 50);
				
			}
		}
	}
	
	@Test
	public void testVerifyLoadOfChildNodeOnly() throws URISyntaxException {
		URI uri = new URI("XTEST:One.Node.ValueSet");
		ResolvedConceptReferenceList list = service.getValueSetEntitiesForURI(uri.toString());
		assertTrue(list.getResolvedConceptReferenceCount() == 1);
		assertTrue(list.getResolvedConceptReference(0).getConceptCode().equals("C0011(5564)"));
	}
	
	@Test
	public void testGetValueSetURIAndVersionForCode() throws LBException{
		List<AbsoluteCodingSchemeVersionReference> refs = service.getResolvedValueSetsforEntityCode("C0011(5564)");
		assertNotNull(refs);
		assertTrue(refs.size() > 0);
		AbsoluteCodingSchemeVersionReference ref = refs.get(0);
		assertEquals(ref.getCodingSchemeURN(), "XTEST:One.Node.ValueSet");
		
		List<AbsoluteCodingSchemeVersionReference> asVSrefs = service.getResolvedValueSetsforEntityCode("C48323");
		assertNotNull(asVSrefs);
		assertTrue(asVSrefs.size() > 0);
		assertTrue(asVSrefs.stream().anyMatch(x -> x.getCodingSchemeURN().equals( "http://evs.nci.nih.gov/valueset/C54453")));
		assertTrue(asVSrefs.stream().anyMatch(x -> x.getCodingSchemeURN().equals( "http://evs.nci.nih.gov/valueset/C117743")));
	}
	
	@Test
	public void testGetValueSetURIAndVersionForCodeWithNoAssertedSource() throws LBException{
		LexEVSResolvedValueSetService nullVsService = LexEVSServiceHolder.instance().getLexEVSAppService().getLexEVSResolvedVSService(null);
		List<AbsoluteCodingSchemeVersionReference> refs = nullVsService.getResolvedValueSetsforEntityCode("C0011(5564)");
		assertNotNull(refs);
		assertTrue(refs.size() > 0);
		AbsoluteCodingSchemeVersionReference ref = refs.get(0);
		assertEquals(ref.getCodingSchemeURN(), "XTEST:One.Node.ValueSet");
		
		List<AbsoluteCodingSchemeVersionReference> asVSrefs = nullVsService.getResolvedValueSetsforEntityCode("C48323");
		assertNotNull(asVSrefs);
		assertTrue(asVSrefs.size()  ==  0);
		assertFalse(asVSrefs.stream().anyMatch(x -> x.getCodingSchemeURN().equals( "http://evs.nci.nih.gov/valueset/C54453")));
		assertFalse(asVSrefs.stream().anyMatch(x -> x.getCodingSchemeURN().equals( "http://evs.nci.nih.gov/valueset/C117743")));
	}
	
	@Test
	public void testGetValueSetURIAndVersionForTextExact() throws LBException{
		long start = System.currentTimeMillis();
		List<AbsoluteCodingSchemeVersionReference> refs = 
				service.getResolvedValueSetsforTextSearch("TrailerCar(Yahoo)", 
						MatchAlgorithm.PRESENTATION_EXACT);
		long end = System.currentTimeMillis();
		System.out.println("Exact Match: " + (end - start) + " mseconds");
		assertNotNull(refs);
		assertTrue(refs.size() > 0);
		AbsoluteCodingSchemeVersionReference ref = refs.get(0);
		assertEquals(ref.getCodingSchemeURN(), "XTEST:One.Node.ValueSet");
		
		long start1 = System.currentTimeMillis();
		List<AbsoluteCodingSchemeVersionReference> asVSrefs = 
				service.getResolvedValueSetsforTextSearch("Black", 
						MatchAlgorithm.PRESENTATION_EXACT);
		long end1 = System.currentTimeMillis();
		System.out.println("Exact Match: " + (end1 - start1) + " mseconds");
		assertNotNull(asVSrefs);
		assertTrue(asVSrefs.stream().
				filter( vsRef -> vsRef.getCodingSchemeURN().equals("http://evs.nci.nih.gov/valueset/C54453") ||
						vsRef.getCodingSchemeURN().equals("http://evs.nci.nih.gov/valueset/C117743"))
				.findAny()
				.isPresent());
	}
	
	@Test
	public void testGetValueSetURIAndVersionForTextLucene() throws LBException{
		long start = System.currentTimeMillis();
		List<AbsoluteCodingSchemeVersionReference> refs = 
				service.getResolvedValueSetsforTextSearch("Domestic", 
						MatchAlgorithm.LUCENE);
		long end = System.currentTimeMillis();
		System.out.println("Lucene Search: " + (end - start) + " mseconds");
		assertNotNull(refs);
		assertTrue(refs.size() > 0);
		AbsoluteCodingSchemeVersionReference ref = refs.get(0);
		assertTrue(ref.getCodingSchemeURN().equals( "SRITEST:AUTO:AllDomesticButGM") || 
				ref.getCodingSchemeURN().equals("SRITEST:AUTO:AllDomesticButGMWithlt250charName"));
		
		long start1 = System.currentTimeMillis();
		List<AbsoluteCodingSchemeVersionReference> asVSrefs = 
				service.getResolvedValueSetsforTextSearch("Black", 
						MatchAlgorithm.LUCENE);
		long end1 = System.currentTimeMillis();
		System.out.println("Lucene Search: " + (end1 - start1) + " mseconds");
		assertNotNull(asVSrefs);
		assertTrue(asVSrefs.stream().
				filter( vsRef -> vsRef.getCodingSchemeURN().equals("http://evs.nci.nih.gov/valueset/C54453") ||
						vsRef.getCodingSchemeURN().equals("http://evs.nci.nih.gov/valueset/C117743"))
				.findAny()
				.isPresent());
	}
	
	@Test
	public void testGetValueSetURIAndVersionForTextContains() throws LBException{
		long start = System.currentTimeMillis();
		List<AbsoluteCodingSchemeVersionReference> refs = 
				service.getResolvedValueSetsforTextSearch("Domestic", 
						MatchAlgorithm.PRESENTATION_CONTAINS);
		long end = System.currentTimeMillis();
		System.out.println("Contains search: " + (end - start) + " mseconds");
		assertNotNull(refs);
		assertTrue(refs.size() > 0);
		AbsoluteCodingSchemeVersionReference ref = refs.get(0);
		assertTrue(ref.getCodingSchemeURN().equals( "SRITEST:AUTO:AllDomesticButGM") || 
				ref.getCodingSchemeURN().equals("SRITEST:AUTO:AllDomesticButGMWithlt250charName"));
		
		long start1 = System.currentTimeMillis();
		List<AbsoluteCodingSchemeVersionReference> asVSrefs = 
				service.getResolvedValueSetsforTextSearch("Bl", 
						MatchAlgorithm.PRESENTATION_CONTAINS);
		long end1 = System.currentTimeMillis();
		System.out.println("Contains search: " + (end1 - start1) + " mseconds");
		assertNotNull(asVSrefs);
		assertTrue(asVSrefs.size() > 0);
		assertNotSame(asVSrefs.size(), 6);
		assertEquals(asVSrefs.size(), 5);
		assertTrue(asVSrefs.stream().anyMatch(x -> x.getCodingSchemeURN().equals("http://evs.nci.nih.gov/valueset/C54453")));
		assertTrue(asVSrefs.stream().anyMatch(x -> x.getCodingSchemeURN().equals("http://evs.nci.nih.gov/valueset/C99999")));
		assertTrue(asVSrefs.stream().anyMatch(x -> x.getCodingSchemeURN().equals("http://evs.nci.nih.gov/valueset/C48323")));
		assertTrue(asVSrefs.stream().anyMatch(x -> x.getCodingSchemeURN().equals("http://evs.nci.nih.gov/valueset/C48325")));
		assertTrue(asVSrefs.stream().anyMatch(x -> x.getCodingSchemeURN().equals("http://evs.nci.nih.gov/valueset/C117743")));
		assertFalse(asVSrefs.stream().anyMatch(x -> x.getCodingSchemeURN().equals("http://evs.nci.nih.gov/valueset/C99996")));
	}
	
	@Test
	public void testGetValueSetURIAndVersionForTextContainsWithNoAssertedSource() throws LBException{
		LexEVSResolvedValueSetService nullVsService = LexEVSServiceHolder.instance().getLexEVSAppService().getLexEVSResolvedVSService(null);
		long start = System.currentTimeMillis();
		List<AbsoluteCodingSchemeVersionReference> refs = 
				nullVsService.getResolvedValueSetsforTextSearch("Domestic", 
						MatchAlgorithm.PRESENTATION_CONTAINS);
		long end = System.currentTimeMillis();
		System.out.println("Contains search: " + (end - start) + " mseconds");
		assertNotNull(refs);
		assertTrue(refs.size() > 0);
		AbsoluteCodingSchemeVersionReference ref = refs.get(0);
		assertTrue(ref.getCodingSchemeURN().equals( "SRITEST:AUTO:AllDomesticButGM") || 
				ref.getCodingSchemeURN().equals("SRITEST:AUTO:AllDomesticButGMWithlt250charName"));
		
		long start1 = System.currentTimeMillis();
		List<AbsoluteCodingSchemeVersionReference> asVSrefs = 
				nullVsService.getResolvedValueSetsforTextSearch("Bl", 
						MatchAlgorithm.PRESENTATION_CONTAINS);
		long end1 = System.currentTimeMillis();
		System.out.println("Contains search: " + (end1 - start1) + " mseconds");
		assertNotNull(asVSrefs);
		assertFalse(asVSrefs.size() > 0);
		assertNotSame(asVSrefs.size(), 6);
		assertNotSame(asVSrefs.size(), 5);
		assertFalse(asVSrefs.stream().anyMatch(x -> x.getCodingSchemeURN().equals("http://evs.nci.nih.gov/valueset/C54453")));
		assertFalse(asVSrefs.stream().anyMatch(x -> x.getCodingSchemeURN().equals("http://evs.nci.nih.gov/valueset/C99999")));
		assertFalse(asVSrefs.stream().anyMatch(x -> x.getCodingSchemeURN().equals("http://evs.nci.nih.gov/valueset/C48323")));
		assertFalse(asVSrefs.stream().anyMatch(x -> x.getCodingSchemeURN().equals("http://evs.nci.nih.gov/valueset/C48325")));
		assertFalse(asVSrefs.stream().anyMatch(x -> x.getCodingSchemeURN().equals("http://evs.nci.nih.gov/valueset/C117743")));
		assertFalse(asVSrefs.stream().anyMatch(x -> x.getCodingSchemeURN().equals("http://evs.nci.nih.gov/valueset/C99996")));
	}
	
	
	@Test
	public void testGetRegularResolvedValueSets(){
		List<CodingScheme> schemes = lbs.getRegularResolvedVSCodingSchemes();
		assertTrue(schemes.size() > 0);
		assertTrue(schemes.stream().anyMatch(x -> x.getCodingSchemeURI().equals("SRITEST:AUTO:AllDomesticButGM")));
		assertFalse(schemes.stream().anyMatch(x -> x.getCodingSchemeURI().equals("http://evs.nci.nih.gov/valueset/FDA/C99999")));
	}
	
	@Test
	public void doesAssertValueSetSystemExist() {
		assertTrue(service.doesServiceContainAssertedValueSetTerminology(params));
		assertFalse(service.doesServiceContainAssertedValueSetTerminology(new AssertedValueSetParameters.Builder().build()));
		assertFalse(service.doesServiceContainAssertedValueSetTerminology(null));
	}
	
	private String getPropertyQualifierValue(String qualifierName, Property prop) {
		for (PropertyQualifier pq : prop.getPropertyQualifier()) {
			if (pq.getPropertyQualifierName().equals(qualifierName)) {
				return pq.getValue().getContent();
			}
		}
		return "";
	}
	

}
