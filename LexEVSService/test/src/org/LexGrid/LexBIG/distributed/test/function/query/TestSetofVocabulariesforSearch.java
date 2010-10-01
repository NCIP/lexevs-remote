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

// LexBIG Test ID: T1_FNC_21	TestSetofVocabulariesforSearch

import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.SearchDesignationOption;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

/**
 * The Class TestSetofVocabulariesforSearch.
 */
public class TestSetofVocabulariesforSearch extends ServiceTestCase
{
    final static String testID = "testSetofVocabulariesforSearch";

    @Override
    protected String getTestID()
    {
        return testID;
    }

    /**
     * Test setof vocabulariesfor search.
     * 
     * @throws LBException the LB exception
     */
    public void testSetofVocabulariesforSearch() throws LBException
    {

        CodedNodeSet cns = LexEVSServiceHolder.instance().getLexEVSAppService()
                .getCodingSchemeConcepts(THES_SCHEME, null);

        CodedNodeSet cns2 = LexEVSServiceHolder.instance().getLexEVSAppService().getCodingSchemeConcepts(ZEBRAFISH_SCHEME, null);

        cns = cns.union(cns2);

        cns = cns.restrictToMatchingDesignations("Pect", SearchDesignationOption.ALL, "startsWith", null);

        ResolvedConceptReference[] rcr = cns.resolveToList(null, null, null, 0).getResolvedConceptReference();

        assertTrue("1",contains(rcr, THES_SCHEME, "C63647"));//Pectin
        assertTrue("2",contains(rcr, ZEBRAFISH_SCHEME, "ZFA:0001161"));//Pectoral fin

    }

    private boolean contains(ResolvedConceptReference[] rcr, String codeSystem, String conceptCode)
    {
        boolean result = false;
        for (int i = 0; i < rcr.length; i++)
        {
            if (rcr[i].getCodingSchemeName().equals(codeSystem) && rcr[i].getConceptCode().equals(conceptCode))
            {
                result = true;
                break;
            }
        }
        return result;
    }

}