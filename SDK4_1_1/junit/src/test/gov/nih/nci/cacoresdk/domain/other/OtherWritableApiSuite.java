package test.gov.nih.nci.cacoresdk.domain.other;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.gov.nih.nci.cacoresdk.domain.other.datatype.AllDataTypeWritableApiTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.NoIdKeyWritableApiTest;

public class OtherWritableApiSuite {
	public static Test suite(){
		TestSuite suite = new TestSuite("Test for Other WritableApi package");
		suite.addTest(new TestSuite(AllDataTypeWritableApiTest.class,AllDataTypeWritableApiTest.getTestCaseName()));
		suite.addTest(new TestSuite(NoIdKeyWritableApiTest.class,NoIdKeyWritableApiTest.getTestCaseName()));
	return suite;
	}
}
