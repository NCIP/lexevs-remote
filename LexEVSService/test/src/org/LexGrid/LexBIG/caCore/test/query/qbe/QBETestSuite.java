/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.test.query.qbe;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * The Class AllTests_EVSQuery.
 */
public class QBETestSuite {
    
    /**
     * Suite.
     * 
     * @return the test
     * 
     * @throws Exception the exception
     */
    public static Test suite() throws Exception
    {
        TestSuite mainSuite = new TestSuite("LexEVS caCORE Query QBE Tests");
        mainSuite.addTestSuite(QBEEntity.class);
        mainSuite.addTestSuite(QBEAssociationQualification.class);
        mainSuite.addTestSuite(QBEAssociationSource.class);
        mainSuite.addTestSuite(QBECodingScheme.class);       
        mainSuite.addTestSuite(QBEAssociation.class);   
        mainSuite.addTestSuite(QBESupportedAssociation.class);
        mainSuite.addTestSuite(QBESupportedAssociationQualifier.class);      
        mainSuite.addTestSuite(QBESupportedContext.class);
        mainSuite.addTestSuite(QBESupportedDegreeOfFidelity.class);
        mainSuite.addTestSuite(QBESupportedSource.class);
    
        return mainSuite;
    }
}
