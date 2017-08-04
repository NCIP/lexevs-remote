package org.LexGrid.LexBIG.distributed.test.testUtility;

import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.valueset.impl.LexEVSResolvedValueSetTest;
import org.junit.Before;
import org.lexgrid.resolvedvalueset.impl.LexEVSResolvedValueSetServiceImpl;

public class DistributedResolvedValueSetTests extends LexEVSResolvedValueSetTest {

	@Before
	public void setUp(){
		super.setLexBIGService(LexEVSServiceHolder.instance().getLexEVSAppService());
		super.setService(
				new LexEVSResolvedValueSetServiceImpl(super.getLexBIGService()));
	}

}
