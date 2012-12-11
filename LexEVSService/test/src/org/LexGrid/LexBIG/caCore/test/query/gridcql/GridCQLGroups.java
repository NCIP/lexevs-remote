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
package org.LexGrid.LexBIG.caCore.test.query.gridcql;

import edu.mayo.informatics.lexgrid.convert.directConversions.TextCommon.Concept;
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
import org.LexGrid.concepts.Entity;

public class GridCQLGroups extends ServiceTestCase
{
	private final String test_id = "CQLTests";

	@Override
	protected String getTestID() {
		return test_id;
	}

	public void testAndGroup() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CQLQuery query = new CQLQuery();	
		Object target = new Object();

		target.setName("org.LexGrid.concepts.Entity");

		Attribute at1 = new Attribute();
		at1.setName("_entityCode");
		at1.setValue("1005009");
		at1.setPredicate(Predicate.EQUAL_TO);	

		Attribute at2 = new Attribute();
		at2.setName("_entityCodeNamespace");
		at2.setValue(ServiceTestCase.SNOMED_SCHEME);
		at2.setPredicate(Predicate.EQUAL_TO);

		Group group = new Group();
		group.setLogicRelation(LogicalOperator.AND);
		group.setAttribute(new Attribute[]{at1, at2});

		target.setGroup(group);

		query.setTarget(target);
		List<Entity> results = service.query(query);

		assertTrue(results.size() > 0);
		for (Entity concept : results){
			assertTrue(concept.getEntityCode().equals("1005009"));
			assertTrue(concept.getEntityCodeNamespace().equals(ServiceTestCase.SNOMED_SCHEME));
		}
	}	

	public void testAndGroupWrongValue() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CQLQuery query = new CQLQuery();	
		Object target = new Object();

		target.setName("org.LexGrid.concepts.Entity");

		Attribute at1 = new Attribute();
		at1.setName("_entityCode");
		at1.setValue("1005009");
		at1.setPredicate(Predicate.EQUAL_TO);	

		Attribute at2 = new Attribute();
		at2.setName("_entityCodeNamespace");
		at2.setValue("WRONG_VALUE_FOR_TESTING");
		at2.setPredicate(Predicate.EQUAL_TO);

		Group group = new Group();
		group.setLogicRelation(LogicalOperator.AND);
		group.setAttribute(new Attribute[]{at1, at2});

		target.setGroup(group);

		query.setTarget(target);
		List<Concept> results = service.query(query);

		assertTrue(results.size() == 0);
	}	

	public void testOrGroup() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CQLQuery query = new CQLQuery();	
		Object target = new Object();

		target.setName("org.LexGrid.concepts.Entity");

		Attribute at1 = new Attribute();
		at1.setName("_entityCode");
		at1.setValue("1005009");
		at1.setPredicate(Predicate.EQUAL_TO);	

		Attribute at2 = new Attribute();
		at2.setName("_entityCodeNamespace");
		at2.setValue("WRONG_VALUE_FOR_TESTING");
		at2.setPredicate(Predicate.EQUAL_TO);

		Group group = new Group();
		group.setLogicRelation(LogicalOperator.OR);
		group.setAttribute(new Attribute[]{at1, at2});

		target.setGroup(group);

		query.setTarget(target);
		List<Entity> results = service.query(query);

		assertTrue(results.size() > 0);
		for (Entity concept : results){
			assertTrue(concept.getEntityCode().equals("1005009"));
			assertTrue(concept.getEntityCodeNamespace().equals(ServiceTestCase.SNOMED_SCHEME));
		}
	}

	public void testOrGroupWrongValue() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CQLQuery query = new CQLQuery();	
		Object target = new Object();

		target.setName("org.LexGrid.concepts.Entity");

		Attribute at1 = new Attribute();
		at1.setName("_entityCode");
		at1.setValue("WRONG_VALUE_FOR_TESTING");
		at1.setPredicate(Predicate.EQUAL_TO);	

		Attribute at2 = new Attribute();
		at2.setName("_entityCodeNamespace");
		at2.setValue("WRONG_VALUE_FOR_TESTING");
		at2.setPredicate(Predicate.EQUAL_TO);

		Group group = new Group();
		group.setLogicRelation(LogicalOperator.OR);
		group.setAttribute(new Attribute[]{at1, at2});

		target.setGroup(group);

		query.setTarget(target);
		List<Concept> results = service.query(query);

		assertTrue(results.size() == 0);
	}

	public void testNestedAndGroup() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CQLQuery query = new CQLQuery();	
		Object target = new Object();

		target.setName("org.LexGrid.concepts.Entity");

		Attribute at1 = new Attribute();
		at1.setName("_entityCode");
		at1.setValue("1005009");
		at1.setPredicate(Predicate.EQUAL_TO);	

		Attribute at2 = new Attribute();
		at2.setName("_entityCodeNamespace");
		at2.setValue(ServiceTestCase.SNOMED_SCHEME);
		at2.setPredicate(Predicate.EQUAL_TO);

		Group mainGroup = new Group();
		Group nestedGroup1 = new Group();
		nestedGroup1.setLogicRelation(LogicalOperator.AND);
		Group nestedGroup2 = new Group();
		nestedGroup2.setLogicRelation(LogicalOperator.AND);

		nestedGroup1.setAttribute(new Attribute[]{at1});
		nestedGroup2.setAttribute(new Attribute[]{at2});

		mainGroup.setGroup(new Group[]{nestedGroup1, nestedGroup2});
		mainGroup.setLogicRelation(LogicalOperator.AND);

		target.setGroup(mainGroup);

		query.setTarget(target);
		List<Entity> results = service.query(query);

		assertTrue(results.size() > 0);
		for (Entity concept : results){
			assertTrue(concept.getEntityCode().equals("1005009"));
			assertTrue(concept.getEntityCodeNamespace().equals(ServiceTestCase.SNOMED_SCHEME));
		}
	}	

	public void testNestedAndGroupWrongValue() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CQLQuery query = new CQLQuery();	
		Object target = new Object();

		target.setName("org.LexGrid.concepts.Entity");

		Attribute at1 = new Attribute();
		at1.setName("_entityCode");
		at1.setValue("1005009");
		at1.setPredicate(Predicate.EQUAL_TO);	

		Attribute at2 = new Attribute();
		at2.setName("_entityCodeNamespace");
		at2.setValue("WRONG_VALUE_FOR_TESTING");
		at2.setPredicate(Predicate.EQUAL_TO);

		Group mainGroup = new Group();
		Group nestedGroup1 = new Group();
		nestedGroup1.setLogicRelation(LogicalOperator.AND);
		Group nestedGroup2 = new Group();
		nestedGroup2.setLogicRelation(LogicalOperator.AND);

		nestedGroup1.setAttribute(new Attribute[]{at1});
		nestedGroup2.setAttribute(new Attribute[]{at2});

		mainGroup.setGroup(new Group[]{nestedGroup1, nestedGroup2});
		mainGroup.setLogicRelation(LogicalOperator.AND);

		target.setGroup(mainGroup);

		query.setTarget(target);

		List<Concept> results = service.query(query);

		assertTrue(results.size() == 0);

	}	

	public void testNestedOrGroup() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CQLQuery query = new CQLQuery();	
		Object target = new Object();

		target.setName("org.LexGrid.concepts.Entity");

		Attribute at1 = new Attribute();
		at1.setName("_entityCode");
		at1.setValue("1005009");
		at1.setPredicate(Predicate.EQUAL_TO);	

		Attribute at2 = new Attribute();
		at2.setName("_entityCodeNamespace");
		at2.setValue("WRONG_VALUE_FOR_TESTING");
		at2.setPredicate(Predicate.EQUAL_TO);

		Group mainGroup = new Group();
		Group nestedGroup1 = new Group();
		nestedGroup1.setLogicRelation(LogicalOperator.AND);
		Group nestedGroup2 = new Group();
		nestedGroup2.setLogicRelation(LogicalOperator.AND);

		nestedGroup1.setAttribute(new Attribute[]{at1});
		nestedGroup2.setAttribute(new Attribute[]{at2});

		mainGroup.setGroup(new Group[]{nestedGroup1, nestedGroup2});
		mainGroup.setLogicRelation(LogicalOperator.OR);

		target.setGroup(mainGroup);

		query.setTarget(target);
		
		List<Entity> results = service.query(query);

		assertTrue(results.size() > 0);
		for (Entity concept : results){
			assertTrue(concept.getEntityCode().equals("1005009"));
			assertTrue(concept.getEntityCodeNamespace().equals(ServiceTestCase.SNOMED_SCHEME));
		}
	}	

	public void testNestedOrGroupWrongValue() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CQLQuery query = new CQLQuery();	
		Object target = new Object();

		target.setName("org.LexGrid.concepts.Entity");

		Attribute at1 = new Attribute();
		at1.setName("_entityCode");
		at1.setValue("WRONG_VALUE_FOR_TESTING");
		at1.setPredicate(Predicate.EQUAL_TO);	

		Attribute at2 = new Attribute();
		at2.setName("_entityCodeNamespace");
		at2.setValue("WRONG_VALUE_FOR_TESTING");
		at2.setPredicate(Predicate.EQUAL_TO);

		Group mainGroup = new Group();
		Group nestedGroup1 = new Group();
		nestedGroup1.setLogicRelation(LogicalOperator.AND);
		Group nestedGroup2 = new Group();
		nestedGroup2.setLogicRelation(LogicalOperator.AND);

		nestedGroup1.setAttribute(new Attribute[]{at1});
		nestedGroup2.setAttribute(new Attribute[]{at2});

		mainGroup.setGroup(new Group[]{nestedGroup1, nestedGroup2});
		mainGroup.setLogicRelation(LogicalOperator.OR);

		target.setGroup(mainGroup);

		query.setTarget(target);
		List<Concept> results = service.query(query);

		assertTrue(results.size() == 0);	
	}	

}
