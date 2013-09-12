/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.test.query.getAssociation;

import gov.nih.nci.system.applicationservice.ApplicationService;

import java.util.List;

import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.codingSchemes.CodingScheme;
import org.LexGrid.naming.Mappings;
import org.apache.commons.lang.ArrayUtils;

public class GetAssociationCodingScheme extends ServiceTestCase
{
	private final String test_id = "CQLTests";
	
	@Override
	protected String getTestID() {
		return test_id;
	}
	
	public void testGetCodingSchemeAssociationsLocalNames() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CodingScheme cs = new CodingScheme();
		cs.setCodingSchemeName(ServiceTestCase.THES_SCHEME);
		cs.setRepresentsVersion(ServiceTestCase.THES_VERSION);	
		
		List<Object> val = service.getAssociation(cs, "_localNameList");
			
		assertTrue(val != null);
		
		//Should return one List of LocalNameLists
		assertTrue(val.size() == 1);
		
		//Should be a List
		assertTrue(val.get(0) instanceof List);

		List localNames = (List)val.get(0);
		String[] expectedLocalNames = new String[]{"NCI Thesaurus", "NCI_Thesaurus", "Thesaurus.owl", 
				"ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl", "NCI", "urn:oid:2.16.840.1.113883.3.26.1.1"};
		
		Object[] foundLocalNames = (Object[])localNames.toArray();

		assertTrue(ArrayUtils.isSameLength(expectedLocalNames, foundLocalNames));
		
		for(int i = 0; i < foundLocalNames.length; i++) {
			assertTrue(ArrayUtils.contains(expectedLocalNames, foundLocalNames[i]));
		}
		
		for(int i = 0; i < expectedLocalNames.length; i++) {
			assertTrue(ArrayUtils.contains(foundLocalNames, expectedLocalNames[i]));
		}		
	}
	
	public void testGetCodingSchemeAssociationsMappings() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CodingScheme cs = new CodingScheme();
		cs.setCodingSchemeName(ServiceTestCase.THES_SCHEME);
		cs.setRepresentsVersion(ServiceTestCase.THES_VERSION);	
		
		List<Object> val = service.getAssociation(cs, "_mappings");
			
		assertTrue(val != null);
		
		//Should return one Mapping Object
		assertTrue(val.size() == 1);
		
		//Should be a Mapping Object
		assertTrue(val.get(0) instanceof Mappings);
		
		Mappings mappings = (Mappings)val.get(0);
		
		//check a few things to make sure its populated
		assertTrue(mappings.getSupportedAssociation().length > 0);
		
		//No supported formats in NCI Thes 08.10e... weird...
		//assertTrue(mappings.getSupportedFormat().length > 0);
		
		assertTrue(mappings.getSupportedAssociationQualifier().length > 0);
	}
}
