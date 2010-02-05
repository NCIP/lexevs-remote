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
import gov.nih.nci.system.query.cql.CQLObject;
import gov.nih.nci.system.query.cql.CQLQuery;
import gov.nih.nci.system.query.hibernate.HQLCriteria;

import java.util.List;

import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.codingSchemes.CodingScheme;

public class HQLCodingScheme extends ServiceTestCase
{
	private final String test_id = "HQLTests";
	
	@Override
	protected String getTestID() {
		return test_id;
	}

	
	public void testGetCodingSchemes() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
				
		HQLCriteria hql = new HQLCriteria("FROM org.LexGrid.codingSchemes.CodingScheme");
		List<CodingScheme> codingSchemes = service.query(hql);
		
		assertTrue(codingSchemes != null);
		assertTrue(codingSchemes.size() > 0);
	}
	
	public void testGetCodingSchemesByNameAndVersion() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
				
		HQLCriteria hql = new HQLCriteria("FROM org.LexGrid.codingSchemes.CodingScheme" +
				" as cs WHERE cs._codingSchemeName = '" + ServiceTestCase.THES_SCHEME +
				"' and cs._representsVersion = '" + ServiceTestCase.THES_VERSION + "'");
		List<CodingScheme> codingSchemes = service.query(hql);
		
		assertTrue(codingSchemes != null);
		assertTrue(codingSchemes.size() == 1);
		assertTrue(codingSchemes.get(0).getCodingSchemeName().equals(ServiceTestCase.THES_SCHEME));
		assertTrue(codingSchemes.get(0).getRepresentsVersion().equals(ServiceTestCase.THES_VERSION));	
	}
	
	public void testGetCodingSchemesByNameAndVersionByWildCard() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		HQLCriteria hql = new HQLCriteria("FROM org.LexGrid.codingSchemes.CodingScheme" +
				" as cs WHERE cs._codingSchemeName LIKE '" + "%Thesaurus%" +
				"' and cs._representsVersion = '" + ServiceTestCase.THES_VERSION + "'");
		List<CodingScheme> codingSchemes = service.query(hql);
		
		assertTrue(codingSchemes != null);
		assertTrue(codingSchemes.size() == 1);
		assertTrue(codingSchemes.get(0).getCodingSchemeName().equals(ServiceTestCase.THES_SCHEME));
		assertTrue(codingSchemes.get(0).getRepresentsVersion().equals(ServiceTestCase.THES_VERSION));	
	}

}
