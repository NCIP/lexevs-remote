/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.test.bugs;

import junit.framework.Test;
import junit.framework.TestSuite;

public class BugsTestSuite {
	
	    public static Test suite() throws Exception
	    {
	        TestSuite mainSuite = new TestSuite("LexEVS caCORE GForge Bug Tests");
	        mainSuite.addTestSuite(GForge19716.class);
	        mainSuite.addTestSuite(GForge17233.class);
	        return mainSuite;
	    }
}
