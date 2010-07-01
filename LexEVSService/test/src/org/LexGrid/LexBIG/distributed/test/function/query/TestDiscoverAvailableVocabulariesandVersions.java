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

// LexBIG Test ID: T1_FNC_25  TestDiscoverAvailableVocabulariesandVersions

import org.LexGrid.LexBIG.DataModel.InterfaceElements.CodingSchemeRendering;
import org.LexGrid.LexBIG.Exceptions.LBInvocationException;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

/**
 * The Class TestDiscoverAvailableVocabulariesandVersions.
 */
public class TestDiscoverAvailableVocabulariesandVersions extends ServiceTestCase
{
    final static String testID = "testDiscoverAvailableVocabulariesandVersions";

    @Override
    protected String getTestID()
    {
        return testID;
    }

    /**
     * Test discover available vocabulariesand versions.
     * 
     * @throws LBInvocationException the LB invocation exception
     */
    public void testDiscoverAvailableVocabulariesandVersions() throws LBInvocationException
    {

        CodingSchemeRendering[] csr = LexEVSServiceHolder.instance().getLexEVSAppService().getSupportedCodingSchemes()
                .getCodingSchemeRendering();

        assertTrue("1",contains(csr, THES_SCHEME, THES_VERSION));
        assertTrue("2",contains(csr, META_SCHEME, META_VERSION));
        assertTrue("3",contains(csr, SNOMED_SCHEME, SNOMED_VERSION));
        assertTrue("4",contains(csr, GO_SCHEME, GO_VERSION));

    }

    /**
     * Contains.
     * 
     * @param csr the csr
     * @param codingSchemeName the coding scheme name
     * @param codingSchemeVersion the coding scheme version
     * 
     * @return true, if successful
     */
    public boolean contains(CodingSchemeRendering[] csr, String codingSchemeName, String codingSchemeVersion)
    {
        boolean result = false;
        for (int i = 0; i < csr.length; i++)
        {
        	assertNotNull(csr[i].getCodingSchemeSummary().getLocalName());
        	assertNotNull(csr[i].getCodingSchemeSummary().getRepresentsVersion());
            if (csr[i].getCodingSchemeSummary().getLocalName().equals(codingSchemeName)
                    && csr[i].getCodingSchemeSummary().getRepresentsVersion().equals(codingSchemeVersion)
                    && csr[i].getRenderingDetail().getLastUpdateTime() != null)
            {
                result = true;
                break;

            }
        }
        return result;
    }
}