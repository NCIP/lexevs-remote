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

public class EntityAssnsToEntityKey implements Serializable {

	private String codingSchemeName;
	private String containerName;
	private String entityCodeNamespace;
	private String entityCode;
	private String sourceEntityCodeNamespace;
	private String sourceEntityCode;
	private String targetEntityCodeNamespace;	
	private String targetEntityCode;
	
	public String getCodingSchemeName() {
		return codingSchemeName;
	}
	public void setCodingSchemeName(String codingSchemeName) {
		this.codingSchemeName = codingSchemeName;
	}
	public String getContainerName() {
		return containerName;
	}
	public void setContainerName(String containerName) {
		this.containerName = containerName;
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
	public String getSourceEntityCodeNamespace() {
		return sourceEntityCodeNamespace;
	}
	public void setSourceEntityCodeNamespace(String sourceEntityCodeNamespace) {
		this.sourceEntityCodeNamespace = sourceEntityCodeNamespace;
	}
	public String getSourceEntityCode() {
		return sourceEntityCode;
	}
	public void setSourceEntityCode(String sourceEntityCode) {
		this.sourceEntityCode = sourceEntityCode;
	}
	public String getTargetEntityCodeNamespace() {
		return targetEntityCodeNamespace;
	}
	public void setTargetEntityCodeNamespace(String targetEntityCodeNamespace) {
		this.targetEntityCodeNamespace = targetEntityCodeNamespace;
	}
	public String getTargetEntityCode() {
		return targetEntityCode;
	}
	public void setTargetEntityCode(String targetEntityCode) {
		this.targetEntityCode = targetEntityCode;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((codingSchemeName == null) ? 0 : codingSchemeName.hashCode());
		result = prime * result
				+ ((containerName == null) ? 0 : containerName.hashCode());
		result = prime * result
				+ ((entityCode == null) ? 0 : entityCode.hashCode());
		result = prime
				* result
				+ ((entityCodeNamespace == null) ? 0 : entityCodeNamespace
						.hashCode());
		result = prime
				* result
				+ ((sourceEntityCode == null) ? 0 : sourceEntityCode.hashCode());
		result = prime
				* result
				+ ((sourceEntityCodeNamespace == null) ? 0
						: sourceEntityCodeNamespace.hashCode());
		result = prime
				* result
				+ ((targetEntityCode == null) ? 0 : targetEntityCode.hashCode());
		result = prime
				* result
				+ ((targetEntityCodeNamespace == null) ? 0
						: targetEntityCodeNamespace.hashCode());
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
		final EntityAssnsToEntityKey other = (EntityAssnsToEntityKey) obj;
		if (codingSchemeName == null) {
			if (other.codingSchemeName != null)
				return false;
		} else if (!codingSchemeName.equals(other.codingSchemeName))
			return false;
		if (containerName == null) {
			if (other.containerName != null)
				return false;
		} else if (!containerName.equals(other.containerName))
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
		if (sourceEntityCode == null) {
			if (other.sourceEntityCode != null)
				return false;
		} else if (!sourceEntityCode.equals(other.sourceEntityCode))
			return false;
		if (sourceEntityCodeNamespace == null) {
			if (other.sourceEntityCodeNamespace != null)
				return false;
		} else if (!sourceEntityCodeNamespace
				.equals(other.sourceEntityCodeNamespace))
			return false;
		if (targetEntityCode == null) {
			if (other.targetEntityCode != null)
				return false;
		} else if (!targetEntityCode.equals(other.targetEntityCode))
			return false;
		if (targetEntityCodeNamespace == null) {
			if (other.targetEntityCodeNamespace != null)
				return false;
		} else if (!targetEntityCodeNamespace
				.equals(other.targetEntityCodeNamespace))
			return false;
		return true;
	}
}
