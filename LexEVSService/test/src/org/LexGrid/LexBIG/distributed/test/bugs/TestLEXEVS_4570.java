package org.LexGrid.LexBIG.distributed.test.bugs;

import org.LexGrid.LexBIG.Extensions.Generic.TerminologyServiceDesignation;
import org.LexGrid.LexBIG.Impl.Extensions.GenericExtensions.LexBIGServiceConvenienceMethodsImpl;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.util.assertedvaluesets.AssertedValueSetParameters;
import org.junit.Before;
import org.junit.Test;

public class TestLEXEVS_4570 extends ServiceTestCase {
	LexEVSApplicationService svc;
	private LexBIGServiceConvenienceMethodsImpl lbscm;
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		svc = LexEVSServiceHolder.instance().getLexEVSAppService();
			 
        ((LexEVSApplicationService)svc).setAssertedValueSetConfiguration(new AssertedValueSetParameters.Builder(THES_VERSION)
		.build());

        lbscm = (LexBIGServiceConvenienceMethodsImpl)svc.getGenericExtension("LexBIGServiceConvenienceMethods");
        lbscm.setLexBIGService(svc);
	}

	@Test
	public void test() {

    	assertEquals(lbscm.getTerminologyServiceObjectType(ZEBRAFISH_URI).getDesignation(), TerminologyServiceDesignation.REGULAR_CODING_SCHEME);
    	assertEquals(lbscm.getTerminologyServiceObjectType(ITERATOR_MAPPING_URI).getDesignation(), TerminologyServiceDesignation.MAPPING_CODING_SCHEME);
    	assertEquals(lbscm.getTerminologyServiceObjectType("http://evs.nci.nih.gov/valueset/FDA/C54453").getDesignation(), TerminologyServiceDesignation.ASSERTED_VALUE_SET_SCHEME);
    	assertEquals(lbscm.getTerminologyServiceObjectType("urn:oid:2.16.840.1.113883.3.26.1.2").getDesignation(), TerminologyServiceDesignation.REGULAR_CODING_SCHEME);
    	assertEquals(lbscm.getTerminologyServiceObjectType("urn:oid:2.NON.SENSE.URI.2").getDesignation(), TerminologyServiceDesignation.UNIDENTIFIABLE);
    	assertEquals(lbscm.getTerminologyServiceObjectType("http://evs.nci.nih.gov/valueset/C126659").getDesignation(), TerminologyServiceDesignation.ASSERTED_VALUE_SET_SCHEME);
		
	}

}
