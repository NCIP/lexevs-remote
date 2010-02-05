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

// LexBIG Test ID: T1_FNC_28	TestEnumerateAllConcepts

import org.LexGrid.LexBIG.DataModel.Collections.ResolvedConceptReferenceList;
import org.LexGrid.LexBIG.DataModel.Core.ConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;

/**
 * The Class TestEnumerateAllConcepts.
 */
public class TestEnumerateAllConcepts extends ServiceTestCase
{
    final static String testID = "testEnumerateAllConcepts";

    @Override
    protected String getTestID()
    {
        return testID;
    }

    /**
     * Test enumerate all concepts.
     * 
     * @throws LBException the LB exception
     */
    public void testEnumerateAllConcepts() throws LBException
    {
        // Perform the query ...
        CodedNodeSet nodes = LexEVSServiceHolder.instance().getLexEVSAppService().getCodingSchemeConcepts(ServiceTestCase.ZEBRAFISH_SCHEME, null);
        // Process the result
        ResolvedConceptReferenceList matches = nodes.resolveToList(null, null, null, 200);
        int count = matches.getResolvedConceptReferenceCount();
        assertTrue("didn't get the expected number of concepts", count == 200);
        ConceptReference ref = (ConceptReference) matches.enumerateResolvedConceptReference().nextElement();
        assertNotNull(ref);

    }
}