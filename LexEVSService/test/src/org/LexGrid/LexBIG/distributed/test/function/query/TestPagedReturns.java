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

// LexBIG Test ID: T1_FNC_42	TestPagedReturns

import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Exceptions.LBParameterException;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.SearchDesignationOption;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.Utility.Iterators.ResolvedConceptReferencesIterator;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

/**
 * The Class TestPagedReturns.
 */
public class TestPagedReturns extends ServiceTestCase
{
    final static String testID = "testPagedReturns";

    @Override
    protected String getTestID()
    {
        return testID;
    }

    /**
     * Test paged returns.
     * 
     * @throws LBException the LB exception
     */
    public void testPagedReturns() throws LBException
    {

        CodedNodeSet cns = LexEVSServiceHolder.instance().getLexEVSAppService()
                .getCodingSchemeConcepts(THES_SCHEME, null);
        cns = cns.restrictToMatchingDesignations("hea", SearchDesignationOption.ALL, "contains", null);

        // invalid property - quicker - doesn't resolve any properties this way.
        ResolvedConceptReferencesIterator iter = cns.resolve(null, Constructors.createLocalNameList("invalidProperty"), null);
       

        // return 100 at a time - should be a total of 210
        int count = 0;  
        
        while ((iter = iter.scroll(100)) != null)
        {
            ResolvedConceptReference[] temp = iter.getNext().getResolvedConceptReference();
            count += temp.length;
            assertTrue(temp.length <= 100);
        }
        
        assertTrue(count > 200);

    }
    
    /**
     * Test paged returnsa.
     * 
     * @throws LBException the LB exception
     */
    public void testPagedReturnsa() throws LBException
    {

        //test the new get(start, end) method
        CodedNodeSet cns = LexEVSServiceHolder.instance().getLexEVSAppService()
                .getCodingSchemeConcepts(THES_SCHEME, null);
        cns = cns.restrictToMatchingDesignations("hea", SearchDesignationOption.ALL, "contains", null);

        // invalid property - quicker - doesn't resolve any properties this way.
        ResolvedConceptReferencesIterator iter = cns.resolve(null, Constructors.createLocalNameList("invalidProperty"), null);

        // return 100 at a time - should be a total of 210
        int start = 0;
        int increment = 100;
        int count = 0;
        //iter.numberRemaining can be used for this purpose if you are not using filters.
        while (start < iter.numberRemaining())
        {
            ResolvedConceptReference[] temp = iter.get(start, start + increment).getResolvedConceptReference();
            count += temp.length;
            assertTrue(temp.length <= 100);
            start = start + temp.length;
        }
        
        //going backwards with this method is legal.
        ResolvedConceptReference[] temp = iter.get(20, 50).getResolvedConceptReference();
        assertTrue(temp.length == 30);

        try
        {
            //going too far with a start point throws an exception
            iter.get(2000, 2100).getResolvedConceptReference();
            fail("Did not throw LBParameter Exception");
        }
        catch (LBParameterException e) {
            // if the test did not run remotely, this should be caught
        }
        catch (Exception e)
        {
            // if the test ran remotely, the LB exception will be buried     
        }
    }
}