/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.test.lazyLoading;

import edu.mayo.informatics.lexgrid.convert.directConversions.TextCommon.Concept;
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


public class LazyLoadConceptTest extends ServiceTestCase {
	String testId = "LexEVSDataServiceSecurityTest";

	private Entity concept;
	
	@Override
	protected String getTestID() {
		// TODO Auto-generated method stub
		return testId;
	}

	//TODO A bug to fix. For NCI_Thesaurus the test failed, because there are multiple NCI_Thesaurus loaded (but version are different)
	// the lazyload messed different versions altogether and throw a exception
	public void setUp(){
		QueryOptions qo = new QueryOptions();
		qo.setLazyLoad(true);
		qo.setResultPageSize(10);
		qo.setCodingScheme(ServiceTestCase.META_SCHEME);
		CodingSchemeVersionOrTag csvt = new CodingSchemeVersionOrTag();
		csvt.setVersion(ServiceTestCase.META_VERSION);
		qo.setCodingSchemeVersionOrTag(csvt);
		LexEVSApplicationService svc = LexEVSServiceHolder.instance().getLexEVSAppService();
		Entity c = new Entity();
		c.setEntityCode("C0000184");
		List<Entity> concepts = null;
		
		assertTrue(concepts.size() == 1);
		concept = concepts.get(0);
	}
	
	public void testLazyLoadConcept() throws Exception {
		assertTrue(concept != null);
		assertTrue(concept.getEntityCode().equals("C0000184"));
	}
	
	public void testLazyLoadPresentations() throws Exception{
		Presentation[] pres = concept.getPresentation();
		assertTrue(pres.length > 0);
		int count = concept.getPresentationCount();
		assertTrue(count > 0);
		Presentation indivPres = concept.getPresentation(0);
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
		Enumeration<? extends Presentation> enumeration = concept.enumeratePresentation();
		assertTrue(enumeration.hasMoreElements() == true);
		
		int expectedPresentations = 30;
		
		int counter = 0;
		while(enumeration.hasMoreElements()){
			enumeration.nextElement();
			counter++;
		}
		
		assertTrue(expectedPresentations == counter);
	}
	
	public void testIterate(){
		Iterator<? extends Presentation> itr = concept.iteratePresentation();
		assertTrue(itr.hasNext());
		
		int expectedPresentations = 30;
		
		int counter = 0;
		while(itr.hasNext()){
			itr.next();
			counter++;
		}
		
		assertTrue(expectedPresentations == counter);
	}
	
}
