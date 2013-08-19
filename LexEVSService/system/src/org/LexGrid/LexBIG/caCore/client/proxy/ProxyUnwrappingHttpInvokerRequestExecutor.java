/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.client.proxy;

import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.client.proxy.ProxyHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;

import net.sf.cglib.proxy.Enhancer;

import org.LexGrid.LexBIG.caCore.utils.LexEVSCaCoreUtils;
import org.LexGrid.annotations.LgProxyClass;
import org.acegisecurity.context.httpinvoker.AuthenticationSimpleHttpInvokerRequestExecutor;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.remoting.rmi.CodebaseAwareObjectInputStream;
import org.springframework.remoting.support.RemoteInvocation;

public class ProxyUnwrappingHttpInvokerRequestExecutor extends AuthenticationSimpleHttpInvokerRequestExecutor implements ApplicationContextAware {

	private ApplicationContext applicationContext;
	private ProxyHelper proxyHelper;
	
	protected void writeRemoteInvocation(RemoteInvocation invocation, OutputStream os) throws IOException {
		ObjectOutputStream oos = new ProxyUnwrappingObjectStream(decorateOutputStream(os));
		try {
			doWriteRemoteInvocation(invocation, oos);
			oos.flush();
		}
		finally {
			oos.close();
		}
	}

	protected ObjectInputStream createObjectInputStream(InputStream is, String codebaseUrl) throws IOException {
		return new ProxyWrappingObjectStream(is, getBeanClassLoader(), codebaseUrl);
	}
	
	private class ProxyWrappingObjectStream extends CodebaseAwareObjectInputStream {

		public ProxyWrappingObjectStream(InputStream in, ClassLoader classLoader, String codebaseUrl) throws IOException {
			super(in, classLoader, codebaseUrl);
			this.enableResolveObject(true);
		}
		
		protected Object resolveObject(Object obj){
			if(obj.getClass().isAnnotationPresent(LgProxyClass.class)){
				Object proxy = LexEVSCaCoreUtils.createProxy(obj, getApplicationService(), proxyHelper);
				return proxy;
			} else {
				return obj;
			}
		}
	}
	
	private class ProxyUnwrappingObjectStream extends ObjectOutputStream {

		public ProxyUnwrappingObjectStream(OutputStream out) throws IOException {
			super(out);
			this.enableReplaceObject(true);
		}

		protected Object replaceObject(Object obj)
        throws IOException {
			Class<?> clazz = obj.getClass();
			if(obj != null && 
				LexEVSCaCoreUtils.isLexBigClass(clazz) &&
					Enhancer.isEnhanced(clazz)){
				try {
					return unwrap(obj);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			} else {
				return obj;
			}
		}
		
	    private Object unwrap(Object proxy) throws Exception {
	        Object interceptor = null;
	        int i = 0;
	        while (true) {
	            Field field = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_"+i);
	            field.setAccessible(true);
	            Object value = field.get(proxy);
	            if (value.getClass().getName().contains("EqualsInterceptor")) {
	                interceptor = value;
	                break;
	            }
	            i++;
	        }

	        Field field = interceptor.getClass().getDeclaredField("advised");
	        field.setAccessible(true);
	        Advised advised = (Advised)field.get(interceptor);
	        Object realObject = advised.getTargetSource().getTarget();
	        return realObject;
	    }

		
		
	}

	public ProxyHelper getProxyHelper() {
		return proxyHelper;
	}

	public void setProxyHelper(ProxyHelper proxyHelper) {
		this.proxyHelper = proxyHelper;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
		
	}
	
	private ApplicationService getApplicationService(){
		ApplicationService appService = (ApplicationService)applicationContext.getBean("LexEVSApplicationService");
		return appService;
	}
}
