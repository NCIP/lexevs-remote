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
import org.LexGrid.naming.SupportedAssociation;

public class QBESupportedAssociation extends ServiceTestCase
{
	private final String test_id = "QBE Tests";
	
	@Override
	protected String getTestID() {
		return test_id;
	}
	
	public void testGetSupportedAssociationByName() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		SupportedAssociation ss = new SupportedAssociation();
		ss.setLocalId("AllDifferent");

		List<SupportedAssociation> sourceList = service.search(SupportedAssociation.class, ss);	
		
		assertTrue(sourceList != null);	
	
		SupportedAssociation source = sourceList.get(0);
		assertTrue(source.getLocalId().equals("AllDifferent"));			
	}
	
	public void testGetSupportedAssociationByURN() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		SupportedAssociation ss = new SupportedAssociation();
		ss.setUri("http://www.w3.org/2002/07/owl#AllDifferent");

		List<SupportedAssociation> sourceList = service.search(SupportedAssociation.class, ss);	
		
		assertTrue(sourceList != null);	
	
		SupportedAssociation source = sourceList.get(0);
		assertTrue(source.getUri().equals("http://www.w3.org/2002/07/owl#AllDifferent"));			
	}
	
	public void testGetSupportedAssociationByNameAndURN() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		SupportedAssociation ss = new SupportedAssociation();
		ss.setLocalId("AllDifferent");
		ss.setUri("http://www.w3.org/2002/07/owl#AllDifferent");

		List<SupportedAssociation> sourceList = service.search(SupportedAssociation.class, ss);	
		
		assertTrue(sourceList != null);	
	
		SupportedAssociation source = sourceList.get(0);
		assertTrue(source.getLocalId().equals("AllDifferent"));
		assertTrue(source.getUri().equals("http://www.w3.org/2002/07/owl#AllDifferent"));			
	}
	
	public void testGetSupportedAssociationByWrongName() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		SupportedAssociation ss = new SupportedAssociation();
		ss.setLocalId("positively_regulates_WRONG_NAME");
		
		List<SupportedAssociation> sourceList = service.search(SupportedAssociation.class, ss);	
		
		assertTrue(sourceList != null);	
		assertTrue(sourceList.size() == 0);			
	}
	
	public void testGetSupportedAssociationByWrongURN() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		SupportedAssociation ss = new SupportedAssociation();
		ss.setUri("http://www.geneontology.org/formats/oboInOwl#R6_WRONG_URN");
		
		List<SupportedAssociation> sourceList = service.search(SupportedAssociation.class, ss);	
		
		assertTrue(sourceList != null);	
		assertTrue(sourceList.size() == 0);			
	}
	
	public void testGetSupportedAssociationByWildCard() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		SupportedAssociation ss = new SupportedAssociation();
		ss.setLocalId("AllD*ferent");
		
		List<SupportedAssociation> sourceList = service.search(SupportedAssociation.class, ss);	
		
		assertTrue(sourceList != null);	
		
		SupportedAssociation source = sourceList.get(0);
		assertTrue(source.getLocalId().equals("AllDifferent"));			
	}
		
	public void testSearchCodingSchemeBySupportedAssociationName() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CodingScheme cs = new CodingScheme();
		cs.setCodingSchemeName(ServiceTestCase.GO_SCHEME);
		cs.setRepresentsVersion(ServiceTestCase.GO_VERSION);
		
		Mappings mappings = new Mappings();
		SupportedAssociation scs = new SupportedAssociation();
		scs.setLocalId("negatively regulates");
		
		mappings.addSupportedAssociation(scs);
		cs.setMappings(mappings);

		List<CodingScheme> sourceList = service.search(CodingScheme.class, cs);	
	
		assertTrue(sourceList != null);	
		assertEquals(1,sourceList.size());
				
		CodingScheme scheme = sourceList.get(0);
		assertTrue(scheme.getCodingSchemeName().equals(ServiceTestCase.GO_SCHEME));
		assertTrue(scheme.getRepresentsVersion().equals(ServiceTestCase.GO_VERSION));
	}
	
	public void testSearchCodingSchemeBySupportedAssociationWrongName() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CodingScheme cs = new CodingScheme();
		cs.setCodingSchemeName(ServiceTestCase.GO_SCHEME);
		cs.setRepresentsVersion(ServiceTestCase.GO_VERSION);
		
		Mappings mappings = new Mappings();
		SupportedAssociation scs = new SupportedAssociation();
		scs.setLocalId("regulates_WRONG_NAME");
		
		mappings.addSupportedAssociation(scs);
		cs.setMappings(mappings);

		List<CodingScheme> sourceList = service.search(CodingScheme.class, cs);	
	
		assertTrue(sourceList != null);	
		assertTrue(sourceList.size() == 0);		
	}
	
	public void testSearchCodingSchemeBySupportedAssociationWildCard() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CodingScheme cs = new CodingScheme();
		cs.setCodingSchemeName(ServiceTestCase.GO_SCHEME);
		cs.setRepresentsVersion(ServiceTestCase.GO_VERSION);
		
		Mappings mappings = new Mappings();
		SupportedAssociation scs = new SupportedAssociation();
		scs.setLocalId("negative*egulates");
		
		mappings.addSupportedAssociation(scs);
		cs.setMappings(mappings);

		List<CodingScheme> sourceList = service.search(CodingScheme.class, cs);	
	
		assertTrue(sourceList != null);	
		assertTrue(sourceList.size() == 1);
				
		CodingScheme scheme = sourceList.get(0);
		assertTrue(scheme.getCodingSchemeName().equals(ServiceTestCase.GO_SCHEME));
		assertTrue(scheme.getRepresentsVersion().equals(ServiceTestCase.GO_VERSION));
	}
	
	/* duplicate of above
	public void testGetSupportedAssociationFromCodingSchemeUsingNameWildCard() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CodingScheme cs = new CodingScheme();
		cs.setCodingSchemeName(ServiceTestCase.GO_SCHEME);
		cs.setRepresentsVersion(ServiceTestCase.GO_VERSION);
		
		Mappings mappings = new Mappings();
		SupportedAssociation scs = new SupportedAssociation();
		scs.setLocalId("*of*");
		
		mappings.addSupportedAssociation(scs);
		cs.setMappings(mappings);

		List<CodingScheme> sourceList = service.search(CodingScheme.class, cs);	
	
		assertTrue(sourceList != null);	
		assertTrue(sourceList.size() == 1);
				
		CodingScheme scheme = sourceList.get(0);
		assertTrue(scheme.getCodingSchemeName().equals(ServiceTestCase.GO_SCHEME));
		assertTrue(scheme.getRepresentsVersion().equals(ServiceTestCase.GO_VERSION));		
	}
	*/
}