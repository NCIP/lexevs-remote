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

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.caCore.applicationservice.QueryOptions;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.commonTypes.Property;
import org.LexGrid.concepts.Definition;
import org.LexGrid.concepts.Entity;
import org.LexGrid.concepts.Presentation;


public class LazyLoadEntityTest extends ServiceTestCase {
	String testId = "LexEVSDataServiceSecurityTest";

	private Entity entity;
	
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
		Entity e = new Entity();
		e.setEntityCode("C53916");
		List<Entity> concepts = null;
		try {
			concepts = svc.search(Entity.class, e, qo);
		} catch (ApplicationException ex) {
			fail(ex.getMessage());
		}
		
		assertTrue(concepts.size() == 1);
		entity = concepts.get(0);
	}
	
	public void testLazyLoadEntity() throws Exception {
		assertTrue(entity != null);
		assertTrue(entity.getEntityCode().equals("C53916"));
	}
	
	public void testLazyLoadPresentations(){
		Presentation[] pres = entity.getPresentation();
		assertTrue(pres.length > 0);
		int count = entity.getPresentationCount();
		assertTrue(count > 0);
		Presentation indivPres = entity.getPresentation(0);
		assertTrue(indivPres.getPropertyId().equals("P0001"));
		assertTrue(indivPres.getPropertyType().equals("presentation"));
	}
	
	public void testLazyLoadDefinitions(){
		Definition[] def = entity.getDefinition();
		assertTrue(def.length > 0);
		int count = entity.getDefinitionCount();
		assertTrue(count > 0);
		Definition indivDef = entity.getDefinition(0);
		assertTrue(indivDef.getPropertyType().equals("definition"));
	}
	
	public void testLazyLoadProperty(){
		Property[] prop = entity.getProperty();
		assertTrue(prop.length > 0);
		int count = entity.getPropertyCount();
		assertTrue(count > 0);
		Property indivDef = entity.getProperty(0);
		assertTrue(indivDef.getPropertyType().equals("property"));
	}
	
	public void testGetCount(){
		Presentation[] pres = entity.getPresentation();
		assertTrue(pres.length > 0);
		int count = entity.getPresentationCount();
		assertTrue(count > 0);
	}
	
	public void testEnumerate(){
		Enumeration<? extends Presentation> enumeration = entity.enumeratePresentation();
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
		Iterator<? extends Presentation> itr = entity.iteratePresentation();
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
