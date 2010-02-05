package test.gov.nih.nci.cacoresdk.domain.other;

import test.gov.nih.nci.cacoresdk.domain.other.primarykey.IntegerPrimitivePkHBMMappingTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.NoIdKeyPkHBMMappingTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class OtherHBMMappingSuite {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for Other HBM Mapping Package");
		suite.addTest(new TestSuite(NoIdKeyPkHBMMappingTest.class,NoIdKeyPkHBMMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(IntegerPrimitivePkHBMMappingTest.class,IntegerPrimitivePkHBMMappingTest.getTestCaseName()));
		return suite;
	}
}
