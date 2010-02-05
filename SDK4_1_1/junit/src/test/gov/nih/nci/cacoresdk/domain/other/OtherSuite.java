package test.gov.nih.nci.cacoresdk.domain.other;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.gov.nih.nci.cacoresdk.domain.other.datatype.AllDataTypeTest;
import test.gov.nih.nci.cacoresdk.domain.other.differentpackage.DifferentPackageWithAssociationTest;
import test.gov.nih.nci.cacoresdk.domain.other.levelassociation.LevelAssociationTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.CharacterKeyTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.CharacterPrimitiveKeyTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.DoubleKeyTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.DoublePrimitiveKeyTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.FloatKeyTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.FloatPrimitiveKeyTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.IntegerKeyTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.IntegerPrimitiveKeyTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.LongKeyTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.LongPrimitiveKeyTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.NoIdKeyTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.StringKeyTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.StringPrimitiveKeyTest;

public class OtherSuite
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test for Other package");
		suite.addTest(new TestSuite(AllDataTypeTest.class,AllDataTypeTest.getTestCaseName()));
		suite.addTest(new TestSuite(CharacterKeyTest.class,CharacterKeyTest.getTestCaseName()));
		suite.addTest(new TestSuite(CharacterPrimitiveKeyTest.class,CharacterPrimitiveKeyTest.getTestCaseName()));
		suite.addTest(new TestSuite(DifferentPackageWithAssociationTest.class,DifferentPackageWithAssociationTest.getTestCaseName()));
		suite.addTest(new TestSuite(DoubleKeyTest.class,DoubleKeyTest.getTestCaseName()));
		suite.addTest(new TestSuite(DoublePrimitiveKeyTest.class,DoublePrimitiveKeyTest.getTestCaseName()));
		suite.addTest(new TestSuite(FloatKeyTest.class,FloatKeyTest.getTestCaseName()));
		suite.addTest(new TestSuite(FloatPrimitiveKeyTest.class,FloatPrimitiveKeyTest.getTestCaseName()));
		suite.addTest(new TestSuite(IntegerKeyTest.class,IntegerKeyTest.getTestCaseName()));
		suite.addTest(new TestSuite(IntegerPrimitiveKeyTest.class,IntegerPrimitiveKeyTest.getTestCaseName()));
		suite.addTest(new TestSuite(LongKeyTest.class,LongKeyTest.getTestCaseName()));
		suite.addTest(new TestSuite(LongPrimitiveKeyTest.class,LongPrimitiveKeyTest.getTestCaseName()));
		suite.addTest(new TestSuite(NoIdKeyTest.class,NoIdKeyTest.getTestCaseName()));
		suite.addTest(new TestSuite(StringKeyTest.class,StringKeyTest.getTestCaseName()));
		suite.addTest(new TestSuite(StringPrimitiveKeyTest.class,StringPrimitiveKeyTest.getTestCaseName()));
		suite.addTest(new TestSuite(LevelAssociationTest.class,LevelAssociationTest.getTestCaseName()));
		return suite;
	}
}
