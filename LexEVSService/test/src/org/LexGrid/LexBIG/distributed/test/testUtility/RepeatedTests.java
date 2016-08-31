package org.LexGrid.LexBIG.distributed.test.testUtility;

import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class RepeatedTests extends TestCase {

	/**
	 * Suite.
	 * 
	 * @return the test
	 * 
	 * @throws Exception the exception
	 */
	public static TestSuite suite() throws Exception
	{
		TestSuite mainSuite = new TestSuite(RepeatedTests.class);

		for(int index = 0;index < 10000; index++){
		TestSuite allTests =  new TestSuite("All Tests");
		allTests.addTest(AllDistributedLexEVSTests.suite());
		mainSuite.addTest(allTests);
		}
		
		return  mainSuite;
	}

}
