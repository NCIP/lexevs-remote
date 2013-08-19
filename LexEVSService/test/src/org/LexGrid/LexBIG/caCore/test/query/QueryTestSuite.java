/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.test.query;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.LexGrid.LexBIG.caCore.test.query.cql.CQLTestSuite;
import org.LexGrid.LexBIG.caCore.test.query.getAssociation.GetAssociationTestSuite;
import org.LexGrid.LexBIG.caCore.test.query.gridcql.GridCQLTestSuite;
import org.LexGrid.LexBIG.caCore.test.query.hql.HQLTestSuite;
import org.LexGrid.LexBIG.caCore.test.query.qbe.QBETestSuite;

public class QueryTestSuite {
    
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
        mainSuite.addTest(HQLTestSuite.suite());
        mainSuite.addTest(QBETestSuite.suite());
        mainSuite.addTest(GetAssociationTestSuite.suite());
        mainSuite.addTest(CQLTestSuite.suite());
        mainSuite.addTest(GridCQLTestSuite.suite());
        return mainSuite;
    }
}
