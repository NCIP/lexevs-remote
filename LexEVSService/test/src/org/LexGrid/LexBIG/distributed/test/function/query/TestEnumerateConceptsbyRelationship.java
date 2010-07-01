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
package org.LexGrid.LexBIG.distributed.test.function.query;

// LexBIG Test ID: T1_FNC_05	TestEnumerateConceptsbyRelationship

import org.LexGrid.LexBIG.DataModel.Collections.ResolvedConceptReferenceList;
import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Extensions.Generic.LexBIGServiceConvenienceMethods;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeGraph;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

/**
 * The Class TestEnumerateConceptsbyRelationship.
 */
public class TestEnumerateConceptsbyRelationship extends ServiceTestCase
{
    final static String testID = "testEnumerateConceptsbyRelationship";

    @Override
    protected String getTestID()
    {
        return testID;
    }

    /**
     * Test enumerate conceptsby relationship.
     * 
     * @throws LBException the LB exception
     */
    public void testEnumerateConceptsbyRelationship() throws LBException
    {
    	LexBIGServiceConvenienceMethods lbscm = (LexBIGServiceConvenienceMethods)
    		LexEVSServiceHolder.instance().getLexEVSAppService().getGenericExtension("LexBIGServiceConvenienceMethods");
    	lbscm.setLexBIGService(LexEVSServiceHolder.instance().getLexEVSAppService());
    	
        CodedNodeGraph cng = LexEVSServiceHolder.instance().getLexEVSAppService().getNodeGraph(THES_SCHEME, null, null);
        cng = cng.restrictToAssociations(Constructors.createNameAndValueList(
        		lbscm.getAssociationCodeFromAssociationName(
        		THES_SCHEME, null, "Concept_In_Subset")),
                null);
        CodedNodeSet cns = cng.toNodeList(Constructors.createConceptReference("C12366", THES_SCHEME),
                true, false, -1, 0);
        ResolvedConceptReferenceList rcrl= cns.resolveToList(null, null, null, -1);
        ResolvedConceptReference[] rcr = rcrl.getResolvedConceptReference();
        
        assertTrue("Length : " + rcr.length, rcr.length == 5);

    }

}