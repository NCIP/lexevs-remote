package org.LexGrid.LexBIG.distributed.test.bugs;

import java.net.URI;
import java.net.URISyntaxException;

import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.naming.SupportedCodingScheme;
import org.LexGrid.naming.SupportedNamespace;
import org.LexGrid.valueSets.ValueSetDefinition;
import org.junit.Before;
import org.junit.Test;
import org.lexgrid.valuesets.LexEVSValueSetDefinitionServices;

public class TestLEXEVS_4244 extends ServiceTestCase{

	LexEVSValueSetDefinitionServices def;

	@Before
	public void setUp() throws Exception {
		LexEVSApplicationService svc = LexEVSServiceHolder.instance().getLexEVSAppService();

		def = svc.getLexEVSValueSetDefinitionServices();
	}

	@Test
	public void testTransformerVSDCorrectCodingSchemeName() throws LBException, URISyntaxException {
		ValueSetDefinition vsd = def.getValueSetDefinition(new URI("http://evs.nci.nih.gov/valueset/CDISC/C61410"), null);
		SupportedCodingScheme scheme = vsd.getMappings().getSupportedCodingScheme(0);
		assertNotNull(scheme);
		assertEquals(scheme.getLocalId(), "NCI_Thesaurus");
		SupportedNamespace nmsp = vsd.getMappings().getSupportedNamespace(0);
		assertNotNull(nmsp);
		assertEquals(nmsp.getLocalId(), "ncit");
	}

}
