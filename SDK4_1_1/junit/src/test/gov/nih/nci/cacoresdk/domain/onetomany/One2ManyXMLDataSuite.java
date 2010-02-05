package test.gov.nih.nci.cacoresdk.domain.onetomany;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.gov.nih.nci.cacoresdk.domain.onetomany.bidirectional.O2MBidirectionalXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.onetomany.bidirectional.withjoin.O2MBidirectionalWJoinXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.onetomany.unidirectional.O2MUnidirectionalXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.onetomany.unidirectional.withjoin.O2MUnidirectionalWJoinXMLDataTest;

public class One2ManyXMLDataSuite
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test for One to Many XML Data package");
		suite.addTest(new TestSuite(O2MBidirectionalXMLDataTest.class,O2MBidirectionalXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2MBidirectionalWJoinXMLDataTest.class,O2MBidirectionalWJoinXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2MUnidirectionalXMLDataTest.class,O2MUnidirectionalXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2MUnidirectionalWJoinXMLDataTest.class,O2MUnidirectionalWJoinXMLDataTest.getTestCaseName()));
		return suite;
	}
}
