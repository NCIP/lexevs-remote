package org.LexGrid.LexBIG.distributed.test.bugs;

import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Exceptions.LBParameterException;
import org.LexGrid.LexBIG.Extensions.Generic.MappingExtension;
import org.LexGrid.LexBIG.Extensions.Generic.MappingExtension.Mapping;
import org.LexGrid.LexBIG.Extensions.Generic.MappingExtension.Mapping.SearchContext;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.Utility.Iterators.ResolvedConceptReferencesIterator;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.junit.Before;
import org.junit.Test;

public class TestLEXEVS_3465 extends ServiceTestCase {
	LexEVSApplicationService svc;

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		svc = LexEVSServiceHolder.instance().getLexEVSAppService();
	}

	@Test
	public void test() throws LBException {

		MappingExtension mappingExtension = (MappingExtension) svc.getGenericExtension("MappingExtension");
		
		Mapping mapping = mappingExtension.getMapping(
				ITERATOR_MAPPING_SCHEME, 
				Constructors.createCodingSchemeVersionOrTagFromVersion(ITERATOR_MAPPING_SCHEME_VERSION), 
				null);
		
		mapping.restrictToCodes(Constructors.createConceptReferenceList(ITERATOR_MAPPING_SCHEME_CODE), SearchContext.TARGET_CODES);
		ResolvedConceptReferencesIterator itr = mapping.resolveMapping();
		
		int count = 0;
		while(itr.hasNext()) {
			ResolvedConceptReference next = itr.next();
			assertNotNull(next);
			count++;
		}
		
		assertEquals(4,count);
	}

}
