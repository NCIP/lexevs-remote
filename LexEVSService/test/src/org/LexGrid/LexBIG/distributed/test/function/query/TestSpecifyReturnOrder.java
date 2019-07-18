/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.distributed.test.function.query;

// LexBIG Test ID: T1_FNC_22	TestSpecifyReturnOrder

import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.SearchDesignationOption;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

/**
 * The Class TestSpecifyReturnOrder.
 */
public class TestSpecifyReturnOrder extends ServiceTestCase
{
    final static String testID = "testSpecifyReturnOrder";

    @Override
    protected String getTestID()
    {
        return testID;
    }

    /**
     * Test specify return order.
     * 
     * @throws LBException the LB exception
     */
    public void testSpecifyReturnOrder() throws LBException
    {

        CodedNodeSet cns = LexEVSServiceHolder.instance().getLexEVSAppService().getCodingSchemeConcepts(THES_SCHEME, null);
        cns = cns.restrictToMatchingDesignations("Pect", SearchDesignationOption.ALL, "startsWith", null);
        ResolvedConceptReference[] rcr = cns.resolveToList(Constructors.createSortOptionList(new String[] {"code"}, new Boolean[] {null}), null, null, -1)
                .getResolvedConceptReference();
        assertTrue("1",rcr[0].getConceptCode().equals("C103178"));System.out.println(rcr[0].getEntityDescription().getContent());
        assertTrue("2",rcr[1].getConceptCode().equals("C120322"));System.out.println(rcr[1].getEntityDescription().getContent());
        assertTrue("3",rcr[2].getConceptCode().equals("C154224"));System.out.println(rcr[2].getEntityDescription().getContent());
        assertTrue("4",rcr[3].getConceptCode().equals("C21031"));System.out.println(rcr[3].getEntityDescription().getContent());
        assertTrue("5",rcr[4].getConceptCode().equals("C25611"));System.out.println(rcr[4].getEntityDescription().getContent());
        assertTrue("6",rcr[5].getConceptCode().equals("C33284"));System.out.println(rcr[5].getEntityDescription().getContent());

        rcr = cns.resolveToList(Constructors.createSortOptionList(new String[] {"entityDescription"}, new Boolean[] {null}), null, null, -1)
                .getResolvedConceptReference();
        assertTrue("6",rcr[0].getConceptCode().equals("C34107"));
        assertTrue("7",rcr[1].getConceptCode().equals("C82601"));
        assertTrue("8",rcr[2].getConceptCode().equals("C103178"));
        
        //reverse sort 1.
        rcr = cns.resolveToList(Constructors.createSortOptionList(new String[]{"code"}, new Boolean[]{new Boolean(false)}), null, null, -1)
                .getResolvedConceptReference();
  
        assertTrue("9",rcr[0].getConceptCode().equals("C84042"));
        assertTrue("10",rcr[1].getConceptCode().equals("C82601"));
        assertTrue("11",rcr[2].getConceptCode().equals("C63647"));

    }

}