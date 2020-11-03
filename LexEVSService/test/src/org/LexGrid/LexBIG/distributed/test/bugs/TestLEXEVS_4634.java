package org.LexGrid.LexBIG.distributed.test.bugs;

import java.util.List;

import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.codingSchemes.CodingScheme;
import org.LexGrid.util.assertedvaluesets.AssertedValueSetParameters;
import org.junit.Before;
import org.junit.Test;
import org.lexevs.dao.index.service.search.SourceAssertedValueSetSearchIndexService;
import org.lexgrid.resolvedvalueset.LexEVSResolvedValueSetService;
import org.lexgrid.valuesets.sourceasserted.SourceAssertedValueSetHierarchyServices;

public class TestLEXEVS_4634 extends ServiceTestCase {
	static AssertedValueSetParameters params;
	static LexEVSResolvedValueSetService service;
	static private LexEVSApplicationService lbs;
	static SourceAssertedValueSetSearchIndexService vsSvc;
	private static SourceAssertedValueSetHierarchyServices hService;

	@Before
	public void setUp() {
		lbs = LexEVSServiceHolder.instance().getLexEVSAppService();
		params = new AssertedValueSetParameters.Builder(THES_VERSION).build();
		service = lbs.getLexEVSResolvedVSService(params); 
		hService = lbs.getLexEVSSourceAssertedValueSetHierarchyServices();
		
		// This calls SourceAssertedValueSetHierarcyServicesImpl.init() 
		// which is testing the init() method with passing no parameters
		// to get NCIt asserted value sets
		hService.preprocessSourceHierarchyData();
	}

	@Test
	public void testListAllResolvedValueSets() throws Exception {
		// The main test is the setup() method above to get the NCIt.
		List<CodingScheme> list = service.listAllResolvedValueSets();
		assertNotNull(list);
		assertTrue(list.size() > 0);
	}
}
