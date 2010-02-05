package test.gov.nih.nci.cacoresdk.domain.onetomany;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.gov.nih.nci.cacoresdk.domain.onetomany.bidirectional.O2MBidirectionalWritableApiTest;
import test.gov.nih.nci.cacoresdk.domain.onetomany.bidirectional.withjoin.O2MBidirectionalWJoinWritableApiTest;
import test.gov.nih.nci.cacoresdk.domain.onetomany.unidirectional.O2MUnidirectionalWritableApiTest;
import test.gov.nih.nci.cacoresdk.domain.onetomany.unidirectional.withjoin.O2MUnidirectionalWJoinWritableApiTest;

public class One2ManyWritableApiSuite
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test for One to Many package");
		suite.addTest(new TestSuite(O2MBidirectionalWritableApiTest.class,O2MBidirectionalWritableApiTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2MBidirectionalWJoinWritableApiTest.class,O2MBidirectionalWJoinWritableApiTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2MUnidirectionalWritableApiTest.class,O2MUnidirectionalWritableApiTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2MUnidirectionalWJoinWritableApiTest.class,O2MUnidirectionalWJoinWritableApiTest.getTestCaseName()));
		return suite;
	}
}
