/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
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