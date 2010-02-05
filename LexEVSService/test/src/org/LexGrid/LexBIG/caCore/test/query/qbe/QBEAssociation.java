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
package org.LexGrid.LexBIG.caCore.test.query.qbe;

import java.util.List;

import gov.nih.nci.system.applicationservice.ApplicationService;

import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.codingSchemes.CodingScheme;
import org.LexGrid.naming.Mappings;
import org.LexGrid.naming.SupportedAssociation;
import org.LexGrid.naming.SupportedAssociationQualifier;
import org.LexGrid.naming.SupportedCodingScheme;
import org.LexGrid.relations.Association;

import junit.framework.TestCase;

public class QBEAssociation extends ServiceTestCase
{
	private final String test_id = "QBE Tests";
	
	@Override
	protected String getTestID() {
		return test_id;
	}
	
	public void testRetrieveAssociations() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		Association as = new Association();
		
		List<Association> sourceList = service.search(Association.class, as);	
		
		assertTrue(sourceList != null);
		assertTrue(sourceList.size() > 0);
	}
	
	public void testRetrieveAssociationById() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		Association as = new Association();
		as.setEntityCode("CHD");
		
		List<Association> assocList = service.search(Association.class, as);	
		
		assertTrue(assocList != null);
		assertTrue(assocList.size() > 0);
		
		Association assoc = assocList.get(0);
		assertTrue(assoc.getEntityCode().equals("CHD"));
		assertTrue(assoc.getForwardName().equals("CHD"));
		assertTrue(assoc.getReverseName().equals("PAR"));
		assertTrue(assoc.getIsNavigable() == true);
		assertTrue(assoc.getIsTransitive() == true);			
	}	
	
	public void testRetrieveAssociationByForwardName() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		Association as = new Association();
		as.setEntityCodeNamespace(ServiceTestCase.THES_SCHEME);
		as.setForwardName("equivalentClass");
		
		List<Association> assocList = service.search(Association.class, as);	
		
		assertTrue(assocList != null);
		assertTrue(assocList.size() > 0);
		
		Association assoc = assocList.get(0);
		assertTrue(assoc.getEntityCodeNamespace().equals(ServiceTestCase.THES_SCHEME));
		assertTrue(assoc.getEntityCode().equals("equivalentClass"));
		assertTrue(assoc.getForwardName().equals("equivalentClass"));
		assertTrue(assoc.getReverseName().equals("equivalentClass"));
		assertTrue(assoc.getIsNavigable() == true);
		assertTrue(assoc.getIsNavigable() == true);
		assertTrue(assoc.getIsTransitive() == true);		
	}	
	
	public void testRetrieveAssociationByReverseName() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		Association as = new Association();
		as.setEntityCodeNamespace(ServiceTestCase.THES_SCHEME);
		as.setReverseName("equivalentClass");
		
		List<Association> assocList = service.search(Association.class, as);	
		
		assertTrue(assocList != null);
		assertTrue(assocList.size() > 0);
		
		Association assoc = assocList.get(0);
		assertTrue(assoc.getEntityCodeNamespace().equals(ServiceTestCase.THES_SCHEME));
		assertTrue(assoc.getEntityCode().equals("equivalentClass"));
		assertTrue(assoc.getForwardName().equals("equivalentClass"));
		assertTrue(assoc.getReverseName().equals("equivalentClass"));
		assertTrue(assoc.getIsNavigable() == true);
		assertTrue(assoc.getIsNavigable() == true);
		assertTrue(assoc.getIsTransitive() == true);					
	}	
	
	public void testRetrieveAssociationByWrongId() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		Association as = new Association();
		as.setEntityCodeNamespace(ServiceTestCase.THES_SCHEME);
		as.setEntityCode("Chemical_Or_Drug_Is_Metabolized_By_Enzyme_WRONG");
		
		List<Association> assocList = service.search(Association.class, as);	
		
		assertTrue(assocList != null);
		assertTrue(assocList.size() == 0);		
	}	
	
	public void testRetrieveAssociationByWrongForwardName() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		Association as = new Association();
		as.setEntityCodeNamespace(ServiceTestCase.THES_SCHEME);
		as.setForwardName("IsMetabolizedByEnzyme_WRONG");
		
		List<Association> assocList = service.search(Association.class, as);	
		
		assertTrue(assocList != null);
		assertTrue(assocList.size() == 0);			
	}	
	
	public void testRetrieveAssociationByWrongReverseName() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		Association as = new Association();
		as.setEntityCodeNamespace(ServiceTestCase.THES_SCHEME);
		as.setReverseName("MetabolizedByEnzymehas_WRONG");
		
		List<Association> assocList = service.search(Association.class, as);	
		
		assertTrue(assocList != null);
		assertTrue(assocList.size() == 0);		
	}	
	
	public void testRetrieveAssociationByIdWildCard() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		Association as = new Association();
		as.setEntityCodeNamespace(ServiceTestCase.THES_SCHEME);
		as.setAssociationName("equival*tClass");
		
		List<Association> assocList = service.search(Association.class, as);	
		
		assertTrue(assocList != null);
		assertTrue(assocList.size() > 0);
		
		Association assoc = assocList.get(0);
		assertTrue(assoc.getEntityCodeNamespace().equals(ServiceTestCase.THES_SCHEME));
		assertTrue(assoc.getEntityCode().equals("equivalentClass"));
		assertTrue(assoc.getForwardName().equals("equivalentClass"));
		assertTrue(assoc.getReverseName().equals("equivalentClass"));
		assertTrue(assoc.getIsNavigable() == true);
		assertTrue(assoc.getIsNavigable() == true);
		assertTrue(assoc.getIsTransitive() == true);					
	}	
	
	public void testRetrieveAssociationByWildCardForwardName() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		Association as = new Association();
		as.setEntityCodeNamespace(ServiceTestCase.THES_SCHEME);
		as.setForwardName("equival*tClass");
		
		List<Association> assocList = service.search(Association.class, as);	
		
		assertTrue(assocList != null);
		assertTrue(assocList.size() > 0);
		
		Association assoc = assocList.get(0);
		assertTrue(assoc.getEntityCodeNamespace().equals(ServiceTestCase.THES_SCHEME));
		assertTrue(assoc.getEntityCode().equals("equivalentClass"));
		assertTrue(assoc.getForwardName().equals("equivalentClass"));
		assertTrue(assoc.getReverseName().equals("equivalentClass"));
		assertTrue(assoc.getIsNavigable() == true);
		assertTrue(assoc.getIsNavigable() == true);
		assertTrue(assoc.getIsTransitive() == true);			
	}	
	
	public void testRetrieveAssociationByWildCardReverseName() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		Association as = new Association();
		as.setEntityCodeNamespace(ServiceTestCase.THES_SCHEME);
		as.setReverseName("equival*tClass");
		
		List<Association> assocList = service.search(Association.class, as);	
		
		assertTrue(assocList != null);
		assertTrue(assocList.size() > 0);
		
		Association assoc = assocList.get(0);
		assertTrue(assoc.getEntityCodeNamespace().equals(ServiceTestCase.THES_SCHEME));
		assertTrue(assoc.getEntityCode().equals("equivalentClass"));
		assertTrue(assoc.getForwardName().equals("equivalentClass"));
		assertTrue(assoc.getReverseName().equals("equivalentClass"));
		assertTrue(assoc.getIsNavigable() == true);
		assertTrue(assoc.getIsNavigable() == true);
		assertTrue(assoc.getIsTransitive() == true);			
	}	

	
}