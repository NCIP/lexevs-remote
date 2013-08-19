/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.test.exampleUseCases;

import java.util.List;

import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.caCore.applicationservice.QueryOptions;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.commonTypes.EntityDescription;
import org.LexGrid.concepts.Entity;

public class GetConceptFromEntityDescription extends ServiceTestCase
{
	private final String test_id = "Get Associations Tests";
	
	private QueryOptions queryOptions;
	
	@Override
	protected String getTestID() {
		return test_id;
	}
	
	public void setUp(){
		//Set up some Query Options so we're always working with the NCI Thes.
		queryOptions = new QueryOptions();
		queryOptions.setCodingScheme(ServiceTestCase.THES_SCHEME);
		
		
		CodingSchemeVersionOrTag csvt = new CodingSchemeVersionOrTag();
		csvt.setVersion(ServiceTestCase.THES_VERSION);
		queryOptions.setCodingSchemeVersionOrTag(csvt);
	}
	
	public void testGetConceptFromEntityDescription() throws Exception {
		LexEVSApplicationService service = LexEVSServiceHolder.instance().getLexEVSAppService();
		Entity concept = new Entity();
		EntityDescription ed = new EntityDescription();
		ed.setContent("Space of Mall");
		concept.setEntityDescription(ed);
		
		List<Entity> results = service.search(Entity.class, concept, queryOptions);
		
		assertTrue(results.size() == 1);	
		
		assertTrue(results.get(0).getEntityCode().equals("C33580"));
	}
}