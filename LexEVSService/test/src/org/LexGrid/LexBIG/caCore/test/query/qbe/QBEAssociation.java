/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
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