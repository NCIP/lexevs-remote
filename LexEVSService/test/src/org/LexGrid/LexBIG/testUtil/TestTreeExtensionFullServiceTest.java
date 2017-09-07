package org.LexGrid.LexBIG.testUtil;

import java.util.ArrayList;
import java.util.List;

import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.Impl.Extensions.tree.dao.iterator.ChildTreeNodeIterator;
import org.LexGrid.LexBIG.Impl.Extensions.tree.dao.iterator.PagingChildNodeIterator;
import org.LexGrid.LexBIG.Impl.Extensions.tree.model.LexEvsTree;
import org.LexGrid.LexBIG.Impl.Extensions.tree.model.LexEvsTreeNode;
import org.LexGrid.LexBIG.Impl.Extensions.tree.service.TreeService;
import org.LexGrid.LexBIG.Impl.Extensions.tree.service.TreeServiceFactory;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.distributed.test.live.testutil.RemoteServerUtil;
import org.junit.Test;


public class TestTreeExtensionFullServiceTest extends ServiceTestCase {

	@Test
    public void testGetChildren() throws Exception {
        List<LexEvsTreeNode> list = new ArrayList<LexEvsTreeNode>();

			CodingSchemeVersionOrTag versionOrTag = new CodingSchemeVersionOrTag();
	        versionOrTag.setVersion(AUTOMOBILES_VERSION);
			TreeService treeService = TreeServiceFactory.getInstance().getTreeService(RemoteServerUtil.createLexBIGService());
			LexEvsTree lexEvsTree =  treeService.getTree(AUTOMOBILES_SCHEME, versionOrTag, AUTOMOBILES_CODE);
			LexEvsTreeNode focus_node = lexEvsTree.findNodeInTree(AUTOMOBILES_CODE);
			assertNotNull(focus_node);
			LexEvsTreeNode.ExpandableStatus focus_node_status = focus_node.getExpandableStatus();
			if (focus_node_status == LexEvsTreeNode.ExpandableStatus.IS_EXPANDABLE) {
				
				PagingChildNodeIterator itr = (PagingChildNodeIterator) focus_node.getChildIterator();
				assertNotNull(itr);
				itr.setPageSize(2);

				try{
						while(itr.hasNext()){
							LexEvsTreeNode child = itr.next();
							list.add(child);
						}
				}catch(Exception e){
					fail();
				}

			}

    }
}


