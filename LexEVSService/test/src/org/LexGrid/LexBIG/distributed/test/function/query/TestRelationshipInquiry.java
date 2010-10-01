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

// LexBIG Test ID: T1_FNC_12	TestRelationshipInquiry

import org.LexGrid.LexBIG.DataModel.Core.ConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeGraph;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

/**
 * The Class TestRelationshipInquiry.
 */
public class TestRelationshipInquiry extends ServiceTestCase
{
    final static String testID = "testRelationshipInquiry";

    @Override
    protected String getTestID()
    {
        return testID;
    }

    /**
     * Test relationship inquiry.
     * 
     * @throws LBException the LB exception
     */
    public void testRelationshipInquiry() throws LBException
    {

        CodedNodeGraph cng = LexEVSServiceHolder.instance().getLexEVSAppService().getNodeGraph(THES_SCHEME, null, null);       
        
        ConceptReference cr1 = new ConceptReference();
        cr1.setCode("C33231");
        cr1.setCodeNamespace("NCI_Thesaurus");
        cr1.setCodingSchemeName(THES_SCHEME);
        
        ConceptReference cr2 = new ConceptReference();
        cr2.setCode("C33090");
        cr2.setCodeNamespace("NCI_Thesaurus");
        cr2.setCodingSchemeName(THES_SCHEME);
        
        assertTrue(cng.areCodesRelated(Constructors.createNameAndValue("Anatomic_Structure_Has_Location", null),
                                       cr1,
                                       cr2, true).booleanValue());
    }
}