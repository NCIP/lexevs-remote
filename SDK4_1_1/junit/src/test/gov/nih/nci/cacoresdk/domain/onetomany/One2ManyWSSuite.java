package test.gov.nih.nci.cacoresdk.domain.onetomany;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.gov.nih.nci.cacoresdk.domain.onetomany.bidirectional.O2MBidirectionalWSTest;
import test.gov.nih.nci.cacoresdk.domain.onetomany.bidirectional.withjoin.O2MBidirectionalWJoinWSTest;
import test.gov.nih.nci.cacoresdk.domain.onetomany.unidirectional.O2MUnidirectionalWSTest;
import test.gov.nih.nci.cacoresdk.domain.onetomany.unidirectional.withjoin.O2MUnidirectionalWJoinWSTest;

public class One2ManyWSSuite
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test for One to Many package");
		suite.addTest(new TestSuite(O2MBidirectionalWSTest.class,O2MBidirectionalWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2MBidirectionalWJoinWSTest.class,O2MBidirectionalWJoinWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2MUnidirectionalWSTest.class,O2MUnidirectionalWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2MUnidirectionalWJoinWSTest.class,O2MUnidirectionalWJoinWSTest.getTestCaseName()));
		return suite;
	}
}
