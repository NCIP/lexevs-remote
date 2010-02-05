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
package org.LexGrid.LexBIG.caCore.dao.orm;

import org.LexGrid.LexBIG.DataModel.Collections.CodingSchemeTagList;

import gov.nih.nci.system.dao.DAO;
import gov.nih.nci.system.dao.DAOException;
import gov.nih.nci.system.dao.Request;
import gov.nih.nci.system.dao.Response;

public interface LexEVSDAO extends DAO {
	public enum DAOType { CODING_SCHEME, HISTORY }
	
	/**
	 * Get the Version of the CodingScheme associated with this DAO.
	 * 
	 * @return The CodingScheme Version.
	 */
	public String getVersion();
	
	
	/**
	 * Get the URI of the CodingScheme associated with this DAO.
	 * 
	 * @return
	 */
	public String getUri();
	
	/**
	 * Get the TagList of the CodingScheme associated with this DAO.
	 * 
	 * @return The TagList associated with this CodingScheme.
	 */
	public CodingSchemeTagList getTagList();
	
	/**
	 * Gets the Type of resource this DAO represents -- either a History or CodingScheme resource.
	 * 
	 * @return The resource type.
	 */
	public DAOType getDaoType();
	
	/**
	 * Returns the default maximum per query size.
	 * 
	 * @return Maximum results returned per query.
	 */
	
	public int getResultCountPerQuery();
	
	/**
	 * Query this DAO.
	 * 
	 * @param request The Request to query.
	 * @param initAll Initialize all the results (false to allow Lazy Loading, true to disable Lazy Loading).
	 * @param maxResults Override the maxium results per query for this query.
	 * 
	 * @return The results of the query.
	 * @throws DAOException
	 * @throws Exception
	 */
	public Response query(Request request, boolean initAll, int maxResults) throws DAOException, Exception;
}
