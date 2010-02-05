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
