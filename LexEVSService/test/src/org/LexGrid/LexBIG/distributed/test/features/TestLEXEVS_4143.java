package org.LexGrid.LexBIG.distributed.test.features;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.LexGrid.LexBIG.DataModel.Collections.ResolvedConceptReferenceList;
import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Extensions.Generic.LexBIGServiceConvenienceMethods;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.SearchDesignationOption;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.Utility.LBConstants;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.junit.Before;
import org.junit.Test;

public class TestLEXEVS_4143 extends ServiceTestCase {
	LexEVSApplicationService svc;
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		svc = LexEVSServiceHolder.instance().getLexEVSAppService();
	
	}
	
	@Test
    public void testsearchAllAscendentsInTransitiveClosureDomainSourceSpecificPreferred( ) throws LBException{
	   LexBIGServiceConvenienceMethods lbscm =(LexBIGServiceConvenienceMethods) svc.getGenericExtension("LexBIGServiceConvenienceMethods");
	   long start = System.currentTimeMillis();
    	List<String> codes = new ArrayList<String>();
    	codes.add("C53316");
    	ResolvedConceptReferenceList refs = lbscm.searchAscendentsInTransitiveClosure(
    			THES_SCHEME, 
    			null,
    			codes, 
    			"subClassOf", 
    			"Connective and Soft Tissue Neoplasm",
    			LBConstants.MatchAlgorithms.LuceneQuery.name(),
    			SearchDesignationOption.PREFERRED_ONLY, 
    			null);
    	System.out.println("Execution time: " + (System.currentTimeMillis() - start));
    	List<ResolvedConceptReference> list = Arrays.asList(refs.getResolvedConceptReference());
    	assertTrue(list.size() > 0);
    	System.out.println(list.size());
    	assertTrue(Arrays.asList(refs.getResolvedConceptReference()).stream().anyMatch(y -> y.getCode().equals("C3810")));
    	assertTrue(Arrays.asList(refs.getResolvedConceptReference()).stream().anyMatch(x -> x.getCode().equals("C53684")));
    	assertTrue(Arrays.asList(refs.getResolvedConceptReference()).stream().anyMatch(x -> x.getCodeNamespace().equals("ncit")));
    	assertTrue(Arrays.asList(refs.getResolvedConceptReference()).stream().anyMatch(x -> x.getEntityDescription().getContent().equals("Connective and Soft Tissue Neoplasm"))); 
    }
	
	
	@Test
    public void testsearchAllAscendentsInTransitiveClosureDomainContainsPreferred( ) throws LBException{
	   LexBIGServiceConvenienceMethods lbscm =(LexBIGServiceConvenienceMethods) svc.getGenericExtension("LexBIGServiceConvenienceMethods");
	   long start = System.currentTimeMillis();
    	List<String> codes = new ArrayList<String>();
    	codes.add("C53316");
    	ResolvedConceptReferenceList refs = lbscm.searchAscendentsInTransitiveClosure(
    			THES_SCHEME, 
    			null,
    			codes, 
    			"subClassOf", 
    			"Neoplasm",
    			LBConstants.MatchAlgorithms.contains.name(),
    			SearchDesignationOption.PREFERRED_ONLY, 
    			null);
    	System.out.println("Execution time: " + (System.currentTimeMillis() - start));
    	List<ResolvedConceptReference> list = Arrays.asList(refs.getResolvedConceptReference());
    	assertTrue(list.size() > 0);
    	System.out.println(list.size());
    	assertTrue(Arrays.asList(refs.getResolvedConceptReference()).stream().anyMatch(y -> y.getCode().equals("C3810")));
    	assertTrue(Arrays.asList(refs.getResolvedConceptReference()).stream().anyMatch(x -> x.getCode().equals("C53684")));
    	assertTrue(Arrays.asList(refs.getResolvedConceptReference()).stream().anyMatch(x -> x.getCodeNamespace().equals("ncit")));
    	assertTrue(Arrays.asList(refs.getResolvedConceptReference()).stream().anyMatch(x -> x.getEntityDescription().getContent().equals("Connective and Soft Tissue Neoplasm"))); 
    }
	
	@Test
    public void testsearchAllAscendentsInTransitiveClosureDomainExactmatch( ) throws LBException{
	   LexBIGServiceConvenienceMethods lbscm =(LexBIGServiceConvenienceMethods) svc.getGenericExtension("LexBIGServiceConvenienceMethods");
	   long start = System.currentTimeMillis();
    	List<String> codes = new ArrayList<String>();
    	codes.add("C53316");
    	ResolvedConceptReferenceList refs = lbscm.searchAscendentsInTransitiveClosure(
    			THES_SCHEME, 
    			null,
    			codes, 
    			"subClassOf", 
    			"Connective and Soft Tissue Neoplasm",
    			LBConstants.MatchAlgorithms.exactMatch.name(),
    			SearchDesignationOption.ALL, 
    			Constructors.createLocalNameList("NCI"));
    	System.out.println("Execution time: " + (System.currentTimeMillis() - start));
    	List<ResolvedConceptReference> list = Arrays.asList(refs.getResolvedConceptReference());
    	assertTrue(list.size() > 0);

    	System.out.println(list.size());

    	assertTrue(Arrays.asList(refs.getResolvedConceptReference()).stream().anyMatch(y -> y.getCode().equals("C3810")));
    	assertFalse(Arrays.asList(refs.getResolvedConceptReference()).stream().anyMatch(x -> x.getCode().equals("C53684")));
    	assertTrue(Arrays.asList(refs.getResolvedConceptReference()).stream().anyMatch(x -> x.getCodeNamespace().equals("ncit")));
    	assertTrue(Arrays.asList(refs.getResolvedConceptReference()).stream().anyMatch(x -> x.getEntityDescription().getContent().equals("Connective and Soft Tissue Neoplasm"))); 
    }
	
	
	
}
