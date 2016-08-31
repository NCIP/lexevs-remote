package org.LexGrid.LexBIG.testUtil;

import org.LexGrid.LexBIG.Impl.function.query.lucene.searchAlgorithms.TestContains;
import org.LexGrid.LexBIG.Impl.function.query.lucene.searchAlgorithms.TestContainsLiteralContains;
import org.LexGrid.LexBIG.Impl.function.query.lucene.searchAlgorithms.TestDoubleMetaphone;
import org.LexGrid.LexBIG.Impl.function.query.lucene.searchAlgorithms.TestExactMatch;
import org.LexGrid.LexBIG.Impl.function.query.lucene.searchAlgorithms.TestLeadingAndTrailingWildcard;
import org.LexGrid.LexBIG.Impl.function.query.lucene.searchAlgorithms.TestLiteral;
import org.LexGrid.LexBIG.Impl.function.query.lucene.searchAlgorithms.TestLiteralContains;
import org.LexGrid.LexBIG.Impl.function.query.lucene.searchAlgorithms.TestLiteralLiteralContains;
import org.LexGrid.LexBIG.Impl.function.query.lucene.searchAlgorithms.TestLiteralSpellingErrorTolerantSubString;
import org.LexGrid.LexBIG.Impl.function.query.lucene.searchAlgorithms.TestLiteralSubString;
import org.LexGrid.LexBIG.Impl.function.query.lucene.searchAlgorithms.TestPhrase;
import org.LexGrid.LexBIG.Impl.function.query.lucene.searchAlgorithms.TestPlainLuceneQuery;
import org.LexGrid.LexBIG.Impl.function.query.lucene.searchAlgorithms.TestRegExp;
import org.LexGrid.LexBIG.Impl.function.query.lucene.searchAlgorithms.TestSearchByPreferred;
import org.LexGrid.LexBIG.Impl.function.query.lucene.searchAlgorithms.TestSpellingErrorTolerantSubString;
import org.LexGrid.LexBIG.Impl.function.query.lucene.searchAlgorithms.TestStartsWith;
import org.LexGrid.LexBIG.Impl.function.query.lucene.searchAlgorithms.TestStemming;
import org.LexGrid.LexBIG.Impl.function.query.lucene.searchAlgorithms.TestSubString;
import org.LexGrid.LexBIG.Impl.function.query.lucene.searchAlgorithms.TestSubStringLiteralSubString;
import org.LexGrid.LexBIG.Impl.function.query.lucene.searchAlgorithms.TestSubStringNonLeadingWildcardLiteralSubString;
import org.LexGrid.LexBIG.Impl.function.query.lucene.searchAlgorithms.TestWeightedDoubleMetaphone;
import org.LexGrid.LexBIG.Utility.IncludeForDistributedTests;
import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Categories.class)
@IncludeCategory(IncludeForDistributedTests.class)
@SuiteClasses({
	TestContains.class,
	TestContainsLiteralContains.class,
	TestDoubleMetaphone.class,
	TestExactMatch.class,
	TestLeadingAndTrailingWildcard.class,
	TestLiteral.class,
	TestLiteralContains.class,
	TestLiteralLiteralContains.class,
	TestLiteralSpellingErrorTolerantSubString.class,
	TestLiteralSubString.class,
	TestPhrase.class,
	TestPlainLuceneQuery.class,
	TestRegExp.class,
	TestSearchByPreferred.class,
	TestSpellingErrorTolerantSubString.class,
	TestStartsWith.class,
	TestStemming.class,
	TestSubString.class,
	TestSubStringLiteralSubString.class,
	TestSubStringNonLeadingWildcardLiteralSubString.class,
	TestWeightedDoubleMetaphone.class})
public class ScannedQueryLuceneSuite {

}