package test.gov.nih.nci.cacoresdk.domain.other;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.gov.nih.nci.cacoresdk.domain.other.datatype.AllDataTypeWSTest;
import test.gov.nih.nci.cacoresdk.domain.other.differentpackage.DifferentPackageWithAssociationWSTest;
import test.gov.nih.nci.cacoresdk.domain.other.levelassociation.LevelAssociationWSTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.CharacterKeyWSTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.CharacterPrimitiveKeyWSTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.DoubleKeyWSTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.DoublePrimitiveKeyWSTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.FloatKeyWSTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.FloatPrimitiveKeyWSTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.IntegerKeyWSTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.IntegerPrimitiveKeyWSTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.LongKeyWSTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.LongPrimitiveKeyWSTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.NoIdKeyWSTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.StringKeyWSTest;
import test.gov.nih.nci.cacoresdk.domain.other.primarykey.StringPrimitiveKeyWSTest;

public class OtherWSSuite
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test for Other WS Package");
		suite.addTest(new TestSuite(AllDataTypeWSTest.class,AllDataTypeWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(CharacterKeyWSTest.class,CharacterKeyWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(CharacterPrimitiveKeyWSTest.class,CharacterPrimitiveKeyWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(DifferentPackageWithAssociationWSTest.class,DifferentPackageWithAssociationWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(DoubleKeyWSTest.class,DoubleKeyWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(DoublePrimitiveKeyWSTest.class,DoublePrimitiveKeyWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(FloatKeyWSTest.class,FloatKeyWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(FloatPrimitiveKeyWSTest.class,FloatPrimitiveKeyWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(IntegerKeyWSTest.class,IntegerKeyWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(IntegerPrimitiveKeyWSTest.class,IntegerPrimitiveKeyWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(LongKeyWSTest.class,LongKeyWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(LongPrimitiveKeyWSTest.class,LongPrimitiveKeyWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(NoIdKeyWSTest.class,NoIdKeyWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(StringKeyWSTest.class,StringKeyWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(StringPrimitiveKeyWSTest.class,StringPrimitiveKeyWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(LevelAssociationWSTest.class,LevelAssociationWSTest.getTestCaseName()));
		return suite;
	}
}
