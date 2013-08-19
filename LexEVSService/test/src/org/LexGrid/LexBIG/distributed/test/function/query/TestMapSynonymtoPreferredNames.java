/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.distributed.test.function.query;

// LexBIG Test ID: T1_FNC_38	TestMapSynonymtoPreferredNames

import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.SearchDesignationOption;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

/**
 * The Class TestMapSynonymtoPreferredNames.
 */
public class TestMapSynonymtoPreferredNames extends ServiceTestCase
{
    final static String testID = "testMapSynonymtoPreferredNames";

    @Override
    protected String getTestID()
    {
        return testID;
    }

    /**
     * Test map synonymto preferred names.
     * 
     * @throws LBException the LB exception
     */
    public void testMapSynonymtoPreferredNames() throws LBException
    {

        CodedNodeSet cns = LexEVSServiceHolder.instance().getLexEVSAppService()
                .getCodingSchemeConcepts(THES_SCHEME, null);
        cns = cns.restrictToMatchingDesignations("skeleton", SearchDesignationOption.ALL, "exactMatch", null);

        ResolvedConceptReference[] rcr = cns.resolveToList(null, null, null, 0)
                .getResolvedConceptReference();

        assertTrue(rcr.length == 2);
        
        boolean found = false;
        for (int i=0; i<rcr.length; i++)
        {
            
            if (rcr[i].getConceptCode().equals("C12788"))
            {
            	found = true;
                assertTrue(rcr[i].getReferencedEntry().getPropertyCount() >= 4);
                assertTrue(rcr[i].getReferencedEntry().getEntityDescription().getContent().equals("Skeletal System"));
            }
        }
        assertTrue(found);


    }

}