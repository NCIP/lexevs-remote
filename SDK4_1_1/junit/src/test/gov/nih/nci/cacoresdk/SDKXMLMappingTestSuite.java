package test.gov.nih.nci.cacoresdk;
import junit.framework.Test;
import junit.framework.TestSuite;
import test.gov.nih.nci.cacoresdk.domain.inheritance.InheritanceXMLMappingSuite;
import test.gov.nih.nci.cacoresdk.domain.interfaze.InterfaceXMLMappingSuite;
import test.gov.nih.nci.cacoresdk.domain.manytomany.Many2ManyXMLMappingSuite;
import test.gov.nih.nci.cacoresdk.domain.manytoone.Many2OneXMLMappingSuite;
import test.gov.nih.nci.cacoresdk.domain.onetomany.One2ManyXMLMappingSuite;
import test.gov.nih.nci.cacoresdk.domain.onetoone.One2OneXMLMappingSuite;
import test.gov.nih.nci.cacoresdk.domain.other.OtherXMLMappingSuite;

/**
 * @author Dan Dumitru
 *
 */
public class SDKXMLMappingTestSuite {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for XML Mapping caCORE SDK");
		suite.addTest(OtherXMLMappingSuite.suite());
		suite.addTest(InheritanceXMLMappingSuite.suite());
		suite.addTest(InterfaceXMLMappingSuite.suite());		
		suite.addTest(Many2ManyXMLMappingSuite.suite());
		suite.addTest(One2ManyXMLMappingSuite.suite());
		suite.addTest(Many2OneXMLMappingSuite.suite());
		suite.addTest(One2OneXMLMappingSuite.suite());
		return suite;
	}
}
