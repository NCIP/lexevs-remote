/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.distributed.test.function.query;

// LexBIG Test ID: T1_FNC_01	TestContentExtraction

import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.Utility.Iterators.ResolvedConceptReferencesIterator;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

/**
 * The Class TestContentExtraction.
 */
public class TestContentExtraction extends ServiceTestCase
{
    final static String testID = "testContentExtraction";

    @Override
    protected String getTestID()
    {
        return testID;
    }

    /**
     * Test content extraction.
     * 
     * @throws LBException the LB exception
     */
    public void testContentExtraction() throws LBException
    {
        CodedNodeSet cns = LexEVSServiceHolder.instance().getLexEVSAppService()
                .getCodingSchemeConcepts(ServiceTestCase.ZEBRAFISH_SCHEME, null);

        ResolvedConceptReferencesIterator iter = cns.resolve(null, null, null);

        // return 100 at a time -
        int count = 0;
        while (iter.hasNext())
        {
            ResolvedConceptReference[] temp = iter.next(100).getResolvedConceptReference();
            count += temp.length;
            assertTrue(temp.length <= 100);

            if (count > 400)
            {
                iter.release();
                break;
            }
        }

        // pretty basic test - iterator will let you go over the entire set of concepts.
        // you can serialize to whatever format you want.
        // have to lookup the sources and targets from each returned concept seperately.

    }

}