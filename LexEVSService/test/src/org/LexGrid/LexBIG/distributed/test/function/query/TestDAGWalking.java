/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.distributed.test.function.query;

// LexBIG Test ID: T1_FNC_07	TestDAGWalking

import org.LexGrid.LexBIG.DataModel.Core.AssociatedConcept;
import org.LexGrid.LexBIG.DataModel.Core.Association;
import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeGraph;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.distributed.test.bugs.GForge19716;
import org.LexGrid.LexBIG.distributed.test.services.LexBIGServiceConvenienceMethodsTest;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

/**
 * The Class TestDAGWalking.
 */
public class TestDAGWalking extends ServiceTestCase
{
    final static String testID = "testDAGWalking";

    @Override
    protected String getTestID()
    {
        return testID;
    }
    
    public static void main(String[] args){
    	
    	Thread t1 = new Thread(){
			@Override
			public void run() {
				while(true){
					TestContentExtraction t = new TestContentExtraction();
					try {
						t.testContentExtraction();
					} catch (LBException e) {
						throw new RuntimeException(e);
					}
				}
			}
    	};
    	
    	Thread t2 = new Thread(){
			@Override
			public void run() {
				while(true){
					TestDAGWalking w = new TestDAGWalking();
					try {
						w.testDAGWalking();
					} catch (LBException e) {
						throw new RuntimeException(e);
					}
				}
			}
    	};
    	
    	Thread t3 = new Thread(){
			@Override
			public void run() {
				while(true){
					LexBIGServiceConvenienceMethodsTest t =
						new LexBIGServiceConvenienceMethodsTest();

					t.setUp();	
					try {
						t.testIsForwardName();
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			}
    	};
    	
    	Thread t4 = new Thread(){
			@Override
			public void run() {
				while(true){
					GForge19716 t = new GForge19716();
					try {
						t.testGetAllProperties();
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			}
    	};
	
    	t1.start();
    	t2.start();	
    	t3.start();
    	t4.start();
    }
    
    
    private interface ThreadTest {
    	
    	public void runTest();
    	
    }
  
    private static void runTest(ThreadTest test){
    	try {
			test.runTest();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    

    /**
     * Test dag walking.
     * 
     * @throws LBException the LB exception
     */
    public void testDAGWalking() throws LBException
    {
        CodedNodeGraph cng = LexEVSServiceHolder.instance().getLexEVSAppService().getNodeGraph(THES_SCHEME, null, null);
        cng = cng.restrictToAssociations(Constructors.createNameAndValueList(new String[]{"subClassOf"}), null);

        ResolvedConceptReference[] rcr = cng.resolveAsList(Constructors.createConceptReference("C12366", THES_SCHEME),
                                                           true, true, 1, 1, null, null, null, 500)
                .getResolvedConceptReference();

        // check for some source (down) codes that I know should be there.
        assertTrue(rcr.length == 1);
        assertTrue(rcr[0].getConceptCode().equals("C12366"));

        Association[] a = rcr[0].getSourceOf().getAssociation();

        assertTrue(contains(a, "subClassOf", "C34076"));

        // check for some target (up) codes that I know should be there
        a = rcr[0].getTargetOf().getAssociation();

        assertTrue(contains(a, "subClassOf", "C33287"));
    }

    private boolean contains(Association[] a, String association, String conceptCode)
    {
        boolean found = false;
        for (int i = 0; i < a.length; i++)
        {
        	String name = a[i].getAssociationName();
            if (name.equals(association))
            {
            	if(contains(a[i].getAssociatedConcepts().getAssociatedConcept(), conceptCode))
            	{
                    found = true;
                    break;
            	}
                        
            }

        }

        return found;
    }

    private boolean contains(AssociatedConcept[] ac, String conceptCode)
    {
        boolean found = false;
        for (int i = 0; i < ac.length; i++)
        {
            if (ac[i].getConceptCode().equals(conceptCode))
            {
                found = true;
                break;
            }

        }

        return found;
    }

}