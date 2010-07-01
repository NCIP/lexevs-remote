/*
 * Copyright: (c) 2004-2006 Mayo Foundation for Medical Education and
 * Research (MFMER).  All rights reserved.  MAYO, MAYO CLINIC, and the
 * triple-shield Mayo logo are trademarks and service marks of MFMER.
 *
 * Except as contained in the copyright notice above, the trade names, 
 * trademarks, service marks, or product names of the copyright holder shall
 * not be used in advertising, promotion or otherwise in connection with
 * this Software without prior written authorization of the copyright holder.
 * 
 * Licensed under the Eclipse Public License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 * 
 * 		http://www.eclipse.org/legal/epl-v10.html
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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