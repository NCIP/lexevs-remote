/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.distributed.test.function.query;

// LexBIG Test ID: T1_FNC_32	TestMapAttributestoTypes

import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.codingSchemes.CodingScheme;
import org.LexGrid.naming.Mappings;
import org.LexGrid.naming.SupportedProperty;

/**
 * The Class TestMapAttributestoTypes.
 */
public class TestMapAttributestoTypes extends ServiceTestCase
{
    final static String testID = "testMapAttributestoTypes";

    @Override
    protected String getTestID()
    {
        return testID;
    }

    /**
     * Test map attributesto types.
     * 
     * @throws LBException the LB exception
     */
    public void testMapAttributestoTypes() throws LBException
    {

        CodingScheme cs = LexEVSServiceHolder.instance().getLexEVSAppService().resolveCodingScheme(THES_SCHEME, null);

        Mappings m = cs.getMappings();
        SupportedProperty[] sp = m.getSupportedProperty();

        assertTrue("1",contains(sp, "ALT_DEFINITION"));
        assertTrue("2",contains(sp, "Accepted_Therapeutic_Use_For"));
       
    }

    private boolean contains(SupportedProperty[] sp, String item)
    {
        boolean result = false;

        for (int i = 0; i < sp.length; i++)
        {
            if (sp[i].getContent().equals(item))
            {
                result = true;
                break;
            }

        }
        return result;
    }

}