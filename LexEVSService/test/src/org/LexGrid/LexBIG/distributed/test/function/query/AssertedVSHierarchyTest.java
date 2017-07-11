package org.LexGrid.LexBIG.distributed.test.function.query;

import java.util.HashMap;
import java.util.List;

import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.junit.Before;
import org.junit.Test;
import org.lexevs.dao.database.service.valuesets.LexEVSTreeItem;
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
		service.preprocessSourceHierarchyData();
	}
	
	@Test
	public void testGetRootNodes() throws LBException{
		HashMap<String, LexEVSTreeItem> items = service.getHierarchyValueSetRoots("C54443");
		assertNotNull(items);
		assertTrue(items.size() > 0);
		printTree(items.get("<Root>")._assocToChildMap.get("inverse_is_a"), 0);
	}
	
	@Test
	public void testGetHierarchyNodes() throws LBException{
		HashMap<String, LexEVSTreeItem> items = service.getSourceValueSetTree(THES_URN, THES_VERSION);
		assertNotNull(items);
		assertTrue(items.size() > 0);
		printTree(items.get("<Root>")._assocToChildMap.get("inverse_is_a"), 0);
	}
	
	private void printTree(List<LexEVSTreeItem> items, int counter){
		if(items == null || items.isEmpty()){return;}
		counter = counter + 5;
		for(LexEVSTreeItem x : items){
			System.out.println(String.format(
					"%1$" + (counter + x.toString().length()) + "s",  
					x.toString()));
			List<LexEVSTreeItem> list = x._assocToChildMap.get(ValueSetHierarchyServiceImpl.INVERSE_IS_A);
			printTree(list, counter);
		}

	 
	}

}
