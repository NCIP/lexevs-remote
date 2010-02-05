package test.gov.nih.nci.cacoresdk.domain.other;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.gov.nih.nci.cacoresdk.domain.other.datatype.AllDataTypeXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.other.differentpackage.DifferentPackageWithAssociationXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.other.levelassociation.LevelAssociationXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.CharacterKeyXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.CharacterPrimitiveKeyXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.DoubleKeyXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.DoublePrimitiveKeyXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.FloatKeyXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.FloatPrimitiveKeyXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.IntegerKeyXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.IntegerPrimitiveKeyXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.LongKeyXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.LongPrimitiveKeyXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.NoIdKeyXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.StringKeyXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.StringPrimitiveKeyXMLMappingTest;

public class OtherXMLMappingSuite
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test for Other XML Mapping Package");
		suite.addTest(new TestSuite(AllDataTypeXMLMappingTest.class,AllDataTypeXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(CharacterKeyXMLMappingTest.class,CharacterKeyXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(CharacterPrimitiveKeyXMLMappingTest.class,CharacterPrimitiveKeyXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(DifferentPackageWithAssociationXMLMappingTest.class,DifferentPackageWithAssociationXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(DoubleKeyXMLMappingTest.class,DoubleKeyXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(DoublePrimitiveKeyXMLMappingTest.class,DoublePrimitiveKeyXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(FloatKeyXMLMappingTest.class,FloatKeyXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(FloatPrimitiveKeyXMLMappingTest.class,FloatPrimitiveKeyXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(IntegerKeyXMLMappingTest.class,IntegerKeyXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(IntegerPrimitiveKeyXMLMappingTest.class,IntegerPrimitiveKeyXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(LongKeyXMLMappingTest.class,LongKeyXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(LongPrimitiveKeyXMLMappingTest.class,LongPrimitiveKeyXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(NoIdKeyXMLMappingTest.class,NoIdKeyXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(StringKeyXMLMappingTest.class,StringKeyXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(StringPrimitiveKeyXMLMappingTest.class,StringPrimitiveKeyXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(LevelAssociationXMLMappingTest.class,LevelAssociationXMLMappingTest.getTestCaseName()));
		return suite;
	}
}
