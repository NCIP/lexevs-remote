package org.LexGrid.LexBIG.distributed.test.features;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.LexGrid.LexBIG.DataModel.Collections.ResolvedConceptReferenceList;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Extensions.Generic.LexBIGServiceConvenienceMethods;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.junit.Before;
import org.junit.Test;


public class TestLEXEVS_4093 extends ServiceTestCase {
	LexEVSApplicationService svc;
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		svc = LexEVSServiceHolder.instance().getLexEVSAppService();
		
	}

	
	 @Test
	 public void testsearchAllDecendentsInTransitiveClosureNeoplasm( ) throws LBException{
		 LexBIGServiceConvenienceMethods lbscm =(LexBIGServiceConvenienceMethods) svc.getGenericExtension("LexBIGServiceConvenienceMethods");
		 
	    	long start = System.currentTimeMillis();
	    	List<String> list = new ArrayList<String>();
	    	list.add("C3262");
	    	ResolvedConceptReferenceList refs = lbscm.searchDescendentsInTransitiveClosure(
	    			"NCI Thesaurus", null,list, "subClassOf", "Lipoma", "LuceneQuery", null,null);
	    	System.out.println("Neoplasm Execution time: " + (System.currentTimeMillis() - start));
	    	assertTrue(Arrays.asList(refs.getResolvedConceptReference()).size() > 0);
	    	assertTrue(Arrays.asList(refs.getResolvedConceptReference()).stream().anyMatch(x -> x.getCode().equals("C5678")));
	    	assertTrue(Arrays.asList(refs.getResolvedConceptReference()).stream().anyMatch(x -> x.getCodeNamespace().equals("ncit")));
	    	assertTrue(Arrays.asList(refs.getResolvedConceptReference()).stream().anyMatch(x -> x.getEntityDescription().getContent().equals("Colorectal Lipoma")));    	
	    }

	    @Test
	    public void testsearchAllDecendentsInTransitiveClosure( ) throws LBException{
	    	LexBIGServiceConvenienceMethods lbscm =(LexBIGServiceConvenienceMethods) svc.getGenericExtension("LexBIGServiceConvenienceMethods");
	    	long start = System.currentTimeMillis();
	    	List<String> list = new ArrayList<String>();
	    	list.add("C1909");
	    	ResolvedConceptReferenceList refs = lbscm.searchDescendentsInTransitiveClosure(
	    			"NCI Thesaurus", null, list, "subClassOf", "Somatostatin", "LuceneQuery", null,null);
	    	System.out.println("Execution time: " + (System.currentTimeMillis() - start));
	    	assertTrue(Arrays.asList(refs.getResolvedConceptReference()).size() > 0);
	    	assertTrue(Arrays.asList(refs.getResolvedConceptReference()).stream().anyMatch(x -> x.getCode().equals("C836")));
	    	assertFalse(Arrays.asList(refs.getResolvedConceptReference()).stream().anyMatch(x -> x.getCode().equals("C74861")));
	    	assertFalse(Arrays.asList(refs.getResolvedConceptReference()).stream().anyMatch(x -> x.getCode().equals("C25294")));
	    	assertTrue(Arrays.asList(refs.getResolvedConceptReference()).stream().anyMatch(x -> x.getCodeNamespace().equals("ncit")));
	    	assertTrue(Arrays.asList(refs.getResolvedConceptReference()).stream().anyMatch(x -> x.getEntityDescription().getContent().equals("Recombinant Somatostatin")));    	
	    }

}
