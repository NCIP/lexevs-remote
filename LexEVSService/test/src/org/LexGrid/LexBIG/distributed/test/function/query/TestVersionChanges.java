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

// LexBIG Test ID: T1_FNC_03	TestVersionChanges

import java.net.URI;
import java.net.URISyntaxException;

import org.LexGrid.LexBIG.DataModel.Core.ConceptReference;
import org.LexGrid.LexBIG.DataModel.NCIHistory.NCIChangeEvent;
import org.LexGrid.LexBIG.DataModel.NCIHistory.types.ChangeType;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.History.HistoryService;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

/**
 * The Class TestVersionChanges.
 */
public class TestVersionChanges extends ServiceTestCase
{
    final static String testID = "testVersionChanges";

    @Override
    protected String getTestID()
    {
        return testID;
    }

    /**
     * Test version changes.
     * 
     * @throws URISyntaxException the URI syntax exception
     * @throws LBException the LB exception
     */
    public void testVersionChanges() throws URISyntaxException, LBException
    {

        HistoryService hs = LexEVSServiceHolder.instance().getLexEVSAppService()
                .getHistoryService(ServiceTestCase.THES_URN);

        ConceptReference cr = Constructors.createConceptReference("C1884", ServiceTestCase.THES_URN);
        
        NCIChangeEvent[] nce = hs.getEditActionList(cr, new URI("http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#:03.12a"))
                .getEntry();
        
        assertTrue(nce.length == 2);
        assertTrue(nce[0].getConceptcode().equals("C1884"));
        assertTrue(nce[0].getConceptName().equals(" "));
        assertTrue(nce[0].getEditDate().getTime() == Long.parseLong("1072760400000"));
        assertTrue(nce[0].getReferencecode() == null || nce[0].getReferencecode().equals("null"));
        assertTrue(nce[0].getEditaction().equals(ChangeType.MODIFY));
    }
}