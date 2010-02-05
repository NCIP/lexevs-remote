package test.gov.nih.nci.cacoresdk.domain.inheritance;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.gov.nih.nci.cacoresdk.domain.inheritance.abstrakt.AbstractParentWithAssociationTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.ChildWithAssociationTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.sametable.ChildWithAssociationSametableTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.implicit.ImplicitParentWithAssociationTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.multiplechild.MultipleChildTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.multiplechild.sametable.MultipleChildSametableTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.onechild.OneChildTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.onechild.sametable.OneChildSametableTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.parentwithassociation.ParentWithAssociationTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.parentwithassociation.sametable.ParentWithAssociationSametableTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.twolevelinheritance.TwoLevelInheritanceTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.twolevelinheritance.sametable.TwoLevelInheritanceSametableTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.twolevelinheritance.sametablerootlevel.TwoLevelInheritanceSametablerootlevelTest;

public class InheritanceSuite
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test for Inheritance package");
		suite.addTest(new TestSuite(AbstractParentWithAssociationTest.class,AbstractParentWithAssociationTest.getTestCaseName()));
		suite.addTest(new TestSuite(ImplicitParentWithAssociationTest.class,ImplicitParentWithAssociationTest.getTestCaseName()));
		suite.addTest(new TestSuite(OneChildTest.class,OneChildTest.getTestCaseName()));
		suite.addTest(new TestSuite(OneChildSametableTest.class,OneChildSametableTest.getTestCaseName()));
		suite.addTest(new TestSuite(TwoLevelInheritanceTest.class,TwoLevelInheritanceTest.getTestCaseName()));
		suite.addTest(new TestSuite(TwoLevelInheritanceSametableTest.class,TwoLevelInheritanceSametableTest.getTestCaseName()));
		suite.addTest(new TestSuite(TwoLevelInheritanceSametablerootlevelTest.class,TwoLevelInheritanceSametablerootlevelTest.getTestCaseName()));
		suite.addTest(new TestSuite(MultipleChildTest.class,MultipleChildTest.getTestCaseName()));
		suite.addTest(new TestSuite(MultipleChildSametableTest.class,MultipleChildSametableTest.getTestCaseName()));
		suite.addTest(new TestSuite(ChildWithAssociationTest.class,ChildWithAssociationTest.getTestCaseName()));
		suite.addTest(new TestSuite(ChildWithAssociationSametableTest.class,ChildWithAssociationSametableTest.getTestCaseName()));
		suite.addTest(new TestSuite(ParentWithAssociationTest.class,ParentWithAssociationTest.getTestCaseName()));
		suite.addTest(new TestSuite(ParentWithAssociationSametableTest.class,ParentWithAssociationSametableTest.getTestCaseName()));		
		return suite;
	}
}
