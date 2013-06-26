package org.LexGrid.LexBIG.distributed.test.testUtility;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.LexGrid.LexBIG.distributed.test.function.query.TestResolvedValueSets;

public class ResolvedValueSetTests {
	
	public static Test suite() throws Exception
	{
		TestSuite mainSuite = new TestSuite("LexBIG validation tests");
		mainSuite.addTestSuite(TestResolvedValueSets.class);
		return mainSuite;
	}

}
