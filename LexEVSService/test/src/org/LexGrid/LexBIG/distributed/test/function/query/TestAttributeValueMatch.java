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

// LexBIG Test ID: T1_FNC_15	TestAttributeValueMatch

import org.LexGrid.LexBIG.DataModel.Collections.LocalNameList;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.PropertyType;

/**
 * The Class TestAttributeValueMatch.
 */
public class TestAttributeValueMatch extends ServiceTestCase
{
    final static String testID = "testAttributeValueMatch";

    @Override
    protected String getTestID()
    {
        return testID;
    }

    private boolean matchAttributeValue(String prop, String value) throws LBException
    {

        CodedNodeSet cns = LexEVSServiceHolder.instance().getLexEVSAppService()
                .getCodingSchemeConcepts(THES_SCHEME, null);
        LocalNameList lnl = new LocalNameList();
        lnl.addEntry(prop);
        CodedNodeSet matches = cns.restrictToMatchingProperties(lnl, null, value, "contains", null);
        int count = matches.resolveToList(null, null, null, 0).getResolvedConceptReferenceCount();
        return (count > 0);
    }
    
    private boolean matchAttributeValueType(PropertyType prop, String value) throws LBException
    {

        CodedNodeSet cns = LexEVSServiceHolder.instance().getLexEVSAppService()
                .getCodingSchemeConcepts(THES_SCHEME, null);
        CodedNodeSet matches = cns.restrictToMatchingProperties(null, new PropertyType[] {prop}, value, "contains", null);
        int count = matches.resolveToList(null, null, null, 0).getResolvedConceptReferenceCount();
        return (count > 0);
    }

    /**
     * Test attribute value matcha.
     * 
     * @throws LBException the LB exception
     */
    public void testAttributeValueMatcha() throws LBException
    {
        assertTrue(matchAttributeValue("DEFINITION", "When"));
    }

    /**
     * Test attribute value matchb.
     * 
     * @throws LBException the LB exception
     */
    public void testAttributeValueMatchb() throws LBException
    {
        assertFalse(matchAttributeValue("DEFINITION", "NOT_VALID"));
    }
    
    /**
     * Test attribute value matchc.
     * 
     * @throws LBException the LB exception
     */
    public void testAttributeValueMatchc() throws LBException
    {
        assertTrue(matchAttributeValueType(PropertyType.DEFINITION, "When"));
    }

    /**
     * Test attribute value matchd.
     * 
     * @throws LBException the LB exception
     */
    public void testAttributeValueMatchd() throws LBException
    {
        assertFalse(matchAttributeValueType(PropertyType.DEFINITION, "NOT_VALID"));
    }

}