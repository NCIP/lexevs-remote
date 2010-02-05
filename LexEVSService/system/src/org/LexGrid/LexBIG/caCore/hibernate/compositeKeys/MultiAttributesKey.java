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
package org.LexGrid.LexBIG.caCore.hibernate.compositeKeys;

import java.io.Serializable;

public class MultiAttributesKey implements Serializable {

	private String multiAttributesKey;

	public String getMultiAttributesKey() {
		return multiAttributesKey;
	}

	public void setMultiAttributesKey(String multiAttributesKey) {
		this.multiAttributesKey = multiAttributesKey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((multiAttributesKey == null) ? 0 : multiAttributesKey
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final MultiAttributesKey other = (MultiAttributesKey) obj;
		if (multiAttributesKey == null) {
			if (other.multiAttributesKey != null)
				return false;
		} else if (!multiAttributesKey.equals(other.multiAttributesKey))
			return false;
		return true;
	}
	
	
}
