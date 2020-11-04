package org.LexGrid.LexBIG.distributed.test.features;

import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.junit.Before;
import org.junit.Test;

public class TestLEXEVS_3732 extends ServiceTestCase {
	LexEVSApplicationService svc;
	
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		svc = LexEVSServiceHolder.instance().getLexEVSAppService();
		
	}

	@Test
	   public void testGetLexevsBuildVersion() throws LBException {
		   String version = svc.getLexEVSBuildVersion();
		   System.out.println("LexEVS Build Version: " + version);
		   assertNotNull(version);
		   assertTrue(!version.equals("@VERSION@"));
	   }

	   @Test
	   public void testGetLexevsBuildTimestamp() throws LBException {
		   String timestamp = svc.getLexEVSBuildTimestamp();
		   System.out.println("LexEVS Build Timestamp: " + timestamp);
		   assertNotNull(timestamp);
		   assertTrue(!timestamp.equals("@TIMESTAMP@"));
	   }
}
