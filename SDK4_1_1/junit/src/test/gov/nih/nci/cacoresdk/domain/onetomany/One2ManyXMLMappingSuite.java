package test.gov.nih.nci.cacoresdk.domain.onetomany;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.gov.nih.nci.cacoresdk.domain.onetomany.bidirectional.O2MBidirectionalXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.onetomany.bidirectional.withjoin.O2MBidirectionalWJoinXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.onetomany.unidirectional.O2MUnidirectionalXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.onetomany.unidirectional.withjoin.O2MUnidirectionalWJoinXMLMappingTest;

public class One2ManyXMLMappingSuite
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test for One to Many XML Mapping package");
		suite.addTest(new TestSuite(O2MBidirectionalXMLMappingTest.class,O2MBidirectionalXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2MBidirectionalWJoinXMLMappingTest.class,O2MBidirectionalWJoinXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2MUnidirectionalXMLMappingTest.class,O2MUnidirectionalXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2MUnidirectionalWJoinXMLMappingTest.class,O2MUnidirectionalWJoinXMLMappingTest.getTestCaseName()));
		return suite;
	}
}
