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
import org.LexGrid.LexBIG.Impl.Extensions.GenericExtensions.MappingExtensionImplTest;
import org.LexGrid.LexBIG.Impl.Extensions.GenericExtensions.SearchExtensionImplTest;
import org.LexGrid.LexBIG.Impl.function.codednodeset.ResolveToListTest;
import org.LexGrid.LexBIG.Impl.testUtility.LexBIGServiceTestFactory;
import org.LexGrid.LexBIG.Utility.RemoveFromDistributedTests;
import org.LexGrid.LexBIG.testUtil.RemoteLexBIGServiceTestFactory;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.internal.TextListener;
import org.junit.rules.ExternalResource;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;


/**
 * An implementation of a JUnit 4 level Catagorical test execution suite allowing the inclusion
 * and exclusion of test classes and methods. 
 *
 */
@RunWith(Categories.class)
@ExcludeCategory(RemoveFromDistributedTests.class)
@SuiteClasses({
//	ScannedLexBigTests.class, 
//	LexBIGServiceConvenienceMethodsImplTest.class, 
//	SearchExtensionImplTest.class,
//	ResolveToListTest.class,
	MappingExtensionImplTest.class})
public class RunDistributedVersionOfSuites {

    @ClassRule
    public static ExternalResource testRule = new ExternalResource(){
            @Override
            protected void before() throws Throwable{
         	   System.setProperty(LexBIGServiceTestFactory.LBS_TEST_FACTORY_ENV, RemoteLexBIGServiceTestFactory.class.getName());
            };

            @Override
            protected void after(){

            };
        };
}