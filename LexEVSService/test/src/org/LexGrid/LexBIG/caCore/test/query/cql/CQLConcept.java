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
package org.LexGrid.LexBIG.caCore.test.query.cql;

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
import org.LexGrid.codingSchemes.CodingScheme;
import org.LexGrid.concepts.Concept;

public class CQLConcept extends ServiceTestCase
{
	private final String test_id = "CQLTests";
	
	@Override
	protected String getTestID() {
		return test_id;
	}
	
	public void testGetConceptByIdAndNamespace() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CQLQuery query = new CQLQuery();	
		CQLObject target = new CQLObject();
		
		target.setName("org.LexGrid.concepts.Concept");
		
		CQLAttribute at1 = new CQLAttribute();
		at1.setName("_entityCode");
		at1.setValue("C26040");
		at1.setPredicate(CQLPredicate.EQUAL_TO);	
		
		CQLAttribute at2 = new CQLAttribute();
		at2.setName("_entityCodeNamespace");
		at2.setValue("NCI_Thesaurus");
		at2.setPredicate(CQLPredicate.EQUAL_TO);
		
		CQLGroup group = new CQLGroup();
		group.setLogicOperator(CQLLogicalOperator.AND);
		group.addAttribute(at1);
		group.addAttribute(at2);
	
		target.setGroup(group);
				
		query.setTarget(target);
		List<Concept> concepts = service.query(query);
		
		assertTrue(concepts != null);
		assertTrue(concepts.size() > 0);
		
		Concept concept = concepts.get(0);
		assertTrue(concept.getEntityCodeNamespace().equals("NCI_Thesaurus"));
		assertTrue(concept.getEntityCode().equals("C26040"));
	}	
	
	public void testGetConceptByIdAndCodingSchemeWildCard() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CQLQuery query = new CQLQuery();	
		CQLObject target = new CQLObject();
		
		target.setName("org.LexGrid.concepts.Concept");
		
		CQLAttribute at1 = new CQLAttribute();
		at1.setName("_entityCode");
		at1.setValue("C2604%");
		at1.setPredicate(CQLPredicate.LIKE);	
		
		CQLAttribute at2 = new CQLAttribute();
		at2.setName("_entityCodeNamespace");
		at2.setValue("NCI_Thesaurus");
		at2.setPredicate(CQLPredicate.EQUAL_TO);
	
		CQLGroup group = new CQLGroup();
		group.setLogicOperator(CQLLogicalOperator.AND);
		group.addAttribute(at1);
		group.addAttribute(at2);
	
		target.setGroup(group);
		
		query.setTarget(target);
		List<Concept> concepts = service.query(query);
		
		assertTrue(concepts != null);
		assertTrue(concepts.size() > 0);
		
		//all returned Concepts should match 'C2604*'
		for(Concept c : concepts){
			assertTrue(c.getEntityCodeNamespace().equals("NCI_Thesaurus"));
			assertTrue(c.getEntityCode().startsWith("C2604"));			
		}
	}	
}
