package org.LexGrid.LexBIG.distributed.test.live;

import java.util.Iterator;

import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.Impl.Extensions.tree.model.LexEvsTreeNode;
import org.LexGrid.LexBIG.Impl.Extensions.tree.service.PathToRootTreeServiceImpl;
import org.LexGrid.LexBIG.Impl.Extensions.tree.service.TreeServiceFactory;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.distributed.test.live.testutil.RemoteServerUtil;

public class TestNamespaceResolution {
	
	LexBIGService lbSvc = null;
	
	public TestNamespaceResolution() throws Exception{
        lbSvc = RemoteServerUtil.createSecureLexBIGService("NCI_Thesaurus", null);
	}

    public int run() {

        PathToRootTreeServiceImpl treeSvc = (PathToRootTreeServiceImpl) TreeServiceFactory.getInstance().getTreeService(lbSvc);
		LexEvsTreeNode fiveNode = 
			treeSvc.
				getSubConcepts(
						"NCI_Thesaurus", 
						null, 
						"C43431", "NCI_Thesaurus");
		
		System.out.println(fiveNode.getCode().equals("C43431"));		
		int count = 0;
		
		Iterator<LexEvsTreeNode> itr = fiveNode.getChildIterator();
		
		while(itr.hasNext()){
			itr.next();
			count++;
		}
		System.out.println("Size: " + count);
		return count;
    }
}

