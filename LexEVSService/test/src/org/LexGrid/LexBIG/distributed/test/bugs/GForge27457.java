/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.distributed.test.bugs;

import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.Utility.Iterators.ResolvedConceptReferencesIterator;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

public class GForge27457 extends ServiceTestCase
{
	private final String test_id = "GForge27457";
	
	@Override
	protected String getTestID() {
		return test_id;
	}
	
	public void testGetPropertiesNoQualRestriction() throws Exception {
		LexBIGService lbsi = LexEVSServiceHolder.instance().getLexEVSAppService();
    	CodedNodeSet cns = lbsi.getCodingSchemeConcepts(THES_SCHEME, null);
    	cns = cns.restrictToProperties(
    			Constructors.createLocalNameList("Legacy_Concept_Name"), null, null, null, null);
    	ResolvedConceptReferencesIterator itr = cns.resolve(null, null, null, null, false);
    	
		assertTrue(itr.numberRemaining() > 0);
	}	
	
	public void testGetPropertiesWithQualRestriction() throws Exception {
		LexBIGService lbsi = LexEVSServiceHolder.instance().getLexEVSAppService();
    	CodedNodeSet cns = lbsi.getCodingSchemeConcepts(THES_SCHEME, null);
    	cns = cns.restrictToProperties(
    			Constructors.createLocalNameList("ALT_DEFINITION"), 
    			null, 
    			null, 
    			null, 
    			Constructors.createNameAndValueList("attr", "eCTD"));
    	ResolvedConceptReferencesIterator itr = cns.resolve(null, null, null, null, false);
    	
		assertTrue(itr.numberRemaining() > 0);
	}		
	
	public void testGetPropertiesWithBlankQualRestriction() throws Exception {
		LexBIGService lbsi = LexEVSServiceHolder.instance().getLexEVSAppService();
    	CodedNodeSet cns = lbsi.getCodingSchemeConcepts(THES_SCHEME, null);
    	cns = cns.restrictToProperties(
    			Constructors.createLocalNameList("ALT_DEFINITION"), 
    			null, 
    			null, 
    			null, 
    			Constructors.createNameAndValueList("attr", ""));
    	ResolvedConceptReferencesIterator itr = cns.resolve(null, null, null, null, false);
    	
		assertTrue(itr.numberRemaining() > 0);
	}		
	
	public void testGetPropertiesWithNullQualRestriction() throws Exception {
		LexBIGService lbsi = LexEVSServiceHolder.instance().getLexEVSAppService();
    	CodedNodeSet cns = lbsi.getCodingSchemeConcepts(THES_SCHEME, null);
    	cns = cns.restrictToProperties(
    			Constructors.createLocalNameList("ALT_DEFINITION"), 
    			null, 
    			null, 
    			null, 
    			Constructors.createNameAndValueList("attr", null));
    	ResolvedConceptReferencesIterator itr = cns.resolve(null, null, null, null, false);
    	
		assertTrue(itr.numberRemaining() > 0);
	}		
	
	
}
