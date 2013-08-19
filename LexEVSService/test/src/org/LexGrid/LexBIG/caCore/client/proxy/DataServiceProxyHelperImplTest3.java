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
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import org.LexGrid.LexBIG.testUtil.MockMethodInvocation;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.concepts.Entity;
import org.LexGrid.concepts.Presentation;
import org.aopalliance.intercept.MethodInvocation;

public class DataServiceProxyHelperImplTest3 extends ServiceTestCase
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
	
	public void testIsInitialized() throws Exception {
		Entity concept = new Entity();
		Method method = concept.getClass().getMethod("getPresentation", null);
		MethodInvocation invocation = new MockMethodInvocation(method, null, concept);	
		assertTrue(dataServiceProxyHelper.isInitialized(invocation));
	}
	
	public void testMakeFirstLetterLowerCase1(){
		String searchString = "TestString";
		assertTrue(dataServiceProxyHelper.makeFirstLetterLowerCase(searchString).
				equals("testString"));	
	}
	
	public void testMakeFirstLetterLowerCase2(){
		String searchString = "testString";
		assertTrue(dataServiceProxyHelper.makeFirstLetterLowerCase(searchString).
				equals("testString"));	
	}
	
	public void testAccountForCastorMethods() throws Exception {
		List result = new ArrayList();
		result.add(new Presentation());
		result.add(new Presentation());
		result.add(new Presentation());
		
		Entity concept = new Entity();
		Method method = concept.getClass().getMethod("getPresentation", null);
		MethodInvocation invocation = new MockMethodInvocation(method, null);	
		
		Object returnedObj = dataServiceProxyHelper.accountForCastorMethods(result, invocation);
		assertTrue(returnedObj.getClass().isArray());
		
		Presentation[] returnedList = (Presentation[])returnedObj;
		
		assertTrue(returnedList.length == 3);
	}
	
	public void testAccountForCastorMethods2() throws Exception {
		List result = new ArrayList();
		Presentation searchPres = new Presentation();
		searchPres.setPropertyId("FIND_THIS_ONE");
		
		result.add(new Presentation());
		result.add(searchPres);
		result.add(new Presentation());
		
		Entity concept = new Entity();
		Method method = concept.getClass().getMethod("getPresentation", int.class);
		MethodInvocation invocation = new MockMethodInvocation(method, new Object[]{1});	
		
		Object returnedObj = dataServiceProxyHelper.accountForCastorMethods(result, invocation);
		assertTrue(returnedObj instanceof Presentation);
		
		Presentation pres = (Presentation)returnedObj;
		assertTrue(pres.getPropertyId().equals("FIND_THIS_ONE"));
	}
	
	public void testAccountForCastorMethods3() throws Exception {
		List result = new ArrayList();	
		
		result.add(new Presentation());
		result.add(new Presentation());
		result.add(new Presentation());
		
		Entity concept = new Entity();
		Method method = concept.getClass().getMethod("getPresentationCount");
		MethodInvocation invocation = new MockMethodInvocation(method, null);	
		
		Object returnedObj = dataServiceProxyHelper.accountForCastorMethods(result, invocation);
		assertTrue(returnedObj instanceof Integer);
		
		Integer count = (Integer)returnedObj;
		assertTrue(count.equals(3));
	}
	
	public void testAccountForCastorMethods4() throws Exception {
		List result = new ArrayList();	
		
		result.add(new Presentation());
		result.add(new Presentation());
		result.add(new Presentation());
		
		Entity concept = new Entity();
		Method method = concept.getClass().getMethod("iteratePresentation");
		MethodInvocation invocation = new MockMethodInvocation(method, null);	
		
		Object returnedObj = dataServiceProxyHelper.accountForCastorMethods(result, invocation);
		assertTrue(returnedObj instanceof Iterator);
		
		Iterator itr = (Iterator)returnedObj;
		assertTrue(itr.hasNext());
	}
	
	public void testAccountForCastorMethods5() throws Exception {
		List result = new ArrayList();	
		
		result.add(new Presentation());
		result.add(new Presentation());
		result.add(new Presentation());
		
		Entity concept = new Entity();
		Method method = concept.getClass().getMethod("enumeratePresentation");
		MethodInvocation invocation = new MockMethodInvocation(method, null);	
		
		Object returnedObj = dataServiceProxyHelper.accountForCastorMethods(result, invocation);
		assertTrue(returnedObj instanceof Enumeration);
		
		Enumeration enumeration = (Enumeration)returnedObj;
		assertTrue(enumeration.hasMoreElements());
	}
}