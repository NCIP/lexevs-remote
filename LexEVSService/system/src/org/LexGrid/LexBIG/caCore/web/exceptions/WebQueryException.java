/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.web.exceptions;

public class WebQueryException extends Exception {

	public WebQueryException(String message, Throwable e){
		super(message, e);
	}
	
	public WebQueryException(String message){
		super(message);
	}
}
