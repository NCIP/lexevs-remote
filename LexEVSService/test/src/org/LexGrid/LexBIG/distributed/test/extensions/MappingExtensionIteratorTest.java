package org.LexGrid.LexBIG.distributed.test.extensions;

import gov.nih.nci.system.client.ApplicationServiceProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.LexGrid.LexBIG.DataModel.Collections.NameAndValueList;
import org.LexGrid.LexBIG.DataModel.Core.AssociatedConcept;
import org.LexGrid.LexBIG.DataModel.Core.Association;
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
import org.LexGrid.LexBIG.Impl.testUtility.ServiceHolder;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.SearchDesignationOption;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.Utility.RemoveFromDistributedTests;
import org.LexGrid.LexBIG.Utility.Iterators.ResolvedConceptReferencesIterator;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
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
				"urn:oid:mapping:sample", 
				Constructors.createCodingSchemeVersionOrTagFromVersion("1.0"), 
				"AutoToGMPMappings");
		
//		mapping = mapping.restrictToRelationship("mapsTo", SearchDesignationOption.ALL, "LuceneQuery", null,null);
		
		ResolvedConceptReferencesIterator itr = mapping.resolveMapping();
		int count = 0;
		int remaining = itr.numberRemaining();
		while(itr.hasNext()){
		ResolvedConceptReference ref = itr.next();
		System.out.println(ref.getCode());
		count++;
		}
		assertEquals(count, remaining);
	}
	

	public void testGetResourceSummariesTargetRestrictionCorrectNumRemaining() throws Exception {
		
		LexEVSApplicationService lbs = (LexEVSApplicationService)ApplicationServiceProvider.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl, "EvsServiceInfo");
		MappingExtension mappingExtension = (MappingExtension) lbs.getGenericExtension("MappingExtension");
		Mapping mapping = mappingExtension.getMapping(
				"urn:oid:mapping:sample", 
				Constructors.createCodingSchemeVersionOrTagFromVersion("1.0"), null);
		
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
	
	public void testGetResourceSummariesTargetRestrictionNoSortCorrectNumRemaining() throws Exception {
		
		LexEVSApplicationService lbs = (LexEVSApplicationService)ApplicationServiceProvider.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl, "EvsServiceInfo");
		MappingExtension mappingExtension = (MappingExtension) lbs.getGenericExtension("MappingExtension");
		Mapping mapping = mappingExtension.getMapping(
				"urn:oid:mapping:sample", 
				Constructors.createCodingSchemeVersionOrTagFromVersion("1.0"), null);
		
		mapping = mapping.restrictToCodes(Constructors.createConceptReferenceList("E0001", "GermanMadePartsNamespace", null), SearchContext.TARGET_CODES);

		ResolvedConceptReferencesIterator itr = mapping.resolveMapping(null);
		
		int count = 0;
		int numberRemaining = itr.numberRemaining();
		while(itr.hasNext()){
			ResolvedConceptReference ref = itr.next();
			System.out.println(ref.getCode());
			count++;
		}
		
		assertEquals(count, numberRemaining);
	}
	public void testHasQualifiers() throws Exception {
		LexEVSDistributed lbs = null;
		lbs = (LexEVSDistributed)ApplicationServiceProvider.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl, "EvsServiceInfo");
		MappingExtension mappingExtension = (MappingExtension) lbs.getGenericExtension("MappingExtension");
		
		ResolvedConceptReferencesIterator itr = mappingExtension.resolveMapping(
				"urn:oid:mapping:sample", 
				Constructors.createCodingSchemeVersionOrTagFromVersion("1.0"), 
				"AutoToGMPMappings", 
				null);
		
		Map<String,NameAndValueList> foundQuals = new HashMap<String,NameAndValueList>();
		
		while(itr.hasNext()) {
			ResolvedConceptReference next = itr.next();
			System.out.println(next.getCode());
			for(Association assoc : next.getSourceOf().getAssociation()) {
				for(AssociatedConcept ac : assoc.getAssociatedConcepts().getAssociatedConcept()) {
					if(ac.getAssociationQualifiers() != null && ac.getAssociationQualifiers().getNameAndValueCount() > 0) {
						foundQuals.put(ac.getCode(), ac.getAssociationQualifiers());
					}
				}
			}
		}

		assertEquals(2,foundQuals.size());
	}
	
	@Test
	public void testResolveMappingCount() throws Exception {
		LexEVSDistributed lbs = null;
		lbs = (LexEVSDistributed)ApplicationServiceProvider.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl, "EvsServiceInfo");
		MappingExtension mappingExtension = (MappingExtension) lbs.getGenericExtension("MappingExtension");
		
		ResolvedConceptReferencesIterator itr = mappingExtension.resolveMapping(
				"urn:oid:mapping:sample", 
				Constructors.createCodingSchemeVersionOrTagFromVersion("1.0"), 
				"AutoToGMPMappings",  
				null);
		
		int count = 0;
		while(itr.hasNext()) {
			ResolvedConceptReference next = itr.next();
			assertNotNull(next);
			count++;
		}
		
		assertEquals(6,count);
	}
}
