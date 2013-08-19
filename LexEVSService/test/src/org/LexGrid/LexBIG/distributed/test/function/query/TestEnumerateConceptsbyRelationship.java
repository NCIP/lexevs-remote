/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
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
import org.LexGrid.util.PrintUtility;

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
        cng = cng.restrictToAssociations(Constructors.createNameAndValueList(""), null);
        
        CodedNodeSet cns = cng.toNodeList(Constructors.createConceptReference("C12366", THES_SCHEME),
                true, false, 1, -1);
        ResolvedConceptReferenceList rcrl= cns.resolveToList(null, null, null, -1);
        ResolvedConceptReference[] rcr = rcrl.getResolvedConceptReference();

        assertTrue("Length : " + rcr.length, rcr.length == 1);
    }
}