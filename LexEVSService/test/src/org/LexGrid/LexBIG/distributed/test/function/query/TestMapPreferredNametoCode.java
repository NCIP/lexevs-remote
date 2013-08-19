/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.distributed.test.function.query;

// LexBIG Test ID: T1_FNC_37	TestMapPreferredNametoCode

import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Utility.ConvenienceMethods;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

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