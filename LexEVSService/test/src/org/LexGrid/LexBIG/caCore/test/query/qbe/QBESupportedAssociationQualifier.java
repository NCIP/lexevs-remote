/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.test.query.qbe;

import gov.nih.nci.system.applicationservice.ApplicationService;

import java.util.List;

import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.codingSchemes.CodingScheme;
import org.LexGrid.naming.Mappings;
import org.LexGrid.naming.SupportedAssociationQualifier;

public class QBESupportedAssociationQualifier extends ServiceTestCase
{
	private final String test_id = "QBE Tests";
	
	@Override
	protected String getTestID() {
		return test_id;
	}
	
	public void testGetSupportedAssociationQualifierByName() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		SupportedAssociationQualifier saq = new SupportedAssociationQualifier();
		saq.setLocalId("owl:allValuesFrom");
		
		List<SupportedAssociationQualifier> sourceList = service.search(SupportedAssociationQualifier.class, saq);	
		
		assertTrue(sourceList != null);	
		
		SupportedAssociationQualifier value = sourceList.get(0);
		assertTrue(value.getLocalId().equals("owl:allValuesFrom"));	
		assertTrue(value.getUri().equals("http://www.w3.org/2002/07/owl#allValuesFrom"));	
	}
	
	public void testGetSupportedAssociationQualifierByWrongName() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		SupportedAssociationQualifier saq = new SupportedAssociationQualifier();
		saq.setLocalId("allValuesFrom_WRONG_NAME");
		
		List<SupportedAssociationQualifier> sourceList = service.search(SupportedAssociationQualifier.class, saq);	
		
		assertTrue(sourceList != null);	
		
		assertTrue(sourceList != null);	
		assertTrue(sourceList.size() == 0);			
	}
	
	public void testGetSupportedAssociationQualifierByWildCard() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		SupportedAssociationQualifier saq = new SupportedAssociationQualifier();
		saq.setLocalId("*ValuesFrom");
		
		List<SupportedAssociationQualifier> sourceList = service.search(SupportedAssociationQualifier.class, saq);	
		
		assertTrue(sourceList != null);	
		
		//Should return at least allValuesFrom and someValuesFrom
		assertTrue(sourceList.size() >= 2);	
		
	}

	public void testSearchCodingSchemeBySupportedAssociationQualifierWrongName() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CodingScheme cs = new CodingScheme();
		cs.setCodingSchemeName(ServiceTestCase.META_SCHEME);
		cs.setRepresentsVersion(ServiceTestCase.META_VERSION);
		
		Mappings mappings = new Mappings();
		SupportedAssociationQualifier saq = new SupportedAssociationQualifier();
		saq.setLocalId("HCD_WRONG_NAME");
		
		mappings.addSupportedAssociationQualifier(saq);
		cs.setMappings(mappings);

		List<CodingScheme> sourceList = service.search(CodingScheme.class, cs);	
	
		assertTrue(sourceList != null);	
		assertTrue(sourceList.size() == 0);				
	}
}