package test.gov.nih.nci.cacoresdk.domain.inheritance;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.gov.nih.nci.cacoresdk.domain.inheritance.abstrakt.AbstractParentWithAssociationXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.ChildWithAssociationXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.sametable.ChildWithAssociationSametableXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.implicit.ImplicitParentWithAssociationXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.multiplechild.MultipleChildXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.multiplechild.sametable.MultipleChildSametableXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.onechild.OneChildXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.onechild.sametable.OneChildSametableXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.parentwithassociation.ParentWithAssociationXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.parentwithassociation.sametable.ParentWithAssociationSametableXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.twolevelinheritance.TwoLevelInheritanceXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.twolevelinheritance.sametable.TwoLevelInheritanceSametableXMLDataTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.twolevelinheritance.sametablerootlevel.TwoLevelInheritanceSametablerootlevelXMLDataTest;

public class InheritanceXMLDataSuite
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test for Inheritance XML Data package");
		suite.addTest(new TestSuite(AbstractParentWithAssociationXMLDataTest.class,AbstractParentWithAssociationXMLDataTest.getTestCaseName()));		
		suite.addTest(new TestSuite(ImplicitParentWithAssociationXMLDataTest.class,ImplicitParentWithAssociationXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(OneChildXMLDataTest.class,OneChildXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(OneChildSametableXMLDataTest.class,OneChildSametableXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(TwoLevelInheritanceXMLDataTest.class,TwoLevelInheritanceXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(TwoLevelInheritanceSametableXMLDataTest.class,TwoLevelInheritanceSametableXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(TwoLevelInheritanceSametablerootlevelXMLDataTest.class,TwoLevelInheritanceSametablerootlevelXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(MultipleChildXMLDataTest.class,MultipleChildXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(MultipleChildSametableXMLDataTest.class,MultipleChildSametableXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(ChildWithAssociationXMLDataTest.class,ChildWithAssociationXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(ChildWithAssociationSametableXMLDataTest.class,ChildWithAssociationSametableXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(ParentWithAssociationXMLDataTest.class,ParentWithAssociationXMLDataTest.getTestCaseName()));
		suite.addTest(new TestSuite(ParentWithAssociationSametableXMLDataTest.class,ParentWithAssociationSametableXMLDataTest.getTestCaseName()));		
		return suite;
	}
}
