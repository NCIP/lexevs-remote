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

// LexBIG Test ID: T1_FNC_37	TestMapPreferredNametoCode

import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.Utility.ConvenienceMethods;

/**
 * The Class TestMapPreferredNametoCode.
 */
public class TestMapPreferredNametoCode extends ServiceTestCase
{
    final static String testID = "testMapPreferredNametoCode";

    @Override
    protected String getTestID()
    {
        return testID;
    }

    /**
     * Test map preferred nameto code.
     * 
     * @throws LBException the LB exception
     */
    public void testMapPreferredNametoCode() throws LBException
    {

        ConvenienceMethods cm = new ConvenienceMethods(LexEVSServiceHolder.instance().getLexEVSAppService());

//        assertTrue(cm.nameToCode("Skeletal_System", THES_SCHEME, null).equals("C12788"));
//        assertFalse(cm.nameToCode("Skeletal_System", THES_SCHEME, null).equals("C12788"));
       
        //This has been taken out for 5.0
        //assertNull(cm.nameToCode("Skeletal_System", THES_SCHEME, null));
    }

}