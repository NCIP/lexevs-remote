package org.LexGrid.LexBIG.distributed.test.live;

import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.SearchDesignationOption;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.Utility.LBConstants.MatchAlgorithms;
import org.LexGrid.LexBIG.Utility.Iterators.ResolvedConceptReferencesIterator;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.distributed.test.live.testutil.RemoteServerUtil;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;

import gov.nih.nci.system.client.ApplicationServiceProvider;

public class DistributedMetaThesaurusTest {
    LexBIGService lbSvc = null;

    public DistributedMetaThesaurusTest() throws Exception{
    	lbSvc = RemoteServerUtil.createLexBIGService();
    }
    
    public int run() throws LBException{

    	CodedNodeSet set = lbSvc.getCodingSchemeConcepts("NCI Metathesaurus", null);

    	long start = System.currentTimeMillis();
    	set = set.restrictToCodes(Constructors.createConceptReferenceList("C0332221"));
    	ResolvedConceptReferencesIterator itr = set.resolve(null, null, null);


    	int[] count = {0};
    	while(itr.hasNext()){
    		itr.next().getEntity().getPropertyAsReference().stream().forEach(x -> {
    			count[0]++;
    			System.out.println(x.getPropertyName());
    			System.out.println(x.getValue().getContent());});
    	}
    	
    	System.out.println("Count: " + count[0]);
    	long stop = System.currentTimeMillis();
    	System.out.println("read resolve time: " + (stop - start));
    	return count[0];
    }
    


}
