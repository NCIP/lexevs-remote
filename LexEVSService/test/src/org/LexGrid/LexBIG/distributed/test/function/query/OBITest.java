package org.LexGrid.LexBIG.distributed.test.function.query;

import org.LexGrid.LexBIG.DataModel.Collections.ResolvedConceptReferenceList;
import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.junit.Test;

public class OBITest extends ServiceTestCase {
	
		


	    public void testgetConceptsFromOBIBloodExactMatchTest() throws LBException{

	        CodedNodeSet cns = LexEVSServiceHolder.instance().getLexEVSAppService()
	                .getCodingSchemeConcepts("http://purl.obolibrary.org/obo/obi.owl", null);
			cns = cns.restrictToMatchingDesignations("blood",
	    	CodedNodeSet.SearchDesignationOption.PREFERRED_ONLY,
	    	"exactMatch",
	    	null);

	    	ResolvedConceptReferenceList lstResult = cns.resolveToList(null,
	    	null,
	    	null,
	    	100
	    	);
	    	
	    	ResolvedConceptReference ref = lstResult.getResolvedConceptReference(0);
	    	assertTrue(ref.getConceptCode().equals("UBERON_0000178"));
	    }
	    
	    @Test
	    public void testgetConceptsFromOBIBloodContainsTest() throws LBException{

	        CodedNodeSet cns = LexEVSServiceHolder.instance().getLexEVSAppService()
	                .getCodingSchemeConcepts("http://purl.obolibrary.org/obo/obi.owl", null);
			cns = cns.restrictToMatchingDesignations("blood",
	    	CodedNodeSet.SearchDesignationOption.PREFERRED_ONLY,
	    	"contains",
	    	null);

	    	ResolvedConceptReferenceList lstResult = cns.resolveToList(null,
	    	null,
	    	null,
	    	100
	    	);
	    	
	    	ResolvedConceptReference ref = lstResult.getResolvedConceptReference(0);
	    	for(ResolvedConceptReference r: lstResult.getResolvedConceptReference()){
	    	System.out.println(r.getEntityDescription().getContent());
	    	}
	    	assertTrue(lstResult.getResolvedConceptReferenceCount() > 1);
	    	assertTrue(ref.getConceptCode().equals("UBERON_0000178"));
	    }
	    
	    @Test
	    public void testgetConceptsFromOBIProteinExactMatchTest() throws LBException{

	        CodedNodeSet cns = LexEVSServiceHolder.instance().getLexEVSAppService()
	                .getCodingSchemeConcepts("http://purl.obolibrary.org/obo/obi.owl", null);
			cns = cns.restrictToMatchingDesignations("protein",
	    	CodedNodeSet.SearchDesignationOption.PREFERRED_ONLY,
	    	"exactMatch",
	    	null);

	    	ResolvedConceptReferenceList lstResult = cns.resolveToList(null,
	    	null,
	    	null,
	    	100
	    	);
	    	
	    	ResolvedConceptReference ref = lstResult.getResolvedConceptReference(0);
	    	assertTrue(ref.getConceptCode().equals("PR_000000001"));
	    }
	    
	    @Test
	    public void testgetConceptsFromOBIContainsTest() throws LBException{

	        CodedNodeSet cns = LexEVSServiceHolder.instance().getLexEVSAppService()
	                .getCodingSchemeConcepts("http://purl.obolibrary.org/obo/obi.owl", null);
			cns = cns.restrictToMatchingDesignations("protein",
	    	CodedNodeSet.SearchDesignationOption.PREFERRED_ONLY,
	    	"contains",
	    	null);

	    	ResolvedConceptReferenceList lstResult = cns.resolveToList(null,
	    	null,
	    	null,
	    	100
	    	);
	    	
	    	ResolvedConceptReference ref = lstResult.getResolvedConceptReference(0);
	    	for(ResolvedConceptReference r: lstResult.getResolvedConceptReference()){
	    	System.out.println(r.getEntityDescription().getContent());
	    	}
	    	assertTrue(lstResult.getResolvedConceptReferenceCount() > 1);
	    	assertTrue(ref.getConceptCode().equals("PR_000000001"));
	    }


}
