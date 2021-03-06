package org.LexGrid.LexBIG.distributed.test.testUtility;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.LexGrid.LexBIG.DataModel.Collections.AbsoluteCodingSchemeVersionReferenceList;
import org.LexGrid.LexBIG.DataModel.Collections.ResolvedConceptReferenceList;
import org.LexGrid.LexBIG.DataModel.Core.AbsoluteCodingSchemeVersionReference;
import org.LexGrid.LexBIG.DataModel.Core.ConceptReference;
import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Exceptions.LBInvocationException;
import org.LexGrid.LexBIG.Exceptions.LBResourceUnavailableException;
import org.LexGrid.LexBIG.Extensions.Generic.SearchExtension.MatchAlgorithm;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.Utility.Iterators.ResolvedConceptReferencesIterator;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.codingSchemes.CodingScheme;
import org.LexGrid.concepts.Entity;
import org.LexGrid.util.assertedvaluesets.AssertedValueSetParameters;
import org.LexGrid.util.assertedvaluesets.AssertedValueSetServices;
import org.junit.Before;
import org.junit.Test;
import org.lexgrid.valuesets.sourceasserted.SourceAssertedValueSetService;
import org.lexgrid.valuesets.sourceasserted.impl.SourceAssertedValueSetServiceImpl;

public class DistributedSourceAssertedValueSetTest {

	
	static SourceAssertedValueSetService svc;

	@Before
	public void setUp() throws Exception {
		AssertedValueSetParameters params = new AssertedValueSetParameters.Builder("0.1.5").
				assertedDefaultHierarchyVSRelation("Concept_In_Subset").
				codingSchemeName("owl2lexevs").
				codingSchemeURI("http://ncicb.nci.nih.gov/xml/owl/EVS/owl2lexevs.owl").
				rootConcept("C54453")
				.build();
		svc = LexEVSServiceHolder.instance().getLexEVSAppService().getLexEVSSourceAssertedValueSetServices(params);
	}
	
	@Test
	public void testListAllSourceAssertedValueSets() throws LBException {
		List<CodingScheme> schemes = svc.listAllSourceAssertedValueSets();
		long count = schemes.stream().count();
		assertTrue(count > 0L);
		assertEquals(count, 6L);
		assertTrue(schemes.stream().filter(x -> x.getCodingSchemeName().equals("Black")).findAny().isPresent());
	}
	
	@Test
	public void testListAllValueSets() throws LBException {
		List<CodingScheme> schemes = svc.getMinimalSourceAssertedValueSetSchemes();
		long count = schemes.stream().count();
		assertTrue(count > 0L);
		assertEquals(count, 9L);
		assertTrue(schemes.stream().filter(x -> x.getCodingSchemeName().equals("Black")).findAny().isPresent());
	}

	@Test
	public void testgetSourceAssertedValueSetsForConceptReference() throws LBException {
		ConceptReference reference = Constructors.createConceptReference("C48323", "owl2lexevs");
		List<CodingScheme> schemes = svc.getSourceAssertedValueSetsForConceptReference(reference );
		long count = schemes.stream().count();
		assertTrue(count > 0L);
 		assertTrue(schemes.stream()
 				.filter(x -> x.getCodingSchemeName().equals("Structured Product Labeling Color Terminology") ||
 						x.getCodingSchemeName().equals("SPL Color Terminology")
 				).findAny().isPresent());
 		// Could be any presentation
 		assertTrue(schemes.stream().filter(x -> 
 		x.getCodingSchemeName().equals("CDISC SDTM Ophthalmic Exam Test Code Terminology") || 
 		x.getCodingSchemeName().equals("SDTM-OETESTCD") ||
 		x.getCodingSchemeName().equals("Ophthalmic Exam Test Code") ||
 		x.getCodingSchemeName().equals("OETESTCD") 
 		).findAny().isPresent());
	}
	
	
	@Test
	public void testSchemeData() throws LBException, URISyntaxException {
		CodingScheme scheme = svc.getSourceAssertedValueSetForValueSetURI(new URI(AssertedValueSetServices.BASE + "C54453"));
		assertEquals(scheme, null);
		
		scheme = svc.getSourceAssertedValueSetForValueSetURI(new URI(AssertedValueSetServices.BASE + "FDA/" + "C54453"));
		assertEquals("Structured Product Labeling Color Terminology",scheme.getCodingSchemeName());
		assertEquals(AssertedValueSetServices.BASE + "FDA/" + "C54453", scheme.getCodingSchemeURI());
		assertTrue(scheme.getIsActive());
		assertEquals("C48323", scheme.getEntities().getEntityAsReference().stream().filter(x -> x.getEntityDescription().
				getContent().equals("Black")).findAny().get().getEntityCode());
	}
	
	
	
	@Test
	public void testGetSourceAssertedValueSetTopNodesForRootCode() {
		List<String> roots = svc.getSourceAssertedValueSetTopNodesForRootCode("C54453");
		assertNotNull(roots);
		assertTrue(roots.size() > 0);
		assertTrue(roots.stream().filter(x -> x.equals("C99999")).findAny().isPresent());
		assertTrue(roots.stream().filter(x -> x.equals("C54453")).findAny().isPresent());
		assertTrue(roots.stream().filter(x -> x.equals("C48323")).findAny().isPresent());
		assertTrue(roots.stream().filter(x -> x.equals("C48325")).findAny().isPresent());
	}
	
	@Test
	public void testGetSourceAssertedValueSetEntitiesForURI() {
		ResolvedConceptReferenceList list = svc.getSourceAssertedValueSetEntitiesForURI(AssertedValueSetServices.BASE + "C99999");
		List<ResolvedConceptReference> refs = Arrays.asList(list.getResolvedConceptReference());
		assertNotNull(refs);
		assertTrue(refs.size() > 0);
		assertTrue(refs.stream().filter(x -> x.getCode().equals("C99989")).findAny().isPresent());
		assertTrue(refs.stream().filter(x -> x.getCode().equals("C99988")).findAny().isPresent());
	}
	
	@Test
	public void testGetSourceAssertedValueSetforEntityCode() throws LBException {
		List<CodingScheme> schemes = svc.getSourceAssertedValueSetforTopNodeEntityCode("C48323");
		assertNotNull(schemes);
		assertTrue(schemes.size() > 0);
		assertEquals("Black", schemes.get(0).getCodingSchemeName());
		assertTrue(schemes.get(0).getEntities().getEntityCount() == 2);
		assertTrue(schemes.get(0).getEntities().getEntityAsReference().stream().anyMatch(x -> x.getEntityCode().equals("C99999")));	
	}
	
	@Test
	public void testGetSourceAssertedValueSetIteratorForURI() throws LBResourceUnavailableException, LBInvocationException {
		ResolvedConceptReferencesIterator itr = svc.getSourceAssertedValueSetIteratorForURI(AssertedValueSetServices.BASE + "FDA/" + "C54453");
		assertTrue(itr.hasNext());
		assertTrue(itr.numberRemaining() > 0);
		assertEquals(itr.numberRemaining(), 3);
		assertNotNull(itr.next());
	}
	
	@Test
	public void testGetSourceAssertedValueSetIteratorForURIForPagedNext() throws LBResourceUnavailableException, LBInvocationException {
		ResolvedConceptReferencesIterator itr = svc.getSourceAssertedValueSetIteratorForURI(AssertedValueSetServices.BASE + "FDA/" + "C54453");
		while(itr.hasNext()) {
			itr.next(2);
		}
		assertFalse(itr.hasNext());
		assertEquals(itr.numberRemaining(), 0);
	}
	
	@Test
	public void testGetListOfCodingSchemeVersionsUsedInResolution() throws LBException {
		List<CodingScheme> schemes = svc.getSourceAssertedValueSetforTopNodeEntityCode("C48323");
		CodingScheme scheme = schemes.get(0);
		AbsoluteCodingSchemeVersionReferenceList list = svc.getListOfCodingSchemeVersionsUsedInResolution(scheme);
		assertTrue(list.getAbsoluteCodingSchemeVersionReferenceCount() == 1);
		assertTrue(list.getAbsoluteCodingSchemeVersionReference(0).getCodingSchemeURN().equals("http://ncicb.nci.nih.gov/xml/owl/EVS/owl2lexevs.owl"));
		assertTrue(list.getAbsoluteCodingSchemeVersionReference(0).getCodingSchemeVersion().equals("0.1.5"));
	}
	
	@Test
	public void testGetSourceAssertedValueSetforValueSetMemberEntityCode() throws LBException {
		List<CodingScheme> schemes = svc.getSourceAssertedValueSetforValueSetMemberEntityCode("C99988");
		assertNotNull(schemes);
		assertTrue(schemes.size() > 0);
		assertTrue(schemes.stream().filter(x -> x.getCodingSchemeName().equals("Blacker")).findAny().isPresent());
		
		schemes = svc.getSourceAssertedValueSetforValueSetMemberEntityCode("C48323");
		long count = schemes.stream().count();
		assertTrue(count > 0L);
 		assertTrue(schemes.stream()
 				.filter(x -> x.getCodingSchemeName().equals("Structured Product Labeling Color Terminology") ||
 						x.getCodingSchemeName().equals("SPL Color Terminology")
 				).findAny().isPresent());
 		// Could be any presentation
 		assertTrue(schemes.stream().filter(x -> 
 		x.getCodingSchemeName().equals("CDISC SDTM Ophthalmic Exam Test Code Terminology") || 
 		x.getCodingSchemeName().equals("SDTM-OETESTCD") ||
 		x.getCodingSchemeName().equals("Ophthalmic Exam Test Code") ||
 		x.getCodingSchemeName().equals("OETESTCD") 
 		).findAny().isPresent());
	}
	
	@Test
	public void testGetSourceAssertedValueSetsforTextSearch() throws LBException {
		List<AbsoluteCodingSchemeVersionReference> acsvr = svc.getSourceAssertedValueSetsforTextSearch("Black", MatchAlgorithm.LUCENE);
		assertTrue(acsvr.stream().filter(x -> x.getCodingSchemeURN().equals(AssertedValueSetServices.BASE + "C54453")).findAny().isPresent());
		assertTrue(acsvr.stream().filter(x -> x.getCodingSchemeVersion().equals("0.1.5")).findAny().isPresent());
		assertTrue(acsvr.stream().filter(x -> x.getCodingSchemeURN().equals(AssertedValueSetServices.BASE + "C117743")).findAny().isPresent());
	}
	
	@Test
	public void testGetAllSourceAssertedValueSetEntities() {
		@SuppressWarnings("unchecked")
		List<Entity> entities = (List<Entity>) svc.getAllSourceAssertedValueSetEntities();
		assertNotNull(entities);
		assertTrue(entities.size() > 0);
		assertEquals(entities.size(), 10);
	}
	
	@Test
	public void testGetValueSetCodeForUri() {
		String code = ((SourceAssertedValueSetServiceImpl) svc).
		getEntityCodeFromValueSetDefinition(AssertedValueSetServices.BASE + "FDA/" + "C54453");
		assertNotNull(code);
		assertEquals(code, "C54453");
	}

}
