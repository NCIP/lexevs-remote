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
import gov.nih.nci.cagrid.cqlquery.QueryModifier;
import gov.nih.nci.system.applicationservice.ApplicationService;

import java.util.List;

import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.concepts.Entity;

public class GridCQLQueryModifiers extends ServiceTestCase
{
	private final String test_id = "CQLTests";

	@Override
	protected String getTestID() {
		return test_id;
	}

	public void testCountOnly() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CQLQuery query = new CQLQuery();	
		Object target = new Object();

		target.setName("org.LexGrid.concepts.Concept");

		Attribute at1 = new Attribute();
		at1.setName("_entityCode");
		at1.setValue("149164001");
		at1.setPredicate(Predicate.EQUAL_TO);	

		Attribute at2 = new Attribute();
		at2.setName("_entityCodeNamespace");
		at2.setValue(ServiceTestCase.SNOMED_SCHEME);
		at2.setPredicate(Predicate.EQUAL_TO);

		Group group = new Group();
		group.setAttribute(new Attribute[]{at1, at2});
		group.setLogicRelation(LogicalOperator.AND);
		
		target.setGroup(group);
		
		QueryModifier modifiers = new QueryModifier();
		modifiers.setCountOnly(true);
		
		query.setTarget(target);
		
		query.setQueryModifier(modifiers);
		List results = service.query(query);

		assertTrue(results.size() > 0);		
		assertTrue(results.size() == 1);	
		java.lang.Object returnObj = results.get(0);
		
		assertTrue(returnObj instanceof Integer);	
		Integer count = (Integer)returnObj;
		
		assertTrue(count == 1);
	}	
	
	public void testCountOnlyFalse() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CQLQuery query = new CQLQuery();	
		Object target = new Object();

		target.setName("org.LexGrid.concepts.Concept");

		Attribute at1 = new Attribute();
		at1.setName("_entityCode");
		at1.setValue("149164001");
		at1.setPredicate(Predicate.EQUAL_TO);	

		Attribute at2 = new Attribute();
		at2.setName("_entityCodeNamespace");
		at2.setValue(ServiceTestCase.SNOMED_SCHEME);
		at2.setPredicate(Predicate.EQUAL_TO);

		Group group = new Group();
		group.setAttribute(new Attribute[]{at1, at2});
		group.setLogicRelation(LogicalOperator.AND);
		
		target.setGroup(group);
		
		QueryModifier modifiers = new QueryModifier();
		modifiers.setCountOnly(false);
		
		query.setTarget(target);
		
		query.setQueryModifier(modifiers);
		List results = service.query(query);

		assertTrue(results.size() > 0);		
		assertTrue(results.size() == 1);	
		java.lang.Object returnObj = results.get(0);
		
		assertTrue(returnObj instanceof Entity);	
		Entity concept = (Entity)returnObj;
		
		assertTrue(concept.getEntityCode().equals("149164001"));
	}	
	
	public void testAttributes() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CQLQuery query = new CQLQuery();	
		Object target = new Object();

		target.setName("org.LexGrid.concepts.Concept");

		Attribute at1 = new Attribute();
		at1.setName("_entityCode");
		at1.setValue("149164001");
		at1.setPredicate(Predicate.EQUAL_TO);	

		Attribute at2 = new Attribute();
		at2.setName("_entityCodeNamespace");
		at2.setValue(ServiceTestCase.SNOMED_SCHEME);
		at2.setPredicate(Predicate.EQUAL_TO);

		Group group = new Group();
		group.setAttribute(new Attribute[]{at1, at2});
		group.setLogicRelation(LogicalOperator.AND);
		
		target.setGroup(group);
		
		QueryModifier modifiers = new QueryModifier();
		modifiers.setAttributeNames(new String[]{"_entityCode"});
		query.setTarget(target);
		
		query.setQueryModifier(modifiers);
		List results = service.query(query);

		assertTrue(results.size() > 0);
		assertTrue(results.size() == 1);
		java.lang.Object returnObj = results.get(0);
		assertTrue(returnObj instanceof String);
		
		String concept = (String)returnObj;	
		assertTrue(concept.equals("149164001"));
	}	
	
	public void testDistinctAttributes() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CQLQuery query = new CQLQuery();	
		Object target = new Object();

		target.setName("org.LexGrid.concepts.Concept");

		//This should return 11 results or so.
		Attribute at1 = new Attribute();
		at1.setName("_entityCode");
		at1.setValue("14916%");
		at1.setPredicate(Predicate.LIKE);	

		Attribute at2 = new Attribute();
		at2.setName("_entityCodeNamespace");
		at2.setValue(ServiceTestCase.SNOMED_SCHEME);
		at2.setPredicate(Predicate.EQUAL_TO);

		Group group = new Group();
		group.setAttribute(new Attribute[]{at1, at2});
		group.setLogicRelation(LogicalOperator.AND);
		
		target.setGroup(group);
		
		QueryModifier modifiers = new QueryModifier();
		modifiers.setCountOnly(false);
		modifiers.setDistinctAttribute("_entityCodeNamespace");
		query.setTarget(target);
		
		query.setQueryModifier(modifiers);
		List results = service.query(query);

		assertTrue(results.size() > 0);
		
		//All the results will have the same _entityCodeNamespace - so there should
		//only be one result returned.
		assertTrue(results.size() == 1);	
	}		
}
