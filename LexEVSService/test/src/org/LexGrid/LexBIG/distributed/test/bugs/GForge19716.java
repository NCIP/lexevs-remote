/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.distributed.test.bugs;

import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.commonTypes.Property;
import org.LexGrid.concepts.Entity;

public class GForge19716 extends ServiceTestCase
{
	private final String test_id = "CQLTests";
	
	@Override
	protected String getTestID() {
		return test_id;
	}
	
	public void testGetAllProperties() throws Exception {
		LexBIGService lbsi = LexEVSServiceHolder.instance().getLexEVSAppService();
    	CodedNodeSet cns = lbsi.getCodingSchemeConcepts(THES_SCHEME, null);
    	cns = cns.restrictToCodes(Constructors.createConceptReferenceList(new String[]{"C48328"}, THES_SCHEME));
    	ResolvedConceptReference[] rcr = cns.resolveToList(null, null, null, 50).getResolvedConceptReference();
    	
		assertTrue(rcr.length == 1);
		
		ResolvedConceptReference foundEntity = rcr[0];
		Entity entity = foundEntity.getReferencedEntry();
		
		Property[] allProps = entity.getAllProperties();
		assertTrue(allProps.length > 1);
	}		
}
