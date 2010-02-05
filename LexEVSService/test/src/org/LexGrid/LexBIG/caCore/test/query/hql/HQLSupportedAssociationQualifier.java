/*******************************************************************************
 * Copyright: (c) 2004-2009 Mayo Foundation for Medical Education and 
 * Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
 * triple-shield Mayo logo are trademarks and service marks of MFMER.
 * 
 * Except as contained in the copyright notice above, or as used to identify 
 * MFMER as the author of this software, the trade names, trademarks, service
 * marks, or product names of the copyright holder shall not be used in
 * advertising, promotion or otherwise in connection with this software without
 * prior written authorization of the copyright holder.
 *   
 * Licensed under the Eclipse Public License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *   
 *  		http://www.eclipse.org/legal/epl-v10.html
 * 
 *  		
 *******************************************************************************/
package org.LexGrid.LexBIG.caCore.test.query.hql;

import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.query.hibernate.HQLCriteria;

import java.util.List;

import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.naming.SupportedAssociationQualifier;

public class HQLSupportedAssociationQualifier extends ServiceTestCase
{
	private final String test_id = "HQLTests";
	
	@Override
	protected String getTestID() {
		return test_id;
	}

	
	public void testGetSupportedAssociationQualifier() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
				
		HQLCriteria hql = new HQLCriteria("FROM org.LexGrid.naming.SupportedAssociationQualifier");
		List<SupportedAssociationQualifier> result = service.query(hql);
		
		assertTrue(result != null);
		assertTrue(result.size() > 0);
	}

	public void testGetSupportedAssociationQualifierByName() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
				
		HQLCriteria hql = new HQLCriteria("FROM org.LexGrid.naming.SupportedAssociationQualifier" +
				" as sa WHERE sa._localId = 'owl:allValuesFrom'");
		List<SupportedAssociationQualifier> result = service.query(hql);
		
		assertTrue(result != null);
		assertTrue(result.size() > 0);
		assertTrue(result.get(0).getLocalId().equals("owl:allValuesFrom"));
	}
	
	
	public void testGetSupportedAssociationQualifierByURN() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		HQLCriteria hql = new HQLCriteria("FROM org.LexGrid.naming.SupportedAssociationQualifier" +
				" as sa WHERE sa._uri = 'http://www.w3.org/2002/07/owl#allValuesFrom'");
		List<SupportedAssociationQualifier> result = service.query(hql);
		
		assertTrue(result != null);
		assertTrue(result.size() > 0);
		assertTrue(result.get(0).getLocalId().contains("allValuesFrom"));
		assertTrue(result.get(0).getUri().equals("http://www.w3.org/2002/07/owl#allValuesFrom"));
	}
	
	public void testGetSupportedAssociationQualifierByWrongURN() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		HQLCriteria hql = new HQLCriteria("FROM org.LexGrid.naming.SupportedAssociationQualifier" +
				" as sa WHERE sa._uri = 'http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#Anatomic_Structure_Has_Location_WRONG_URN'");
		List<SupportedAssociationQualifier> result = service.query(hql);
		
		assertTrue(result != null);
		assertTrue(result.size() == 0);
	}
	
	public void testGetSupportedAssociationQualifierByURNByWildCard() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		HQLCriteria hql = new HQLCriteria("FROM org.LexGrid.naming.SupportedAssociationQualifier" +
				" as sa WHERE sa._uri LIKE '%/2002/07/owl#allValuesFrom'");
		List<SupportedAssociationQualifier> result = service.query(hql);
		
		assertTrue(result != null);
		assertTrue(result.size() > 0);
		assertTrue(result.get(0).getLocalId().contains("allValuesFrom"));
		assertTrue(result.get(0).getUri().equals("http://www.w3.org/2002/07/owl#allValuesFrom"));
	}
	
	public void testGetSupportedAssociationQualifierByURNByWrongWildCard() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		HQLCriteria hql = new HQLCriteria("FROM org.LexGrid.naming.SupportedAssociationQualifier" +
				" as sa WHERE sa._uri LIKE '%Thesaurus.owl#Anatomic_Structure_Has_Location_WRONG_URN'");
		List<SupportedAssociationQualifier> result = service.query(hql);
		
		assertTrue(result != null);
		assertTrue(result.size() == 0);
	}

}