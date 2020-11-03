/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.distributed.test.function.query;

// LexBIG Test ID: T1_FNC_12	TestRelationshipInquiry

import org.LexGrid.LexBIG.DataModel.Core.ConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeGraph;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

/**
 * The Class TestRelationshipInquiry.
 */
public class TestRelationshipInquiry extends ServiceTestCase
{
    final static String testID = "testRelationshipInquiry";

    @Override
    protected String getTestID()
    {
        return testID;
    }

    /**
     * Test relationship inquiry.
     * 
     * @throws LBException the LB exception
     */
    public void testRelationshipInquiry() throws LBException
    {

        CodedNodeGraph cng = LexEVSServiceHolder.instance().getLexEVSAppService().getNodeGraph(THES_SCHEME, null, null);       
        
        ConceptReference cr1 = new ConceptReference();
        cr1.setCode("C33231");
        cr1.setCodeNamespace("ncit");
        cr1.setCodingSchemeName(THES_SCHEME);
        
        ConceptReference cr2 = new ConceptReference();
        cr2.setCode("C33090");
        cr2.setCodeNamespace("ncit");
        cr2.setCodingSchemeName(THES_SCHEME);
        
        assertTrue(cng.areCodesRelated(Constructors.createNameAndValue("Anatomic_Structure_Has_Location", null),
                                       cr1,
                                       cr2, true).booleanValue());
    }
}