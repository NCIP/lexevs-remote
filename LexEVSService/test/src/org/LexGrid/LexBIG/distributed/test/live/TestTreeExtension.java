package org.LexGrid.LexBIG.distributed.test.live;

import java.util.ArrayList;
import java.util.List;

import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.Impl.Extensions.tree.dao.iterator.ChildTreeNodeIterator;
import org.LexGrid.LexBIG.Impl.Extensions.tree.model.LexEvsTree;
import org.LexGrid.LexBIG.Impl.Extensions.tree.model.LexEvsTreeNode;
import org.LexGrid.LexBIG.Impl.Extensions.tree.service.TreeService;
import org.LexGrid.LexBIG.Impl.Extensions.tree.service.TreeServiceFactory;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.distributed.test.live.testutil.RemoteServerUtil;


public class TestTreeExtension {
	LexBIGService lbSvc = null;
	public TestTreeExtension(LexBIGService lbSvc) {
		this.lbSvc = lbSvc;
	}

    public List<LexEvsTreeNode> getChildren(String codingScheme, String version, String code, String namespace) {
        List<LexEvsTreeNode> list = new ArrayList();
        try {
			CodingSchemeVersionOrTag versionOrTag = new CodingSchemeVersionOrTag();
			if (version != null) versionOrTag.setVersion(version);
			TreeService treeService = TreeServiceFactory.getInstance().getTreeService(lbSvc);
			LexEvsTree lexEvsTree = null;
			try {
				lexEvsTree = treeService.getTree(codingScheme, versionOrTag, code);
			} catch (Exception ex) {
				System.out.println(	"treeService.getTree failed.");
				ex.printStackTrace();
			}
			LexEvsTreeNode focus_node = null;
			focus_node = lexEvsTree.findNodeInTree(code);
			if (focus_node == null) {
				return null;
			}
			LexEvsTreeNode.ExpandableStatus focus_node_status = focus_node.getExpandableStatus();
			if (focus_node_status == LexEvsTreeNode.ExpandableStatus.IS_EXPANDABLE) {
				ChildTreeNodeIterator itr = focus_node.getChildIterator();
				if (itr == null) {
					System.out.println(	"itr == null???");
				} else {
					try {
						while(itr.hasNext()){
							LexEvsTreeNode child = itr.next();
							list.add(child);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						System.out.println("WARNING: ChildTreeNodeIterator throws exception...");
					}
			    }
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
    }

    public static void main(String[] args) {

		LexBIGService lbSvc = null;
		try {
			lbSvc = RemoteServerUtil.createLexBIGService();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String codingScheme = "NCI_Thesaurus";
		String version = "17.02d";
		String code = "C1404";
		String namespace = "NCI_Thesaurus";
		List<LexEvsTreeNode> list = new TestTreeExtension(lbSvc).getChildren(codingScheme, version, code, namespace);
		for(LexEvsTreeNode node: list){
			System.out.println(node.getEntityDescription());
		}
	}
}


