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

import gov.nih.nci.system.applicationservice.ApplicationService;

import java.util.List;

import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.relations.AssociationPredicate;

public class QBEAssociation extends ServiceTestCase
{
	private final String test_id = "QBE Tests";
	
	@Override
	protected String getTestID() {
		return test_id;
	}
	
	public void testRetrieveAssociations() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		AssociationPredicate as = new AssociationPredicate();
		
		List<AssociationPredicate> sourceList = service.search(AssociationPredicate.class, as);	
		
		assertTrue(sourceList != null);
		assertTrue(sourceList.size() > 0);
	}
	
	public void testRetrieveAssociationById() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		AssociationPredicate as = new AssociationPredicate();
		as.setAssociationName("CHD");
		
		List<AssociationPredicate> assocList = service.search(AssociationPredicate.class, as);	
		
		assertTrue(assocList != null);
		assertTrue(assocList.size() > 0);
		
		AssociationPredicate assoc = assocList.get(0);
		assertTrue(assoc.getAssociationName().equals("CHD"));
	}	

	public void testRetrieveAssociationByIdWildCard() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		AssociationPredicate as = new AssociationPredicate();
		as.setAssociationName("C*D");
		
		List<AssociationPredicate> assocList = service.search(AssociationPredicate.class, as);	
		
		assertTrue(assocList != null);
		assertTrue(assocList.size() > 0);
		
		AssociationPredicate assoc = assocList.get(0);
		assertTrue(assoc.getAssociationName().equals("CHD"));
	}	
}