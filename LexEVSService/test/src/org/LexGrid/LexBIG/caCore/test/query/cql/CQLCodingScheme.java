/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.test.query.cql;

import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.query.cql.CQLAttribute;
import gov.nih.nci.system.query.cql.CQLObject;
import gov.nih.nci.system.query.cql.CQLPredicate;
import gov.nih.nci.system.query.cql.CQLQuery;

import java.util.List;

import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.codingSchemes.CodingScheme;

public class CQLCodingScheme extends ServiceTestCase
{
	private final String test_id = "CQLTests";
	
	@Override
	protected String getTestID() {
		return test_id;
	}
	
	public void testGetCodingSchemes() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CQLQuery query = new CQLQuery();	
		CQLObject target = new CQLObject();
		
		target.setName("org.LexGrid.codingSchemes.CodingScheme");
		
		query.setTarget(target);
		List<CodingScheme> codingSchemes = service.query(query);
		
		assertTrue(codingSchemes != null);
		assertTrue(codingSchemes.size() > 0);
	}
	
	public void testGetCodingSchemeByNameAndVersion() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CQLQuery query = new CQLQuery();	
		CQLObject target = new CQLObject();
		
		target.setName("org.LexGrid.codingSchemes.CodingScheme");
		CQLAttribute at1 = new CQLAttribute();
		at1.setName("_codingScheme");
		at1.setValue(ServiceTestCase.THES_SCHEME);
		at1.setPredicate(CQLPredicate.EQUAL_TO);
		
		CQLAttribute at2 = new CQLAttribute();
		at2.setName("_representsVersion");
		at2.setValue(ServiceTestCase.THES_VERSION);
		at2.setPredicate(CQLPredicate.EQUAL_TO);
		
		target.setAttribute(at1);
		target.setAttribute(at2);
			
		query.setTarget(target);
		List<CodingScheme> codingSchemes = service.query(query);
		
		assertTrue(codingSchemes != null);
		assertTrue(codingSchemes.size() == 1);
		
		CodingScheme scheme = codingSchemes.get(0);
		assertTrue(scheme.getCodingSchemeName().equals(ServiceTestCase.THES_SCHEME));
		assertTrue(scheme.getRepresentsVersion().equals(ServiceTestCase.THES_VERSION));		
	}
}
