package test.gov.nih.nci.cacoresdk.domain.manytoone;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.gov.nih.nci.cacoresdk.domain.manytoone.unidirectional.M2OUnidirectionalXSDTest;
import test.gov.nih.nci.cacoresdk.domain.manytoone.unidirectional.withjoin.M2OUnidirectionalWJoinXSDTest;

public class Many2OneXSDSuite
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test for Many to One XSD Package");
		suite.addTest(new TestSuite(M2OUnidirectionalXSDTest.class,M2OUnidirectionalXSDTest.getTestCaseName()));
		suite.addTest(new TestSuite(M2OUnidirectionalWJoinXSDTest.class,M2OUnidirectionalWJoinXSDTest.getTestCaseName()));
		return suite;
	}
}
