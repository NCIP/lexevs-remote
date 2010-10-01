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
package org.LexGrid.LexBIG.caCore.test.query.qbe;

import java.util.List;

import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.caCore.applicationservice.QueryOptions;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.commonTypes.EntityDescription;
import org.LexGrid.commonTypes.Property;
import org.LexGrid.commonTypes.Text;
import org.LexGrid.concepts.Comment;
import org.LexGrid.concepts.Definition;
import org.LexGrid.concepts.Entity;
import org.LexGrid.concepts.Presentation;

import edu.mayo.informatics.lexgrid.convert.directConversions.TextCommon.Concept;

public class QBEEntity extends ServiceTestCase {

	private final String test_id = "QBETests";
	private QueryOptions thesQueryOptions;
	
	@Override
	protected String getTestID() {
		return test_id;
	}

	public void setUp(){
		thesQueryOptions = new QueryOptions();
		thesQueryOptions.setCodingScheme(ServiceTestCase.THES_SCHEME);
		CodingSchemeVersionOrTag csvt = new CodingSchemeVersionOrTag();
		csvt.setVersion(ServiceTestCase.THES_VERSION);
		thesQueryOptions.setCodingSchemeVersionOrTag(csvt);
	}
	
	public void testGetConceptByCode() throws Exception {
		LexEVSApplicationService service = LexEVSServiceHolder.instance().getLexEVSAppService();
		
		Entity c = new Entity();
		c.setEntityCode("C43856");
		c.setEntityCodeNamespace("NCI_Thesaurus");
		List<Entity> list = service.search(Entity.class, c, thesQueryOptions);
		assertTrue(list.size() > 0);
		Entity foundConcept = (Entity)list.get(0);
		assertTrue(foundConcept.getComment().length == 0);
		assertTrue(foundConcept.getProperty().length == 4);
		assertTrue(foundConcept.getDefinition().length == 1);
		assertTrue(foundConcept.getComment().length == 0);
		assertTrue(foundConcept.getPresentation().length == 4);		
	}
	
	public void testGetConceptByCodeWildcard() throws Exception {
		LexEVSApplicationService service = LexEVSServiceHolder.instance().getLexEVSAppService();	
		
		Entity c = new Entity();
		c.setEntityCode("C4385*");
		c.setEntityCodeNamespace("NCI_Thesaurus");
		List<Entity> list = service.search(Entity.class, c, thesQueryOptions);
		assertTrue(list.size() == 11);
	}
	
	public void testGetConceptByCodeEntityDescription() throws Exception {
		LexEVSApplicationService service = LexEVSServiceHolder.instance().getLexEVSAppService();		
		
		Entity c = new Entity();
		c.setEntityCodeNamespace("NCI_Thesaurus");
		EntityDescription ed = new EntityDescription();
		ed.setContent("Irish");
		c.setEntityDescription(ed);
		List<Entity> list = service.search(Entity.class, c, thesQueryOptions);
		assertTrue(list.size() == 1);
		Entity foundConcept = list.get(0);
		assertTrue(foundConcept.getEntityCode().equals("C43856"));
	}
	
	public void testGetConceptByCodeWrongEntityDescription() throws Exception {
		LexEVSApplicationService service = LexEVSServiceHolder.instance().getLexEVSAppService();		
		
		Entity c = new Entity();
		c.setEntityCodeNamespace("NCI_Thesaurus");
		EntityDescription ed = new EntityDescription();
		ed.setContent("Irish_WRONG");
		c.setEntityDescription(ed);
		List<Entity> list = service.search(Entity.class, c, thesQueryOptions);
		assertTrue(list.size() == 0);
	}
	
	public void testGetConceptByCodePresentation() throws Exception {
		LexEVSApplicationService service = LexEVSServiceHolder.instance().getLexEVSAppService();		
		
		Entity c = new Entity();
		c.setEntityCodeNamespace("NCI_Thesaurus");
		Presentation pres = new Presentation();
		Text text = new Text();
		text.setContent("Irish Water Spaniel");
		pres.setValue(text);
		c.addPresentation(pres);
		List<Entity> list = service.search(Entity.class, c, thesQueryOptions);
		assertTrue(list.size() == 1);
		Entity foundConcept = list.get(0);
		assertTrue(foundConcept.getEntityCode().equals("C53889"));
	}
	
	public void testGetConceptByCodePreferredPresentation() throws Exception {
		LexEVSApplicationService service = LexEVSServiceHolder.instance().getLexEVSAppService();
		
		Entity c = new Entity();
		c.setEntityCodeNamespace("NCI_Thesaurus");
		Presentation pres = new Presentation();
		Text text = new Text();
		text.setContent("Oxalis acetosella");
		pres.setValue(text);
		pres.setIsPreferred(true);
		c.addPresentation(pres);
		List<Entity> list = service.search(Entity.class, c, thesQueryOptions);

		assertTrue(list.size() == 1);
		Entity foundConcept = list.get(0);
		assertTrue(foundConcept.getEntityCode().equals("C72387"));
	}
	
	public void testGetConceptByCodeNonPreferredPresentation() throws Exception {
		LexEVSApplicationService service = LexEVSServiceHolder.instance().getLexEVSAppService();
		
		Entity c = new Entity();
		c.setEntityCodeNamespace("NCI_Thesaurus");
		Presentation pres = new Presentation();
		Text text = new Text();
		text.setContent("Cuckoo-bread");
		pres.setValue(text);
		c.addPresentation(pres);
		List<Entity> list = service.search(Entity.class, c, thesQueryOptions);

		assertTrue(list.size() == 1);
		Entity foundConcept = list.get(0);
		assertTrue(foundConcept.getEntityCode().equals("C72387"));
	}
	
	public void testGetConceptByCodeWrongPreferredPresentation() throws Exception {
		LexEVSApplicationService service = LexEVSServiceHolder.instance().getLexEVSAppService();
		
		Entity c = new Entity();
		c.setEntityCodeNamespace("NCI_Thesaurus");
		Presentation pres = new Presentation();
		Text text = new Text();
		text.setContent("Cuckoo-bread");
		pres.setValue(text);
		pres.setIsPreferred(true);
		c.addPresentation(pres);
		List<Entity> list = service.search(Entity.class, c, thesQueryOptions);

		assertTrue(list.size() == 0);
	}
	
	public void testGetConceptByCodeDefinition() throws Exception {
		LexEVSApplicationService service = LexEVSServiceHolder.instance().getLexEVSAppService();
		
		Entity c = new Entity();
		c.setEntityCodeNamespace("NCI_Thesaurus");
		Definition def = new Definition();
		Text text = new Text();
		text.setContent("A mechanical device that sometimes resembles a living animal and is capable of performing a variety of often complex human tasks on command or by being programmed in advance.");
		def.setValue(text);
		c.addDefinition(def);
		List<Entity> list = service.search(Entity.class, c, thesQueryOptions);

		assertTrue(list.size() == 1);
		Entity foundConcept = list.get(0);
		assertTrue(foundConcept.getEntityCode().equals("C20678"));
	}
	
	public void testGetConceptByCodeDefinitionWildcard() throws Exception {
		LexEVSApplicationService service = LexEVSServiceHolder.instance().getLexEVSAppService();
		
		Entity c = new Entity();
		c.setEntityCodeNamespace("NCI_Thesaurus");
		Definition def = new Definition();
		Text text = new Text();
		text.setContent("*A mechanical device that sometimes resembles a living animal and is capable of performing " +
				"a variety of often complex human tasks on command or by being*");
		def.setValue(text);
		c.addDefinition(def);
		List<Entity> list = service.search(Entity.class, c, thesQueryOptions);

		assertTrue(list.size() == 1);
		Entity foundConcept = list.get(0);
		assertTrue(foundConcept.getEntityCode().equals("C20678"));
	}
	
	public void testGetConceptByProperty() throws Exception {
		LexEVSApplicationService service = LexEVSServiceHolder.instance().getLexEVSAppService();
		
		Entity c = new Entity();
		c.setEntityCodeNamespace("NCI_Thesaurus");
		Property cp = new Property();
		Text text = new Text();
		text.setContent("C0336537");
		cp.setValue(text);
		cp.setPropertyName("UMLS_CUI");
		c.addProperty(cp);
		List<Entity> list = service.search(Entity.class, c, thesQueryOptions);

		assertTrue(list.size() == 1);
		Entity foundConcept = list.get(0);
		assertTrue(foundConcept.getEntityCode().equals("C20678"));
	}
	
	public void testGetConceptByWrongProperty() throws Exception {
		LexEVSApplicationService service = LexEVSServiceHolder.instance().getLexEVSAppService();
		
		Entity c = new Entity();
		c.setEntityCodeNamespace("NCI_Thesaurus");
		Property cp = new Property();
		Text text = new Text();
		text.setContent("C20678_WRONG");
		cp.setValue(text);
		cp.setPropertyName("CONCEPT_NAME");
		c.addProperty(cp);
		List<Entity> list = service.search(Entity.class, c, thesQueryOptions);

		assertTrue(list.size() == 0);
	}
	
	public void testGetConceptByPropertyWithoutPropertyName() throws Exception {
		LexEVSApplicationService service = LexEVSServiceHolder.instance().getLexEVSAppService();
		
		Entity c = new Entity();
		c.setEntityCodeNamespace("NCI_Thesaurus");
		Property cp = new Property();
		Text text = new Text();
		
		//should match C20678 'Robot'
		text.setContent("C0336537");
		cp.setValue(text);
		c.addProperty(cp);
		List<Entity> list = service.search(Entity.class, c, thesQueryOptions);

		assertTrue(list.size() == 1);
		Entity foundConcept = list.get(0);
		assertTrue(foundConcept.getEntityCode().equals("C20678"));
	}
	
	public void testGetConceptByComment() throws Exception {
		LexEVSApplicationService service = LexEVSServiceHolder.instance().getLexEVSAppService();

		Entity c = new Entity();
		c.setEntityCodeNamespace("NCI_Thesaurus");
		Comment comment = new Comment();
		Text text = new Text();
		text.setContent("Frequently used in persons with heart disease, prosthetic heart valves, joints, etc.");
		comment.setValue(text);
		c.addComment(comment);
		List<Entity> list = service.search(Entity.class, c, thesQueryOptions);

		assertTrue(list.size() == 1);
		Entity foundConcept = list.get(0);
		assertTrue(foundConcept.getEntityCode().equals("C51993"));
	}
	
	public void testGetConceptByWrongComment() throws Exception {
		LexEVSApplicationService service = LexEVSServiceHolder.instance().getLexEVSAppService();		
		
		Entity c = new Entity();
		c.setEntityCodeNamespace("NCI_Thesaurus");
		Comment comment = new Comment();
		Text text = new Text();
		text.setContent("Frequently used in persons with heart disease, prosthetic heart valves, joints, etc.WRONG");
		comment.setValue(text);
		c.addComment(comment);
		List<Entity> list = service.search(Entity.class, c, thesQueryOptions);

		assertTrue(list.size() == 0);
	}
	
	public void testGetConceptByWildcardComment() throws Exception {
		LexEVSApplicationService service = LexEVSServiceHolder.instance().getLexEVSAppService();
		
		Entity c = new Entity();
		c.setEntityCodeNamespace("NCI_Thesaurus");
		Comment comment = new Comment();
		Text text = new Text();
		text.setContent("Frequently used in persons with heart disease, prosthetic heart valves, joints*");
		comment.setValue(text);
		c.addComment(comment);
		List<Entity> list = service.search(Entity.class, c, thesQueryOptions);

		assertTrue(list.size() == 1);
		Entity foundConcept = list.get(0);
		assertTrue(foundConcept.getEntityCode().equals("C51993"));
	}
}
