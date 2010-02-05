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
import java.util.Arrays;
import java.util.List;

import gov.nih.nci.system.applicationservice.ApplicationService;

import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.caCore.applicationservice.QueryOptions;
import org.LexGrid.LexBIG.caCore.connection.orm.interceptors.EVSHibernateInterceptor;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
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
import org.apache.commons.lang.ArrayUtils;

import junit.framework.TestCase;

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
		Concept concept = new Concept();
		Method method = concept.getClass().getMethod("getPresentation", null);	
		assertTrue(dataServiceProxyHelper.isLazyLoadableMethod(method));
		
	}

	public void testIsLazyLoadable2() throws Exception {
		Concept concept = new Concept();
		Method method = concept.getClass().getMethod("getPresentationCount", null);	
		assertTrue(dataServiceProxyHelper.isLazyLoadableMethod(method));
		
	}
	
	public void testIsLazyLoadable3() throws Exception {
		Concept concept = new Concept();
		Method method = concept.getClass().getMethod("getDefinition", null);	
		assertTrue(dataServiceProxyHelper.isLazyLoadableMethod(method));
		
	}
	
	public void testIsLazyLoadable4() throws Exception {
		Concept concept = new Concept();
		Method method = concept.getClass().getMethod("enumeratePresentation", null);	
		assertTrue(dataServiceProxyHelper.isLazyLoadableMethod(method));		
	}
	
	public void testIsLazyLoadable5() throws Exception {
		Concept concept = new Concept();
		Method method = concept.getClass().getMethod("iterateComment", null);	
		assertTrue(dataServiceProxyHelper.isLazyLoadableMethod(method));		
	}
	
	public void testIsLazyLoadable6() throws Exception {
		Concept concept = new Concept();
		Method method = concept.getClass().getMethod("setPresentation", Presentation[].class);	
		assertFalse(dataServiceProxyHelper.isLazyLoadableMethod(method));		
	}
	
	public void testIsLazyLoadable7() throws Exception {
		Concept concept = new Concept();
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
		Concept concept = new Concept();
		Method method = concept.getClass().getMethod("getPresentation", int.class);	
		assertTrue(dataServiceProxyHelper.isLazyLoadableMethod(method));		
	}
	
	public void testIsLazyLoadable11() throws Exception {
		Concept concept = new Concept();
		Method method = concept.getClass().getMethod("setPresentation", int.class, Presentation.class);	
		assertFalse(dataServiceProxyHelper.isLazyLoadableMethod(method));		
	}
	
	
}