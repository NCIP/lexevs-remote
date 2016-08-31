package org.LexGrid.LexBIG.distributed.test.testUtility;

import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.valueset.impl.LexEVSValueSetDefServicesImplTest;
import org.junit.Before;

public class DistributedValueSetDefinitionTests extends LexEVSValueSetDefServicesImplTest{

	@Before
	public void setUp(){
		super.setValueSetDefinitionService(LexEVSServiceHolder.instance()
				.getLexEVSAppService().getLexEVSValueSetDefinitionServices());
		
	}
}
