package test.gov.nih.nci.cacoresdk.domain.manytoone;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.gov.nih.nci.cacoresdk.domain.manytoone.unidirectional.M2OUnidirectionalXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.manytoone.unidirectional.withjoin.M2OUnidirectionalWJoinXMLDataTest;

public class Many2OneXMLDataSuite
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test for Many to One XML Data Package");
		suite.addTest(new TestSuite(M2OUnidirectionalXMLDataTest.class,M2OUnidirectionalXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(M2OUnidirectionalWJoinXMLDataTest.class,M2OUnidirectionalWJoinXMLDataTest.getTestCaseName()));
		return suite;
	}
}
