/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.client.proxy;

import junit.framework.Test;
import junit.framework.TestSuite;


public class ProxyTestSuite {
	 public static Test suite() throws Exception
	    {
	        TestSuite mainSuite = new TestSuite("LexEVS caCORE Proxy Tests");
	  

	        mainSuite.addTestSuite(LexEVSApplicationServiceProxyTest.class);
	        mainSuite.addTestSuite(LexEVSApplicationServiceProxyTestGforge21879.class);
	        
	        return mainSuite;
	    }

}
