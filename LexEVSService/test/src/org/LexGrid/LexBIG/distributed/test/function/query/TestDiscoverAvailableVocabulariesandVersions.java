/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
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