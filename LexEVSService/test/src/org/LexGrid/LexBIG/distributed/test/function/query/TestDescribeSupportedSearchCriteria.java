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
            assertTrue(css.getCodingSchemeDescription().toString() != null && css.getCodingSchemeDescription().toString().length() > 0);
            RenderingDetail rd = csr.getRenderingDetail();
            assertTrue(rd != null);
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