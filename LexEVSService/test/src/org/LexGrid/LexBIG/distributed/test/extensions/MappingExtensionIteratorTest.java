package org.LexGrid.LexBIG.distributed.test.extensions;

import gov.nih.nci.system.client.ApplicationServiceProvider;

import java.util.ArrayList;
import java.util.List;

import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Extensions.Generic.LexBIGServiceConvenienceMethods;
import org.LexGrid.LexBIG.Extensions.Generic.MappingExtension;
import org.LexGrid.LexBIG.Extensions.Generic.MappingExtension.Direction;
import org.LexGrid.LexBIG.Extensions.Generic.MappingExtension.Mapping;
import org.LexGrid.LexBIG.Extensions.Generic.MappingExtension.MappingSortOption;
import org.LexGrid.LexBIG.Extensions.Generic.MappingExtension.MappingSortOptionName;
import org.LexGrid.LexBIG.Extensions.Generic.MappingExtension.Mapping.SearchContext;
import org.LexGrid.LexBIG.Impl.LexBIGServiceImpl;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.SearchDesignationOption;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.Utility.RemoveFromDistributedTests;
import org.LexGrid.LexBIG.Utility.Iterators.ResolvedConceptReferencesIterator;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSDistributed;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.junit.Test;
import org.junit.experimental.categories.Category;



public class MappingExtensionIteratorTest extends org.LexGrid.LexBIG.testUtil.ServiceTestCase {
	
	public void testResolveMappingWithRestrictionCountGreaterThanOne() throws Exception {
		LexEVSDistributed lbs = null;
			lbs = (LexEVSDistributed)ApplicationServiceProvider.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl, "EvsServiceInfo");

		MappingExtension mappingExtension = (MappingExtension) lbs.getGenericExtension("MappingExtension");
	
		Mapping mapping = mappingExtension.getMapping(
				MAPPING_SCHEME, 
				Constructors.createCodingSchemeVersionOrTagFromVersion(MAPPING_VERSION), 
				"AutoToGMPMappings");
		
		mapping = mapping.restrictToRelationship("uses", SearchDesignationOption.ALL, "LuceneQuery", null,null);
		
		ResolvedConceptReferencesIterator itr = mapping.resolveMapping();
		itr.next();
		assertEquals(1, itr.numberRemaining());
	}
	

	public void testGetResourceSummariesTargetRestrictionCorrectNumRemaining() throws Exception {
		
		LexBIGService lbs = LexBIGServiceImpl.defaultInstance();
		MappingExtension mappingExtension = (MappingExtension) lbs.getGenericExtension("MappingExtension");
	
		Mapping mapping = mappingExtension.getMapping(
				MAPPING_SCHEME, 
				Constructors.createCodingSchemeVersionOrTagFromVersion(MAPPING_VERSION), null);
		
		mapping = mapping.restrictToCodes(Constructors.createConceptReferenceList("E0001", "GermanMadePartsNamespace", null), SearchContext.TARGET_CODES);
		MappingSortOption mapOp = new MappingSortOption(MappingSortOptionName.SOURCE_CODE, Direction.ASC);
		List<MappingSortOption> list = new ArrayList<MappingSortOption>();
		list.add(mapOp);
		ResolvedConceptReferencesIterator itr = mapping.resolveMapping(list);
		
		int count = 0;
		int numberRemaining = itr.numberRemaining();
		while(itr.hasNext()){
			ResolvedConceptReference ref = itr.next();
			System.out.println(ref.getCode());
			count++;
		}
		
		assertEquals(count, numberRemaining);
	}
}
