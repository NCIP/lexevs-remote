package org.LexGrid.LexBIG.distributed.test.bugs;

import java.util.List;

import org.LexGrid.LexBIG.Extensions.Generic.MappingExtension;
import org.LexGrid.LexBIG.Extensions.Generic.MappingExtension.Mapping;
import org.LexGrid.LexBIG.Extensions.Generic.MappingExtension.Mapping.SearchContext;
import org.LexGrid.LexBIG.Impl.LexBIGServiceImpl;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.Utility.Iterators.ResolvedConceptReferencesIterator;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.custom.relations.TerminologyMapBean;
import org.junit.Before;
import org.junit.Test;

public class TestLEXEVS_4376 extends ServiceTestCase {

	private LexEVSApplicationService svc;
	private MappingExtension mapExt;

	@Before
	protected void setUp() throws Exception {
		svc = LexEVSServiceHolder.instance().getFreshLexEVSAppService();
		mapExt = (MappingExtension) svc.getGenericExtension("MappingExtension");
		super.setUp();
	}

	@Test
	public void test() {


		List<TerminologyMapBean> tmb =  mapExt.resolveBulkMapping(ITERATOR_MAPPING_SCHEME, ITERATOR_MAPPING_SCHEME_VERSION);
		assertEquals(tmb.stream().filter(x -> x.getTargetCode().equals("THIRST")).findAny().get().getMapRank(), "2");
		assertEquals(tmb.stream().filter(x -> x.getTargetCode().equals("EAR DIS")).findAny().get().getMapRank(), "1");
		assertEquals(tmb.stream().filter(x -> x.getTargetCode().equals("PLEURAL DIS")).findAny().get().getMapRank(), "1");
		assertEquals(tmb.stream().filter(x -> x.getTargetCode().equals("HYPERTROPHY")).findAny().get().getMapRank(), "1");
		assertEquals(tmb.stream().filter(x -> x.getTargetCode().equals("PYELONEPHRITIS")).findAny().get().getMapRank(), "1");
		assertEquals(tmb.stream().filter(x -> x.getTargetCode().equals("ANOMALY CONGEN")).findAny().get().getMapRank(), "2");
		assertEquals(tmb.stream().filter(x -> x.getTargetCode().equals("SKIN DISCOLOR")).findAny().get().getMapRank(), "");
		//Asserted in mr map but no terminology map
		assertTrue(tmb.stream().noneMatch(x -> x.getTargetCode().equals("HEM EYE")));
		assertEquals(tmb.stream().filter(x -> x.getTargetCode().equals("ENTERITIS")).findAny().get().getMapRank(), "4");
		assertEquals(tmb.stream().filter(x -> x.getTargetCode().equals("EDEMA TONGUE")).findAny().get().getMapRank(), "1");
	}

}
