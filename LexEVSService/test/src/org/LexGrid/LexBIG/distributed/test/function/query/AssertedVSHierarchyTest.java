package org.LexGrid.LexBIG.distributed.test.function.query;

import java.util.HashMap;

import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.junit.Before;
import org.junit.Test;
import org.lexevs.dao.database.service.valuesets.LexEVSTreeItem;
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
	}

}
