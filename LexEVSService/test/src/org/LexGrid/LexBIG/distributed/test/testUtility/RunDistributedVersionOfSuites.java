/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.distributed.test.testUtility;

import org.LexGrid.LexBIG.Impl.Extensions.GenericExtensions.LexBIGServiceConvenienceMethodsImplTest;
import org.LexGrid.LexBIG.Utility.RemoveFromDistributedTests;
import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;


/**
 * An implementation of a JUnit 4 level Catagorical test execution suite allowing the inclusion
 * and exclusion of test classes and methods. 
 *
 */
@RunWith(Categories.class)
@ExcludeCategory(RemoveFromDistributedTests.class)
@SuiteClasses({ScannedLexBigTests.class, LexBIGServiceConvenienceMethodsImplTest.class})
public class RunDistributedVersionOfSuites {

}
