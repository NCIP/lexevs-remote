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

// LexBIG Test ID: T1_FNC_14	TestAttributePresenceMatch

import org.LexGrid.LexBIG.DataModel.Collections.LocalNameList;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Exceptions.LBParameterException;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.SearchDesignationOption;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

/**
 * The Class TestAttributePresenceMatch.
 */
public class TestAttributePresenceMatch extends ServiceTestCase
{
    final static String testID = "testAttributePresenceMatch";

    @Override
    protected String getTestID()
    {
        return testID;
    }

    /**
     * Match attribute.
     * 
     * @param attribute the attribute
     * 
     * @return true, if successful
     * 
     * @throws LBException the LB exception
     */
    public boolean matchAttribute(String attribute) throws LBException
    {
        CodedNodeSet cns = LexEVSServiceHolder.instance().getLexEVSAppService()
                .getCodingSchemeConcepts(THES_SCHEME, null);
        cns = cns.restrictToMatchingDesignations("heart", SearchDesignationOption.PREFERRED_ONLY, "contains", null);
        
        LocalNameList lnl = new LocalNameList();
        lnl.addEntry(attribute);

        CodedNodeSet matches = null;
        try
        {
            matches = cns.restrictToProperties(lnl, null);
        }
        catch (LBParameterException e)
        {
            return (false);
        }
        catch (Exception e) {
           // if (e.getCause().getCause().getCause().getClass() == LBParameterException.class) {
           //     return false;
            //}
            //e.printStackTrace();
            return (false);
        }

        int count = matches.resolveToList(null, null, null, 100).getResolvedConceptReferenceCount();
        return (count > 0);
    }

    /**
     * Test attribute presence matcha.
     * 
     * @throws LBException the LB exception
     */
    public void testAttributePresenceMatcha() throws LBException
    {
        assertTrue(matchAttribute("DEFINITION"));
    }

    /**
     * Test attribute presence matchb.
     * 
     * @throws LBException the LB exception
     */
    public void testAttributePresenceMatchb() throws LBException
    {
        assertFalse(matchAttribute("defunition"));
    }
    
    /**
     * Test attribute presence matchb.
     * 
     * @throws LBException the LB exception
     */
    public void testAttributePresenceMatchc() throws LBException
    {
        assertTrue(matchAttribute("FULL_SYN"));
    }


}