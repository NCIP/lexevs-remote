/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.test.query.gridcql;

import junit.framework.Test;
import junit.framework.TestSuite;

public class GridCQLTestSuite {
	
	    public static Test suite() throws Exception
	    {
	        TestSuite mainSuite = new TestSuite("LexEVS caCORE Query Grid CQL Tests");       
	        mainSuite.addTestSuite(GridCQLConcept.class);
	        mainSuite.addTestSuite(GridCQLCodingScheme.class);
	        mainSuite.addTestSuite(GridCQLGroups.class);
	        mainSuite.addTestSuite(GridCQLPredicates.class);
	        return mainSuite;
	    }
}
