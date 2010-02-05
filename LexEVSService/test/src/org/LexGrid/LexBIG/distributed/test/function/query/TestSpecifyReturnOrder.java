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

// LexBIG Test ID: T1_FNC_22	TestSpecifyReturnOrder

import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.SearchDesignationOption;
import org.LexGrid.LexBIG.Utility.Constructors;

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

        assertTrue("2",rcr[0].getConceptCode().equals("C21031"));
        assertTrue("3",rcr[1].getConceptCode().equals("C25611"));
        assertTrue("4",rcr[2].getConceptCode().equals("C33284"));

        rcr = cns.resolveToList(Constructors.createSortOptionList(new String[] {"entityDescription"}, new Boolean[] {null}), null, null, -1)
                .getResolvedConceptReference();
        assertTrue("5",rcr[0].getConceptCode().equals("C34107"));
        assertTrue("6",rcr[1].getConceptCode().equals("C82601"));
        assertTrue("7",rcr[2].getConceptCode().equals("C63647"));
        
        //reverse sort 1.
        rcr = cns.resolveToList(Constructors.createSortOptionList(new String[]{"code"}, new Boolean[]{new Boolean(false)}), null, null, -1)
                .getResolvedConceptReference();
  
        assertTrue("9",rcr[0].getConceptCode().equals("C84042"));
        assertTrue("10",rcr[1].getConceptCode().equals("C82601"));
        assertTrue("11",rcr[2].getConceptCode().equals("C63647"));

    }

}