/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.dao.orm.translators;

import gov.nih.nci.system.applicationservice.ApplicationException;

public class TranslationException extends ApplicationException {

	public TranslationException(String message){
		super(message);
	}
	
	public TranslationException(String translatorName, Throwable e){
		super("Error occured in translator: " + translatorName, e);
	}
}
