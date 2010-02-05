package org.LexGrid.LexBIG.caCore.security.test;

import org.LexGrid.LexBIG.caCore.security.test.SecurityTokenTest;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() throws Exception{
		TestSuite suite = new TestSuite("Test for Token Security");
		//$JUnit-BEGIN$

		suite.addTestSuite(SecurityTokenTest.class);
		
		//$JUnit-END$
		return suite;
	}

}
