/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.distributed.test.function.query;

// LexBIG Test ID: T1_FNC_31	TestEnumerateRelationships

import java.util.List;

import org.LexGrid.LexBIG.DataModel.Core.ConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeGraph;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.Utility.ConvenienceMethods;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

/**
 * The Class TestEnumerateRelationships.
 */
public class TestEnumerateRelationships extends ServiceTestCase
{
    final static String testID = "testEnumerateRelationships";

    @Override
    protected String getTestID()
    {
        return testID;
    }

    /*
     * Example of listCodeRelationships
     */
    /**
     * Test enumerate relationships.
     * 
     * @throws LBException the LB exception
     */
    public void testEnumerateRelationships() throws LBException
    {

        CodedNodeGraph cng = LexEVSServiceHolder.instance().getLexEVSAppService().getNodeGraph(GO_SCHEME, Constructors.createCodingSchemeVersionOrTagFromVersion(GO_VERSION), null);

        ConceptReference ref4 = ConvenienceMethods.createConceptReference("GO:0015489", GO_SCHEME);
     
        ConceptReference ref5 = ConvenienceMethods.createConceptReference("GO:0015203", GO_SCHEME);

        List<String> rels = cng.listCodeRelationships(ref4, ref5, true);
        
        assertTrue("1", rels.contains("is_a"));    
    }
}