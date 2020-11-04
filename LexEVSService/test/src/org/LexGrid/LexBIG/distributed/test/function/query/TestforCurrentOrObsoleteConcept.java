/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.distributed.test.function.query;

// LexBIG Test ID: T1_FNC_23	TestforCurrentOrObsoleteConcept

import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.ActiveOption;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.SearchDesignationOption;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

/**
 * The Class TestforCurrentOrObsoleteConcept.
 */
public class TestforCurrentOrObsoleteConcept extends ServiceTestCase
{
    final static String testID = "testforCurrentOrObsoleteConcept";

    @Override
    protected String getTestID()
    {
        return testID;
    }

    /**
     * Testfor current or obsolete concepta.
     * 
     * @throws LBException the LB exception
     */
    

    /**
     * Testfor current or obsolete conceptb.
     * 
     * @throws LBException the LB exception
     */
    public void testforCurrentOrObsoleteConceptb() throws LBException
    {

        //same as above, but this time, using the new methods (that aren't deprecated)
        CodedNodeSet cns = LexEVSServiceHolder.instance().getLexEVSAppService().getCodingSchemeConcepts(GO_SCHEME, null);
        cns = cns.restrictToStatus(ActiveOption.ACTIVE_ONLY, null);
        cns = cns.restrictToCodes(Constructors.createConceptReferenceList(new String[]{"GO:0000008"}, GO_SCHEME));
        ResolvedConceptReference[] rcr = cns.resolveToList(null, null, null, 0).getResolvedConceptReference();

        assertTrue("1",rcr.length == 0);

        cns = LexEVSServiceHolder.instance().getLexEVSAppService().getCodingSchemeConcepts(GO_SCHEME, null);
        cns = cns.restrictToStatus(ActiveOption.ALL, null);
        cns = cns.restrictToCodes(Constructors.createConceptReferenceList(new String[]{"GO:0000008"}, GO_SCHEME));
        rcr = cns.resolveToList(null, null, null, 0).getResolvedConceptReference();

        assertTrue("2",rcr.length == 1);
        
        //same test again - no status restriction 
        cns = LexEVSServiceHolder.instance().getLexEVSAppService().getCodingSchemeConcepts(GO_SCHEME, null);
        cns = cns.restrictToCodes(Constructors.createConceptReferenceList(new String[]{"GO:0000008"}, GO_SCHEME));
        rcr = cns.resolveToList(null, null, null, 0).getResolvedConceptReference();

        assertTrue("3",rcr.length == 1);

        //add a status restriction
        cns = LexEVSServiceHolder.instance().getLexEVSAppService().getCodingSchemeConcepts(GO_SCHEME, null);
        cns = cns.restrictToMatchingDesignations("obsolete thioredoxin", SearchDesignationOption.ALL, "exactMatch", null);
        cns = cns.restrictToStatus(ActiveOption.INACTIVE_ONLY, null);
        rcr = cns.resolveToList(null, null, null, 0).getResolvedConceptReference();
        assertTrue("4",rcr.length == 1);

        assertFalse("5",rcr[0].getReferencedEntry().getIsActive().booleanValue());
    }
}