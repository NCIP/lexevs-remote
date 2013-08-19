/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.distributed.test.function.query;

// LexBIG Test ID: T1_FNC_28	TestEnumerateAllConcepts

import org.LexGrid.LexBIG.DataModel.Collections.ResolvedConceptReferenceList;
import org.LexGrid.LexBIG.DataModel.Core.ConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

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