/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.testUtil;

import org.LexGrid.LexBIG.Impl.Extensions.GenericExtensions.LexBIGServiceConvenienceMethodsImplTest;
import org.LexGrid.LexBIG.Impl.Extensions.GenericExtensions.MappingExtensionImplTest;
import org.LexGrid.LexBIG.Impl.Extensions.GenericExtensions.SearchExtensionImplTest;
import org.LexGrid.LexBIG.Impl.Extensions.tree.service.MultipleNamspaceErrorLEXEVS_598_Test;
import org.LexGrid.LexBIG.Impl.Extensions.tree.service.PathToRootTreeServiceImplTest;
import org.LexGrid.LexBIG.Impl.function.codednodeset.ResolveToListTest;
import org.LexGrid.LexBIG.Impl.testUtility.LexBIGServiceTestFactory;
import org.LexGrid.LexBIG.Utility.RemoveFromDistributedTests;
import org.LexGrid.LexBIG.distributed.test.testUtility.DistributedResolvedValueSetTests;
import org.LexGrid.LexBIG.distributed.test.testUtility.DistributedValueSetDefinitionTests;
import org.LexGrid.LexBIG.distributed.test.testUtility.ScannedCodedGraphTestSuite;
import org.LexGrid.LexBIG.distributed.test.testUtility.ScannedCodedNodeTestSuite;
import org.LexGrid.LexBIG.distributed.test.valueset.AssertedVSHierarchyTest;
import org.LexGrid.LexBIG.distributed.test.valueset.TestLexEVSValueSetDefinitionServices;
import org.junit.ClassRule;
import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;


/**
 * An implementation of a JUnit 4 level Categorical test execution suite allowing the inclusion
 * and exclusion of test classes and methods. 
 *
 */

@RunWith(Categories.class)
@ExcludeCategory(RemoveFromDistributedTests.class)
@SuiteClasses({
	ScannedCodedGraphTestSuite.class, 
	ScannedCodedNodeTestSuite.class,
	ScannedQueryLuceneSuite.class,
	ScannedQueryUtilityTestSuite.class,
	LexBIGServiceConvenienceMethodsImplTest.class, 
	SearchExtensionImplTest.class,
	ResolveToListTest.class,
	MappingExtensionImplTest.class,
	MultipleNamspaceErrorLEXEVS_598_Test.class,
	PathToRootTreeServiceImplTest.class,
	DistributedValueSetDefinitionTests.class,
	DistributedResolvedValueSetTests.class,
	TestLexEVSValueSetDefinitionServices.class,
	AssertedVSHierarchyTest.class,
	TestTreeExtensionFullServiceTest.class
	})
public class AllLexEVSAPITests{

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