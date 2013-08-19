/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.test.setup;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.LexGrid.LexBIG.caCore.test.bugs.BugsTestSuite;
import org.LexGrid.LexBIG.caCore.test.exampleUseCases.ExampleUseCasesTestSuite;
import org.LexGrid.LexBIG.caCore.test.infrastructure.InfrastructureTestSuite;
import org.LexGrid.LexBIG.caCore.test.lazyLoading.LazyLoadTestSuite;
import org.LexGrid.LexBIG.caCore.test.pagination.PaginationTestSuite;
import org.LexGrid.LexBIG.caCore.test.query.QueryTestSuite;
import org.LexGrid.LexBIG.caCore.test.queryOptions.QueryOptionsTestSuite;
import org.LexGrid.LexBIG.caCore.test.rest.RESTTestSuite;
import org.LexGrid.LexBIG.caCore.test.security.SecurityTestSuite;
import org.LexGrid.LexBIG.caCore.test.webservice.WebServiceTestSuite;

public class AllDataServiceTests {

	public static Test suite() throws Exception{
		TestSuite suite = new TestSuite("Tests for the LexEVS Data Services");
		//$JUnit-BEGIN$

		suite.addTest(BugsTestSuite.suite());
		suite.addTest(ExampleUseCasesTestSuite.suite());
		suite.addTest(RESTTestSuite.suite());
		suite.addTest(QueryTestSuite.suite());
		suite.addTest(SecurityTestSuite.suite());		
		suite.addTest(WebServiceTestSuite.suite());
		suite.addTest(QueryOptionsTestSuite.suite());
		suite.addTest(LazyLoadTestSuite.suite());
		suite.addTest(PaginationTestSuite.suite());
		suite.addTest(InfrastructureTestSuite.suite());
		
		//$JUnit-END$
		return suite;
	}

}
