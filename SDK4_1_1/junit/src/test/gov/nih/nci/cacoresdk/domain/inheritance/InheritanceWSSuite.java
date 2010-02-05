package test.gov.nih.nci.cacoresdk.domain.inheritance;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.gov.nih.nci.cacoresdk.domain.inheritance.abstrakt.AbstractParentWithAssociationWSTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.ChildWithAssociationWSTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.sametable.ChildWithAssociationSametableWSTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.implicit.ImplicitParentWithAssociationWSTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.multiplechild.MultipleChildWSTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.multiplechild.sametable.MultipleChildSametableWSTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.onechild.OneChildWSTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.onechild.sametable.OneChildSametableWSTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.parentwithassociation.ParentWithAssociationWSTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.parentwithassociation.sametable.ParentWithAssociationSametableWSTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.twolevelinheritance.TwoLevelInheritanceWSTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.twolevelinheritance.sametable.TwoLevelInheritanceSametableWSTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.twolevelinheritance.sametablerootlevel.TwoLevelInheritanceSametablerootlevelWSTest;

public class InheritanceWSSuite
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test for Inheritance WS package");
		suite.addTest(new TestSuite(AbstractParentWithAssociationWSTest.class,AbstractParentWithAssociationWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(ImplicitParentWithAssociationWSTest.class,ImplicitParentWithAssociationWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(OneChildWSTest.class,OneChildWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(OneChildSametableWSTest.class,OneChildSametableWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(TwoLevelInheritanceWSTest.class,TwoLevelInheritanceWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(TwoLevelInheritanceSametableWSTest.class,TwoLevelInheritanceSametableWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(TwoLevelInheritanceSametablerootlevelWSTest.class,TwoLevelInheritanceSametablerootlevelWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(MultipleChildWSTest.class,MultipleChildWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(MultipleChildSametableWSTest.class,MultipleChildSametableWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(ChildWithAssociationWSTest.class,ChildWithAssociationWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(ChildWithAssociationSametableWSTest.class,ChildWithAssociationSametableWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(ParentWithAssociationWSTest.class,ParentWithAssociationWSTest.getTestCaseName()));
		suite.addTest(new TestSuite(ParentWithAssociationSametableWSTest.class,ParentWithAssociationSametableWSTest.getTestCaseName()));		
		return suite;
	}
}
