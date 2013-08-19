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