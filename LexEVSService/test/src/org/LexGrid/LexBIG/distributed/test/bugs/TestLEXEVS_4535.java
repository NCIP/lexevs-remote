package org.LexGrid.LexBIG.distributed.test.bugs;

import org.LexGrid.LexBIG.Impl.Extensions.tree.dao.iterator.ChildTreeNodeIterator;
import org.LexGrid.LexBIG.Impl.Extensions.tree.service.PathToRootTreeServiceImpl;
import org.LexGrid.LexBIG.Impl.Extensions.tree.service.TreeServiceFactory;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.junit.Before;
import org.junit.Test;

public class TestLEXEVS_4535 extends ServiceTestCase {
	LexEVSApplicationService svc;
	
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		svc = LexEVSServiceHolder.instance().getLexEVSAppService();
	}

	@Test
	public void test() {
		
		 PathToRootTreeServiceImpl pathToRootTreeServiceImpl = (PathToRootTreeServiceImpl) TreeServiceFactory.getInstance().getTreeService(svc);
		 ChildTreeNodeIterator iterator = pathToRootTreeServiceImpl.getTree(OBIB_SCHEME, 
				  Constructors.createCodingSchemeVersionOrTagFromVersion(OBIB_VERSION), "BFO_0000001").getCurrentFocus().getChildIterator();
		  assertNotNull(iterator.next());
	}

}
