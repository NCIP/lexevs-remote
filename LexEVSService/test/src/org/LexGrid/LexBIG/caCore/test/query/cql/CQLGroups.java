/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.test.query.cql;

import edu.mayo.informatics.lexgrid.convert.directConversions.TextCommon.Concept;
import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.query.cql.CQLAttribute;
import gov.nih.nci.system.query.cql.CQLGroup;
import gov.nih.nci.system.query.cql.CQLLogicalOperator;
import gov.nih.nci.system.query.cql.CQLObject;
import gov.nih.nci.system.query.cql.CQLPredicate;
import gov.nih.nci.system.query.cql.CQLQuery;

import java.util.List;

import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.concepts.Entity;

public class CQLGroups extends ServiceTestCase
{
	private final String test_id = "CQLTests";

	@Override
	protected String getTestID() {
		return test_id;
	}

	public void testAndGroup() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CQLQuery query = new CQLQuery();	
		CQLObject target = new CQLObject();

		target.setName("org.LexGrid.concepts.Entity");

		CQLAttribute at1 = new CQLAttribute();
		at1.setName("_entityCode");
		at1.setValue("10000006");
		at1.setPredicate(CQLPredicate.EQUAL_TO);	

		CQLAttribute at2 = new CQLAttribute();
		at2.setName("_entityCodeNamespace");
		at2.setValue(ServiceTestCase.SNOMED_SCHEME);
		at2.setPredicate(CQLPredicate.EQUAL_TO);

		CQLGroup group = new CQLGroup();
		group.setLogicOperator(CQLLogicalOperator.AND);
		group.addAttribute(at1);
		group.addAttribute(at2);

		target.setGroup(group);

		query.setTarget(target);
		List<Entity> results = service.query(query);

		assertTrue(results.size() > 0);
		for (Entity concept : results){
			assertTrue(concept.getEntityCode().equals("10000006"));
			assertTrue(concept.getEntityCodeNamespace().equals(ServiceTestCase.SNOMED_SCHEME));
		}
	}	

	public void testAndCQLGroupWrongValue() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CQLQuery query = new CQLQuery();	
		CQLObject target = new CQLObject();

		target.setName("org.LexGrid.concepts.Entity");

		CQLAttribute at1 = new CQLAttribute();
		at1.setName("_entityCode");
		at1.setValue("10000006");
		at1.setPredicate(CQLPredicate.EQUAL_TO);	

		CQLAttribute at2 = new CQLAttribute();
		at2.setName("_entityCodeNamespace");
		at2.setValue("WRONG_VALUE_FOR_TESTING");
		at2.setPredicate(CQLPredicate.EQUAL_TO);

		CQLGroup group = new CQLGroup();
		group.setLogicOperator(CQLLogicalOperator.AND);
		group.addAttribute(at1);
		group.addAttribute(at2);

		target.setGroup(group);

		query.setTarget(target);
		List<Concept> results = service.query(query);

		assertTrue(results.size() == 0);
	}	

	public void testOrCQLGroup() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CQLQuery query = new CQLQuery();	
		CQLObject target = new CQLObject();

		target.setName("org.LexGrid.concepts.Entity");

		CQLAttribute at1 = new CQLAttribute();
		at1.setName("_entityCode");
		at1.setValue("10000006");
		at1.setPredicate(CQLPredicate.EQUAL_TO);	

		CQLAttribute at2 = new CQLAttribute();
		at2.setName("_entityCodeNamespace");
		at2.setValue("WRONG_VALUE_FOR_TESTING");
		at2.setPredicate(CQLPredicate.EQUAL_TO);

		CQLGroup group = new CQLGroup();
		group.setLogicOperator(CQLLogicalOperator.OR);
		group.addAttribute(at1);
		group.addAttribute(at2);

		target.setGroup(group);

		query.setTarget(target);
		List<Entity> results = service.query(query);

		assertTrue(results.size() > 0);
		for (Entity concept : results){
			assertTrue(concept.getEntityCode().equals("10000006"));
			assertTrue(concept.getEntityCodeNamespace().equals(ServiceTestCase.SNOMED_SCHEME));
		}
	}

	public void testOrCQLGroupWrongValue() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CQLQuery query = new CQLQuery();	
		CQLObject target = new CQLObject();

		target.setName("org.LexGrid.concepts.Entity");

		CQLAttribute at1 = new CQLAttribute();
		at1.setName("_entityCode");
		at1.setValue("WRONG_VALUE_FOR_TESTING");
		at1.setPredicate(CQLPredicate.EQUAL_TO);	

		CQLAttribute at2 = new CQLAttribute();
		at2.setName("_entityCodeNamespace");
		at2.setValue("WRONG_VALUE_FOR_TESTING");
		at2.setPredicate(CQLPredicate.EQUAL_TO);

		CQLGroup group = new CQLGroup();
		group.setLogicOperator(CQLLogicalOperator.OR);
		group.addAttribute(at1);
		group.addAttribute(at2);

		target.setGroup(group);

		query.setTarget(target);
		List<Concept> results = service.query(query);

		assertTrue(results.size() == 0);
	}

	public void testNestedAndCQLGroup() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CQLQuery query = new CQLQuery();	
		CQLObject target = new CQLObject();

		target.setName("org.LexGrid.concepts.Entity");

		CQLAttribute at1 = new CQLAttribute();
		at1.setName("_entityCode");
		at1.setValue("10000006");
		at1.setPredicate(CQLPredicate.EQUAL_TO);	

		CQLAttribute at2 = new CQLAttribute();
		at2.setName("_entityCodeNamespace");
		at2.setValue(ServiceTestCase.SNOMED_SCHEME);
		at2.setPredicate(CQLPredicate.EQUAL_TO);

		CQLGroup mainCQLGroup = new CQLGroup();
		CQLGroup nestedCQLGroup1 = new CQLGroup();
		nestedCQLGroup1.setLogicOperator(CQLLogicalOperator.AND);
		CQLGroup nestedCQLGroup2 = new CQLGroup();
		nestedCQLGroup2.setLogicOperator(CQLLogicalOperator.AND);

		nestedCQLGroup1.addAttribute(at1);
		nestedCQLGroup2.addAttribute(at2);

		mainCQLGroup.addGroup(nestedCQLGroup1);
		mainCQLGroup.addGroup(nestedCQLGroup2);
		mainCQLGroup.setLogicOperator(CQLLogicalOperator.AND);

		target.setGroup(mainCQLGroup);

		query.setTarget(target);
		List<Entity> results = service.query(query);

		assertTrue(results.size() > 0);
		for (Entity concept : results){
			assertTrue(concept.getEntityCode().equals("10000006"));
			assertTrue(concept.getEntityCodeNamespace().equals(ServiceTestCase.SNOMED_SCHEME));
		}
	}	

	public void testNestedAndCQLGroupWrongValue() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CQLQuery query = new CQLQuery();	
		CQLObject target = new CQLObject();

		target.setName("org.LexGrid.concepts.Entity");

		CQLAttribute at1 = new CQLAttribute();
		at1.setName("_entityCode");
		at1.setValue("149164001");
		at1.setPredicate(CQLPredicate.EQUAL_TO);	

		CQLAttribute at2 = new CQLAttribute();
		at2.setName("_entityCodeNamespace");
		at2.setValue("WRONG_VALUE_FOR_TESTING");
		at2.setPredicate(CQLPredicate.EQUAL_TO);

		CQLGroup mainCQLGroup = new CQLGroup();
		CQLGroup nestedCQLGroup1 = new CQLGroup();
		nestedCQLGroup1.setLogicOperator(CQLLogicalOperator.AND);
		CQLGroup nestedCQLGroup2 = new CQLGroup();
		nestedCQLGroup2.setLogicOperator(CQLLogicalOperator.AND);

		nestedCQLGroup1.addAttribute(at1);
		nestedCQLGroup2.addAttribute(at2);

		mainCQLGroup.addGroup(nestedCQLGroup1);
		mainCQLGroup.addGroup(nestedCQLGroup2);
		mainCQLGroup.setLogicOperator(CQLLogicalOperator.AND);

		target.setGroup(mainCQLGroup);

		query.setTarget(target);

		List<Concept> results = service.query(query);

		assertTrue(results.size() == 0);

	}	

	public void testNestedOrCQLGroup() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CQLQuery query = new CQLQuery();	
		CQLObject target = new CQLObject();

		target.setName("org.LexGrid.concepts.Entity");

		CQLAttribute at1 = new CQLAttribute();
		at1.setName("_entityCode");
		at1.setValue("10000006");
		at1.setPredicate(CQLPredicate.EQUAL_TO);	

		CQLAttribute at2 = new CQLAttribute();
		at2.setName("_entityCodeNamespace");
		at2.setValue("WRONG_VALUE_FOR_TESTING");
		at2.setPredicate(CQLPredicate.EQUAL_TO);

		CQLGroup mainCQLGroup = new CQLGroup();
		CQLGroup nestedCQLGroup1 = new CQLGroup();
		nestedCQLGroup1.setLogicOperator(CQLLogicalOperator.AND);
		CQLGroup nestedCQLGroup2 = new CQLGroup();
		nestedCQLGroup2.setLogicOperator(CQLLogicalOperator.AND);

		nestedCQLGroup1.addAttribute(at1);
		nestedCQLGroup2.addAttribute(at2);

		mainCQLGroup.addGroup(nestedCQLGroup1);
		mainCQLGroup.addGroup(nestedCQLGroup2);
		
		mainCQLGroup.setLogicOperator(CQLLogicalOperator.OR);

		target.setGroup(mainCQLGroup);

		query.setTarget(target);
		
		List<Entity> results = service.query(query);

		assertTrue(results.size() > 0);
		for (Entity concept : results){
			assertTrue(concept.getEntityCode().equals("10000006"));
			assertTrue(concept.getEntityCodeNamespace().equals(ServiceTestCase.SNOMED_SCHEME));
		}
	}	

	public void testNestedOrCQLGroupWrongValue() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CQLQuery query = new CQLQuery();	
		CQLObject target = new CQLObject();

		target.setName("org.LexGrid.concepts.Entity");

		CQLAttribute at1 = new CQLAttribute();
		at1.setName("_entityCode");
		at1.setValue("WRONG_VALUE_FOR_TESTING");
		at1.setPredicate(CQLPredicate.EQUAL_TO);	

		CQLAttribute at2 = new CQLAttribute();
		at2.setName("_entityCodeNamespace");
		at2.setValue("WRONG_VALUE_FOR_TESTING");
		at2.setPredicate(CQLPredicate.EQUAL_TO);

		CQLGroup mainCQLGroup = new CQLGroup();
		CQLGroup nestedCQLGroup1 = new CQLGroup();
		nestedCQLGroup1.setLogicOperator(CQLLogicalOperator.AND);
		CQLGroup nestedCQLGroup2 = new CQLGroup();
		nestedCQLGroup2.setLogicOperator(CQLLogicalOperator.AND);

		nestedCQLGroup1.addAttribute(at1);
		nestedCQLGroup2.addAttribute(at2);

		mainCQLGroup.addGroup(nestedCQLGroup1);
		mainCQLGroup.addGroup(nestedCQLGroup2);
			
		mainCQLGroup.setLogicOperator(CQLLogicalOperator.OR);

		target.setGroup(mainCQLGroup);

		query.setTarget(target);
		List<Concept> results = service.query(query);

		//Should not return any results with bogus values
		assertTrue(results.size() == 0);
		
	}	

}
