/*
 * Copyright: (c) 2004-2006 Mayo Foundation for Medical Education and
 * Research (MFMER).  All rights reserved.  MAYO, MAYO CLINIC, and the
 * triple-shield Mayo logo are trademarks and service marks of MFMER.
 *
 * Except as contained in the copyright notice above, the trade names, 
 * trademarks, service marks, or product names of the copyright holder shall
 * not be used in advertising, promotion or otherwise in connection with
 * this Software without prior written authorization of the copyright holder.
 * 
 * Licensed under the Eclipse Public License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 * 
 * 		http://www.eclipse.org/legal/epl-v10.html
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.LexGrid.LexBIG.caCore.test.exampleUseCases;

import java.util.Arrays;
import java.util.List;

import gov.nih.nci.system.applicationservice.ApplicationService;

import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.caCore.applicationservice.QueryOptions;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.codingSchemes.CodingScheme;
import org.LexGrid.commonTypes.EntityDescription;
import org.LexGrid.concepts.Concept;
import org.LexGrid.naming.Mappings;
import org.LexGrid.naming.SupportedAssociation;
import org.LexGrid.naming.SupportedAssociationQualifier;
import org.LexGrid.naming.SupportedCodingScheme;
import org.LexGrid.relations.Association;
import org.LexGrid.relations.AssociationSource;
import org.apache.commons.lang.ArrayUtils;

import junit.framework.TestCase;

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
		Concept concept = new Concept();
		EntityDescription ed = new EntityDescription();
		ed.setContent("Space of Mall");
		concept.setEntityDescription(ed);
		
		List<Concept> results = service.search(Concept.class, concept, queryOptions);
		
		assertTrue(results.size() == 1);	
		
		assertTrue(results.get(0).getEntityCode().equals("C33580"));
	}
}