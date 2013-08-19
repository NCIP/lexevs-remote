/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.test.queryOptions;

import junit.framework.Test;
import junit.framework.TestSuite;

public class QueryOptionsTestSuite {
    
    /**
     * Suite.
     * 
     * @return the test
     * 
     * @throws Exception the exception
     */
    public static Test suite() throws Exception
    {
        TestSuite mainSuite = new TestSuite("LexEVS caCORE Query Tests");
        mainSuite.addTestSuite(QueryOptionsTest.class);
        mainSuite.addTestSuite(QueryOptionsSecurityTokensTest.class);
        mainSuite.addTestSuite(QueryOptionsCodingSchemeTest.class);
        mainSuite.addTestSuite(QueryOptionsVersionTest.class);
       
        return mainSuite;
    }
}
