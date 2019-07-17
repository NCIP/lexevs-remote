package org.LexGrid.LexBIG.distributed.test.features;

import org.LexGrid.LexBIG.Impl.Extensions.tree.model.LexEvsTreeNode;
import org.LexGrid.LexBIG.Impl.Extensions.tree.service.PathToRootTreeServiceImpl;
import org.LexGrid.LexBIG.Impl.Extensions.tree.service.TreeServiceFactory;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.junit.Before;
import org.junit.Test;

public class TestLEXEVS_4444 extends ServiceTestCase {
	LexEVSApplicationService lbs;
	public PathToRootTreeServiceImpl pathToRootTreeServiceImpl;

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		lbs = LexEVSServiceHolder.instance().getLexEVSAppService();
		pathToRootTreeServiceImpl = (PathToRootTreeServiceImpl) TreeServiceFactory.getInstance().getTreeService(lbs);
	}

	@Test
	public void test() {
		
		LexEvsTreeNode fiveNode = 
				pathToRootTreeServiceImpl.
				getSubConcepts(
						OBIB_SCHEME, 
						Constructors.createCodingSchemeVersionOrTagFromVersion(
								OBIB_VERSION), "BFO_0000001", "obo", null);
			
			assertTrue(fiveNode.getCode().equals("BFO_0000001"));
			assertTrue(fiveNode.getExpandableStatus().name().equals("IS_EXPANDABLE"));
	}
	
	@Test
	public void test2() {
		
		LexEvsTreeNode fiveNode = 
				pathToRootTreeServiceImpl.
				getSubConcepts(
						THES_SCHEME, 
						Constructors.createCodingSchemeVersionOrTagFromVersion(
								THES_VERSION), "C7507", "ncit", null);
			
			assertTrue(fiveNode.getCode().equals("C7507"));
			assertTrue(fiveNode.getExpandableStatus().name().equals("IS_EXPANDABLE"));
	}

}
