package test.gov.nih.nci.cacoresdk.domain.other;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.gov.nih.nci.cacoresdk.domain.other.datatype.AllDataTypeXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.other.differentpackage.DifferentPackageWithAssociationXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.other.levelassociation.LevelAssociationXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.CharacterKeyXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.CharacterPrimitiveKeyXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.DoubleKeyXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.DoublePrimitiveKeyXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.FloatKeyXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.FloatPrimitiveKeyXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.IntegerKeyXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.IntegerPrimitiveKeyXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.LongKeyXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.LongPrimitiveKeyXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.NoIdKeyXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.StringKeyXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.StringPrimitiveKeyXMLDataTest;

public class OtherXMLDataSuite
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test for Other XML Data Package");
		suite.addTest(new TestSuite(AllDataTypeXMLDataTest.class,AllDataTypeXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(CharacterKeyXMLDataTest.class,CharacterKeyXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(CharacterPrimitiveKeyXMLDataTest.class,CharacterPrimitiveKeyXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(DoubleKeyXMLDataTest.class,DoubleKeyXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(DoublePrimitiveKeyXMLDataTest.class,DoublePrimitiveKeyXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(DifferentPackageWithAssociationXMLDataTest.class,DifferentPackageWithAssociationXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(FloatKeyXMLDataTest.class,FloatKeyXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(FloatPrimitiveKeyXMLDataTest.class,FloatPrimitiveKeyXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(IntegerKeyXMLDataTest.class,IntegerKeyXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(IntegerPrimitiveKeyXMLDataTest.class,IntegerPrimitiveKeyXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(LongKeyXMLDataTest.class,LongKeyXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(LongPrimitiveKeyXMLDataTest.class,LongPrimitiveKeyXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(NoIdKeyXMLDataTest.class,NoIdKeyXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(StringKeyXMLDataTest.class,StringKeyXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(StringPrimitiveKeyXMLDataTest.class,StringPrimitiveKeyXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(LevelAssociationXMLDataTest.class,LevelAssociationXMLDataTest.getTestCaseName()));
		return suite;
	}
}
