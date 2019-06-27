package org.LexGrid.LexBIG.distributed.test.features;

import java.util.List;
import java.util.Map;

import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.util.assertedvaluesets.AssertedValueSetParameters;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.lexevs.dao.database.service.valuesets.LexEVSTreeItem;
import org.lexevs.dao.database.service.valuesets.ValueSetHierarchyServiceImpl;
import org.lexgrid.valuesets.sourceasserted.impl.SourceAssertedValueSetHierarchyServicesImpl;

public class TestLEXEVS_4552 extends ServiceTestCase {

	LexEVSApplicationService svc;
	SourceAssertedValueSetHierarchyServicesImpl service;
	
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		
		service = LexEVSServiceHolder.instance().
				getLexEVSAppService().getLexEVSSourceAssertedValueSetHierarchyServices();
		//svc = LexEVSServiceHolder.instance().getAssertedValueSetConfiguredLexEVSAppService(new AssertedValueSetParameters.Builder(THES_VERSION).build());
		//service = svc.getLexEVSSourceAssertedValueSetHierarchyServices();
		service.setLexBIGService(LexEVSServiceHolder.instance().
				getLexEVSAppService());

			service.preprocessSourceHierarchyData(THES_SCHEME,  
					THES_VERSION, 
					"subClassOf", 
					"Contributing_Source",
					"Publish_Value_Set", 
					"C54443");
//	 		Comment this in instead for direct to NCIt testing
//			service.preprocessSourceHierarchyData();
		}
	
	@Test
	public void testBuildTree() throws LBException{
		long startNano = System.currentTimeMillis();
		Map<String, LexEVSTreeItem> items  = service.getSourceValueSetTree();
		long endNano = System.currentTimeMillis();
		System.out.println("Performance output milli sec: " + (endNano - startNano));
		LexEVSTreeItem item = items.get(ValueSetHierarchyServiceImpl.ROOT);
		assertTrue(items.size() > 0);
		int tabCounter = 0;
		printTree(item._assocToChildMap.get(ValueSetHierarchyServiceImpl.INVERSE_IS_A), tabCounter);		
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
