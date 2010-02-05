package test.gov.nih.nci.cacoresdk.domain.onetoone;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.gov.nih.nci.cacoresdk.domain.onetoone.bidirectional.O2OBidirectionalWritableApiTest;
import test.gov.nih.nci.cacoresdk.domain.onetoone.bidirectional.withjoin.O2OBidirectionalWithJoinWritableApiTest;
import test.gov.nih.nci.cacoresdk.domain.onetoone.multipleassociation.O2OMultipleAssociationWritableApiTest;
import test.gov.nih.nci.cacoresdk.domain.onetoone.multipleassociation.withjoin.O2OMultipleAssociationWithJoinWritableApiTest;
import test.gov.nih.nci.cacoresdk.domain.onetoone.unidirectional.O2OUnidirectionalWritableApiTest;
import test.gov.nih.nci.cacoresdk.domain.onetoone.unidirectional.withjoin.O2OUnidirectionalWithJoinWritableApiTest;

public class One2OneWritableApiSuite
{
	public static Test suite(){
		TestSuite suite = new TestSuite("Test for One to One package");
		suite.addTest(new TestSuite(O2OBidirectionalWritableApiTest.class,O2OBidirectionalWritableApiTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2OBidirectionalWithJoinWritableApiTest.class,O2OBidirectionalWithJoinWritableApiTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2OUnidirectionalWritableApiTest.class,O2OUnidirectionalWritableApiTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2OUnidirectionalWithJoinWritableApiTest.class,O2OUnidirectionalWithJoinWritableApiTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2OMultipleAssociationWritableApiTest.class,O2OMultipleAssociationWritableApiTest.getTestCaseName()));
		suite.addTest(new TestSuite(O2OMultipleAssociationWithJoinWritableApiTest.class,O2OMultipleAssociationWithJoinWritableApiTest.getTestCaseName()));
		return suite;
	}
}
