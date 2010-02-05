/*******************************************************************************
 * Copyright: (c) 2004-2009 Mayo Foundation for Medical Education and 
 * Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
 * triple-shield Mayo logo are trademarks and service marks of MFMER.
 * 
 * Except as contained in the copyright notice above, or as used to identify 
 * MFMER as the author of this software, the trade names, trademarks, service
 * marks, or product names of the copyright holder shall not be used in
 * advertising, promotion or otherwise in connection with this software without
 * prior written authorization of the copyright holder.
 *   
 * Licensed under the Eclipse Public License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *   
 *  		http://www.eclipse.org/legal/epl-v10.html
 * 
 *  		
 *******************************************************************************/
package org.LexGrid.LexBIG.testUtil;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

public class MockMethodInvocation implements MethodInvocation {

	private Method method;
	private Object[] arguments;
	private Object this_;
	
	public MockMethodInvocation(Method method, Object[] arguments){
		this.method = method;
		this.arguments = arguments;
	}
	
	public MockMethodInvocation(Method method, Object[] arguments, Object this_){
		this.method = method;
		this.arguments = arguments;
		this.this_ = this_;
	}
	
	public Method getMethod() {
		return method;
	}

	public Object[] getArguments() {
		return arguments;
	}

	public AccessibleObject getStaticPart() {
		 throw new UnsupportedOperationException();
	}

	public Object getThis() {
		 return this_;
	}

	public Object proceed() throws Throwable {
		 throw new UnsupportedOperationException();
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public void setArguments(Object[] arguments) {
		this.arguments = arguments;
	}

	public void setThis(Object this_) {
		this.this_ = this_;
	}

}
