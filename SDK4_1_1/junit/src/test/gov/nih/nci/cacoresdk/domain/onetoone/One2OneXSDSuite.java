package test.gov.nih.nci.cacoresdk.domain.onetoone;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.gov.nih.nci.cacoresdk.domain.onetoone.bidirectional.O2OBidirectionalXSDTest;
import test.gov.nih.nci.cacoresdk.domain.onetoone.bidirectional.withjoin.O2OBidirectionalWJoinXSDTest;
import test.gov.nih.nci.cacoresdk.domain.onetoone.multipleassociation.O2OMultipleAssociationXSDTest;
import test.gov.nih.nci.cacoresdk.domain.onetoone.multipleassociation.withjoin.O2OMultipleAssociationWJoinXSDTest;
import test.gov.nih.nci.cacoresdk.domain.onetoone.unidirectional.O2OUnidirectionalXSDTest;
import test.gov.nih.nci.cacoresdk.domain.onetoone.unidirectional.withjoin.O2OUnidirectionalWJoinXSDTest;

public class One2OneXSDSuite
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test for One to One XSD Package");
		suite.addTest(new TestSuite(O2OBidirectionalXSDTest.class,O2OBidirectionalXSDTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2OBidirectionalWJoinXSDTest.class,O2OBidirectionalWJoinXSDTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2OUnidirectionalXSDTest.class,O2OUnidirectionalXSDTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2OUnidirectionalWJoinXSDTest.class,O2OUnidirectionalWJoinXSDTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2OMultipleAssociationXSDTest.class,O2OMultipleAssociationXSDTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2OMultipleAssociationWJoinXSDTest.class,O2OMultipleAssociationWJoinXSDTest.getTestCaseName()));
		return suite;
	}
}
