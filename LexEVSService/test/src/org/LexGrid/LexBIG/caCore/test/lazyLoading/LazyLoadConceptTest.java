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
package org.LexGrid.LexBIG.caCore.test.lazyLoading;

import gov.nih.nci.system.applicationservice.ApplicationException;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.caCore.applicationservice.QueryOptions;
import org.LexGrid.LexBIG.caCore.client.proxy.LexEVSListProxy;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.codingSchemes.CodingScheme;
import org.LexGrid.commonTypes.Property;
import org.LexGrid.commonTypes.Text;
import org.LexGrid.concepts.Concept;
import org.LexGrid.concepts.Definition;
import org.LexGrid.concepts.Presentation;


public class LazyLoadConceptTest extends ServiceTestCase {
	String testId = "LexEVSDataServiceSecurityTest";

	private Concept concept;
	
	@Override
	protected String getTestID() {
		// TODO Auto-generated method stub
		return testId;
	}
	
	public void setUp(){
		QueryOptions qo = new QueryOptions();
		qo.setLazyLoad(true);
		qo.setResultPageSize(10);
		qo.setCodingScheme(ServiceTestCase.THES_SCHEME);
		CodingSchemeVersionOrTag csvt = new CodingSchemeVersionOrTag();
		csvt.setVersion(ServiceTestCase.THES_VERSION);
		qo.setCodingSchemeVersionOrTag(csvt);
		LexEVSApplicationService svc = LexEVSServiceHolder.instance().getLexEVSAppService();
		Concept c = new Concept();
		c.setEntityCode("C53916");
		List<Concept> concepts = null;
		try {
			concepts = svc.search(Concept.class, c, qo);
		} catch (ApplicationException e) {
			fail(e.getMessage());
		}
		
		assertTrue(concepts.size() == 1);
		concept = concepts.get(0);
	}
	
	public void testLazyLoadConcept() throws Exception {
		assertTrue(concept != null);
		assertTrue(concept.getEntityCode().equals("C53916"));
	}
	
	public void testLazyLoadPresentations(){
		Presentation[] pres = concept.getPresentation();
		assertTrue(pres.length > 0);
		int count = concept.getPresentationCount();
		assertTrue(count > 0);
		Presentation indivPres = concept.getPresentation(0);
		assertTrue(indivPres.getPropertyId().equals("P0001"));
		assertTrue(indivPres.getPropertyType().equals("presentation"));
	}
	
	public void testLazyLoadDefinitions(){
		Definition[] def = concept.getDefinition();
		assertTrue(def.length > 0);
		int count = concept.getDefinitionCount();
		assertTrue(count > 0);
		Definition indivDef = concept.getDefinition(0);
		assertTrue(indivDef.getPropertyType().equals("definition"));
	}
	
	public void testLazyLoadProperty(){
		Property[] prop = concept.getProperty();
		assertTrue(prop.length > 0);
		int count = concept.getPropertyCount();
		assertTrue(count > 0);
		Property indivDef = concept.getProperty(0);
		assertTrue(indivDef.getPropertyType().equals("property"));
	}
	
	public void testGetCount(){
		Presentation[] pres = concept.getPresentation();
		assertTrue(pres.length > 0);
		int count = concept.getPresentationCount();
		assertTrue(count > 0);
	}
	
	public void testEnumerate(){
		Enumeration<Presentation> enumeration = concept.enumeratePresentation();
		assertTrue(enumeration.hasMoreElements() == true);
		
		int expectedPresentations = 3;
		
		int counter = 0;
		while(enumeration.hasMoreElements()){
			enumeration.nextElement();
			counter++;
		}
		
		assertTrue(expectedPresentations == counter);
	}
	
	public void testIterate(){
		Iterator<Presentation> itr = concept.iteratePresentation();
		assertTrue(itr.hasNext());
		
		int expectedPresentations = 3;
		
		int counter = 0;
		while(itr.hasNext()){
			itr.next();
			counter++;
		}
		
		assertTrue(expectedPresentations == counter);
	}
	
}
