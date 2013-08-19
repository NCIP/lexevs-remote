/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.dao.orm.selectionStrategy;

import gov.nih.nci.evs.security.SecurityToken;
import gov.nih.nci.system.dao.DAO;
import gov.nih.nci.system.dao.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.LexGrid.LexBIG.caCore.applicationservice.QueryOptions;
import org.LexGrid.LexBIG.caCore.connection.orm.utils.DBConnector;
import org.LexGrid.LexBIG.caCore.dao.orm.LexEVSDAO;
import org.LexGrid.LexBIG.caCore.security.Validator;

public class TokenSecurityStrategy implements DAOSelectionStrategy {

	private Validator validator;
	
	public List<LexEVSDAO> getDAOList(List<LexEVSDAO> daoList, Request request){
		return getDAOList(daoList, request, null);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.LexGrid.LexBIG.caCore.dao.orm.selectionStrategy.DAOSelectionStrategy#getDAOList(java.util.List, gov.nih.nci.system.dao.Request, java.util.HashMap, org.LexGrid.LexBIG.caCore.applicationservice.CodingSchemeVersionPair)
	 *
	 * CodingSchemeVersionPair is ignored
	 */
	public List<LexEVSDAO> getDAOList(List<LexEVSDAO> daoList, Request request, QueryOptions queryOptions){
		HashMap tokens = null;
		
		//if QueryOptions is null or doesn't contain any SecurityTokens, create an empty SecurityToken HashMap
		if(queryOptions == null){
			tokens = new HashMap();
		} else {
			tokens = queryOptions.getSecurityTokens();
			if(tokens == null){
				tokens = new HashMap();
			}
		}
		
		List<LexEVSDAO> usableDAOS = new ArrayList<LexEVSDAO>();
		
		for(LexEVSDAO dao : daoList){
			String urn = dao.getUri();
			
			if(validator.isSecured(urn)){
				//this vocab associated to this DAO is secured, check it....
				
				//get the SecurityToken for it, if there is one
				SecurityToken token = (SecurityToken)tokens.get(urn);
				
				//continue if the token is present
				if(token != null){
					boolean isValidToken = validator.validate(urn, token);
					if(isValidToken){
						usableDAOS.add(dao);
					}
				}
			} else {
				//this vocab wasn't secured -- add it to the "GOOD" list
				usableDAOS.add(dao);
			}
			
		}
		
		return usableDAOS;
	}

	public Validator getValidator() {
		return validator;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}
