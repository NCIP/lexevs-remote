/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.dao.orm.selectionStrategy;

import gov.nih.nci.system.dao.Request;

import java.util.ArrayList;
import java.util.List;

import org.LexGrid.LexBIG.DataModel.Collections.CodingSchemeTagList;
import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.Exceptions.LBParameterException;
import org.LexGrid.LexBIG.caCore.applicationservice.QueryOptions;
import org.LexGrid.LexBIG.caCore.connection.orm.utils.DBConnector;
import org.LexGrid.LexBIG.caCore.dao.orm.LexEVSDAO;
import org.LexGrid.LexBIG.caCore.dao.orm.LexEVSDAO.DAOType;
import org.LexGrid.LexBIG.caCore.dao.orm.selectionStrategy.exceptions.SelectionStrategyException;
import org.apache.log4j.Logger;

public class CodingSchemeSelectionStrategy implements DAOSelectionStrategy {
	private static String PRODUCTION_TAG = "PRODUCTION";
	private DBConnector dbConnector;
	private static Logger log = Logger.getLogger(CodingSchemeSelectionStrategy.class.getName());
	
	public List<LexEVSDAO> getDAOList(List<LexEVSDAO> daoList, Request request){
		log.warn("Either Coding Scheme Name or Version weren't specified, this search will" +
		" query every loaded Coding Scheme!");
		return daoList;
	}
	
	public List<LexEVSDAO> getDAOList(List<LexEVSDAO> daoList, Request request,
			QueryOptions queryOptions) throws SelectionStrategyException{
		//if there are no QueryOptions, we can't make any restrictions
		if(queryOptions == null){
			return daoList;
		}
		
		String csName = queryOptions.getCodingScheme();
		//if the QueryOptions don't include a CodingSchemeName, we still can't
		//make any restrictions
		if(csName == null || csName.equals("")){
			return daoList;
		}
		
		return restrictToCodingScheme(daoList, csName, queryOptions.getCodingSchemeVersionOrTag());
	}
	
	private List<LexEVSDAO> restrictToCodingScheme(List<LexEVSDAO> list, String csName, CodingSchemeVersionOrTag tagOrVersion) throws SelectionStrategyException {
		String urn = null;
		try{
			urn = dbConnector.getURIFromCodingSchemeName(csName, tagOrVersion);
		} catch (LBParameterException e) {
			log.warn("Didn't find CodingScheme/History for CodingScheme Name: " + csName + " " + e.getMessage());
			throw new SelectionStrategyException("Didn't find CodingScheme/History for CodingScheme Name: " + csName, e);
		}
		
		boolean isActive = false;
		try {
			isActive = dbConnector.isCodingSchemeActive(csName, tagOrVersion);
		} catch (LBParameterException e) {
			throw new SelectionStrategyException("Error validating Coding Scheme: " + csName, e);
		}
		
		if(!isActive){
			throw new SelectionStrategyException("Cannot specify an inactive Coding Scheme: " + csName);
		}
		
		List<LexEVSDAO> returnList = new ArrayList<LexEVSDAO>();
		for(LexEVSDAO dao : list){
			//Filter by URN
			if(dao.getUri().equals(urn)){
				//If its a History DAO, don't chech the version, just add it to the return list.
				if(dao.getDaoType().equals(DAOType.HISTORY)){
					returnList.add(dao);
				} else {
					//If its a CodingScheme DAO, check the version.
					if(tagOrVersion == null){
						CodingSchemeTagList cstl = dao.getTagList();
						if(containsTag(cstl, PRODUCTION_TAG)){
							returnList.add(dao);
						}	
					} else {
						if(dao.getVersion().equals(tagOrVersion.getVersion())){
							returnList.add(dao);
						}
					}
				}
			}
		}
		return returnList;
	}
	
	private boolean containsTag(CodingSchemeTagList tagList, String tagName){
		String[] tags = tagList.getTag();
		for(String tag : tags){
			if(tag.equals(tagName)){
				return true;
			}
		}
		return false;
	}
	
	public DBConnector getDbConnector() {
		return dbConnector;
	}

	public void setDbConnector(DBConnector dbConnector) {
		this.dbConnector = dbConnector;
	}

}

