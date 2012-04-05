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