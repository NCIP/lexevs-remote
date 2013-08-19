/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.client.proxy;

import java.lang.reflect.Method;

import org.LexGrid.LexBIG.testUtil.MockMethodInvocation;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.concepts.Entity;
import org.aopalliance.intercept.MethodInvocation;

public class TestSkipSpecialLazyLoadMethods extends ServiceTestCase
{
	private final String test_id = "Skip Special LazyLoad Methods";
	DataServiceProxyHelperImpl dataServiceProxyHelper;

	
	@Override
	protected String getTestID() {
		return test_id;	
	}
	
	public void setUp(){
		dataServiceProxyHelper = new DataServiceProxyHelperImpl();
	}
	
	public void testAcceptableLazyLoadMethod() throws Exception {
		Entity concept = new Entity();
		Method method = concept.getClass().getMethod("getPresentation", null);
		MethodInvocation invocation = new MockMethodInvocation(method, null, concept);	
		assertTrue(dataServiceProxyHelper.isLazyLoadableMethod(method));
	}
	
	public void testSkipUserDefinedNonLazyLoadableMethod() throws Exception {
		Entity concept = new Entity();
		Method method = concept.getClass().getMethod("getAllProperties", null);
		MethodInvocation invocation = new MockMethodInvocation(method, null, concept);	
		assertFalse(dataServiceProxyHelper.isLazyLoadableMethod(method));
	}
}