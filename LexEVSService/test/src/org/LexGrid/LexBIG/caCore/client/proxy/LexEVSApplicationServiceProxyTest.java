/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.client.proxy;

import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Exceptions.LBParameterException;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

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
	
//	public void testIsDataServiceLazyLoadableString1() throws Exception {
//		Annotation[] annotations = CodingScheme.class.getMethod("getMappings", null).getAnnotations();
//		assertFalse(proxy.isDataServiceLazyLoadable(new Object[]{annotations}));
//	}
//	
//	public void testIsDataServiceLazyLoadable2() throws Exception {
//		Annotation[] annotations = LexEVSDistributed.class.getMethod("getSupportedCodingSchemes", null).getAnnotations();
//		assertFalse(proxy.isDataServiceLazyLoadable(new Object[]{annotations}));
//	}
//	
//	public void testIsDataServiceLazyLoadable3() throws Exception {
//		Annotation[] annotations = LexEVSService.class.getMethod("query", new Class[]{DetachedCriteria.class}).getAnnotations();
//		assertFalse(proxy.isDataServiceLazyLoadable(new Object[]{annotations}));
//	}
//	
//	public void testIsDataServiceLazyLoadable4() throws Exception {
//		Annotation[] annotations = LexEVSDataService.class.getMethod("query", new Class[]{DetachedCriteria.class, QueryOptions.class}).getAnnotations();
//		assertTrue(proxy.isDataServiceLazyLoadable(new Object[]{annotations}));
//	}
	
	public void testFindLBParameterException() throws Exception {
		
		LBParameterException lbParameterException = new LBParameterException("This is an LBParameterException test");
		Exception exception = new Exception("Exception with embedded LBParameterException", lbParameterException);
		
		Exception returnException = LexEVSApplicationServiceProxy.digOutRealExceptionAndThrowIt(exception);
		
		assertTrue(returnException instanceof LBParameterException);
	}
	
	public void testFindLBException() throws Exception {
		
		LBException lbException = new LBException("This is an LBException test");
		Exception exception = new Exception("Exception with embedded LBException", lbException);
		
		Exception returnException = LexEVSApplicationServiceProxy.digOutRealExceptionAndThrowIt(exception);
		
		assertTrue(returnException instanceof LBException);
	}

}