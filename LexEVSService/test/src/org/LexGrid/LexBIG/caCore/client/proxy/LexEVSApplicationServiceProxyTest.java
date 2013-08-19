/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.client.proxy;

import java.lang.annotation.Annotation;

import org.LexGrid.LexBIG.caCore.applicationservice.QueryOptions;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSDataService;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSDistributed;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSService;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.codingSchemes.CodingScheme;
import org.hibernate.criterion.DetachedCriteria;

public class LexEVSApplicationServiceProxyTest extends ServiceTestCase
{
	private final String test_id = "Get Associations Tests";
	private LexEVSApplicationServiceProxy proxy;
	
	@Override
	protected String getTestID() {
		return test_id;	
	}
	
	public void setUp(){
		proxy = new LexEVSApplicationServiceProxy();	
	}
	
	public void testIsDataServiceLazyLoadableString1() throws Exception {
		Annotation[] annotations = CodingScheme.class.getMethod("getMappings", null).getAnnotations();
		assertFalse(proxy.isDataServiceLazyLoadable(new Object[]{annotations}));
	}
	
	public void testIsDataServiceLazyLoadable2() throws Exception {
		Annotation[] annotations = LexEVSDistributed.class.getMethod("getSupportedCodingSchemes", null).getAnnotations();
		assertFalse(proxy.isDataServiceLazyLoadable(new Object[]{annotations}));
	}
	
	public void testIsDataServiceLazyLoadable3() throws Exception {
		Annotation[] annotations = LexEVSService.class.getMethod("query", new Class[]{DetachedCriteria.class}).getAnnotations();
		assertFalse(proxy.isDataServiceLazyLoadable(new Object[]{annotations}));
	}
	
	public void testIsDataServiceLazyLoadable4() throws Exception {
		Annotation[] annotations = LexEVSDataService.class.getMethod("query", new Class[]{DetachedCriteria.class, QueryOptions.class}).getAnnotations();
		assertTrue(proxy.isDataServiceLazyLoadable(new Object[]{annotations}));
	}

}