/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.distributed.test.function.query;

// LexBIG Test ID: T1_FNC_16	TestSearchbyStatus

import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.ActiveOption;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

/**
 * The Class TestSearchbyStatus.
 */
public class TestSearchbyStatus extends ServiceTestCase
{
    final static String testID = "testSearchbyStatus";

    @Override
    protected String getTestID()
    {
        return testID;
    }

    /**
     * Test searchby status.
     * 
     * @throws LBException the LB exception
     */
    public void testSearchbyStatus() throws LBException
    {

        CodedNodeSet cns = LexEVSServiceHolder.instance().getLexEVSAppService()
                .getCodingSchemeConcepts(THES_SCHEME, null);

        cns = cns.restrictToStatus(ActiveOption.INACTIVE_ONLY, null);
        ResolvedConceptReference[] rcr = cns.resolveToList(null, null, null, 400).getResolvedConceptReference();
        assertTrue("1",rcr.length == 400);

    }
    
    private boolean contains(ResolvedConceptReference[] rcr, String code, String codeSystem)
    {
        boolean contains = false;
        for (int i = 0; i < rcr.length; i++)
        {
            if (rcrEquals(rcr[i], code, codeSystem))
            {
                contains = true;
                break;
            }
        }
        return contains;
    }

    private boolean rcrEquals(ResolvedConceptReference rcr, String code, String codeSystem)
    {
        if (rcr.getConceptCode().equals(code) && rcr.getCodingSchemeName().equals(codeSystem))
        {
            return true;
        }
        return false;
    }
 
}