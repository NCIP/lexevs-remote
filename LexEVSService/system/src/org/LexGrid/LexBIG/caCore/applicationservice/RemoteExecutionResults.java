/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.applicationservice;

import java.io.Serializable;

/**
 * Enables server to return not only the results of a remotely-executed method, but the
 * state of the underlying object as well, enabling the Proxy to be updated on the client.
 *
 * @author <a href="mailto:kevin.peterson@mayo.edu">Kevin Peterson</a>
 */

public class RemoteExecutionResults implements Serializable  {
	private Object obj;
    private Object returnValue;
    
	public RemoteExecutionResults(Object obj, Object results) {
		this.obj = obj;
		this.returnValue = results;
	}
	
	public Object getObj() {
		return obj;
	}
	
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public Object getReturnValue() {
		return returnValue;
	}
	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}


}
