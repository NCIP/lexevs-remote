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
    			Constructors.createLocalNameList("Legacy_Concept_Name"), 
    			null, 
    			null, 
    			null, 
    			Constructors.createNameAndValueList("label", "Legacy_Concept_Name"));
    	ResolvedConceptReferencesIterator itr = cns.resolve(null, null, null, null, false);
    	
		assertTrue(itr.numberRemaining() > 0);
	}		
	
	public void testGetPropertiesWithBlankQualRestriction() throws Exception {
		LexBIGService lbsi = LexEVSServiceHolder.instance().getLexEVSAppService();
    	CodedNodeSet cns = lbsi.getCodingSchemeConcepts(THES_SCHEME, null);
    	cns = cns.restrictToProperties(
    			Constructors.createLocalNameList("Legacy_Concept_Name"), 
    			null, 
    			null, 
    			null, 
    			Constructors.createNameAndValueList("label", ""));
    	ResolvedConceptReferencesIterator itr = cns.resolve(null, null, null, null, false);
    	
		assertTrue(itr.numberRemaining() > 0);
	}		
	
	public void testGetPropertiesWithNullQualRestriction() throws Exception {
		LexBIGService lbsi = LexEVSServiceHolder.instance().getLexEVSAppService();
    	CodedNodeSet cns = lbsi.getCodingSchemeConcepts(THES_SCHEME, null);
    	cns = cns.restrictToProperties(
    			Constructors.createLocalNameList("Legacy_Concept_Name"), 
    			null, 
    			null, 
    			null, 
    			Constructors.createNameAndValueList("label", null));
    	ResolvedConceptReferencesIterator itr = cns.resolve(null, null, null, null, false);
    	
		assertTrue(itr.numberRemaining() > 0);
	}		
	
	
}
