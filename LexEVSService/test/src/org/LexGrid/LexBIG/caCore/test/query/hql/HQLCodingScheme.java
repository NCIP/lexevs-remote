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
