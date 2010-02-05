package test.gov.nih.nci.cacoresdk.domain.manytomany;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.gov.nih.nci.cacoresdk.domain.manytomany.bidirectional.M2MBidirectionalXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.manytomany.unidirectional.M2MUnidirectionalXMLDataTest;

public class Many2ManyXMLDataSuite
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test for Many to Many XML Mapping Package");
		suite.addTest(new TestSuite(M2MBidirectionalXMLDataTest.class,M2MBidirectionalXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(M2MUnidirectionalXMLDataTest.class,M2MUnidirectionalXMLDataTest.getTestCaseName()));
		return suite;
	}
}
