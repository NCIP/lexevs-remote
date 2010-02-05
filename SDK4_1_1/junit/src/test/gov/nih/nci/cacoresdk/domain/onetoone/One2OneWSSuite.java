package test.gov.nih.nci.cacoresdk.domain.onetoone;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.gov.nih.nci.cacoresdk.domain.onetoone.bidirectional.O2OBidirectionalWSTest;
import test.gov.nih.nci.cacoresdk.domain.onetoone.bidirectional.withjoin.O2OBidirectionalWJoinWSTest;
import test.gov.nih.nci.cacoresdk.domain.onetoone.multipleassociation.O2OMultipleAssociationWSTest;
import test.gov.nih.nci.cacoresdk.domain.onetoone.multipleassociation.withjoin.O2OMultipleAssociationWJoinWSTest;
import test.gov.nih.nci.cacoresdk.domain.onetoone.unidirectional.O2OUnidirectionalWSTest;
import test.gov.nih.nci.cacoresdk.domain.onetoone.unidirectional.withjoin.O2OUnidirectionalWJoinWSTest;

public class One2OneWSSuite
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test for One to One WS Package");
		suite.addTest(new TestSuite(O2OBidirectionalWSTest.class,O2OBidirectionalWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2OBidirectionalWJoinWSTest.class,O2OBidirectionalWJoinWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2OUnidirectionalWSTest.class,O2OUnidirectionalWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2OUnidirectionalWJoinWSTest.class,O2OUnidirectionalWJoinWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2OMultipleAssociationWSTest.class,O2OMultipleAssociationWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2OMultipleAssociationWJoinWSTest.class,O2OMultipleAssociationWJoinWSTest.getTestCaseName()));
		return suite;
	}
}
