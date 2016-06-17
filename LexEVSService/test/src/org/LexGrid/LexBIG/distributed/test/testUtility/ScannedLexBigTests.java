/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.distributed.test.testUtility;

import java.util.Set;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.LexGrid.LexBIG.Utility.RemoteApiSafeTest;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

/**
 * A programmatic Test builder that scans the LexBIG local tests for the {@link RemoteApiSafeTest} annotation
 * and adds them to the suite.
 */

public class ScannedLexBigTests {

	public static Test suite() throws Exception {
		TestSuite mainSuite = new TestSuite("LexBIG validation tests");

		Reflections reflections = new Reflections("org.LexGrid.LexBIG",
				new TypeAnnotationsScanner(),
				new SubTypesScanner(false));

		Set<Class<? extends Object>> allClasses =
				reflections.getTypesAnnotatedWith(RemoteApiSafeTest.class);

		for(Class test : allClasses) {
			mainSuite.addTestSuite(test);
		}

		return mainSuite;
	}
}