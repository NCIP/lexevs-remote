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