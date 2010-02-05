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

// LexBIG Test ID: T1_FNC_40	TestRetrieveRelationsforConcept

import org.LexGrid.LexBIG.DataModel.Core.Association;
import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeGraph;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.Utility.Constructors;

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
                .getCodingSchemeConcepts(THES_SCHEME, null);
        cns = cns.restrictToCodes(Constructors.createConceptReferenceList(new String[]{"C12223"}, THES_SCHEME));

        CodedNodeGraph cng = LexEVSServiceHolder.instance().getLexEVSAppService().getNodeGraph(THES_SCHEME, null, "roles");

        cng = cng.restrictToCodes(cns);
        cng = cng.restrictToAssociations(Constructors.createNameAndValueList("R82"),
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
        assertTrue(a[0].getAssociationName().equals("R82"));

    }

}