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

// LexBIG Test ID: T1_FNC_31	TestEnumerateRelationships

import java.util.Arrays;
import java.util.List;

import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Utility.ConvenienceMethods;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

/**
 * The Class TestEnumerateAssociationNames.
 */
public class TestEnumerateAssociationNames extends ServiceTestCase
{
    final static String testID = "testEnumerateAssociationNames";

    @Override
    protected String getTestID()
    {
        return testID;
    }

   
    /**
     * Test enumerate association names.
     * 
     * @throws LBException the LB exception
     */
    public void testEnumerateAssociationNames() throws LBException
    {
    	ConvenienceMethods cm = new ConvenienceMethods(LexEVSServiceHolder.instance().getLexEVSAppService());
        String[] association_names;
        association_names = cm.getAssociationForwardNames(THES_SCHEME, null);
        assertTrue(contains(association_names, new String[] {"AllDifferent","AllDifferent"}));
        association_names = cm.getAssociationReverseNames(THES_SCHEME, null);
        assertTrue(contains(association_names, new String[] {"disjointWith","equivalentClass"}));
        association_names = cm.getAssociationForwardAndReverseNames(THES_SCHEME, null);
        assertTrue(contains(association_names, new String[] {"AllDifferent","Concept_In_Subset","Has_Free_Acid_Or_Base_Form","Has_Salt_Form"}));
    }

    
    private boolean contains(String[] association_names, String[] ref_list)
    {       
    	List association_names_list= Arrays.asList(association_names);
    	boolean found=true;
        for (int i = 0; i < ref_list.length; i++)
        {            
                if (!association_names_list.contains(ref_list[i]))
                {
                    found = false;
                    break;
                }
        }
        return found;
    }
}