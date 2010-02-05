package test.gov.nih.nci.cacoresdk;
import junit.framework.Test;
import junit.framework.TestSuite;
import test.gov.nih.nci.cacoresdk.domain.inheritance.InheritanceWSSuite;
import test.gov.nih.nci.cacoresdk.domain.interfaze.InterfaceWSSuite;
import test.gov.nih.nci.cacoresdk.domain.manytomany.Many2ManyWSSuite;
import test.gov.nih.nci.cacoresdk.domain.manytoone.Many2OneWSSuite;
import test.gov.nih.nci.cacoresdk.domain.onetomany.One2ManyWSSuite;
import test.gov.nih.nci.cacoresdk.domain.onetoone.One2OneWSSuite;
import test.gov.nih.nci.cacoresdk.domain.other.OtherWSSuite;

/**
 * @author Dan Dumitru
 *
 */
public class SDKWSTestSuite {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for WS caCORE SDK");
		suite.addTest(OtherWSSuite.suite());
		suite.addTest(InheritanceWSSuite.suite());
		suite.addTest(InterfaceWSSuite.suite());
		suite.addTest(Many2ManyWSSuite.suite());
		suite.addTest(One2ManyWSSuite.suite());
		suite.addTest(Many2OneWSSuite.suite());
		suite.addTest(One2OneWSSuite.suite());
		return suite;
	}
}
