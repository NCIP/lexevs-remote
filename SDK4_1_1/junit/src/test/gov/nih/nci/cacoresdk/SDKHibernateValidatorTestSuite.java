package test.gov.nih.nci.cacoresdk;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import test.gov.nih.nci.cacoresdk.domain.other.validationtype.AllValidationTypeHibernateValidatorTest;

public  class SDKHibernateValidatorTestSuite extends TestCase {

	public static Test suite() {
		TestSuite suite = new TestSuite("Suite for Hibernate Validator Test Files");
		suite.addTest(new TestSuite(AllValidationTypeHibernateValidatorTest.class,AllValidationTypeHibernateValidatorTest.getTestCaseName()));
		return suite;
	}
}