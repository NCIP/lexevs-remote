package test.gov.nih.nci.cacoresdk.domain.interfaze;

import junit.framework.Test;
import junit.framework.TestSuite;

public class InterfaceWSSuite
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test for Interface WS package");
		suite.addTest(new TestSuite(InterfaceWSTest.class,InterfaceWSTest.getTestCaseName()));
		return suite;
	}
}
