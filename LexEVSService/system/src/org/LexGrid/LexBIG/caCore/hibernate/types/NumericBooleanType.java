/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.hibernate.types;

import org.hibernate.type.CharBooleanType;

public class NumericBooleanType extends CharBooleanType {

	@Override
	protected String getFalseString() {	
		return "0";
	}

	@Override
	protected String getTrueString() {
		return "1";
	}

}
