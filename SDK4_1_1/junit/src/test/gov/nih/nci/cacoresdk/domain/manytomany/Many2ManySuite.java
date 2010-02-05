package test.gov.nih.nci.cacoresdk.domain.manytomany;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.gov.nih.nci.cacoresdk.domain.manytomany.bidirectional.M2MBidirectionalTest;
import test.gov.nih.nci.cacoresdk.domain.manytomany.unidirectional.M2MUnidirectionalTest;

public class Many2ManySuite
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test for Many to Many package");
		suite.addTest(new TestSuite(M2MBidirectionalTest.class,M2MBidirectionalTest.getTestCaseName()));
		suite.addTest(new TestSuite(M2MUnidirectionalTest.class,M2MUnidirectionalTest.getTestCaseName()));
		return suite;
	}
}
