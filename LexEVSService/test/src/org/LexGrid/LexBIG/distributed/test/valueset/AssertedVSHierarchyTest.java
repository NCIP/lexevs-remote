package org.LexGrid.LexBIG.distributed.test.valueset;

import java.util.List;
import java.util.Map;

import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.junit.Before;
import org.junit.Test;
import org.lexevs.dao.database.service.valuesets.LexEVSTreeItem;
import org.lexevs.dao.database.service.valuesets.ValueSetHierarchyService;
import org.lexevs.dao.database.service.valuesets.ValueSetHierarchyServiceImpl;
import org.lexgrid.valuesets.sourceasserted.impl.SourceAssertedValueSetHierarchyServicesImpl;

public class AssertedVSHierarchyTest extends ServiceTestCase {
	SourceAssertedValueSetHierarchyServicesImpl service;

	public AssertedVSHierarchyTest() {
		super();
	}
	
	@Before
	public void setUp(){
		service = LexEVSServiceHolder.instance().
				getLexEVSAppService().getLexEVSSourceAssertedValueSetHierarchyServices();
		service.setLexBIGService(LexEVSServiceHolder.instance().
				getLexEVSAppService());
		service.preprocessSourceHierarchyData("http://ncicb.nci.nih.gov/xml/owl/EVS/owl2lexevs.owl",  "0.1.5", "Concept_In_Subset", "Contributing_Source","Publish_Value_Set", "C54453");
 		//Comment this in instead for direct to NCIt testing
//		service.preprocessSourceHierarchyData();
	}

	
	
	@Test 
	public void testGetHierarchyRoots() throws LBException{
		Map<String, LexEVSTreeItem> map = service.getHierarchyValueSetRoots(
				"C54453");
		assertTrue(map.size() > 0);
		assertTrue(map.get(ValueSetHierarchyService.ROOT)._assocToChildMap.size() > 0);
		Map<String, List<LexEVSTreeItem>> items  = map.get(ValueSetHierarchyService.ROOT)._assocToChildMap;
		
		items.forEach((y,z) -> z.forEach(x->System.out.println(x.get_code() + ": " + x.get_text() )));
	}
//	Direct to NCIt test	
//	@Test
//	public void testBuildTree() throws LBException{
//		long startNano = System.currentTimeMillis();
//		Map<String, LexEVSTreeItem> items  = service.getSourceValueSetTree();
//		long endNano = System.currentTimeMillis();
//		System.out.println("Performance output milli sec: " + (endNano - startNano));
//		LexEVSTreeItem item = items.get(ValueSetHierarchyServiceImpl.ROOT);
//		assertTrue(items.size() > 0);
//		int tabCounter = 0;
//		printTree(item._assocToChildMap.get(ValueSetHierarchyServiceImpl.INVERSE_IS_A), tabCounter);		
//	}
	
	@Test
	public void testBuildFullServiceTree() throws LBException{
		Map<String, LexEVSTreeItem> items  = service.getFullServiceValueSetTree();
		LexEVSTreeItem item = items.get(ValueSetHierarchyServiceImpl.ROOT);
		assertTrue(items.size() > 0);
		int tabCounter = 0;
		System.out.println("Printing Full Service Tree");
		printTree(item._assocToChildMap.get(ValueSetHierarchyServiceImpl.INVERSE_IS_A), tabCounter);
	}
	
	@Test
	public void testBuildSourceDefinedTree() throws LBException{
		Map<String, LexEVSTreeItem> items  = service.getSourceDefinedTree();
		LexEVSTreeItem item = items.get(ValueSetHierarchyServiceImpl.ROOT);
		assertTrue(items.size() > 0);
		int tabCounter = 0;
		System.out.println("Printing Full Source Defined Tree");
		printTree(item._assocToChildMap.get(ValueSetHierarchyServiceImpl.INVERSE_IS_A), tabCounter);
	}
	
	@Test
	public void testValidateSourceDefinedTreeContent() throws LBException{
		Map<String, LexEVSTreeItem> items  = service.getSourceDefinedTree();
		LexEVSTreeItem item = items.get(ValueSetHierarchyServiceImpl.ROOT);
		assertTrue(items.size() > 0);
		List<LexEVSTreeItem> roots =  item._assocToChildMap.get(ValueSetHierarchyServiceImpl.INVERSE_IS_A);
		assertTrue(roots.size() > 0);
		assertTrue(roots.stream().anyMatch(x -> x.get_text().equals("owl2lexevs")));
		assertTrue(roots.stream().filter(x -> x.get_text().equals("owl2lexevs")).findAny().get().is_expandable());
		assertTrue(roots.stream().filter(x -> x.get_text().equals("owl2lexevs")).findAny().get().
				_assocToChildMap.get(ValueSetHierarchyService.INVERSE_IS_A).stream().anyMatch(y ->
				y.get_text().equals("Black_TEST")));
		assertTrue(roots.stream().filter(x -> x.get_text().equals("owl2lexevs")).findAny().get().
				_assocToChildMap.get(ValueSetHierarchyService.INVERSE_IS_A).stream().filter(y ->
				y.get_text().equals("Black_FDA")).findAny().get().is_expandable());
		assertTrue(roots.stream().filter(x -> x.get_text().equals("owl2lexevs")).findAny().get().
				_assocToChildMap.get(ValueSetHierarchyService.INVERSE_IS_A).stream().anyMatch(y ->
				y.get_text().equals("White")));
		assertTrue(roots.stream().filter(x -> x.get_text().equals("owl2lexevs")).findAny().get().
				_assocToChildMap.get(ValueSetHierarchyService.INVERSE_IS_A).stream().filter(y ->
				y.get_text().equals("White")).findAny().get().is_expandable());
	}
	
	@Test
	public void testBuildTree() throws LBException{
		Map<String, LexEVSTreeItem> items  = service.getSourceValueSetTree();
		LexEVSTreeItem item = items.get(ValueSetHierarchyServiceImpl.ROOT);
		assertTrue(items.size() > 0);
		int tabCounter = 0;
		printTree(item._assocToChildMap.get(ValueSetHierarchyServiceImpl.INVERSE_IS_A), tabCounter);
	}
	
	@Test
	public void testValidateTreeContent() throws LBException{
		Map<String, LexEVSTreeItem> items  = service.getSourceValueSetTree();
		LexEVSTreeItem item = items.get(ValueSetHierarchyServiceImpl.ROOT);
		assertTrue(items.size() > 0);
		List<LexEVSTreeItem> roots =  item._assocToChildMap.get(ValueSetHierarchyServiceImpl.INVERSE_IS_A);
		assertTrue(roots.size() > 0);
		assertEquals(roots.get(0).get_text(),"Black_FDA");
		assertTrue(roots.get(0).is_expandable());
		assertTrue(roots.get(0)._assocToChildMap.get(ValueSetHierarchyServiceImpl.INVERSE_IS_A).size() > 0);
		assertEquals(roots.get(0)._assocToChildMap.get(ValueSetHierarchyServiceImpl.INVERSE_IS_A).get(0).get_text(), "Blacker");
		assertTrue(roots.get(0)._assocToChildMap.get(ValueSetHierarchyServiceImpl.INVERSE_IS_A).get(0).is_expandable());
		assertTrue(roots.get(0)._assocToChildMap.get(ValueSetHierarchyServiceImpl.INVERSE_IS_A).get(0).
				_assocToChildMap.get(ValueSetHierarchyServiceImpl.INVERSE_IS_A).get(0).get_text().equals("Ack"));
		assertFalse(roots.get(0)._assocToChildMap.get(ValueSetHierarchyServiceImpl.INVERSE_IS_A).get(0).
				_assocToChildMap.get(ValueSetHierarchyServiceImpl.INVERSE_IS_A).get(0).is_expandable());
		assertTrue(roots.get(0)._assocToChildMap.get(ValueSetHierarchyServiceImpl.INVERSE_IS_A).get(0).
				_assocToChildMap.get(ValueSetHierarchyServiceImpl.INVERSE_IS_A).get(1).get_text().equals("Bla"));
		assertFalse(roots.get(0)._assocToChildMap.get(ValueSetHierarchyServiceImpl.INVERSE_IS_A).get(0).
				_assocToChildMap.get(ValueSetHierarchyServiceImpl.INVERSE_IS_A).get(1).is_expandable());
		assertEquals(roots.get(0)._assocToChildMap.get(ValueSetHierarchyServiceImpl.INVERSE_IS_A).get(1).get_text(), "UberBlack");
		assertFalse(roots.get(0)._assocToChildMap.get(ValueSetHierarchyServiceImpl.INVERSE_IS_A).get(1).is_expandable());
		assertEquals(roots.get(2).get_text(), "White");
		assertTrue(roots.get(2).is_expandable());
		assertTrue(roots.get(2)._assocToChildMap.get(ValueSetHierarchyServiceImpl.INVERSE_IS_A).size() > 0);
		assertEquals(roots.get(2)._assocToChildMap.get(ValueSetHierarchyServiceImpl.INVERSE_IS_A).get(0).get_text(), "ArchWhite");
		assertFalse(roots.get(2)._assocToChildMap.get(ValueSetHierarchyServiceImpl.INVERSE_IS_A).get(0).is_expandable());
		assertEquals(roots.get(2)._assocToChildMap.get(ValueSetHierarchyServiceImpl.INVERSE_IS_A).get(1).get_text(), "BlindingWhite");
		assertFalse(roots.get(2)._assocToChildMap.get(ValueSetHierarchyServiceImpl.INVERSE_IS_A).get(1).is_expandable());
	}
	
	private void printTree(List<LexEVSTreeItem> items, int counter){
		if(items == null || items.isEmpty()){return;}
		counter = counter + 5;
		for(LexEVSTreeItem x : items){
			System.out.println(String.format(
					"%1$" + (counter + x.get_text().length()) + "s",  
					x.get_text()));
			List<LexEVSTreeItem> list = x._assocToChildMap.get(ValueSetHierarchyServiceImpl.INVERSE_IS_A);
			printTree(list, counter);
		}

	 
	}

}
