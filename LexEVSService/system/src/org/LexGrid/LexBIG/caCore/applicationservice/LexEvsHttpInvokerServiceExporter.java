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
package org.LexGrid.LexBIG.caCore.applicationservice;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.LexGrid.LexBIG.Impl.helpers.ResolvedConceptReferencesIteratorImpl;
import org.LexGrid.LexBIG.caCore.applicationservice.resource.RemoteResourceManager;
import org.lexevs.locator.LexEvsServiceLocator;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.remoting.rmi.CodebaseAwareObjectInputStream;

import com.healthmarketscience.rmiio.SerializableInputStream;

/**
 * Ues the LexEVS Classloader (for loading extensions, SQL drivers, etc).
 * 
 * @author <a href="mailto:kevin.peterson@mayo.edu">Kevin Peterson</a>
 *
 */
public class LexEvsHttpInvokerServiceExporter extends HttpInvokerServiceExporter {

	private RemoteResourceManager remoteResourceManager;
	
	@Override
	protected ObjectInputStream createObjectInputStream(InputStream is)
			throws IOException {
		 return new ProxyWrappingObjectInputStream(is, LexEvsServiceLocator.getInstance().getSystemResourceService().getClassLoader(), null);
	}

	@Override
	protected ObjectOutputStream createObjectOutputStream(OutputStream os)
			throws IOException {
		return new ProxyWrappingObjectOutputStream(os);
	}

	private class ProxyWrappingObjectInputStream extends CodebaseAwareObjectInputStream {

		public ProxyWrappingObjectInputStream(InputStream in, ClassLoader classLoader, String codebaseUrl) throws IOException {
			super(in, classLoader, codebaseUrl);
			this.enableResolveObject(true);
		}

		@Override
		protected Object resolveObject(Object obj) throws IOException {
			return remoteResourceManager.unWrapShell(obj);
		}
	}	
	
	private class ProxyWrappingObjectOutputStream extends ObjectOutputStream {

		public ProxyWrappingObjectOutputStream(OutputStream os) throws IOException {
			super(os);
			this.enableReplaceObject(true);
		}

		
		
		/**
		 * We need to release the ResolvedConceptReferencesIteratorImpl before
		 * we can send it to the client, because there is no way of releasing
		 * it from the client side. It is safe to release at this point because
		 * the client will lose reference to this particular Iterator instance.
		 * 
		 *  (non-Javadoc)
		 * @see java.io.ObjectOutputStream#replaceObject(java.lang.Object)
		 */
		@Override
		protected Object replaceObject(Object obj) throws IOException {
			if(obj instanceof ResolvedConceptReferencesIteratorImpl){
				try {
					ResolvedConceptReferencesIteratorImpl itr = (ResolvedConceptReferencesIteratorImpl) obj;

					Object clone = itr.clone();
					
					itr.release();
					
					obj = clone;
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			else if (obj instanceof InputStream){
				final InputStream in = (InputStream) obj;
				obj = new SerializableInputStream(in);
//				obj = RemoteInputStreamServer.wrap(in);
			}
			return super.replaceObject(obj);
		}
	}

	public RemoteResourceManager getRemoteResourceManager() {
		return remoteResourceManager;
	}

	public void setRemoteResourceManager(RemoteResourceManager remoteResourceManager) {
		this.remoteResourceManager = remoteResourceManager;
	}	
}
