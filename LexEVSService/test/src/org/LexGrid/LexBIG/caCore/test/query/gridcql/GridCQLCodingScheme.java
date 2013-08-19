/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.test.query.gridcql;

import gov.nih.nci.cagrid.cqlquery.Attribute;
import gov.nih.nci.cagrid.cqlquery.CQLQuery;
import gov.nih.nci.cagrid.cqlquery.Group;
import gov.nih.nci.cagrid.cqlquery.LogicalOperator;
import gov.nih.nci.cagrid.cqlquery.Object;
import gov.nih.nci.cagrid.cqlquery.Predicate;
import gov.nih.nci.system.applicationservice.ApplicationService;

import java.util.List;

import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.codingSchemes.CodingScheme;

public class GridCQLCodingScheme extends ServiceTestCase
{
	private final String test_id = "CQLTests";
	
	@Override
	protected String getTestID() {
		return test_id;
	}
	
	public void testGetCodingSchemes() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CQLQuery query = new CQLQuery();	
		Object target = new Object();
		
		target.setName("org.LexGrid.codingSchemes.CodingScheme");
		
		query.setTarget(target);
		List<CodingScheme> cs = service.query(query);
		
		assertTrue(cs != null);
		assertTrue(cs.size() > 0);
	}
	
	public void testGetCodingSchemeByNameAndVersion() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CQLQuery query = new CQLQuery();	
		Object target = new Object();
		
		target.setName("org.LexGrid.codingSchemes.CodingScheme");
		Attribute at1 = new Attribute();
		at1.setName("_codingSchemeName");
		at1.setValue(ServiceTestCase.SNOMED_SCHEME);
		at1.setPredicate(Predicate.EQUAL_TO);
		
		Attribute at2 = new Attribute();
		at2.setName("_representsVersion");
		at2.setValue(ServiceTestCase.SNOMED_VERSION);
		at2.setPredicate(Predicate.EQUAL_TO);
		
		Group group = new Group();
		group.setLogicRelation(LogicalOperator.AND);
		group.setAttribute(new Attribute[]{at1, at2});
		
		target.setGroup(group);
			
		query.setTarget(target);
		
		List<CodingScheme> codingSchemes = service.query(query);
		assertTrue(codingSchemes != null);
		assertTrue(codingSchemes.size() > 0);
		
		for(CodingScheme cs : codingSchemes){
			assertTrue(cs.getCodingSchemeName().equals(ServiceTestCase.SNOMED_SCHEME));
			assertTrue(cs.getRepresentsVersion().equals(ServiceTestCase.SNOMED_VERSION));
		}		
	}
}
