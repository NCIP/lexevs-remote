package org.LexGrid.LexBIG.distributed.test.bugs;

import java.util.List;

import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.codingSchemes.CodingScheme;
import org.LexGrid.util.assertedvaluesets.AssertedValueSetParameters;
import org.junit.Before;
import org.junit.Test;
import org.lexgrid.valuesets.sourceasserted.SourceAssertedValueSetService;
import org.lexgrid.valuesets.sourceasserted.impl.SourceAssertedValueSetServiceImpl;

public class TestLEXEVS_3994 extends ServiceTestCase {

	
	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void test() throws LBException {
		LexEVSApplicationService svc;
		SourceAssertedValueSetService vsSvc;
		 AssertedValueSetParameters params = new AssertedValueSetParameters.Builder(THES_VERSION)
					.build();
		svc = LexEVSServiceHolder.instance().getFreshLexEVSAppService();

		vsSvc = svc.getLexEVSSourceAssertedValueSetServices(params);
				List<CodingScheme> schemes = vsSvc.listAllSourceAssertedValueSets();
				long count = schemes.stream().count();
				assertTrue(count > 0L);

	assertTrue(schemes.stream().filter(x -> x.getCodingSchemeURI().equals("http://evs.nci.nih.gov/valueset/CDISC/C74456")).findAny().isPresent());
	assertTrue(schemes.stream().filter(x -> x.getCodingSchemeURI().equals("http://evs.nci.nih.gov/valueset/NICHD/C74456")).findAny().isPresent());
	}

}
