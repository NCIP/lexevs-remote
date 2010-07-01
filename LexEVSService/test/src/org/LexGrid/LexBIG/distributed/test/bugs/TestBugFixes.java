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
package org.LexGrid.LexBIG.distributed.test.bugs;

import org.LexGrid.LexBIG.DataModel.Collections.LocalNameList;
import org.LexGrid.LexBIG.DataModel.Collections.ResolvedConceptReferenceList;
import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeGraph;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.SearchDesignationOption;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.codingSchemes.CodingScheme;
import org.LexGrid.naming.SupportedAssociation;

/**
 * This class should be used as a place to write JUnit tests which show a bug, and pass when the bug is fixed.
 * 
 * @author <A HREF="mailto:armbrust.daniel@mayo.edu">Dan Armbrust</A>
 */
public class TestBugFixes extends ServiceTestCase
{
    final static String testID = "TestBugFixes";

    @Override
    protected String getTestID()
    {
        return testID;
    }

    /*
     * LexBIG Bug #2765 - http://gforge.nci.nih.gov/tracker/?func=detail&atid=134&aid=2765&group_id=14
     * 
     * Requesting to walk up a tree from a root node throws exceptions.
     */
    /**
     * Test nci root nodes1.
     * 
     * @throws LBException the LB exception
     */
    public void testNCIRootNodes1() throws LBException 
    {
        CodedNodeGraph cng = LexEVSServiceHolder.instance().getLexEVSAppService().getNodeGraph(THES_SCHEME, null, null);

        // C43652 is a root node in NCI.
        //just running this query is enough to show the bug.
        ResolvedConceptReference[] rcr = cng.resolveAsList(Constructors.createConceptReference("C12366", THES_SCHEME),
                                                           true, true, 1, 1, null, null, null, 50)
                .getResolvedConceptReference();
        
        assertTrue(rcr.length == 1);

    }

    /*
     * LexBIG Bug #2883 -
     * http://gforge.nci.nih.gov/tracker/index.php?func=detail&aid=2883&group_id=14&atid=134
     * 
     * Get Supported Associations returning unexpected items.
     */
    /**
     * Test nci supported associations.
     * 
     * @throws LBException the LB exception
     */
    public void testNCISupportedAssociations() throws LBException
    {

        CodingScheme cs = LexEVSServiceHolder.instance().getLexEVSAppService().resolveCodingScheme(THES_SCHEME, null);

        SupportedAssociation[] sa = cs.getMappings().getSupportedAssociation();
        assertTrue(sa.length >= 108);
        assertTrue(contains(sa, "differentFrom"));
        assertTrue(contains(sa, "equivalentClass"));
        assertTrue(contains(sa, "inverseOf"));

    }
    
    /*
     * Scott discovered a CodedNodeSet bug that was occuring on some databases when you provided
     * a language restriction.  
     */
    /**
     * Test validate language.
     * 
     * @throws LBException the LB exception
     */
    public void testValidateLanguage() throws LBException
    {

        CodedNodeSet cns = LexEVSServiceHolder.instance().getLexEVSAppService().
                         getCodingSchemeConcepts(THES_SCHEME, null);
        
        //priviously, just specifying a language restriction was causing an error.
        cns = cns.restrictToMatchingDesignations("heart", SearchDesignationOption.ALL, "LuceneQuery", "en");
        
        ResolvedConceptReference[] rcr = cns.resolveToList(null, null, null, 50).getResolvedConceptReference();
        
        assertTrue(rcr.length >= 40);
    }
    
    /*
     * LexBIG Bug # 3686
     * Kim discovered two bugs - it wasn't allowing you to restrict to association qualifiers, 
     * and it wasn't returning association qualifications. 
     */
    
    /**
     * Test association qualifiers.
     * 
     * @throws LBException the LB exception
     */
    public void testAssociationQualifiers() throws LBException
    {
        CodedNodeGraph cng = LexEVSServiceHolder.instance().getLexEVSAppService().getNodeGraph(THES_SCHEME, null, null);
        cng = cng.restrictToAssociations(Constructors.createNameAndValueList("R82"), Constructors.createNameAndValueList("owl:someValuesFrom"));
        ResolvedConceptReference[] rcr = cng.resolveAsList(Constructors.createConceptReference("C12366", null), 
                                                                     true, false, 1, 1, Constructors.createLocalNameList("invalid"), null, null, -1).getResolvedConceptReference();

        assertTrue(rcr.length>=1);
        assertTrue(
        	rcr[0].getSourceOf()
        		.getAssociation()[0]
        		.getAssociatedConcepts()
        		.getAssociatedConcept(0)
        		.getAssociationQualifiers()
        		.getNameAndValue(0)
        		.getName()
        		.equals("owl:someValuesFrom"));
    }
    
    private boolean contains(SupportedAssociation[] sa, String association)
    {
        boolean found = false;
        for (int i = 0; i < sa.length; i++)
        {
            if (sa[i].getLocalId().equals(association))
            {
                found = true;
                break;
            }

        }
        return found;
    }
    
    
    //GF15015
    /*
     * There was an error in the RestrictToMatchingProperties class
      that was impacting search by the special purpose 'conceptCode'
        property, and searches that intermixed 'conceptCode' with other
       properties.
     */
    public void testSearchConceptCodeAsProperty()
    {
    	try{
    		LexBIGService lbsi = LexEVSServiceHolder.instance().getLexEVSAppService();
    		LocalNameList propertyNames = new LocalNameList();
    		propertyNames.addEntry("conceptCode");
    		CodedNodeSet cns = lbsi.getCodingSchemeConcepts(ServiceTestCase.THES_SCHEME, null).restrictToMatchingProperties(propertyNames, null, "France", "exactMatch",
    				null);
    		assertNotNull("CodedNodeSet created", cns);// will have no values, since France is not a valid conceptCode
    	    ResolvedConceptReferenceList rcrl = cns.resolveToList(null, propertyNames, null, 10);

    		//propertyNames.addEntry("Synonym");
    		propertyNames.addEntry("FULL_SYN");
    		cns = lbsi.getCodingSchemeConcepts(ServiceTestCase.THES_SCHEME, null).restrictToMatchingProperties(propertyNames, null, "France", "exactMatch",
    				null);
    	    assertNotNull("CodedNodeSet created 2", cns);
    	    rcrl = cns.resolveToList(null, propertyNames, null, 10);
    	    assertTrue("Values returned",rcrl.getResolvedConceptReferenceCount()>0);
    	    		
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    		fail("Exception thrown");
    	}
    }
}