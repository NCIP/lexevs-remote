package test.gov.nih.nci.cacoresdk.domain.onetoone;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.gov.nih.nci.cacoresdk.domain.onetoone.bidirectional.O2OBidirectionalXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.onetoone.bidirectional.withjoin.O2OBidirectionalWJoinXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.onetoone.multipleassociation.O2OMultipleAssociationXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.onetoone.multipleassociation.withjoin.O2OMultipleAssociationWJoinXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.onetoone.unidirectional.O2OUnidirectionalXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.onetoone.unidirectional.withjoin.O2OUnidirectionalWJoinXMLDataTest;

public class One2OneXMLDataSuite
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test for One to One XML Data Package");
		suite.addTest(new TestSuite(O2OBidirectionalXMLDataTest.class,O2OBidirectionalXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2OBidirectionalWJoinXMLDataTest.class,O2OBidirectionalWJoinXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2OUnidirectionalXMLDataTest.class,O2OUnidirectionalXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2OUnidirectionalWJoinXMLDataTest.class,O2OUnidirectionalWJoinXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2OMultipleAssociationXMLDataTest.class,O2OMultipleAssociationXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2OMultipleAssociationWJoinXMLDataTest.class,O2OMultipleAssociationWJoinXMLDataTest.getTestCaseName()));
		return suite;
	}
}
