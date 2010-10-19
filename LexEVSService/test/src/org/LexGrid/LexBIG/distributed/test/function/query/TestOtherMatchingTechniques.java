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

// LexBIG Test ID: T1_FNC_18 TestOtherMatchingTechniques

import gov.nih.nci.evs.security.SecurityToken;

import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.SearchDesignationOption;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSDistributed;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

/**
 * The Class TestOtherMatchingTechniques.
 */
public class TestOtherMatchingTechniques extends ServiceTestCase
{
    final static String testID = "testOtherMatchingTechniques";

    @Override
    protected String getTestID()
    {
        return testID;
    }

    /**
     * Test other matching techniques.
     * 
     * @throws LBException the LB exception
     */
    public void testOtherMatchingTechniques() throws LBException
    {

        CodedNodeSet cns = LexEVSServiceHolder.instance().getLexEVSAppService()
                .getCodingSchemeConcepts(THES_SCHEME, null);

        cns = cns.restrictToMatchingDesignations("base heart", null, "LuceneQuery", null);

        ResolvedConceptReference[] rcr = cns.resolveToList(null, null, null, 0).getResolvedConceptReference();

        // should have found the concept code C48589 - "Base of the Heart"
        boolean found = false;
        for (int i = 0; i < rcr.length; i++)
        {
            if (rcr[i].getConceptCode().equals("C48589"))
            {
                found = true;
            }
        }
        assertTrue(found);

    }

    /**
     * Test other matching techniquesb.
     * 
     * @throws LBException the LB exception
     */
    public void testOtherMatchingTechniquesb() throws Exception
    {
        // Test some other code systems that aren't used by the other tests...

        // Do a query against a umls loaded terminology - make sure it is present.
    	LexEVSDistributed svc = LexEVSServiceHolder.instance().getLexEVSAppService();
    	SecurityToken token = new SecurityToken();
    	token.setAccessToken(ServiceTestCase.MEDDRA_TOKEN);
    	
    	svc.registerSecurityToken(ServiceTestCase.MEDDRA_SCHEME, token);
        CodedNodeSet cns = svc.getCodingSchemeConcepts(ServiceTestCase.MEDDRA_SCHEME, null);

        cns = cns.restrictToMatchingDesignations("person", null, "LuceneQuery", null);

        ResolvedConceptReference[] rcr = cns.resolveToList(null, null, null, 0).getResolvedConceptReference();

        // should have found the concept code: PURPR
        boolean found = false;
        for (int i = 0; i < rcr.length; i++)
        {
            if (rcr[i].getConceptCode().equals("10001321"))
            {
                found = true;
            }
        }
        assertTrue(found);
        
        // Do this again but resolve with the URN (Security Test).
        LexEVSDistributed svc2 = LexEVSServiceHolder.instance().getLexEVSAppService();
        svc2.registerSecurityToken(ServiceTestCase.MEDDRA_SCHEME, token);
        
        CodedNodeSet cns2 = svc2.getCodingSchemeConcepts(ServiceTestCase.MEDDRA_SCHEME, null);
        
        cns2 = cns2.restrictToMatchingDesignations("person", null, "LuceneQuery", null);

        ResolvedConceptReference[] rcr2 = cns2.resolveToList(null, null, null, 0).getResolvedConceptReference();

        // should have found the concept code: PURPR
        boolean found2 = false;
        for (int i = 0; i < rcr2.length; i++)
        {
            if (rcr2[i].getConceptCode().equals("10001321"))
            {
                found2 = true;
            }
        }
        assertTrue(found2);

    }

    /**
     * Test other matching techniquesc.
     * 
     * @throws LBException the LB exception
     */
    public void testOtherMatchingTechniquesc() throws LBException
    {
        CodedNodeSet cns = LexEVSServiceHolder.instance().getLexEVSAppService()
                .getCodingSchemeConcepts(THES_SCHEME, null);

        cns = cns.restrictToMatchingDesignations("go.*ldenrod", null, "RegExp", null);

        ResolvedConceptReference[] rcr = cns.resolveToList(null, null, null, 0).getResolvedConceptReference();

        // should have found the concept code: 73

        assertTrue("1",rcr.length == 1);
        assertTrue("2",rcr[0].getConceptCode().equals("C52191"));

    }

    /**
     * Test other matching techniquesd.
     * 
     * @throws LBException the LB exception
     */
    public void testOtherMatchingTechniquesd() throws LBException
    {
        // non preferred should have 1 hit.
        CodedNodeSet cns = LexEVSServiceHolder.instance().getLexEVSAppService()
                .getCodingSchemeConcepts(THES_SCHEME, null);

        cns = cns.restrictToMatchingDesignations("\"Blood Clot\"", SearchDesignationOption.NON_PREFERRED_ONLY,
                                           "LuceneQuery", null);
        ResolvedConceptReference[] rcr = cns.resolveToList(null, null, null, 0).getResolvedConceptReference();

        assertTrue("1",rcr.length == 1);
        assertTrue("2",rcr[0].getConceptCode().equals("C27083"));

        // preferred should have 0 hits.
        cns = LexEVSServiceHolder.instance().getLexEVSAppService().getCodingSchemeConcepts(THES_SCHEME, null);
        cns = cns.restrictToMatchingDesignations("\"Blood Vessel Tumor\"", SearchDesignationOption.PREFERRED_ONLY, "LuceneQuery",
                                           null);
        rcr = cns.resolveToList(null, null, null, 0).getResolvedConceptReference();
        assertTrue("3",rcr.length == 0);

        // Any should have 1 hit.
        cns = LexEVSServiceHolder.instance().getLexEVSAppService().getCodingSchemeConcepts(THES_SCHEME, null);
        cns = cns.restrictToMatchingDesignations("\"Blood Clot\"", SearchDesignationOption.ALL, "LuceneQuery", null);
        rcr = cns.resolveToList(null, null, null, 0).getResolvedConceptReference();
        assertTrue("4",rcr.length == 1);
        assertTrue("5",rcr[0].getConceptCode().equals("C27083"));

        // null should have 1 hit.
        cns = LexEVSServiceHolder.instance().getLexEVSAppService().getCodingSchemeConcepts(THES_SCHEME, null);
        cns = cns.restrictToMatchingDesignations("\"Blood Clot\"", null, "LuceneQuery", null);
        rcr = cns.resolveToList(null, null, null, 0).getResolvedConceptReference();
        assertTrue("6",rcr.length == 1);
        assertTrue("7",rcr[0].getConceptCode().equals("C27083"));

        // now, do the reverse queries. preferred should match, non-preferred should not.
        // preferred should have 1 hits.
        cns = LexEVSServiceHolder.instance().getLexEVSAppService().getCodingSchemeConcepts(THES_SCHEME, null);
        cns = cns.restrictToMatchingDesignations("\"Blood Clot\"", SearchDesignationOption.PREFERRED_ONLY, "LuceneQuery",
                                           null);
        rcr = cns.resolveToList(null, null, null, 0).getResolvedConceptReference();
        assertTrue(rcr.length == 1);
        
        // non-preferred should have 0 hits.
        cns = LexEVSServiceHolder.instance().getLexEVSAppService().getCodingSchemeConcepts(GO_SCHEME, null);
        cns = cns.restrictToMatchingDesignations("\"delta5-delta2,4-dienoyl-CoA isomerase activity\"", SearchDesignationOption.NON_PREFERRED_ONLY,
                                           "LuceneQuery", null);
        rcr = cns.resolveToList(null, null, null, 0).getResolvedConceptReference();
        assertTrue("8",rcr.length == 0);

        // all should have 1 hits.
        cns = LexEVSServiceHolder.instance().getLexEVSAppService().getCodingSchemeConcepts(THES_SCHEME, null);
        cns = cns.restrictToMatchingDesignations("\"Blood Clot\"", SearchDesignationOption.ALL, "LuceneQuery", null);
        rcr = cns.resolveToList(null, null, null, 0).getResolvedConceptReference();
        assertTrue("9",rcr.length == 1);

        // and so should null
        cns = LexEVSServiceHolder.instance().getLexEVSAppService().getCodingSchemeConcepts(THES_SCHEME, null);
        cns = cns.restrictToMatchingDesignations("\"Blood Clot\"", null, "LuceneQuery", null);
        rcr = cns.resolveToList(null, null, null, 0).getResolvedConceptReference();
        assertTrue("10",rcr.length == 1);

    }

}