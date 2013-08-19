/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.distributed.test.function.query;

// LexBIG Test ID: T1_FNC_27	TestDescribeSupportedSearchCriteria

import org.LexGrid.LexBIG.DataModel.Collections.CodingSchemeRenderingList;
import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeSummary;
import org.LexGrid.LexBIG.DataModel.InterfaceElements.CodingSchemeRendering;
import org.LexGrid.LexBIG.DataModel.InterfaceElements.RenderingDetail;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

/**
 * The Class TestDescribeSupportedSearchCriteria.
 */
public class TestDescribeSupportedSearchCriteria extends ServiceTestCase
{
    final static String testID = "testDescribeSupportedSearchCriteria";

    @Override
    protected String getTestID()
    {
        return testID;
    }

    /**
     * Coding scheme summary.
     * 
     * @throws LBException the LB exception
     */
    @SuppressWarnings("null")
    public void codingSchemeSummary() throws LBException
    {
        CodingSchemeRenderingList schemeList = LexEVSServiceHolder.instance().getLexEVSAppService().getSupportedCodingSchemes();

        //<dan> not sure why this is being done... doesn't have anythign to do with the intent
        //of the test, but what the heck.  I can't figure out from the design document what on earth this 
        //test is supposed to do.
        for (CodingSchemeRendering csr : schemeList.getCodingSchemeRendering())
        {
            CodingSchemeSummary css = csr.getCodingSchemeSummary();
            assertNotNull(css);
        }
    }

    /**
     * Test describe supported search criteria.
     * 
     * @throws LBException the LB exception
     */
    public void testDescribeSupportedSearchCriteria() throws LBException
    {
        codingSchemeSummary();
    }

}