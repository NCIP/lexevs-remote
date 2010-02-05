package test.gov.nih.nci.cacoresdk.domain.onetomany;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.gov.nih.nci.cacoresdk.domain.onetomany.bidirectional.O2MBidirectionalTest;
import test.gov.nih.nci.cacoresdk.domain.onetomany.bidirectional.withjoin.O2MBidirectionalWJoinTest;
import test.gov.nih.nci.cacoresdk.domain.onetomany.unidirectional.O2MUnidirectionalTest;
import test.gov.nih.nci.cacoresdk.domain.onetomany.unidirectional.withjoin.O2MUnidirectionalWJoinTest;

public class One2ManySuite
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test for One to Many package");
		suite.addTest(new TestSuite(O2MBidirectionalTest.class,O2MBidirectionalTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2MBidirectionalWJoinTest.class,O2MBidirectionalWJoinTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2MUnidirectionalTest.class,O2MUnidirectionalTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2MUnidirectionalWJoinTest.class,O2MUnidirectionalWJoinTest.getTestCaseName()));
		return suite;
	}
}
