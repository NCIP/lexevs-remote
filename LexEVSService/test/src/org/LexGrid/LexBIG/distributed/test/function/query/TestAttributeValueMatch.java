/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.distributed.test.function.query;

// LexBIG Test ID: T1_FNC_15	TestAttributeValueMatch

import org.LexGrid.LexBIG.DataModel.Collections.LocalNameList;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.PropertyType;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

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
        assertTrue(matchAttributeValue("DEFINITION", "Shopping"));
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
        assertTrue(matchAttributeValueType(PropertyType.DEFINITION, "water"));
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