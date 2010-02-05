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

// LexBIG Test ID: T1_FNC_32	TestMapAttributestoTypes

import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
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