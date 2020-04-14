package org.LexGrid.LexBIG.distributed.test.bugs;

import static org.junit.Assert.*;

import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.junit.Before;
import org.junit.Test;

public class TestLEXEVS_4892 extends ServiceTestCase{

	@Before
	public void setUp() throws Exception {
	}

	   public void testMoreCodeSystemsLargeCUI() throws LBException {

	        CodedNodeSet cns = LexEVSServiceHolder.instance().getFreshLexEVSAppService().getCodingSchemeConcepts("urn:oid:2.16.840.1.113883.6.90", Constructors.createCodingSchemeVersionOrTagFromVersion("2020"));

	        cns = cns.restrictToMatchingDesignations("Other persistent atrial fibrillation", null, "LuceneQuery", null);

	        ResolvedConceptReference[] rcr = cns.resolveToList(null, null, null, 0).getResolvedConceptReference();

	        boolean found = false;
	        for (int i = 0; i < rcr.length; i++) {
	            if (rcr[i].getConceptCode().equals("I48.19")) {
	                found = true;
	            }
	        }
	        assertTrue(found);

	    }

}
