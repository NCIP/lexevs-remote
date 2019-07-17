package org.LexGrid.LexBIG.distributed.test.features;

import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.codingSchemes.CodingScheme;
import org.LexGrid.util.assertedvaluesets.AssertedValueSetParameters;
import org.junit.Before;
import org.junit.Test;
import org.lexgrid.resolvedvalueset.LexEVSResolvedValueSetService;

public class TestLEXEVS_4345 extends ServiceTestCase {
	LexEVSApplicationService lbs;
	AssertedValueSetParameters params;
	private LexEVSResolvedValueSetService service;

	@Before
	protected void setUp() throws Exception {
		lbs = LexEVSServiceHolder.instance().getLexEVSAppService();
		params =
		new AssertedValueSetParameters.Builder(THES_VERSION)
		.build();
		service = lbs.getLexEVSResolvedVSService(params);
	}

	@Test
	public void test() throws LBException {
		long start = System.currentTimeMillis();
		CodingScheme cs = service.listResolvedValueSetForDescription("CDISC Questionnaire BEBQ Concurrent Version Test Code Terminology");
		assertNotNull(cs);
		assertEquals(cs.getCodingSchemeName(), "CDISC Questionnaire BEBQ Concurrent Version Test Code Terminology");
		assertTrue(cs.getCodingSchemeURI().
				equals("http://evs.nci.nih.gov/valueset/CDISC/C135680"));
		long end = System.currentTimeMillis();
		System.out.println("Retrieving scheme value set from description (CDISC Questionnaire BEBQ Concurrent Version Test Code Terminology): " + (end - start) + " mseconds");
	}
		@Test
		public void test2() throws LBException {
			long start = System.currentTimeMillis();
		start = System.currentTimeMillis();
		CodingScheme cs = service.listResolvedValueSetForDescription("CDISC SDTM Directionality Terminology");
		assertNotNull(cs);
		assertEquals(cs.getCodingSchemeName(), "CDISC SDTM Directionality Terminology");
		assertTrue(cs.getCodingSchemeURI().equals( "http://evs.nci.nih.gov/valueset/CDISC/C99074") || cs.getCodingSchemeURI().equals( "http://evs.nci.nih.gov/valueset/NICHD/C99074"));
		long end = System.currentTimeMillis();
		System.out.println("Retrieving scheme value set from description (CDISC SDTM Directionality Terminology): " + (end - start) + " mseconds");
		}
		
		@Test
		public void test3() throws LBException {
			long start = System.currentTimeMillis();
		start = System.currentTimeMillis();
		CodingScheme cs = service.listResolvedValueSetForDescription("NCIt Antineoplastic Agent Terminology");
		assertNotNull(cs);
		assertEquals(cs.getCodingSchemeName(), "NCIt Antineoplastic Agent Terminology");
		assertEquals(cs.getCodingSchemeURI(), "http://evs.nci.nih.gov/valueset/C128784");
		long end = System.currentTimeMillis();
		System.out.println("Retrieving scheme value set from description (http://evs.nci.nih.gov/valueset/C128784): " + (end - start) + " mseconds");
		}
	}


