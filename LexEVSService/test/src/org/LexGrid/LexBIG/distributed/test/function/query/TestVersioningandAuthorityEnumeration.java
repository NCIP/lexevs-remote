/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.distributed.test.function.query;

// LexBIG Test ID: T1_FNC_33 TestVersioningandAuthorityEnumeration

import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Utility.ConvenienceMethods;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
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

    	CodingSchemeVersionOrTag csvt = new CodingSchemeVersionOrTag();
    	csvt.setVersion(THES_VERSION);
        CodingScheme cs = LexEVSServiceHolder.instance().getLexEVSAppService().resolveCodingScheme(THES_SCHEME, csvt);
        assertTrue("1",cs.getRepresentsVersion().equals(THES_VERSION));
        assertTrue("2",cs.getSource().length == 1);
        
        //Current Version of the NCI Thesaurus doesn't list any Sources.
        //assertTrue("3",cs.getSource()[0].getContent().equals("NCI"));

        ConvenienceMethods cm = new ConvenienceMethods(LexEVSServiceHolder.instance().getLexEVSAppService());
        assertTrue("4",cm.getRenderingDetail(THES_SCHEME, null).getRenderingDetail().getLastUpdateTime() != null);
    }

}