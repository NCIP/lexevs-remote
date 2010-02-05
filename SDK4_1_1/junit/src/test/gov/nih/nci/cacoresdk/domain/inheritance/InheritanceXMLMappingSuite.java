package test.gov.nih.nci.cacoresdk.domain.inheritance;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.gov.nih.nci.cacoresdk.domain.inheritance.abstrakt.AbstractParentWithAssociationXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.ChildWithAssociationXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.sametable.ChildWithAssociationSametableXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.implicit.ImplicitParentWithAssociationXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.multiplechild.MultipleChildXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.multiplechild.sametable.MultipleChildSametableXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.onechild.OneChildXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.onechild.sametable.OneChildSametableXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.parentwithassociation.ParentWithAssociationXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.parentwithassociation.sametable.ParentWithAssociationSametableXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.twolevelinheritance.TwoLevelInheritanceXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.twolevelinheritance.sametable.TwoLevelInheritanceSametableXMLMappingTest;
import test.gov.nih.nci.cacoresdk.domain.inheritance.twolevelinheritance.sametablerootlevel.TwoLevelInheritanceSametablerootlevelXMLMappingTest;

public class InheritanceXMLMappingSuite
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Test for Inheritance XML Mapping package");
		suite.addTest(new TestSuite(AbstractParentWithAssociationXMLMappingTest.class,AbstractParentWithAssociationXMLMappingTest.getTestCaseName()));		
		suite.addTest(new TestSuite(ImplicitParentWithAssociationXMLMappingTest.class,ImplicitParentWithAssociationXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(OneChildXMLMappingTest.class,OneChildXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(OneChildSametableXMLMappingTest.class,OneChildSametableXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(TwoLevelInheritanceXMLMappingTest.class,TwoLevelInheritanceXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(TwoLevelInheritanceSametableXMLMappingTest.class,TwoLevelInheritanceSametableXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(TwoLevelInheritanceSametablerootlevelXMLMappingTest.class,TwoLevelInheritanceSametablerootlevelXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(MultipleChildXMLMappingTest.class,MultipleChildXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(MultipleChildSametableXMLMappingTest.class,MultipleChildSametableXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(ChildWithAssociationXMLMappingTest.class,ChildWithAssociationXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(ChildWithAssociationSametableXMLMappingTest.class,ChildWithAssociationSametableXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(ParentWithAssociationXMLMappingTest.class,ParentWithAssociationXMLMappingTest.getTestCaseName()));
		suite.addTest(new TestSuite(ParentWithAssociationSametableXMLMappingTest.class,ParentWithAssociationSametableXMLMappingTest.getTestCaseName()));		
		return suite;
	}
}
