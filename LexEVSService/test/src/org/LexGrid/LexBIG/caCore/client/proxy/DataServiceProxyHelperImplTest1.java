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
import org.LexGrid.codingSchemes.CodingScheme;
import org.LexGrid.concepts.Entity;
import org.aopalliance.intercept.MethodInvocation;

import edu.mayo.informatics.lexgrid.convert.directConversions.TextCommon.Concept;

public class DataServiceProxyHelperImplTest1 extends ServiceTestCase
{
	private final String test_id = "Get Associations Tests";
	DataServiceProxyHelperImpl dataServiceProxyHelper;

	
	@Override
	protected String getTestID() {
		return test_id;
		
	}
	
	public void setUp(){
		dataServiceProxyHelper = new DataServiceProxyHelperImpl();
	}
	
	public void testGetPropertyNameFromMethodName1() throws Exception {
		Entity concept = new Entity();
		Method method = concept.getClass().getMethod("getPresentation", null);
		MethodInvocation invocation = new MockMethodInvocation(method, null);		
		String propertyName = dataServiceProxyHelper.getPropertyNameFromMethodName(invocation);
		assertTrue(propertyName.equals("_presentationList"));
	}
	
	public void testGetPropertyNameFromMethodName2() throws Exception {
		Entity concept = new Entity();
		Method method = concept.getClass().getMethod("getPresentationCount", null);
		MethodInvocation invocation = new MockMethodInvocation(method, null);		
		String propertyName = dataServiceProxyHelper.getPropertyNameFromMethodName(invocation);
		assertTrue(propertyName.equals("_presentationList"));
	}
	
	public void testGetPropertyNameFromMethodName3() throws Exception {
		Entity concept = new Entity();
		Method method = concept.getClass().getMethod("getDefinition", null);
		MethodInvocation invocation = new MockMethodInvocation(method, null);		
		String propertyName = dataServiceProxyHelper.getPropertyNameFromMethodName(invocation);
		assertTrue(propertyName.equals("_definitionList"));
	}
	
	public void testGetPropertyNameFromMethodName4() throws Exception {
		Entity concept = new Entity();
		Method method = concept.getClass().getMethod("enumeratePresentation", null);
		MethodInvocation invocation = new MockMethodInvocation(method, null);		
		String propertyName = dataServiceProxyHelper.getPropertyNameFromMethodName(invocation);
		assertTrue(propertyName.equals("_presentationList"));
	}
	
	public void testGetPropertyNameFromMethodName5() throws Exception {
		Entity concept = new Entity();
		Method method = concept.getClass().getMethod("iteratePresentation", null);
		MethodInvocation invocation = new MockMethodInvocation(method, null);		
		String propertyName = dataServiceProxyHelper.getPropertyNameFromMethodName(invocation);
		assertTrue(propertyName.equals("_presentationList"));
	}
	
	public void testGetPropertyNameFromMethodName6() throws Exception {
		Entity concept = new Entity();
		Method method = concept.getClass().getMethod("getPresentation", int.class);
		MethodInvocation invocation = new MockMethodInvocation(method, new Object[]{1});		
		String propertyName = dataServiceProxyHelper.getPropertyNameFromMethodName(invocation);
		assertTrue(propertyName.equals("_presentationList"));
	}
	
	public void testGetPropertyNameFromMethodName7() throws Exception {
		Entity concept = new Entity();
		Method method = concept.getClass().getMethod("enumeratePresentation", null);
		MethodInvocation invocation = new MockMethodInvocation(method, null);		
		String propertyName = dataServiceProxyHelper.getPropertyNameFromMethodName(invocation);
		assertTrue(propertyName.equals("_presentationList"));
	}
	
	public void testGetPropertyNameFromMethodName8() throws Exception {
		CodingScheme cs = new CodingScheme();
		Method method = cs.getClass().getMethod("getMappings", null);
		MethodInvocation invocation = new MockMethodInvocation(method, null);		
		String propertyName = dataServiceProxyHelper.getPropertyNameFromMethodName(invocation);
		assertTrue(propertyName.equals("_mappings"));
	}	
}