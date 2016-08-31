package org.LexGrid.LexBIG.distributed.test.testUtility;

import org.LexGrid.LexBIG.Impl.codedNodeSetOperations.RestrictToProperties;
import org.LexGrid.LexBIG.Impl.function.codednodeset.CodedNodeSetOperationsTest;
import org.LexGrid.LexBIG.Impl.function.codednodeset.DifferenceTest;
import org.LexGrid.LexBIG.Impl.function.codednodeset.ExtensionCodedNodeSetTest;
import org.LexGrid.LexBIG.Impl.function.codednodeset.IntersectionTest;
import org.LexGrid.LexBIG.Impl.function.codednodeset.IsCodeInSetTest;
import org.LexGrid.LexBIG.Impl.function.codednodeset.MultipeRestrictionsTest;
import org.LexGrid.LexBIG.Impl.function.codednodeset.RestrictToMatchingDesignationsTest;
import org.LexGrid.LexBIG.Impl.function.codednodeset.UnionTest;
import org.LexGrid.LexBIG.Impl.function.mapping.MappingToNodeListTest;
import org.LexGrid.LexBIG.Utility.IncludeForDistributedTests;
import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Categories.class)
@IncludeCategory(IncludeForDistributedTests.class)
@SuiteClasses({
	CodedNodeSetOperationsTest.class,
	DifferenceTest.class,
	ExtensionCodedNodeSetTest.class,
	IntersectionTest.class,
	IsCodeInSetTest.class,
	MultipeRestrictionsTest.class,
	RestrictToMatchingDesignationsTest.class,
	RestrictToProperties.class,
	UnionTest.class,
	MappingToNodeListTest.class
	})
public class ScannedCodedNodeTestSuite {


}
