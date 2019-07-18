/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.distributed.test.services;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.LexGrid.LexBIG.DataModel.Collections.ModuleDescriptionList;
import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.DataModel.InterfaceElements.ModuleDescription;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.ActiveOption;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.PropertyType;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.concepts.Definition;
import org.LexGrid.concepts.Entity;
import org.LexGrid.concepts.Presentation;
import org.springframework.aop.framework.Advised;

/**
 * JUnit Tests for the CodedNodeSetImpl
 * 
 * @author <A HREF="mailto:armbrust.daniel@mayo.edu">Dan Armbrust</A>
 * @version subversion $Revision: 1.7 $ checked in on $Date: 2008/02/06 21:33:55 $
 */
public class CodedNodeSetImplTest extends ServiceTestCase
{

    /**
     * Test union.
     * 
     * @throws LBException the LB exception
     */
    public void testUnion() throws LBException
    {
    	LexBIGService lbsi = LexEVSServiceHolder.instance().getLexEVSAppService();

    	CodedNodeSet cns = lbsi.getCodingSchemeConcepts(THES_SCHEME, null);

    	cns = cns.restrictToCodes(Constructors.createConceptReferenceList(new String[]{"C48328"}, THES_SCHEME));
    	CodedNodeSet cns2 = lbsi.getCodingSchemeConcepts(THES_SCHEME, null);
    	cns2 = cns2.restrictToCodes(Constructors.createConceptReferenceList(new String[]{"C12727"}, THES_SCHEME));

    	cns = cns.union(cns2);

    	cns = cns.restrictToStatus(ActiveOption.ACTIVE_ONLY, null);
        ResolvedConceptReference[] rcr = cns.resolveToList(null, null, null, 50).getResolvedConceptReference();
    
        assertTrue("1",rcr.length == 2);
        assertTrue("2",contains(rcr, "C48328", THES_SCHEME));
        assertTrue("3",contains(rcr, "C12727", THES_SCHEME));  
    }

    /**
     * Test intersection.
     * 
     * @throws LBException the LB exception
     */
    public void testIntersection() throws LBException
    {
        LexBIGService lbsi = LexEVSServiceHolder.instance().getLexEVSAppService();

        CodedNodeSet cns = lbsi.getCodingSchemeConcepts(THES_SCHEME, null);

        cns = cns.restrictToCodes(Constructors.createConceptReferenceList(new String[]{"C48328", "C48327"}, THES_SCHEME));

        CodedNodeSet cns2 = lbsi.getCodingSchemeConcepts(THES_SCHEME, null);

        cns2 = cns2.restrictToCodes(Constructors.createConceptReferenceList(new String[]{"C48327", "C48326"}, THES_SCHEME));

        CodedNodeSet cnsIntersect = cns.intersect(cns2);

        ResolvedConceptReference[] rcr = cnsIntersect.resolveToList(null, null, null, 50).getResolvedConceptReference();

        assertTrue("1",rcr.length == 1);
        assertTrue("2",contains(rcr, "C48327", THES_SCHEME));
    }

    /**
     * Test difference.
     * 
     * @throws LBException the LB exception
     */
    public void testDifference() throws LBException
    {
        LexBIGService lbsi = LexEVSServiceHolder.instance().getLexEVSAppService();

        CodedNodeSet cns = lbsi.getCodingSchemeConcepts(THES_SCHEME, null);

        cns = cns.restrictToCodes(Constructors.createConceptReferenceList(new String[]{"C48328", "C48327", "C48326"},
        		THES_SCHEME));

        CodedNodeSet cns2 = lbsi.getCodingSchemeConcepts(THES_SCHEME, null);

        cns2 = cns2.restrictToCodes(Constructors.createConceptReferenceList(new String[]{"C48328", "C48329"}, THES_SCHEME));

        CodedNodeSet difference = cns.difference(cns2);

        ResolvedConceptReference[] rcr = difference.resolveToList(null, null, null, 50).getResolvedConceptReference();

        assertTrue("1",rcr.length == 2);
        assertTrue("2",contains(rcr, "C48327", THES_SCHEME));
        assertFalse("3",contains(rcr, "C48329", THES_SCHEME));
    }

    /**
     * Test union to self.
     * 
     * @throws LBException the LB exception
     */
    public void testUnionToSelf() throws LBException
    {
        LexBIGService lbsi = LexEVSServiceHolder.instance().getLexEVSAppService();

        CodedNodeSet cns = lbsi.getCodingSchemeConcepts(THES_SCHEME, null);

        cns = cns.restrictToCodes(Constructors.createConceptReferenceList(new String[]{"C48327"}, THES_SCHEME));

        CodedNodeSet union = cns.union(cns);

        ResolvedConceptReference[] rcr = union.resolveToList(null, null, null, 50).getResolvedConceptReference();

        assertTrue("1",rcr.length == 1);
        assertTrue("2",contains(rcr, "C48327", THES_SCHEME));
    }

    /**
     * Test result limit.
     * 
     * @throws LBException the LB exception
     */
    public void testResultLimit() throws LBException
    {
        LexBIGService lbsi = LexEVSServiceHolder.instance().getLexEVSAppService();

        CodedNodeSet cns = lbsi.getCodingSchemeConcepts(THES_SCHEME, null);
        
        cns = cns.restrictToMatchingDesignations("heart", null, "startsWith", null);

        ResolvedConceptReference[] rcr = cns.resolveToList(null, null, null, 1).getResolvedConceptReference();

        assertTrue("1",rcr.length == 1);
    }

    /**
 * Test restrict property type returns.
 * 
 * @throws LBException the LB exception
 */
public void testRestrictPropertyTypeReturns() throws LBException
    {
        LexBIGService lbsi = LexEVSServiceHolder.instance().getLexEVSAppService();

        CodedNodeSet cns = lbsi.getCodingSchemeConcepts(THES_SCHEME, null);
        cns = cns.restrictToCodes(Constructors.createConceptReferenceList(new String[] {"C48327"}, THES_SCHEME));

        // no type restriction
        ResolvedConceptReference[] rcrs = cns.resolveToList(null, null, null, 0).getResolvedConceptReference();
        assertTrue(rcrs.length == 1);
        Entity ce = rcrs[0].getReferencedEntry();

        assertTrue("1",ce.getCommentCount() == 0);
        assertTrue("2: " + ce.getPropertyCount(),ce.getPropertyCount() == 7);
        assertTrue("3",ce.getDefinitionCount() == 1);
        assertTrue("5",ce.getPresentationCount() >= 3);

        // restrict to a couple of presentation types
        rcrs = cns.resolveToList(
                                 null,
                                 null,
                                 new PropertyType[]{PropertyType.COMMENT, PropertyType.PRESENTATION,
                                         PropertyType.DEFINITION}, 500).getResolvedConceptReference();
        assertTrue("6",rcrs.length == 1);
        ce = rcrs[0].getReferencedEntry();

        assertTrue("7",ce.getCommentCount() == 0);
        assertTrue("8",ce.getPropertyCount() == 0);
        assertTrue("9",ce.getDefinitionCount() == 1);
        assertTrue("11",ce.getPresentationCount() >= 3);
        List<Presentation> pres = Arrays.asList(ce.getPresentation());
        List<Definition> defs	= Arrays.asList(ce.getDefinition());
        assertTrue("12",pres.stream().anyMatch(x -> x.getValue().getContent().equals("PURPLE")));
        assertTrue("13",defs.stream().anyMatch(x -> x.getValue().getContent().contains("Any of a group of colors with a hue between that of violet and red.")));  
        
        // restrict to one presentation type and one property name (which don't line up)
        rcrs = cns.resolveToList(
                                 null,
                                 Constructors.createLocalNameList("Symomym"),
                                 new PropertyType[]{PropertyType.DEFINITION},
                                 500).getResolvedConceptReference();
        assertTrue("32",rcrs.length == 1);
        ce = rcrs[0].getReferencedEntry();

        assertTrue("33",ce.getCommentCount() == 0);
        assertTrue("34",ce.getPropertyCount() == 0);
        assertTrue("35",ce.getDefinitionCount() == 0);
        assertTrue("37",ce.getPresentationCount() == 0);

    }

    private boolean contains(ResolvedConceptReference[] rcr, String code, String codeSystem)
    {
        boolean contains = false;
        for (int i = 0; i < rcr.length; i++)
        {
            if (rcrEquals(rcr[i], code, codeSystem))
            {
                contains = true;
                break;
            }
        }
        return contains;
    }

    private boolean rcrEquals(ResolvedConceptReference rcr, String code, String codeSystem)
    {
        if (rcr.getConceptCode().equals(code) && rcr.getCodingSchemeName().equals(codeSystem))
        {
            return true;
        }
        return false;
    }
    
    private ResolvedConceptReference getConcept(ResolvedConceptReference[] inRef, String conceptCode)
    {
    	ResolvedConceptReference rcr = null;
    	for (int i=0; i< inRef.length; i++)
    	{
    		if (inRef[i].getConceptCode().compareTo(conceptCode) == 0)
    		{
    			rcr = inRef[i];
    		}   		
    	}
    	
    	return rcr;
    }
    
    private void getMatchingAlgorithms(LexBIGService lbsi)
    {
   	
    	ModuleDescriptionList mdl = lbsi.getMatchAlgorithms();
    	ModuleDescription md = null;
    	for (int i=0;i< mdl.getModuleDescriptionCount();i++)
    	{
    		md = mdl.getModuleDescription(i);
    		md.getName();
    	}
    }
    
    private CodedNodeSet getObjectFromCallback(CodedNodeSet cns) throws Exception
    {
        Object interceptor = null;
        int i = 0;
        
        try{
        while (true) {
            Field field = cns.getClass().getDeclaredField("CGLIB$CALLBACK_"+i);
            field.setAccessible(true);
            Object value = field.get(cns);
            String testValue = value.getClass().getName();
            if (value.getClass().getName().contains("EqualsInterceptor")) {
                interceptor = value;
                break;
            }
            i++;
        }
        Field field = interceptor.getClass().getDeclaredField("advised");
        field.setAccessible(true);
        Advised advised = (Advised)field.get(interceptor);
        Object realObject = advised.getTargetSource().getTarget();
        CodedNodeSet cns_out = (CodedNodeSet) realObject;
        return cns_out;
        }
        catch (Exception e){
        	throw new Exception("Class not convertable");
        }
 
    }
}