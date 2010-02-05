package test.gov.nih.nci.cacoresdk.domain.onetoone;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.gov.nih.nci.cacoresdk.domain.onetoone.bidirectional.O2OBidirectionalXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.onetoone.bidirectional.withjoin.O2OBidirectionalWJoinXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.onetoone.multipleassociation.O2OMultipleAssociationXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.onetoone.multipleassociation.withjoin.O2OMultipleAssociationWJoinXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.onetoone.unidirectional.O2OUnidirectionalXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.onetoone.unidirectional.withjoin.O2OUnidirectionalWJoinXMLMappingTest;

public class One2OneXMLMappingSuite
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test for One to One XML Mapping Package");
		suite.addTest(new TestSuite(O2OBidirectionalXMLMappingTest.class,O2OBidirectionalXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2OBidirectionalWJoinXMLMappingTest.class,O2OBidirectionalWJoinXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2OUnidirectionalXMLMappingTest.class,O2OUnidirectionalXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2OUnidirectionalWJoinXMLMappingTest.class,O2OUnidirectionalWJoinXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2OMultipleAssociationXMLMappingTest.class,O2OMultipleAssociationXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2OMultipleAssociationWJoinXMLMappingTest.class,O2OMultipleAssociationWJoinXMLMappingTest.getTestCaseName()));
		return suite;
	}
}
