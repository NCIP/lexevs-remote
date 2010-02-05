package test.gov.nih.nci.cacoresdk.domain.manytomany;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.gov.nih.nci.cacoresdk.domain.manytomany.bidirectional.M2MBidirectionalWSTest;
import test.gov.nih.nci.cacoresdk.domain.manytomany.unidirectional.M2MUnidirectionalWSTest;

public class Many2ManyWSSuite
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test for Many to Many WS Package");
		suite.addTest(new TestSuite(M2MBidirectionalWSTest.class,M2MBidirectionalWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(M2MUnidirectionalWSTest.class,M2MUnidirectionalWSTest.getTestCaseName()));
		return suite;
	}
}
