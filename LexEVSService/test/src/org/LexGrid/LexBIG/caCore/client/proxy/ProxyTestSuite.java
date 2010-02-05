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
package org.LexGrid.LexBIG.caCore.client.proxy;

import junit.framework.Test;
import junit.framework.TestSuite;


public class ProxyTestSuite {
	 public static Test suite() throws Exception
	    {
	        TestSuite mainSuite = new TestSuite("LexEVS caCORE Proxy Tests");
	  
	        mainSuite.addTestSuite(DataServiceProxyHelperImplTest1.class);
	        mainSuite.addTestSuite(DataServiceProxyHelperImplTest2.class);
	        mainSuite.addTestSuite(DataServiceProxyHelperImplTest3.class);
	        mainSuite.addTestSuite(LexEVSApplicationServiceProxyTest.class);
	        mainSuite.addTestSuite(LexEVSProxyHelperImplTest.class);
	        mainSuite.addTestSuite(LexEVSApplicationServiceProxyTestGforge21879.class);
	        
	        return mainSuite;
	    }

}
