/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.distributed.test.function.query;

// LexBIG Test ID: T1_FNC_04	TestGenerateDAG

import org.LexGrid.LexBIG.DataModel.Core.AssociatedConcept;
import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeGraph;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

/**
 * The Class TestGenerateDAG.
 */
public class TestGenerateDAG extends ServiceTestCase
{
    final static String testID = "testGenerateDAG";

    @Override
    protected String getTestID()
    {
        return testID;
    }

    /**
     * Test generate dag.
     * 
     * @throws LBException the LB exception
     */
    public void testGenerateDAG() throws LBException
    {

        CodedNodeGraph cng = LexEVSServiceHolder.instance().getLexEVSAppService().getNodeGraph(THES_SCHEME, null, null);
        cng = cng.restrictToAssociations(Constructors.createNameAndValueList("subClassOf"), null);

        ResolvedConceptReference[] rcr = cng.resolveAsList(Constructors.createConceptReference("C12727", THES_SCHEME),
                                                           true, true, 10, -1, null, null, null, 0)
                .getResolvedConceptReference();

        assertTrue("1",rcr.length == 1);
        assertTrue("2",rcr[0].getConceptCode().equals("C12727"));//Heart
    }

    private boolean contains(AssociatedConcept[] ac, String conceptCode)
    {
        boolean found = false;
        for (int i = 0; i < ac.length; i++)
        {
            if (ac[i].getConceptCode().equals(conceptCode))
            {
                found = true;
                break;
            }

        }

        return found;
    }
}