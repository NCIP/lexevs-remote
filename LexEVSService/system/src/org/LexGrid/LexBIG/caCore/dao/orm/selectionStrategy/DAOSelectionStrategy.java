/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.dao.orm.selectionStrategy;

import java.util.List;

import org.LexGrid.LexBIG.caCore.applicationservice.QueryOptions;
import org.LexGrid.LexBIG.caCore.dao.orm.LexEVSDAO;
import org.LexGrid.LexBIG.caCore.dao.orm.selectionStrategy.exceptions.SelectionStrategyException;

import gov.nih.nci.system.dao.Request;

public interface DAOSelectionStrategy {

	/**
	 * Given a Request, Determine the DAOs to use.
	 * 
	 * @param daoList
	 * @param request
	 * @return the list of usable DAOs.
	 * @throws SelectionStrategyException
	 */
	public List<LexEVSDAO> getDAOList(List<LexEVSDAO> daoList, Request request) throws SelectionStrategyException;

	/**
	 * Given a Request, Determine the DAOs to use (with SecurityTokens).
	 * 
	 * @param daoList
	 * @param request
	 * @param queryOptions
	 * @return the list of usable DAOs.
	 * @throws SelectionStrategyException
	 */
	public List<LexEVSDAO> getDAOList(List<LexEVSDAO> daoList, Request request, QueryOptions queryOptions) throws SelectionStrategyException;

}
