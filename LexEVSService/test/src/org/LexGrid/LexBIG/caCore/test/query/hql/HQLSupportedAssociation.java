/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.test.query.hql;

import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.query.hibernate.HQLCriteria;

import java.util.List;

import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.naming.SupportedAssociation;

public class HQLSupportedAssociation extends ServiceTestCase
{
	private final String test_id = "HQLTests";
	
	@Override
	protected String getTestID() {
		return test_id;
	}

	
	public void testGetSupportedAssociation() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
				
		HQLCriteria hql = new HQLCriteria("FROM org.LexGrid.naming.SupportedAssociation");
		List<SupportedAssociation> result = service.query(hql);
		
		assertTrue(result != null);
		assertTrue(result.size() > 0);
	}
	
	public void testGetSupportedAssociationByName() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
				
		HQLCriteria hql = new HQLCriteria("FROM org.LexGrid.naming.SupportedAssociation" +
				" as sa WHERE sa._localId = 'Concept_In_Subset'");
		List<SupportedAssociation> result = service.query(hql);
		
		assertTrue(result != null);
		assertTrue(result.size() > 0);
		assertTrue(result.get(0).getLocalId().equals("Concept_In_Subset"));
	}
	
	public void testGetSupportedAssociationByURN() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		HQLCriteria hql = new HQLCriteria("FROM org.LexGrid.naming.SupportedAssociation" +
				" as sa WHERE sa._uri = 'http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#R156'");
		List<SupportedAssociation> result = service.query(hql);
		
		assertTrue(result != null);
		assertTrue(result.size() > 0);
		assertTrue(result.get(0).getLocalId().equals("Allele_Absent_From_Wild-type_Chromosomal_Location"));
		assertTrue(result.get(0).getUri().equals("http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#R156"));
	}
	
	public void testGetSupportedAssociationByWrongURN() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		HQLCriteria hql = new HQLCriteria("FROM org.LexGrid.naming.SupportedAssociation" +
				" as sa WHERE sa._uri = 'http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#Anatomic_Structure_Has_Location_WRONG_URN'");
		List<SupportedAssociation> result = service.query(hql);
		
		assertTrue(result != null);
		assertTrue(result.size() == 0);
	}
	
	public void testGetSupportedAssociationByURNByWildCard() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		HQLCriteria hql = new HQLCriteria("FROM org.LexGrid.naming.SupportedAssociation" +
				" as sa WHERE sa._uri LIKE '%.nih.gov/xml/owl/EVS/Thesaurus.owl#R156'");
		List<SupportedAssociation> result = service.query(hql);
		
		assertTrue(result != null);
		assertTrue(result.size() > 0);
		assertTrue(result.get(0).getLocalId().equals("Allele_Absent_From_Wild-type_Chromosomal_Location"));
		assertTrue(result.get(0).getUri().equals("http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#R156"));
	}
	
	public void testGetSupportedAssociationByURNByWrongWildCard() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		HQLCriteria hql = new HQLCriteria("FROM org.LexGrid.naming.SupportedAssociation" +
				" as sa WHERE sa._uri LIKE '%Thesaurus.owl#Anatomic_Structure_Has_Location_WRONG_URN'");
		List<SupportedAssociation> result = service.query(hql);
		
		assertTrue(result != null);
		assertTrue(result.size() == 0);
	}

}