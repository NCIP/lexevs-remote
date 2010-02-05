package test.gov.nih.nci.cacoresdk.domain.inheritance;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.gov.nih.nci.cacoresdk.domain.inheritance.abstrakt.AbstractParentWithAssociationWritableApiTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.ChildWithAssociationWritableApiTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.sametable.ChildWithAssociationSametableWritableApiTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.implicit.ImplicitParentWithAssociationWritableApiTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.multiplechild.MultipleChildWritableApiTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.multiplechild.sametable.MultipleChildSametableWritableApiTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.onechild.OneChildWritableApiTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.onechild.sametable.OneChildSametableWritableApiTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.parentwithassociation.ParentWithAssociationWritableApiTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.parentwithassociation.sametable.ParentWithAssociationSametableWritableApiTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.twolevelinheritance.TwoLevelInheritanceWritableApiTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.twolevelinheritance.sametable.TwoLevelInheritanceSametableWritableApiTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.twolevelinheritance.sametablerootlevel.TwoLevelInheritanceSametablerootlevelWritableApiTest;

public class InheritanceWritableApiSuite
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test for Inheritance Writable Api package");
		suite.addTest(new TestSuite(AbstractParentWithAssociationWritableApiTest.class,AbstractParentWithAssociationWritableApiTest.getTestCaseName()));
		suite.addTest(new TestSuite(ImplicitParentWithAssociationWritableApiTest.class,ImplicitParentWithAssociationWritableApiTest.getTestCaseName()));
		suite.addTest(new TestSuite(OneChildWritableApiTest.class,OneChildWritableApiTest.getTestCaseName()));
		suite.addTest(new TestSuite(OneChildSametableWritableApiTest.class,OneChildSametableWritableApiTest.getTestCaseName()));
		suite.addTest(new TestSuite(TwoLevelInheritanceWritableApiTest.class,TwoLevelInheritanceWritableApiTest.getTestCaseName()));
		suite.addTest(new TestSuite(TwoLevelInheritanceSametableWritableApiTest.class,TwoLevelInheritanceSametableWritableApiTest.getTestCaseName()));
		suite.addTest(new TestSuite(TwoLevelInheritanceSametablerootlevelWritableApiTest.class,TwoLevelInheritanceSametablerootlevelWritableApiTest.getTestCaseName()));
		suite.addTest(new TestSuite(MultipleChildWritableApiTest.class,MultipleChildWritableApiTest.getTestCaseName()));
		suite.addTest(new TestSuite(MultipleChildSametableWritableApiTest.class,MultipleChildSametableWritableApiTest.getTestCaseName()));
		suite.addTest(new TestSuite(ChildWithAssociationWritableApiTest.class,ChildWithAssociationWritableApiTest.getTestCaseName()));
		suite.addTest(new TestSuite(ChildWithAssociationSametableWritableApiTest.class,ChildWithAssociationSametableWritableApiTest.getTestCaseName()));
		suite.addTest(new TestSuite(ParentWithAssociationWritableApiTest.class,ParentWithAssociationWritableApiTest.getTestCaseName()));
		suite.addTest(new TestSuite(ParentWithAssociationSametableWritableApiTest.class,ParentWithAssociationSametableWritableApiTest.getTestCaseName()));		
		return suite;
	}
}
