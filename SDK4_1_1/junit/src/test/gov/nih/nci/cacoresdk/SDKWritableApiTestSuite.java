package test.gov.nih.nci.cacoresdk;

import junit.framework.Test;
import junit.framework.TestSuite;

import test.gov.nih.nci.cacoresdk.domain.inheritance.InheritanceWritableApiSuite;
import test.gov.nih.nci.cacoresdk.domain.manytomany.Many2ManyWritableApiSuite;
import test.gov.nih.nci.cacoresdk.domain.manytoone.Many2OneWritableApiSuite;
import test.gov.nih.nci.cacoresdk.domain.onetomany.One2ManyWritableApiSuite;
import test.gov.nih.nci.cacoresdk.domain.onetoone.One2OneWritableApiSuite;
import test.gov.nih.nci.cacoresdk.domain.other.OtherWritableApiSuite;

public class SDKWritableApiTestSuite {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for Writable Api caCORE SDK");
		suite.addTest(InheritanceWritableApiSuite.suite());
		suite.addTest(Many2ManyWritableApiSuite.suite());
		suite.addTest(Many2OneWritableApiSuite.suite());
		suite.addTest(One2ManyWritableApiSuite.suite());
		suite.addTest(One2OneWritableApiSuite.suite());
		suite.addTest(OtherWritableApiSuite.suite());
		return suite;
	}
}