package test.gov.nih.nci.cacoresdk.domain.manytoone;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.gov.nih.nci.cacoresdk.domain.manytoone.unidirectional.M2OUnidirectionalXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.manytoone.unidirectional.withjoin.M2OUnidirectionalWJoinXMLMappingTest;

public class Many2OneXMLMappingSuite
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test for Many to One XML Mapping Package");
		suite.addTest(new TestSuite(M2OUnidirectionalXMLMappingTest.class,M2OUnidirectionalXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(M2OUnidirectionalWJoinXMLMappingTest.class,M2OUnidirectionalWJoinXMLMappingTest.getTestCaseName()));
		return suite;
	}
}
