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

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import gov.nih.nci.system.applicationservice.ApplicationService;

import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.caCore.applicationservice.QueryOptions;
import org.LexGrid.LexBIG.caCore.connection.orm.interceptors.EVSHibernateInterceptor;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.MockMethodInvocation;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.codingSchemes.CodingScheme;
import org.LexGrid.concepts.Concept;
import org.LexGrid.concepts.Presentation;
import org.LexGrid.naming.Mappings;
import org.LexGrid.naming.SupportedAssociation;
import org.LexGrid.naming.SupportedAssociationQualifier;
import org.LexGrid.naming.SupportedCodingScheme;
import org.LexGrid.relations.Association;
import org.LexGrid.relations.AssociationSource;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.ArrayUtils;

import junit.framework.TestCase;

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
		Concept concept = new Concept();
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
		
		Concept concept = new Concept();
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
		
		Concept concept = new Concept();
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
		
		Concept concept = new Concept();
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
		
		Concept concept = new Concept();
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
		
		Concept concept = new Concept();
		Method method = concept.getClass().getMethod("enumeratePresentation");
		MethodInvocation invocation = new MockMethodInvocation(method, null);	
		
		Object returnedObj = dataServiceProxyHelper.accountForCastorMethods(result, invocation);
		assertTrue(returnedObj instanceof Enumeration);
		
		Enumeration enumeration = (Enumeration)returnedObj;
		assertTrue(enumeration.hasMoreElements());
	}
}