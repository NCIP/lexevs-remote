package org.LexGrid.LexBIG.distributed.test.bugs;

import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Exceptions.LBParameterException;
import org.LexGrid.LexBIG.Extensions.Generic.MappingExtension;
import org.LexGrid.LexBIG.Extensions.Generic.MappingExtension.Mapping;
import org.LexGrid.LexBIG.Extensions.Generic.MappingExtension.Mapping.SearchContext;
import org.LexGrid.LexBIG.Impl.LexBIGServiceImpl;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.SearchDesignationOption;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.Utility.Iterators.ResolvedConceptReferencesIterator;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.junit.Before;
import org.junit.Test;

import gov.nih.nci.system.client.ApplicationServiceProvider;

public class TestLEXEVS_3465 extends ServiceTestCase {

	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void test() throws LBException {
		
		LexEVSApplicationService lbs = LexEVSServiceHolder.instance().getLexEVSAppService();
		MappingExtension mappingExtension = (MappingExtension) lbs.getGenericExtension("MappingExtension");
		Mapping mapping = mappingExtension.getMapping(
				ITERATOR_MAPPING_SCHEME, 
				Constructors.createCodingSchemeVersionOrTagFromVersion(ITERATOR_MAPPING_SCHEME_VERSION), null);
		
		mapping = mapping.restrictToMatchingDesignations("Cholera, unspecified", SearchDesignationOption.PREFERRED_ONLY,"LuceneQuery", null, SearchContext.TARGET_CODES);

		ResolvedConceptReferencesIterator itr = mapping.resolveMapping(null);
		
		int count = 0;
		int numberRemaining = itr.numberRemaining();
		while(itr.hasNext()) {
			ResolvedConceptReference next = itr.next();
			assertNotNull(next);
			System.out.println(next.getCode());
			count++;
			if(count > 5){fail("too many results");}
		}
		
		assertEquals(count, numberRemaining);
	}
	
	@Test
	public void test2() throws LBException {
		LexEVSApplicationService lbs = LexEVSServiceHolder.instance().getLexEVSAppService();

		MappingExtension mappingExtension = (MappingExtension) lbs.getGenericExtension("MappingExtension");
		
		Mapping mapping = mappingExtension.getMapping(
				ITERATOR_MAPPING_SCHEME, 
				Constructors.createCodingSchemeVersionOrTagFromVersion(ITERATOR_MAPPING_SCHEME_VERSION), 
				null);
		
		mapping = mapping.restrictToCodes(Constructors.createConceptReferenceList(ITERATOR_MAPPING_SCHEME_CODE), SearchContext.TARGET_CODES);
		long start = System.currentTimeMillis();
		ResolvedConceptReferencesIterator itr = mapping.resolveMapping();
		long stop = System.currentTimeMillis() - start;
		System.out.println(stop/1000);
		int count = 0;
		while(itr.hasNext()) {
			ResolvedConceptReference next = itr.next();
			assertNotNull(next);
			count++;
		}
		assertEquals(5,count);
	}

}
