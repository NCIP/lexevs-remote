package test.gov.nih.nci.cacoresdk;
import junit.framework.Test;
import junit.framework.TestSuite;
import test.gov.nih.nci.cacoresdk.domain.inheritance.InheritanceXMLDataSuite;
import test.gov.nih.nci.cacoresdk.domain.interfaze.InterfaceXMLDataSuite;
import test.gov.nih.nci.cacoresdk.domain.manytomany.Many2ManyXMLDataSuite;
import test.gov.nih.nci.cacoresdk.domain.manytoone.Many2OneXMLDataSuite;
import test.gov.nih.nci.cacoresdk.domain.onetomany.One2ManyXMLDataSuite;
import test.gov.nih.nci.cacoresdk.domain.onetoone.One2OneXMLDataSuite;
import test.gov.nih.nci.cacoresdk.domain.other.OtherXMLDataSuite;

/**
 * @author Dan Dumitru
 *
 */
public class SDKXMLDataTestSuite {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for XML Data caCORE SDK");
		suite.addTest(OtherXMLDataSuite.suite());
		suite.addTest(InheritanceXMLDataSuite.suite());
		suite.addTest(InterfaceXMLDataSuite.suite());		
		suite.addTest(Many2ManyXMLDataSuite.suite());
		suite.addTest(One2ManyXMLDataSuite.suite());
		suite.addTest(Many2OneXMLDataSuite.suite());
		suite.addTest(One2OneXMLDataSuite.suite());
		return suite;
	}
}
