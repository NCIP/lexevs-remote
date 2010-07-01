/*******************************************************************************
 * Copyright: (c) 2004-2009 Mayo Foundation for Medical Education and 
 * Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
 * triple-shield Mayo logo are trademarks and service marks of MFMER.
 * 
 * Except as contained in the copyright notice above, or as used to identify 
 * MFMER as the author of this software, the trade names, trademarks, service
 * marks, or product names of the copyright holder shall not be used in
 * advertising, promotion or otherwise in connection with this software without
 * prior written authorization of the copyright holder.
 *   
 * Licensed under the Eclipse Public License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *   
 *  		http://www.eclipse.org/legal/epl-v10.html
 * 
 *  		
 *******************************************************************************/
package org.LexGrid.LexBIG.caCore.test.setup;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.LexGrid.LexBIG.caCore.test.bugs.BugsTestSuite;
import org.LexGrid.LexBIG.caCore.test.exampleUseCases.ExampleUseCasesTestSuite;
import org.LexGrid.LexBIG.caCore.test.history.HistoryTestSuite;
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
		suite.addTest(HistoryTestSuite.suite());
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
