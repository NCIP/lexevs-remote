package org.LexGrid.LexBIG.distributed.test.live.testutil;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.LexGrid.LexBIG.DataModel.Collections.ResolvedConceptReferenceList;
import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Impl.Extensions.tree.model.LexEvsTreeNode;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.Utility.LBConstants.MatchAlgorithms;
import org.LexGrid.LexBIG.Utility.Iterators.ResolvedConceptReferencesIterator;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.distributed.test.live.DistributedMetaThesaurusTest;
import org.LexGrid.LexBIG.distributed.test.live.TestNamespaceResolution;
import org.LexGrid.LexBIG.distributed.test.live.TestTreeExtension;
import org.LexGrid.LexBIG.distributed.test.live.cadsr.EVSConcept;
import org.LexGrid.LexBIG.distributed.test.live.cadsr.EVSException;
import org.LexGrid.LexBIG.distributed.test.live.cadsr.IteratorTest;
import org.LexGrid.LexBIG.distributed.test.live.cadsr.LexEVSCompare;
import org.LexGrid.LexBIG.distributed.test.live.cadsr.LexEVSQueryService;
import org.LexGrid.LexBIG.distributed.test.live.cadsr.LexEVSQueryServiceImpl;
import org.LexGrid.LexBIG.distributed.test.live.cadsr.LexEVSSearch;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CentralLiveTest {
	static LexEVSQueryServiceImpl service = null;
	private static LexEVSSearch search = null;
	private static IteratorTest itertest = null;
	private static LexEVSCompare compare = null;
	private static DistributedMetaThesaurusTest metaTest = null;
	private static TestNamespaceResolution treeNamespace = null;
	private static TestTreeExtension testTE = null;
	
	CodedNodeSet cns = null;
	private String[] conceptNames = {"C12434", "C41234", "C2300"};
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		 service = new LexEVSQueryServiceImpl();
		 search  = new LexEVSSearch();
		 itertest = new IteratorTest();
		 compare = new LexEVSCompare();
		 metaTest = new DistributedMetaThesaurusTest();
		 treeNamespace = new TestNamespaceResolution();
		 testTE = new TestTreeExtension(RemoteServerUtil.createLexBIGService());
	}
	
	@Before
	public void setUp() throws LBException, Exception{
		cns = RemoteServerUtil.createLexBIGService().getCodingSchemeConcepts(
				RemoteServerUtil.NCIT_SCHEME_NAME, null);
	}

	@Test
	public void testResolveNodeSet() throws Exception {
		ResolvedConceptReferencesIterator itr = service.resolveNodeSet(cns, false);
		assertNotNull(itr.hasNext());
	}

	@Test
	public void testFindConceptsByCodeStringBooleanInt() throws EVSException {
		List list = service.findConceptsByCode("C41234", true, 5);
		assertNotNull(list);
		assertEquals(list.size(), 1);
		assertTrue(list.get(0) instanceof EVSConcept);
		assertTrue(((EVSConcept)list.get(0)).getName().equals("Increased Red Cell Mass"));
	}

	@Test
	public void testFindConceptDetailsByNameListOfStringBooleanString() throws EVSException {
		List<EVSConcept> concepts = service.findConceptDetailsByName(Arrays.asList(conceptNames) , true);
		concepts.stream().forEach(x -> assertNotNull(x.getName()));
		concepts.stream().forEach(x -> System.out.println(x.getPreferredName()));
	}

	@Test
	public void testFindConceptsByPreferredNameStringBooleanString() throws EVSException {
		List<EVSConcept> concepts = service.findConceptsByPreferredName("Sex",false);
		assertTrue(concepts.size() > 1);
		assertEquals(concepts.get(0).getPreferredName(), "Sex");
		concepts.forEach(x -> System.out.println(x.getPreferredName()));
		concepts.forEach(x -> System.out.println(x.getCode()));
	}


	@Test
	public void testFindConceptsBySynonymStringBooleanIntString() throws EVSException {
		List<EVSConcept> concepts = service.findConceptsBySynonym("Blue", true, 5);
		assertTrue(concepts.size() > 0);
		assertEquals(concepts.get(0).getPreferredName(), "Blue");
		concepts.forEach(x -> System.out.println(x.getPreferredName()));
		concepts.forEach(x -> System.out.println(x.getCode()));
	}
	
	//****************************************************************
	//EVSSearch methods
	//****************************************************************
	
	@Test
	public void testdo_getEVSCode(){
		String result = search.do_getEVSCode("Sex");
		assertEquals(result, "C1522384");
	}
	
	@Test
	public void testreturnSubConcepts() throws LBException{
		HashMap<String, ResolvedConceptReference> map = search.
				returnSubConcepts("C12434", RemoteServerUtil.NCIT_SCHEME_NAME);
		map.keySet().stream().forEach(x -> System.out.println(x));
		map.forEach((x,y) -> {System.out.println(x); 
		System.out.println(y.getEntityDescription().getContent());});
	}
	
	@Test
	public void testreturnSuperConcepts() throws LBException{
		HashMap<String, String> map = search.
				returnSuperConceptNames("C12434", RemoteServerUtil.NCIT_SCHEME_NAME);
		map.keySet().stream().forEach(x -> System.out.println(x));
		map.forEach((x,y) -> {System.out.println(x); 
		System.out.println(y);});
	}
	
	@Test
	public void testreturnSubConceptNames() throws LBException{
		HashMap<String, String> map = search.
				returnSubConceptNames("C12434", RemoteServerUtil.NCIT_SCHEME_NAME);
		map.keySet().stream().forEach(x -> System.out.println(x));
		map.forEach((x,y) -> {System.out.println(x); 
		System.out.println(y);});
	}
	
	@Test
	public void testreturnSuperConceptNames() throws LBException{
		HashMap<String, ResolvedConceptReference> map = search.
				returnSuperConcepts("C12434", RemoteServerUtil.NCIT_SCHEME_NAME);
		map.keySet().stream().forEach(x -> System.out.println(x));
		map.forEach((x,y) -> {System.out.println(x); 
		System.out.println(y.getEntityDescription().getContent());});
	}
	
	
	@Test
	public void testsearchPrefTerm() throws Exception{
		ResolvedConceptReferenceList list = search.searchPrefTerm((LexEVSApplicationService)RemoteServerUtil.createLexBIGService(), 
				RemoteServerUtil.NCIM_SCHEME_NAME, "Sex", 5, MatchAlgorithms.exactMatch.name(), null);
		Arrays.asList(list.getResolvedConceptReference()).stream().forEach(x -> System.out.println(x.getCode()));
	}
	
	@Test
	public void testsearchConceptNames() throws Exception{
		int hasConcept = search.searchConceptsName((LexEVSApplicationService)RemoteServerUtil.createLexBIGService(), 
				RemoteServerUtil.NCIT_SCHEME_NAME, null);
		assertTrue(hasConcept > 0);
	}
	
	@Test
	public void runIteratorTest() throws Exception{
		itertest.run();
	}
	
	//Fixed for LexEVS 6.5.1
	@Test
	public void runCompareTest(){
		int compareNo = compare.runCompare();
		assertTrue(compareNo > 0);
	}
	
	@Test
	public void runDistMetaThesTest() throws LBException, Exception{
		assertTrue(metaTest.run() > 1);
	}
	
	@Test
	public void testTestNamespaceResolution(){
		assertTrue(treeNamespace.run() > 0);
	}
	
	@Test
	public void testTestTreeExtension(){
		List<LexEvsTreeNode> nodes = testTE.getChildren(RemoteServerUtil.NCIT_SCHEME_NAME, 
				null, "C1404", RemoteServerUtil.NCIT_SCHEME_NAMESPACE);
		assertTrue(nodes.size() > 0);
		nodes.forEach(x -> System.out.println(x.getEntityDescription()));
	}
}
