/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.distributed.test.bugs;

import org.LexGrid.LexBIG.DataModel.Collections.ConceptReferenceList;
import org.LexGrid.LexBIG.DataModel.Collections.ResolvedConceptReferenceList;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

public class GForge17233 extends ServiceTestCase
{
	private final String test_id = "OWL Entity Id Tests";
	
	@Override
	protected String getTestID() {
		return test_id;
	}
	
	public void testGetCode1() throws Exception {
		LexBIGService lbsi = LexEVSServiceHolder.instance().getLexEVSAppService();
    	CodedNodeSet cns = lbsi.getCodingSchemeConcepts(THES_SCHEME, null);
    	ConceptReferenceList crl = Constructors.createConceptReferenceList(new String[]{"C12392"});
    	cns.restrictToCodes(crl);
    	ResolvedConceptReferenceList rcrl = cns.resolveToList(null, null, null, 10);
    	assertTrue(rcrl.getResolvedConceptReference().length == 1);
	}		
	
	public void testGetCodeWithWrongNamespaceAttaced1() throws Exception {
		LexBIGService lbsi = LexEVSServiceHolder.instance().getLexEVSAppService();
    	CodedNodeSet cns = lbsi.getCodingSchemeConcepts(THES_SCHEME, null);
    	ConceptReferenceList crl = Constructors.createConceptReferenceList(new String[]{"Thesaurus:C12392"});
    	cns.restrictToCodes(crl);
    	
    	//This used to be a valid code, should not be now
    	ResolvedConceptReferenceList rcrl = cns.resolveToList(null, null, null, 10);
    	assertTrue(rcrl.getResolvedConceptReference().length == 0);
	}	
	
	public void testGetCode2() throws Exception {
		LexBIGService lbsi = LexEVSServiceHolder.instance().getLexEVSAppService();
    	CodedNodeSet cns = lbsi.getCodingSchemeConcepts(THES_SCHEME, null);
    	ConceptReferenceList crl = Constructors.createConceptReferenceList(new String[]{"C43612"});
    	cns.restrictToCodes(crl);
    	ResolvedConceptReferenceList rcrl = cns.resolveToList(null, null, null, 10);
    	assertTrue(rcrl.getResolvedConceptReference().length == 1);
	}		
	
	public void testGetCodeWithWrongNamespaceAttaced2() throws Exception {
		LexBIGService lbsi = LexEVSServiceHolder.instance().getLexEVSAppService();
    	CodedNodeSet cns = lbsi.getCodingSchemeConcepts(THES_SCHEME, null);
    	ConceptReferenceList crl = Constructors.createConceptReferenceList(new String[]{"Thesaurus:C43612"});
    	cns.restrictToCodes(crl);
    	
    	//This used to be a valid code, should not be now
    	ResolvedConceptReferenceList rcrl = cns.resolveToList(null, null, null, 10);
    	assertTrue(rcrl.getResolvedConceptReference().length == 0);
	}	
}
