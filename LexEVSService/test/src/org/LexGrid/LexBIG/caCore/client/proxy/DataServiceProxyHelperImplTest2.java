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

import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.codingSchemes.CodingScheme;
import org.LexGrid.concepts.Entity;
import org.LexGrid.concepts.Presentation;

public class DataServiceProxyHelperImplTest2 extends ServiceTestCase
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
	
	public void testIsLazyLoadable1() throws Exception {
		Entity concept = new Entity();
		Method method = concept.getClass().getMethod("getPresentation", null);	
		assertTrue(dataServiceProxyHelper.isLazyLoadableMethod(method));
		
	}

	public void testIsLazyLoadable2() throws Exception {
		Entity concept = new Entity();
		Method method = concept.getClass().getMethod("getPresentationCount", null);	
		assertTrue(dataServiceProxyHelper.isLazyLoadableMethod(method));
		
	}
	
	public void testIsLazyLoadable3() throws Exception {
		Entity concept = new Entity();
		Method method = concept.getClass().getMethod("getDefinition", null);	
		assertTrue(dataServiceProxyHelper.isLazyLoadableMethod(method));
		
	}
	
	public void testIsLazyLoadable4() throws Exception {
		Entity concept = new Entity();
		Method method = concept.getClass().getMethod("enumeratePresentation", null);	
		assertTrue(dataServiceProxyHelper.isLazyLoadableMethod(method));		
	}
	
	public void testIsLazyLoadable5() throws Exception {
		Entity concept = new Entity();
		Method method = concept.getClass().getMethod("iterateComment", null);	
		assertTrue(dataServiceProxyHelper.isLazyLoadableMethod(method));		
	}
	
	public void testIsLazyLoadable6() throws Exception {
		Entity concept = new Entity();
		Method method = concept.getClass().getMethod("setPresentation", Presentation[].class);	
		assertFalse(dataServiceProxyHelper.isLazyLoadableMethod(method));		
	}
	
	public void testIsLazyLoadable7() throws Exception {
		Entity concept = new Entity();
		Method method = concept.getClass().getMethod("toString", null);	
		assertFalse(dataServiceProxyHelper.isLazyLoadableMethod(method));		
	}
	
	public void testIsLazyLoadable8() throws Exception {
		CodingScheme cs = new CodingScheme();
		Method method = cs.getClass().getMethod("getMappings", null);	
		assertTrue(dataServiceProxyHelper.isLazyLoadableMethod(method));		
	}
	
	public void testIsLazyLoadable9() throws Exception {
		Object object = new Object();
		Method method = object.getClass().getMethod("toString", null);	
		assertFalse(dataServiceProxyHelper.isLazyLoadableMethod(method));		
	}
	
	public void testIsLazyLoadable10() throws Exception {
		Entity concept = new Entity();
		Method method = concept.getClass().getMethod("getPresentation", int.class);	
		assertTrue(dataServiceProxyHelper.isLazyLoadableMethod(method));		
	}
	
	public void testIsLazyLoadable11() throws Exception {
		Entity concept = new Entity();
		Method method = concept.getClass().getMethod("setPresentation", int.class, Presentation.class);	
		assertFalse(dataServiceProxyHelper.isLazyLoadableMethod(method));		
	}
}