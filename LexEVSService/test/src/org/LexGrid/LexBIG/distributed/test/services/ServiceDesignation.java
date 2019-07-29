package org.LexGrid.LexBIG.distributed.test.services;

import static org.junit.Assert.assertEquals;

import org.LexGrid.LexBIG.Extensions.Generic.TerminologyServiceDesignation;
import org.LexGrid.LexBIG.Impl.LexBIGServiceImpl;
import org.LexGrid.LexBIG.Impl.Extensions.GenericExtensions.LexBIGServiceConvenienceMethodsImpl;
import org.LexGrid.LexBIG.Impl.function.LexBIGServiceTestCase;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.util.assertedvaluesets.AssertedValueSetParameters;
import org.junit.Before;
import org.junit.Test;
import org.lexevs.locator.LexEvsServiceLocator;

public class ServiceDesignation extends LexBIGServiceTestCase{
	LexEVSApplicationService svc;
	private LexBIGServiceConvenienceMethodsImpl lbscm;
	@Before
	protected void setUp() throws Exception {
		svc = LexEVSServiceHolder.instance().getLexEVSAppService();
			 
//		((LexBIGServiceImpl)svc).setAssertedValueSetConfiguration(new AssertedValueSetParameters.Builder("18.05")
//		.build());
       svc.setAssertedValueSetConfiguration(new AssertedValueSetParameters.Builder("0.1.5").
				assertedDefaultHierarchyVSRelation("Concept_In_Subset").
				codingSchemeName("owl2lexevs").
				codingSchemeURI("http://ncicb.nci.nih.gov/xml/owl/EVS/owl2lexevs.owl").
				rootConcept("C54453")
				.build());
        lbscm = (LexBIGServiceConvenienceMethodsImpl)svc.getGenericExtension("LexBIGServiceConvenienceMethods");
        lbscm.setLexBIGService(svc);
	}

	@Test
	public void test() {
    	assertEquals(lbscm.getTerminologyServiceObjectType(AUTO_URN).getDesignation(), TerminologyServiceDesignation.REGULAR_CODING_SCHEME);
    	assertEquals(lbscm.getTerminologyServiceObjectType(MAPPING_SCHEME_URI).getDesignation(), TerminologyServiceDesignation.MAPPING_CODING_SCHEME);
    	assertEquals(lbscm.getTerminologyServiceObjectType("urn:oid:C3645687.SNOMEDCT_US.ICD10").getDesignation(), TerminologyServiceDesignation.MAPPING_CODING_SCHEME);
    	assertEquals(lbscm.getTerminologyServiceObjectType("SRITEST:AUTO:AllDomesticButGM").getDesignation(), TerminologyServiceDesignation.RESOLVED_VALUESET_CODING_SCHEME);
    	assertEquals(lbscm.getTerminologyServiceObjectType("http://evs.nci.nih.gov/valueset/FDA/C54453").getDesignation(), TerminologyServiceDesignation.ASSERTED_VALUE_SET_SCHEME);
    	assertEquals(lbscm.getTerminologyServiceObjectType("urn:oid:2.NON.SENSE.URI.2").getDesignation(), TerminologyServiceDesignation.UNIDENTIFIABLE);
    	assertEquals(lbscm.getTerminologyServiceObjectType("urn:oid:2.16.840.1.113883.6.3").getDesignation(), TerminologyServiceDesignation.REGULAR_CODING_SCHEME);
//    	assertEquals(lbscm.getTerminologyServiceObjectType().getDesignation(), TerminologyServiceDesignation.REGULAR_CODING_SCHEME);
//    	assertEquals(lbscm.getTerminologyServiceObjectType(ITERATOR_MAPPING_URI).getDesignation(), TerminologyServiceDesignation.MAPPING_CODING_SCHEME);
//    	assertEquals(lbscm.getTerminologyServiceObjectType("http://evs.nci.nih.gov/valueset/FDA/C54453").getDesignation(), TerminologyServiceDesignation.ASSERTED_VALUE_SET_SCHEME);
////    	assertEquals(lbscm.getTerminologyServiceObjectType("urn:oid:2.16.840.1.113883.3.26.1.2").getDesignation(), TerminologyServiceDesignation.REGULAR_CODING_SCHEME);
//    	assertEquals(lbscm.getTerminologyServiceObjectType("urn:oid:2.NON.SENSE.URI.2").getDesignation(), TerminologyServiceDesignation.UNIDENTIFIABLE);
//    	assertEquals(lbscm.getTerminologyServiceObjectType(RESOLVED_NEOPLASM_SCHEME).getDesignation(), TerminologyServiceDesignation.RESOLVED_VALUESET_CODING_SCHEME);
		
	}

	@Override
	protected String getTestID() {
		// TODO Auto-generated method stub
		return null;
	}
}
