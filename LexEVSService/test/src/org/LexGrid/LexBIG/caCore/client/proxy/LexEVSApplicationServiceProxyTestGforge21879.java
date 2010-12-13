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
	
	public void testGforge21879Adam() throws Exception {
		
		lexevsService = LexEVSServiceHolder.instance().getLexEVSAppService();

		CodedNodeSet cns = lexevsService.getCodingSchemeConcepts("NCI Metathesaurus", null);
		cns = cns.restrictToMatchingDesignations(".*ene.*", SearchDesignationOption.ALL, "RegExp", null);

		boolean isLexEx = false;
		try {
			ResolvedConceptReference[] list = null;			
		    list = cns.resolveToList(null, null, null, 500).getResolvedConceptReference();
		} catch (Exception ex) {
			isLexEx = isLexBigException(ex.getCause());
		}		
		assertTrue(isLexEx);
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