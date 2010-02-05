package test.gov.nih.nci.cacoresdk.domain.other;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.gov.nih.nci.cacoresdk.domain.other.datatype.AllDataTypeXSDTest;
import test.gov.nih.nci.cacoresdk.domain.other.differentpackage.DifferentPackageWithAssociationXSDTest;
import test.gov.nih.nci.cacoresdk.domain.other.levelassociation.LevelAssociationXSDTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.CharacterKeyXSDTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.CharacterPrimitiveKeyXSDTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.DoubleKeyXSDTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.DoublePrimitiveKeyXSDTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.FloatKeyXSDTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.FloatPrimitiveKeyXSDTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.IntegerKeyXSDTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.IntegerPrimitiveKeyXSDTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.LongKeyXSDTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.LongPrimitiveKeyXSDTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.NoIdKeyXSDTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.StringKeyXSDTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.StringPrimitiveKeyXSDTest;

public class OtherXSDSuite
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test for Other XSD Package");
		suite.addTest(new TestSuite(AllDataTypeXSDTest.class,AllDataTypeXSDTest.getTestCaseName()));
		suite.addTest(new TestSuite(CharacterKeyXSDTest.class,CharacterKeyXSDTest.getTestCaseName()));
		suite.addTest(new TestSuite(CharacterPrimitiveKeyXSDTest.class,CharacterPrimitiveKeyXSDTest.getTestCaseName()));
		suite.addTest(new TestSuite(DifferentPackageWithAssociationXSDTest.class,DifferentPackageWithAssociationXSDTest.getTestCaseName()));		
		suite.addTest(new TestSuite(DoubleKeyXSDTest.class,DoubleKeyXSDTest.getTestCaseName()));
		suite.addTest(new TestSuite(DoublePrimitiveKeyXSDTest.class,DoublePrimitiveKeyXSDTest.getTestCaseName()));
		suite.addTest(new TestSuite(FloatKeyXSDTest.class,FloatKeyXSDTest.getTestCaseName()));
		suite.addTest(new TestSuite(FloatPrimitiveKeyXSDTest.class,FloatPrimitiveKeyXSDTest.getTestCaseName()));
		suite.addTest(new TestSuite(IntegerKeyXSDTest.class,IntegerKeyXSDTest.getTestCaseName()));
		suite.addTest(new TestSuite(IntegerPrimitiveKeyXSDTest.class,IntegerPrimitiveKeyXSDTest.getTestCaseName()));
		suite.addTest(new TestSuite(LongKeyXSDTest.class,LongKeyXSDTest.getTestCaseName()));
		suite.addTest(new TestSuite(LongPrimitiveKeyXSDTest.class,LongPrimitiveKeyXSDTest.getTestCaseName()));
		suite.addTest(new TestSuite(NoIdKeyXSDTest.class,NoIdKeyXSDTest.getTestCaseName()));
		suite.addTest(new TestSuite(StringKeyXSDTest.class,StringKeyXSDTest.getTestCaseName()));
		suite.addTest(new TestSuite(StringPrimitiveKeyXSDTest.class,StringPrimitiveKeyXSDTest.getTestCaseName()));
		suite.addTest(new TestSuite(LevelAssociationXSDTest.class,LevelAssociationXSDTest.getTestCaseName()));
		return suite;
	}
}
