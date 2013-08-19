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

public class ConceptsKey implements Serializable {
	
	private String codingSchemeName;
	
	public String getCodingSchemeName() {
		return codingSchemeName;
	}
	public void setCodingSchemeName(String codingSchemeId) {
		this.codingSchemeName = codingSchemeId;
	}
	
	public boolean equals(Object o){
		ConceptsKey key = (ConceptsKey)o;
		if(key.codingSchemeName.equals(this.codingSchemeName)){
			return true;
		} else {
			return false;
		}	
	}
	
	public int hashCode(){
		return this.codingSchemeName.hashCode();
	}
	
}