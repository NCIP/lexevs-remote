/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.test.infrastructure;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.LexGrid.LexBIG.caCore.client.proxy.DataServiceProxyHelperImplTest1;
import org.LexGrid.LexBIG.caCore.client.proxy.ProxyTestSuite;

public class InfrastructureTestSuite {
    
    /**
     * Suite.
     * 
     * @return the test
     * 
     * @throws Exception the exception
     */
    public static Test suite() throws Exception
    {
        TestSuite mainSuite = new TestSuite("Tests for non-query internal classes.");
        mainSuite.addTestSuite(EVSHibernateInterceptorTest.class);
        mainSuite.addTestSuite(DataServiceProxyHelperImplTest1.class);
        mainSuite.addTest(ProxyTestSuite.suite());

        return mainSuite;
    }
}
