/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
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
