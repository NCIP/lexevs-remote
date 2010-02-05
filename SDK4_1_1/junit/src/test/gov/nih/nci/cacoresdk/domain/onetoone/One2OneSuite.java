package test.gov.nih.nci.cacoresdk.domain.onetoone;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.gov.nih.nci.cacoresdk.domain.onetoone.bidirectional.O2OBidirectionalTest;
import test.gov.nih.nci.cacoresdk.domain.onetoone.bidirectional.withjoin.O2OBidirectionalWJoinTest;
import test.gov.nih.nci.cacoresdk.domain.onetoone.multipleassociation.O2OMultipleAssociationTest;
import test.gov.nih.nci.cacoresdk.domain.onetoone.multipleassociation.withjoin.O2OMultipleAssociationWJoinTest;
import test.gov.nih.nci.cacoresdk.domain.onetoone.unidirectional.O2OUnidirectionalTest;
import test.gov.nih.nci.cacoresdk.domain.onetoone.unidirectional.withjoin.O2OUnidirectionalWJoinTest;

public class One2OneSuite
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test for One to One package");
		suite.addTest(new TestSuite(O2OBidirectionalTest.class,O2OBidirectionalTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2OBidirectionalWJoinTest.class,O2OBidirectionalWJoinTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2OUnidirectionalTest.class,O2OUnidirectionalTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2OUnidirectionalWJoinTest.class,O2OUnidirectionalWJoinTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2OMultipleAssociationTest.class,O2OMultipleAssociationTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2OMultipleAssociationWJoinTest.class,O2OMultipleAssociationWJoinTest.getTestCaseName()));
		return suite;
	}
}
