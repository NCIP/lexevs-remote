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

import java.lang.reflect.Field;
import java.util.List;

import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.annotations.LgClientSideSafe;
import org.LexGrid.annotations.LgHasRemoteDependencies;
import org.LexGrid.annotations.LgProxyField;

public class LexEVSProxyHelperImplTest extends ServiceTestCase{
	
	private LexEVSProxyHelperImpl proxyHelper;
	
	@Override
	protected String getTestID() {
		return "LexEVSProxyHelperImpl Tests";	
	}
	
	public void setUp(){
		proxyHelper = new LexEVSProxyHelperImpl();
	}

	public void testGetAnnotatedFieldHasField(){
		List<Field> fields = proxyHelper.getAnnotatedFields(new TestRemoteDependencies(), LgProxyField.class);	
		assertTrue(fields.size() == 1);	
		Field field = fields.get(0);
		assertTrue(field.getName().equals("annotatedField"));
	}
	
	public void testGetAnnotatedFieldNoField(){
		List<Field> fields = proxyHelper.getAnnotatedFields(new TestNoRemoteDependencies(), LgProxyField.class);	
		assertTrue(fields.size() == 0);
	}
	
	@LgHasRemoteDependencies
	@LgClientSideSafe
	private class TestRemoteDependencies {
		
		@LgProxyField
		private Object annotatedField;
		
		private Object nonAnnotatedField;
		
	}
	
	@LgClientSideSafe
	private class TestNoRemoteDependencies {}
}
