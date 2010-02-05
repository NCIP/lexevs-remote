package test.gov.nih.nci.cacoresdk;
import test.gov.nih.nci.cacoresdk.domain.inheritance.InheritanceSuite;
import test.gov.nih.nci.cacoresdk.domain.interfaze.InterfaceSuite;
import test.gov.nih.nci.cacoresdk.domain.manytomany.Many2ManySuite;
import test.gov.nih.nci.cacoresdk.domain.manytoone.Many2OneSuite;
import test.gov.nih.nci.cacoresdk.domain.onetomany.One2ManySuite;
import test.gov.nih.nci.cacoresdk.domain.onetoone.One2OneSuite;
import test.gov.nih.nci.cacoresdk.domain.other.OtherSuite;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Satish Patel
 *
 */
public class SDKTestSuite {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for caCORE SDK");
		suite.addTest(InheritanceSuite.suite());
		suite.addTest(InterfaceSuite.suite());
		suite.addTest(Many2ManySuite.suite());
		suite.addTest(One2ManySuite.suite());
		suite.addTest(Many2OneSuite.suite());
		suite.addTest(One2OneSuite.suite());
		suite.addTest(OtherSuite.suite());
		return suite;
	}
}
