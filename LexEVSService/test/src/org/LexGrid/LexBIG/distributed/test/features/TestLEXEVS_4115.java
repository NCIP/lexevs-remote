package org.LexGrid.LexBIG.distributed.test.features;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.LexGrid.LexBIG.DataModel.Collections.ResolvedConceptReferenceList;
import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Exceptions.LBParameterException;
import org.LexGrid.LexBIG.Extensions.Generic.LexBIGServiceConvenienceMethods;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.SearchDesignationOption;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.Utility.LBConstants;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.junit.Before;
import org.junit.Test;

public class TestLEXEVS_4115 extends ServiceTestCase {

	LexEVSApplicationService svc;
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		svc = LexEVSServiceHolder.instance().getLexEVSAppService();
		
	}

	   @Test
	    public void testsearchAllDecendentsInTransitiveClosureDomainMildlySickPatientSourceSpecificPreferred( ) throws LBException{
		   LexBIGServiceConvenienceMethods lbscm =(LexBIGServiceConvenienceMethods) svc.getGenericExtension("LexBIGServiceConvenienceMethods");
		   long start = System.currentTimeMillis();
	    	List<String> codes = new ArrayList<String>();
	    	codes.add("C3262");
	    	ResolvedConceptReferenceList refs = lbscm.searchDescendentsInTransitiveClosure(
	    			THES_SCHEME, 
	    			null,
	    			codes, 
	    			"subClassOf", 
	    			"Cavernous Lymphangioma",
	    			LBConstants.MatchAlgorithms.LuceneQuery.name(),
	    			SearchDesignationOption.PREFERRED_ONLY, 
	    			null);
	    	System.out.println("Execution time: " + (System.currentTimeMillis() - start));
	    	List<ResolvedConceptReference> list = Arrays.asList(refs.getResolvedConceptReference());
	    	assertTrue(list.size() > 0);
	    	assertEquals(list.get(0).getCode(),"C53316");
	    	System.out.println(list.size());
	    	//assertFalse(list.stream().anyMatch(x -> x.getCode().equals("C53316")));
	    	assertTrue(Arrays.asList(refs.getResolvedConceptReference()).stream().anyMatch(y -> y.getCode().equals("C53316")));
	    	//assertFalse(Arrays.asList(refs.getResolvedConceptReference()).stream().anyMatch(x -> x.getCode().equals("PatientWithCold")));
	    	assertTrue(Arrays.asList(refs.getResolvedConceptReference()).stream().anyMatch(x -> x.getCodeNamespace().equals("ncit")));
	    	assertTrue(Arrays.asList(refs.getResolvedConceptReference()).stream().anyMatch(x -> x.getEntityDescription().getContent().equals("Cavernous Lymphangioma"))); 
	    }

	    @Test
	    public void testsearchAllDecendentsInTransitiveClosureDomainMildlySickPatientContainsPreferred( ) throws LBException{
	    	LexBIGServiceConvenienceMethods lbscm =(LexBIGServiceConvenienceMethods) svc.getGenericExtension("LexBIGServiceConvenienceMethods");
	    	long start = System.currentTimeMillis();
	    	List<String> codes = new ArrayList<String>();
	    	codes.add("C3262");
	    	ResolvedConceptReferenceList refs = lbscm.searchDescendentsInTransitiveClosure(
	    			THES_SCHEME, 
	    			null,
	    			codes, 
	    			"subClassOf", 
	    			"Lymphangioma",
	    			LBConstants.MatchAlgorithms.contains.name(),
	    			SearchDesignationOption.PREFERRED_ONLY, 
	    			null);
	    	System.out.println("Execution time: " + (System.currentTimeMillis() - start));
	    	List<ResolvedConceptReference> list = Arrays.asList(refs.getResolvedConceptReference());
	    	assertTrue(list.size() > 0);
	    	assertTrue(list.stream().anyMatch(x -> x.getCode().equals("C53316")));
	    	assertTrue(Arrays.asList(refs.getResolvedConceptReference()).stream().anyMatch(y -> y.getCode().equals("C8965")));
	    	//assertTrue(Arrays.asList(refs.getResolvedConceptReference()).stream().anyMatch(x -> x.getCode().equals("Lymphangioma")));
	    	assertTrue(Arrays.asList(refs.getResolvedConceptReference()).stream().anyMatch(x -> x.getCodeNamespace().equals("ncit")));
	    	assertTrue(Arrays.asList(refs.getResolvedConceptReference()).stream().anyMatch(x -> x.getEntityDescription().getContent().equals("Cavernous Lymphangioma"))); 
	    }
	
}
