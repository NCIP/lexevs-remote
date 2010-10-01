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
package org.LexGrid.LexBIG.distributed.test.services;

import java.util.List;

import org.LexGrid.LexBIG.DataModel.Collections.NameAndValueList;
import org.LexGrid.LexBIG.DataModel.Collections.ResolvedConceptReferenceList;
import org.LexGrid.LexBIG.DataModel.Core.AssociatedConcept;
import org.LexGrid.LexBIG.DataModel.Core.Association;
import org.LexGrid.LexBIG.DataModel.Core.ConceptReference;
import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeGraph;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.Utility.ConvenienceMethods;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

/**
 * JUnit test cases for the coded node graph.
 *
 * @author <A HREF="mailto:armbrust.daniel@mayo.edu">Dan Armbrust</A>
 * @version subversion $Revision: 1.9 $ checked in on $Date: 2007/12/15 00:07:42 $
 */
public class CodedNodeGraphImplTest extends ServiceTestCase
{

/**
 * Test intersection.
 * 
 * @throws LBException the LB exception
 */
public void testIntersection() throws LBException
    {
        LexBIGService lbsi = LexEVSServiceHolder.instance().getLexEVSAppService();
        ConvenienceMethods cm = new ConvenienceMethods(lbsi);
        
        CodedNodeGraph cng = lbsi.getNodeGraph(THES_SCHEME, null, "roles");
        
        NameAndValueList nv = Constructors.createNameAndValueList("subClassOf");
        cng.restrictToAssociations(nv, null);
        cng.restrictToSourceCodes(cm.createCodedNodeSet(new String[] {"C25377"}, THES_SCHEME, null));

        CodedNodeGraph cng2 = lbsi.getNodeGraph(THES_SCHEME, null, "roles");
        cng2.restrictToSourceCodes(cm.createCodedNodeSet(new String[] {"C37927","C25377","C54453","C48326"}, THES_SCHEME, null));//color
        
        cng = cng.intersect(cng2);
        ResolvedConceptReferenceList rcrl = cng.resolveAsList(null, true, false, -1, -1, null, null, null, 50);
        ResolvedConceptReference[] rcr = rcrl.getResolvedConceptReference();
        
        assertNotNull("0",rcr);

        assertTrue("1",rcr.length == 1);

        assertTrue("2",rcr[0].getConceptCode().equals("C25377"));

        //no uplink
        assertTrue("3",rcr[0].getTargetOf() == null ||  rcr[0].getTargetOf().getAssociation().length == 0);
        
        //1 down link
        Association[] assn = rcr[0].getSourceOf().getAssociation();
        assertTrue("4",assn.length == 1);
        AssociatedConcept[] ac = assn[0].getAssociatedConcepts().getAssociatedConcept();
        assertTrue("5",ac.length == 1);
        assertTrue("6",ac[0].getConceptCode().equals("C25447"));
        //no more below it 
        assertTrue("7",ac[0].getSourceOf() == null);
    }
    
    /**
     * Test is code in graph.
     * 
     * @throws LBException the LB exception
     */
    public void testIsCodeInGraph() throws LBException
    {
        LexBIGService lbsi = LexEVSServiceHolder.instance().getLexEVSAppService();
        ConvenienceMethods cm = new ConvenienceMethods(lbsi);
        CodedNodeGraph cng = lbsi.getNodeGraph(THES_SCHEME, null, "roles");
         
       assertTrue("1",cng.isCodeInGraph(Constructors.createConceptReference("C12434", THES_SCHEME)).booleanValue());//Blood
 
       assertTrue("2",cng.isCodeInGraph(Constructors.createConceptReference("C12727", THES_SCHEME)).booleanValue());//Heart  
        
        cng = cng.restrictToSourceCodes(cm.createCodedNodeSet(new String[] {"C12727"}, THES_SCHEME, null));//Heart
        assertTrue("3",cng.isCodeInGraph(Constructors.createConceptReference("C13018", THES_SCHEME)).booleanValue());//Heart  
        
        cng = cng.restrictToTargetCodes(cm.createCodedNodeSet(new String[] {"C13018"}, THES_SCHEME, null));
        assertFalse("4",cng.isCodeInGraph(Constructors.createConceptReference("C12392", THES_SCHEME)).booleanValue());
    }
    
    /**
     * Test list code relationships.
     * 
     * @throws LBException the LB exception
     */
    public void testListCodeRelationships() throws  LBException
    {
        LexBIGService lbsi = LexEVSServiceHolder.instance().getLexEVSAppService();
        CodedNodeGraph cng = lbsi.getNodeGraph(THES_SCHEME, null, null);

       List<String> rels = cng.listCodeRelationships(Constructors.createConceptReference("C32770",THES_SCHEME), Constructors.createConceptReference("C61410",THES_SCHEME), false);
       assertTrue("1",rels.size() == 1);
        //Yes, has subtype should come back with the global oid for hasSubtype.
        assertTrue("2",rels.contains("Concept_In_Subset"));
    }
    
    /**
     * Test union.
     * 
     * @throws LBException the LB exception
     */
    public void testUnion() throws LBException
    {
        LexBIGService lbsi = LexEVSServiceHolder.instance().getLexEVSAppService();
        ConvenienceMethods cm = new ConvenienceMethods(lbsi);
       CodedNodeGraph cng = lbsi.getNodeGraph(THES_SCHEME, null, "roles");
      cng = cng.restrictToAssociations(Constructors.createNameAndValueList("hasSubtype"), null);
      cng = cng.restrictToSourceCodes(cm.createCodedNodeSet(new String[] {"C13018"}, THES_SCHEME, null));//Organ
        cng = cng.restrictToTargetCodes(cm.createCodedNodeSet(new String[] {"C12727"}, THES_SCHEME, null));//Heart
        
        CodedNodeGraph cng2 = lbsi.getNodeGraph(THES_SCHEME, null, "roles");
        cng2 = cng2.restrictToSourceCodes(cm.createCodedNodeSet(new String[] {"C12727"}, THES_SCHEME, null));

        //join them
        cng = cng.union(cng2);
        
        ResolvedConceptReference[] rcr = cng.resolveAsList(null, true, false, -1, -1, null, null, null, 50).getResolvedConceptReference();

        assertTrue("1",rcr.length == 1);
        //top node
        assertTrue("2",rcr[0].getConceptCode().equals("C12727"));

        //no uplink
        assertTrue("3",rcr[0].getTargetOf() == null ||  rcr[0].getTargetOf().getAssociation().length == 0);
        
        //1 down link
        Association[] assn = rcr[0].getSourceOf().getAssociation();
        assertTrue("4",assn.length == 3);   
    }
}