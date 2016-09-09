package org.LexGrid.LexBIG.distributed.test.testUtility;


import org.LexGrid.LexBIG.Impl.function.codednodegraph.CodingSchemeExtensionResolveTest;
import org.LexGrid.LexBIG.Impl.function.codednodegraph.CrossOntologyResolveTest;
import org.LexGrid.LexBIG.Impl.function.codednodegraph.FocusTest;
import org.LexGrid.LexBIG.Impl.function.codednodegraph.IntersectionTest;
import org.LexGrid.LexBIG.Impl.function.codednodegraph.RestrictToAnonymousTest;
import org.LexGrid.LexBIG.Impl.function.codednodegraph.RestrictToAssociationsTest;
import org.LexGrid.LexBIG.Impl.function.codednodegraph.RestrictToDirectionalNamesTest;
import org.LexGrid.LexBIG.Impl.function.codednodegraph.RestrictToSourceCodesTest;
import org.LexGrid.LexBIG.Impl.function.codednodegraph.RestrictToTargetCodesTest;
import org.LexGrid.LexBIG.Impl.function.codednodegraph.RootsTest;
import org.LexGrid.LexBIG.Impl.function.codednodegraph.SearchByRelationshipTest;
import org.LexGrid.LexBIG.Impl.function.codednodegraph.SortGraphTest;
import org.LexGrid.LexBIG.Impl.function.codednodegraph.UnionTest;
import org.LexGrid.LexBIG.Impl.function.codednodeset.ResolveToListTest;
import org.LexGrid.LexBIG.Utility.IncludeForDistributedTests;
import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Categories.class)
@IncludeCategory(IncludeForDistributedTests.class)
@SuiteClasses({
//	CodingSchemeExtensionResolveTest.class,
//	CrossOntologyResolveTest.class,
//	FocusTest.class,
//	IntersectionTest.class,
//	ResolveToListTest.class,
//	RestrictToAnonymousTest.class,
//	RestrictToAssociationsTest.class,
//	RestrictToDirectionalNamesTest.class,
//	RestrictToSourceCodesTest.class,
//	RestrictToTargetCodesTest.class,
//	RootsTest.class,
//	SearchByRelationshipTest.class,
	SortGraphTest.class,
	UnionTest.class
	})
public class ScannedCodedGraphTestSuite {}
