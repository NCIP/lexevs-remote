/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.client.proxy;

import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.SearchDesignationOption;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

public class LexEVSApplicationServiceProxyTestGforge21879 extends ServiceTestCase
{
	private final String test_id = "Tests for GForge item 21879";
	private LexEVSService lexevsService;
	
	@Override
	protected String getTestID() {
		return test_id;	
	}

	public void testGforge21879Boy() {
		boolean isLexEx = false;
		try {
			Exception ex = LexEVSApplicationServiceProxy.digOutRealExceptionAndThrowIt(new NullPointerException("21879Boy"));
			isLexEx = isLexBigException(ex);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertFalse(isLexEx);		
	}
	
	public void testGforge21879Charlie() {
		boolean isLexEx = false;
		try {
			Exception ex = LexEVSApplicationServiceProxy.digOutRealExceptionAndThrowIt(new LBException("Unexpected Internal Error"));
			isLexEx = isLexBigException(ex);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(isLexEx);
	}
	
	private boolean isLexBigException(Throwable th) {
		boolean rv = false;
		if(th.toString().indexOf("org.LexGrid.LexBIG.Exceptions") != -1) {
			if(th.getMessage().indexOf("Unexpected Internal Error") != -1) {
				rv = true;
			}
		} 
		return rv;
	}	
}