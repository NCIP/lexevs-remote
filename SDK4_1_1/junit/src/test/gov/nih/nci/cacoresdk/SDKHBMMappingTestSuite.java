package test.gov.nih.nci.cacoresdk;

import test.gov.nih.nci.cacoresdk.domain.other.OtherHBMMappingSuite;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public  class SDKHBMMappingTestSuite extends TestCase {

	public static Test suite() {
		TestSuite suite = new TestSuite("Suite for HBM Mapping Xml Files");
		suite.addTest(new TestSuite(AllHBMMappingTest.class,AllHBMMappingTest.getTestCaseName()));
		suite.addTest(OtherHBMMappingSuite.suite());
		return suite;
	}
}