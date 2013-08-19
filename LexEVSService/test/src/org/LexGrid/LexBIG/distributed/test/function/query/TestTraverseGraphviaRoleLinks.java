/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.distributed.test.function.query;

// LexBIG Test ID: T1_FNC_11	TestTraverseGraphviaRoleLinks

import org.LexGrid.LexBIG.DataModel.Core.AssociatedConcept;
import org.LexGrid.LexBIG.DataModel.Core.Association;
import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeGraph;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

/**
 * The Class TestTraverseGraphviaRoleLinks.
 */
public class TestTraverseGraphviaRoleLinks extends ServiceTestCase
{
    final static String testID = "testTraverseGraphviaRoleLinks";

    @Override
    protected String getTestID()
    {
        return testID;
    }

    /**
     * Test traverse graphvia role links.
     * 
     * @throws LBException the LB exception
     */
    public void testTraverseGraphviaRoleLinks() throws LBException
    {
        // in LexBig, a role is just an association, so this is traversing associations.

        CodedNodeGraph cng = LexEVSServiceHolder.instance().getLexEVSAppService().getNodeGraph(THES_SCHEME, null, null);
        cng = cng.restrictToAssociations(Constructors.createNameAndValueList(new String[]{"subClassOf"}), null);

        // role "Anatomy_Kind"
        ResolvedConceptReference[] rcr = cng.resolveAsList(Constructors.createConceptReference("C6739", THES_SCHEME),
                                                           false, true, 1, 1, null, null, null, 0)
                .getResolvedConceptReference();
        
        Association[] a = rcr[0].getTargetOf().getAssociation();
        assertTrue(contains(a, "subClassOf", "C45747"));
        assertTrue(contains(a, "subClassOf", "C49179"));
    }
    
    

    private boolean contains(Association[] a, String association, String conceptCode)
    {
        boolean found = false;
        for (int i = 0; i < a.length; i++)
        {
            if (a[i].getAssociationName().equals(association)
                    && contains(a[i].getAssociatedConcepts().getAssociatedConcept(), conceptCode))
            {
                found = true;
                break;
            }
        }

        return found;
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