package org.LexGrid.LexBIG.distributed.test.bugs;

import static org.junit.Assert.*;

import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Extensions.Generic.MappingExtension;
import org.LexGrid.LexBIG.Extensions.Generic.MappingExtension.Mapping;
import org.LexGrid.LexBIG.Extensions.Generic.MappingExtension.Mapping.SearchContext;
import org.LexGrid.LexBIG.Impl.Extensions.tree.service.TreeService;
import org.LexGrid.LexBIG.Impl.Extensions.tree.service.TreeServiceFactory;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.SearchDesignationOption;
import org.LexGrid.LexBIG.Utility.Iterators.ResolvedConceptReferencesIterator;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.junit.Before;
import org.junit.Test;

public class TestLEXEVS_4613 extends ServiceTestCase {
	
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
				mappingExtension.getMapping(MAPPING_SCHEME, csvt, "NCIt_to_ChEBI_Mappings");

			if (mapping != null) {
				mapping = mapping.restrictToMatchingDesignations(
							MAPPING_TEXT, SearchDesignationOption.ALL, "LuceneQuery", null, SearchContext.SOURCE_OR_TARGET_CODES);
				itr = mapping.resolveMapping();
		System.out.println(itr.next());
				assertTrue(itr.numberRemaining() > 0);

			}
	}

}
