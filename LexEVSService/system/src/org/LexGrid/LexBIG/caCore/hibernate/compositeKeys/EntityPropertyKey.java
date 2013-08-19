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

public class EntityPropertyKey implements Serializable {

	private String codingSchemeName;
	private String entityCodeNamespace;
	private String entityCode;
	private String propertyId;
	
	public String getCodingSchemeName() {
		return codingSchemeName;
	}
	public void setCodingSchemeName(String codingSchemeName) {
		this.codingSchemeName = codingSchemeName;
	}
	public String getEntityCodeNamespace() {
		return entityCodeNamespace;
	}
	public void setEntityCodeNamespace(String entityCodeNamespace) {
		this.entityCodeNamespace = entityCodeNamespace;
	}
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
	public String getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((codingSchemeName == null) ? 0 : codingSchemeName.hashCode());
		result = prime * result
				+ ((entityCode == null) ? 0 : entityCode.hashCode());
		result = prime
				* result
				+ ((entityCodeNamespace == null) ? 0 : entityCodeNamespace
						.hashCode());
		result = prime * result
				+ ((propertyId == null) ? 0 : propertyId.hashCode());
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
		final EntityPropertyKey other = (EntityPropertyKey) obj;
		if (codingSchemeName == null) {
			if (other.codingSchemeName != null)
				return false;
		} else if (!codingSchemeName.equals(other.codingSchemeName))
			return false;
		if (entityCode == null) {
			if (other.entityCode != null)
				return false;
		} else if (!entityCode.equals(other.entityCode))
			return false;
		if (entityCodeNamespace == null) {
			if (other.entityCodeNamespace != null)
				return false;
		} else if (!entityCodeNamespace.equals(other.entityCodeNamespace))
			return false;
		if (propertyId == null) {
			if (other.propertyId != null)
				return false;
		} else if (!propertyId.equals(other.propertyId))
			return false;
		return true;
	}
	
	
	
}