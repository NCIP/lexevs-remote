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