/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.distributed.test.function.query;

// LexBIG Test ID: T1_FNC_40	TestRetrieveRelationsforConcept

import org.LexGrid.LexBIG.DataModel.Core.Association;
import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeGraph;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

// TODO: Auto-generated Javadoc
/**
 * The Class TestRetrieveRelationsforConcept.
 */
public class TestRetrieveRelationsforConcept extends ServiceTestCase
{
    
    /** The Constant testID. */
    final static String testID = "testRetrieveRelationsforConcept";

    /* (non-Javadoc)
     * @see org.LexGrid.LexBIG.testUtil.ServiceTestCase#getTestID()
     */
    @Override
    protected String getTestID()
    {
        return testID;
    }

    /**
     * Test retrieve relationsfor concept.
     * 
     * @throws LBException the LB exception
     */
    public void testRetrieveRelationsforConcept() throws LBException
    {

        CodedNodeSet cns = LexEVSServiceHolder.instance().getLexEVSAppService()
                .getCodingSchemeConcepts(THES_SCHEME, Constructors.createCodingSchemeVersionOrTagFromVersion(THES_VERSION));
        cns = cns.restrictToCodes(Constructors.createConceptReferenceList(new String[]{"C12223"}, THES_SCHEME));

        CodedNodeGraph cng = LexEVSServiceHolder.instance().getLexEVSAppService().getNodeGraph(THES_SCHEME, Constructors.createCodingSchemeVersionOrTagFromVersion(THES_VERSION), "roles");

        cng = cng.restrictToSourceCodes(cns);
        cng = cng.restrictToAssociations(Constructors.createNameAndValueList("Anatomic_Structure_Is_Physical_Part_Of"),
                                   null);

        ResolvedConceptReference[] rcr = cng.resolveAsList(Constructors.createConceptReference("C12223", THES_SCHEME),
                                                           true, false, 1, 1,
                                                           Constructors.createLocalNameList("Semantic_Type"), null, null, 0)
                .getResolvedConceptReference();
        assertTrue(rcr.length >=1);
        assertTrue(rcr[0].getReferencedEntry().getProperty()[0].getPropertyName().equals("Semantic_Type"));
        assertTrue(rcr[0].getReferencedEntry().getProperty().length == 1);

        Association[] a = rcr[0].getSourceOf().getAssociation();
        assertTrue(a.length == 1);
        assertTrue(a[0].getAssociationName().equals("Anatomic_Structure_Is_Physical_Part_Of"));

    }

}