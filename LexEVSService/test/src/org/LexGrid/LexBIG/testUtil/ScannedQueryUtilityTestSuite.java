package org.LexGrid.LexBIG.testUtil;

import org.LexGrid.LexBIG.Impl.function.query.TestAttributePresenceMatch;
import org.LexGrid.LexBIG.Impl.function.query.TestChildIndicator;
import org.LexGrid.LexBIG.Impl.function.query.TestCodingSchemesWithSupportedAssociation;
import org.LexGrid.LexBIG.Impl.function.query.TestDescribeSearchTechniques;
import org.LexGrid.LexBIG.Impl.function.query.TestDescribeSupportedSearchCriteria;
import org.LexGrid.LexBIG.Impl.function.query.TestEnumerateAllConcepts;
import org.LexGrid.LexBIG.Impl.function.query.TestEnumerateAssociationNames;
import org.LexGrid.LexBIG.Impl.function.query.TestEnumerateRelationships;
import org.LexGrid.LexBIG.Impl.function.query.TestGenerateDAG;
import org.LexGrid.LexBIG.Impl.function.query.TestMapAttributestoTypes;
import org.LexGrid.LexBIG.Impl.function.query.TestSameCodeDifferentNamespace;
import org.LexGrid.LexBIG.Impl.function.query.TestSearchbyStatus;
import org.LexGrid.LexBIG.Impl.function.query.TestSpecifyReturnOrder;
import org.LexGrid.LexBIG.Impl.function.query.TestTransitiveClosure;
import org.LexGrid.LexBIG.Impl.function.query.TestVersioningandAuthorityEnumeration;
import org.LexGrid.LexBIG.Utility.IncludeForDistributedTests;
import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Categories.class)
@IncludeCategory(IncludeForDistributedTests.class)
@SuiteClasses({
	TestAttributePresenceMatch.class,
	TestChildIndicator.class,
	TestCodingSchemesWithSupportedAssociation.class,
	TestDescribeSearchTechniques.class,
	TestDescribeSupportedSearchCriteria.class,
	TestEnumerateAllConcepts.class,
	TestEnumerateAssociationNames.class,
	TestEnumerateRelationships.class,
	TestGenerateDAG.class,
	TestMapAttributestoTypes.class,
	TestSameCodeDifferentNamespace.class,
	TestSearchbyStatus.class,
	TestSpecifyReturnOrder.class,
	TestTransitiveClosure.class,
	TestVersioningandAuthorityEnumeration.class
	})
public class ScannedQueryUtilityTestSuite {}
