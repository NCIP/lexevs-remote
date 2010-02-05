package test.gov.nih.nci.cacoresdk.domain.manytoone;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.gov.nih.nci.cacoresdk.domain.manytoone.unidirectional.M2OUnidirectionalTest;
import test.gov.nih.nci.cacoresdk.domain.manytoone.unidirectional.M2OUnidirectionalWritableApiTest;
import test.gov.nih.nci.cacoresdk.domain.manytoone.unidirectional.withjoin.M2OUnidirectionalWJoinTest;
import test.gov.nih.nci.cacoresdk.domain.manytoone.unidirectional.withjoin.M2OUnidirectionalWithJoinWritableApiTest;

public class Many2OneWritableApiSuite
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test for Many to One Writable package");
		suite.addTest(new TestSuite(M2OUnidirectionalWritableApiTest.class,M2OUnidirectionalWritableApiTest.getTestCaseName()));
		suite.addTest(new TestSuite(M2OUnidirectionalWithJoinWritableApiTest.class,M2OUnidirectionalWithJoinWritableApiTest.getTestCaseName()));
		return suite;
	}
}
