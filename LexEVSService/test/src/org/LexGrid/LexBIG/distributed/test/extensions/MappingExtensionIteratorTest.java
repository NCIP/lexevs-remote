package org.LexGrid.LexBIG.distributed.test.extensions;

import gov.nih.nci.system.client.ApplicationServiceProvider;

import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Extensions.Generic.LexBIGServiceConvenienceMethods;
import org.LexGrid.LexBIG.Extensions.Generic.MappingExtension;
import org.LexGrid.LexBIG.Extensions.Generic.MappingExtension.Mapping;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.SearchDesignationOption;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.Utility.Iterators.ResolvedConceptReferencesIterator;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSDistributed;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;



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
}
