/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.dao.orm.selectionStrategy.exceptions;

public class SelectionStrategyException extends Exception {
	public SelectionStrategyException(String message, Throwable ex){
		super(message, ex);
	}
	
	public SelectionStrategyException(String message){
		super(message);
	}
}
