package org.LexGrid.LexBIG.distributed.test.services;

import java.util.Iterator;
import java.util.List;

import org.LexGrid.LexBIG.DataModel.Collections.CodingSchemeRenderingList;
import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.DataModel.InterfaceElements.CodingSchemeRendering;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Extensions.Generic.LexBIGServiceConvenienceMethods;
import org.LexGrid.LexBIG.Extensions.Generic.MappingExtension;
import org.LexGrid.LexBIG.Extensions.Generic.MappingExtension.Mapping;
import org.LexGrid.LexBIG.Extensions.Generic.MappingExtension.Mapping.SearchContext;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.SearchDesignationOption;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.Utility.Iterators.ResolvedConceptReferencesIterator;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.junit.Before;
import org.junit.Test;
import org.lexevs.tree.model.LexEvsTree;
import org.lexevs.tree.model.LexEvsTreeNode;
import org.lexevs.tree.service.TreeService;
import org.lexevs.tree.service.TreeServiceFactory;

public class LexBIGExtensionTests extends ServiceTestCase {

	private LexBIGService lbs;
	private MappingExtension mappingExtension;
	private TreeService treeExtension;

	@Before
	public void setUp(){
		lbs = LexEVSServiceHolder.instance().getLexEVSAppService();
		try {
			mappingExtension = (MappingExtension)lbs.getGenericExtension("MappingExtension");
		     treeExtension = TreeServiceFactory.getInstance().getTreeService(lbs);
		} catch (LBException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testMappingExtension() throws Exception {
	ResolvedConceptReferencesIterator itr;
	CodingSchemeVersionOrTag csvt = new CodingSchemeVersionOrTag();
	csvt.setVersion(MAPPING_VERSION);
		Mapping mapping =
				mappingExtension.getMapping(MAPPING_SCHEME, csvt, "NCIt_to_ChEBI_Mapping");

			if (mapping != null) {
				mapping = mapping.restrictToMatchingDesignations(
							MAPPING_TEXT, SearchDesignationOption.ALL, "LuceneQuery", null, SearchContext.SOURCE_OR_TARGET_CODES);
				itr = mapping.resolveMapping();
		System.out.println(itr.next());
				assertTrue(itr.numberRemaining() > 0);

			}
	}
	
// Comment this in when and if the plugin extension is being tested.	
	@Test
	public void testTreeServiceDistributed() throws Exception{
		 LexEvsTree tree = null;
	        CodingSchemeVersionOrTag csvt = new CodingSchemeVersionOrTag();
	        csvt.setVersion("TestForMultiNamespace");
	         tree = treeExtension.getTree("npo", csvt, "NPO_1607", "npo", "is_a");
	            
	            LexEvsTreeNode focusNode = tree.getCurrentFocus();
	            focusNode.setNamespace("npo");
	            List<LexEvsTreeNode> nodeList = treeExtension.getEvsTreeConverter().buildEvsTreePathFromRootTree(focusNode);
	            assertTrue(nodeList.size() > 0);
	}
}
	
