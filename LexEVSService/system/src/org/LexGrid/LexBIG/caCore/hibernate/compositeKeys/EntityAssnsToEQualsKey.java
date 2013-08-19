/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.hibernate.compositeKeys;

import java.io.Serializable;

public class EntityAssnsToEQualsKey implements Serializable {	
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
		final EntityAssnsToEQualsKey other = (EntityAssnsToEQualsKey) obj;
		if (multiAttributesKey == null) {
			if (other.multiAttributesKey != null)
				return false;
		} else if (!multiAttributesKey.equals(other.multiAttributesKey))
			return false;
		return true;
	}
	
	
}
