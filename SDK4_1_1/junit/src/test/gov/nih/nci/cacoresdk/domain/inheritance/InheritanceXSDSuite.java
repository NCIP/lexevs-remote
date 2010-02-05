package test.gov.nih.nci.cacoresdk.domain.inheritance;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.gov.nih.nci.cacoresdk.domain.inheritance.abstrakt.AbstractParentWithAssociationXSDTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.ChildWithAssociationXSDTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.sametable.ChildWithAssociationSametableXSDTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.implicit.ImplicitParentWithAssociationXSDTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.multiplechild.MultipleChildXSDTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.multiplechild.sametable.MultipleChildSametableXSDTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.onechild.OneChildXSDTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.onechild.sametable.OneChildSametableXSDTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.parentwithassociation.ParentWithAssociationXSDTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.parentwithassociation.sametable.ParentWithAssociationSametableXSDTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.twolevelinheritance.TwoLevelInheritanceXSDTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.twolevelinheritance.sametable.TwoLevelInheritanceSametableXSDTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.twolevelinheritance.sametablerootlevel.TwoLevelInheritanceSametablerootlevelXSDTest;

public class InheritanceXSDSuite
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test for Inheritance XSD package");
		suite.addTest(new TestSuite(AbstractParentWithAssociationXSDTest.class,AbstractParentWithAssociationXSDTest.getTestCaseName()));		
		suite.addTest(new TestSuite(ImplicitParentWithAssociationXSDTest.class,ImplicitParentWithAssociationXSDTest.getTestCaseName()));
		suite.addTest(new TestSuite(OneChildXSDTest.class,OneChildXSDTest.getTestCaseName()));
		suite.addTest(new TestSuite(OneChildSametableXSDTest.class,OneChildSametableXSDTest.getTestCaseName()));
		suite.addTest(new TestSuite(TwoLevelInheritanceXSDTest.class,TwoLevelInheritanceXSDTest.getTestCaseName()));
		suite.addTest(new TestSuite(TwoLevelInheritanceSametableXSDTest.class,TwoLevelInheritanceSametableXSDTest.getTestCaseName()));
		suite.addTest(new TestSuite(TwoLevelInheritanceSametablerootlevelXSDTest.class,TwoLevelInheritanceSametablerootlevelXSDTest.getTestCaseName()));
		suite.addTest(new TestSuite(MultipleChildXSDTest.class,MultipleChildXSDTest.getTestCaseName()));
		suite.addTest(new TestSuite(MultipleChildSametableXSDTest.class,MultipleChildSametableXSDTest.getTestCaseName()));
		suite.addTest(new TestSuite(ChildWithAssociationXSDTest.class,ChildWithAssociationXSDTest.getTestCaseName()));
		suite.addTest(new TestSuite(ChildWithAssociationSametableXSDTest.class,ChildWithAssociationSametableXSDTest.getTestCaseName()));
		suite.addTest(new TestSuite(ParentWithAssociationXSDTest.class,ParentWithAssociationXSDTest.getTestCaseName()));
		suite.addTest(new TestSuite(ParentWithAssociationSametableXSDTest.class,ParentWithAssociationSametableXSDTest.getTestCaseName()));		
		return suite;
	}
}
