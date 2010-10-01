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
import gov.nih.nci.cagrid.cqlquery.Association;
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

public class GridCQLConcept extends ServiceTestCase
{
	private final String test_id = "CQLTests";
	
	@Override
	protected String getTestID() {
		return test_id;
	}
	
	public void testGetConceptByIdAndCodingSchemeNamespace() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CQLQuery query = new CQLQuery();	
		gov.nih.nci.cagrid.cqlquery.Object target = new gov.nih.nci.cagrid.cqlquery.Object();
				
		target.setName("org.LexGrid.concepts.Entity");
		
		Attribute at1 = new Attribute();
		at1.setName("_entityCode");
		at1.setValue("149164001");
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
		
		
		boolean foundResults = false;
		List<Entity> concepts = service.query(query);
		for (Entity concept : concepts) {
			foundResults = true;
			assertTrue(concept.getEntityCode().equals("149164001"));
			assertTrue(concept.getEntityCodeNamespace().equals(ServiceTestCase.SNOMED_SCHEME));
		}
		assertTrue(foundResults);
	}	
	
	public void testGetConceptByIdAndCodingSchemeNamespaceWildCard() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CQLQuery query = new CQLQuery();	
		Object target = new Object();
				
		target.setName("org.LexGrid.concepts.Entity");
		
		Attribute at1 = new Attribute();
		at1.setName("_entityCode");
		at1.setValue("10000500%");
		at1.setPredicate(Predicate.LIKE);	
		
		Attribute at2 = new Attribute();
		at2.setName("_entityCodeNamespace");
		at2.setValue(ServiceTestCase.SNOMED_SCHEME);
		at2.setPredicate(Predicate.EQUAL_TO);
		
		Group group = new Group();
		group.setLogicRelation(LogicalOperator.AND);
		group.setAttribute(new Attribute[]{at1, at2});
		
		target.setGroup(group);
				
		query.setTarget(target);
		
		boolean foundResults = false;
		List<Entity> concepts = service.query(query);
		for (Entity concept : concepts) {
			foundResults = true;
			assertTrue(concept.getEntityCode().startsWith("10000500"));
			assertTrue(concept.getEntityCodeNamespace().equals(ServiceTestCase.SNOMED_SCHEME));
		}
		assertTrue(foundResults);
	}	
	
	public void testGetConceptByEntityDescriptionAndCodingSchemeNamespace() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CQLQuery query = new CQLQuery();	
		Object target = new Object();
				
		target.setName("org.LexGrid.concepts.Entity");
	
		Attribute at = new Attribute();
		at.setName("_entityCodeNamespace");
		at.setValue(ServiceTestCase.SNOMED_SCHEME);
		at.setPredicate(Predicate.EQUAL_TO);
		
		target.setAttribute(at);
		
		Association assoc = new Association();
		assoc.setRoleName("_entityDescription");
		
		Attribute entityDes = new Attribute();
		entityDes.setName("_content");
		entityDes.setValue("Boxing");
		entityDes.setPredicate(Predicate.EQUAL_TO);
		
		assoc.setAttribute(entityDes);	
		target.setAssociation(assoc);
		
		query.setTarget(target);
			
		boolean foundResults = false;
		List<Entity> concepts = service.query(query);
		for (Entity concept : concepts) {
			foundResults = true;
			assertTrue(concept.getEntityCode().equals("29506000"));
			assertTrue(concept.getEntityCodeNamespace().equals(ServiceTestCase.SNOMED_SCHEME));
		}
		assertTrue(foundResults);
	}	
}
