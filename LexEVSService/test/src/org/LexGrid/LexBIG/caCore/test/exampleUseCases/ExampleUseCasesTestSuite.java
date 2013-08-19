/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.test.exampleUseCases;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ExampleUseCasesTestSuite {
    
    /**
     * Suite.
     * 
     * @return the test
     * 
     * @throws Exception the exception
     */
    public static Test suite() throws Exception
    {
        TestSuite mainSuite = new TestSuite("Tests for expected much-used service usescases.");
        mainSuite.addTestSuite(GetAssociations.class);
        mainSuite.addTestSuite(GetConceptFromEntityDescription.class);
        return mainSuite;
    }
}
