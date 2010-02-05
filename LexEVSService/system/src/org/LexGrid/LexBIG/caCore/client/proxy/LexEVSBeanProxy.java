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
package org.LexGrid.LexBIG.caCore.client.proxy;

import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.client.proxy.BeanProxy;
import gov.nih.nci.system.client.proxy.ProxyHelper;

import org.aopalliance.intercept.MethodInvocation;

/**
 * LexEVS specific implementation of a BeanProxy.
 * 
 * @author <a href="mailto:kevin.peterson@mayo.edu">Kevin Peterson</a>
 */
public class LexEVSBeanProxy extends BeanProxy
{
	ApplicationService as;
	ProxyHelper proxyHelper;
	
	public LexEVSBeanProxy(ApplicationService as, ProxyHelper proxyHelper)
	{
		super(as, proxyHelper);
		this.as = as;
		this.proxyHelper = proxyHelper;
	}
	/*
	 * (non-Javadoc)
	 * @see gov.nih.nci.system.client.proxy.BeanProxy#invoke(org.aopalliance.intercept.MethodInvocation)
	 * 
	 * This changed from 4.0 to 4.1 in the SDK. We need the old implementation.
	 */	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
	    if(!proxyHelper.isInitialized(invocation))
	    	return proxyHelper.lazyLoad(as,invocation);
	    else
	    	return invocation.proceed();
	}
}