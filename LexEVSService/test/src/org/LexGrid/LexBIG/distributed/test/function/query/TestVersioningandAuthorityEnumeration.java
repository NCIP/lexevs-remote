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

// LexBIG Test ID: T1_FNC_33 TestVersioningandAuthorityEnumeration

import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.Utility.ConvenienceMethods;
import org.LexGrid.codingSchemes.CodingScheme;

/**
 * The Class TestVersioningandAuthorityEnumeration.
 */
public class TestVersioningandAuthorityEnumeration extends ServiceTestCase
{
    final static String testID = "testVersioningandAuthorityEnumeration";

    @Override
    protected String getTestID()
    {
        return testID;
    }

    /**
     * Test versioningand authority enumeration.
     * 
     * @throws LBException the LB exception
     */
    public void testVersioningandAuthorityEnumeration() throws LBException
    {

        CodingScheme cs = LexEVSServiceHolder.instance().getLexEVSAppService().resolveCodingScheme(THES_SCHEME, null);
        assertTrue("1",cs.getRepresentsVersion().equals(THES_VERSION));
        assertTrue("2",cs.getSource().length == 1);
        
        //Current Version of the NCI Thesaurus doesn't list any Sources.
        //assertTrue("3",cs.getSource()[0].getContent().equals("NCI"));

        ConvenienceMethods cm = new ConvenienceMethods(LexEVSServiceHolder.instance().getLexEVSAppService());
        assertTrue("4",cm.getRenderingDetail(THES_SCHEME, null).getRenderingDetail().getLastUpdateTime() != null);
    }

}