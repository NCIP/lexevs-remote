/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.test.query.gridcql;

import edu.mayo.informatics.lexgrid.convert.directConversions.TextCommon.Concept;
import gov.nih.nci.cagrid.cqlquery.Attribute;
import gov.nih.nci.cagrid.cqlquery.CQLQuery;
import gov.nih.nci.cagrid.cqlquery.Object;
import gov.nih.nci.cagrid.cqlquery.Predicate;
import gov.nih.nci.system.applicationservice.ApplicationService;

import java.util.List;

import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.concepts.Entity;

public class GridCQLPredicates extends ServiceTestCase
{
	private final String test_id = "CQLTests";

	@Override
	protected String getTestID() {
		return test_id;
	}

	public void testEqualToPredicate() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();

		CQLQuery query = new CQLQuery();	
		Object target = new Object();

		target.setName("org.LexGrid.concepts.Entity");

		Attribute at1 = new Attribute();
		at1.setName("_entityCode");
		at1.setValue("76880004");
		at1.setPredicate(Predicate.EQUAL_TO);	

		target.setAttribute(at1);
		query.setTarget(target);
		List<Entity> results = service.query(query);

		assertTrue(results.size() > 0);
		for (Entity concept : results){
			assertTrue(concept.getEntityCode().equals("76880004"));
			assertTrue(concept.getEntityCodeNamespace().equals(ServiceTestCase.SNOMED_SCHEME));
		}
	}	

	public void testLikePredicate() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CQLQuery query = new CQLQuery();	
		Object target = new Object();

		target.setName("org.LexGrid.concepts.Entity");

		Attribute at1 = new Attribute();
		at1.setName("_entityCode");
		at1.setValue("7688000%");
		at1.setPredicate(Predicate.LIKE);	

		target.setAttribute(at1);
		query.setTarget(target);
		List<Entity> results = service.query(query);

		assertTrue(results.size() > 0);
		for (Entity concept : results){
			assertTrue(concept.getEntityCode().equals("76880004"));
			assertTrue(concept.getEntityCodeNamespace().equals(ServiceTestCase.SNOMED_SCHEME));
		}
	}	
}
