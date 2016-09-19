/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.applicationservice.impl;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.system.dao.Response;

/**
 * Class to enable pagination over multiple Coding Schemes (databases).
 * 
 * @author <a href="mailto:kevin.peterson@mayo.edu">Kevin Peterson</a>
 */
public class PaginationHelper {
	LexEVSApplicationServiceImpl lexEVSApplicationService;
	int maxResultsPerQuery;

	/**
	 * Return the results (Correctly paged) from multipe databases.
	 * 
	 * @param request The Request being processed
	 * @param daoList List of available DAOs
	 * @param maxToReturn Maximum results to return per query, i.e. page size
	 * @return Paged results
	 * @throws Exception
	 */
//	public Response getResponseFromMultipleCodingSchemeQuery(Request request, List<LexEVSDAO> daoList, int maxToReturn) throws Exception {
//		//This has not been narrowed down, we must make sure we return the right amount
//		//of results across all Coding Schemes.
//		int startRow = request.getFirstRow();
//		int skippedRows = 0;
//		int returnedResults = 0;
//		
//		if(maxToReturn < 0){
//			maxToReturn = maxResultsPerQuery;
//		}
//
//		List<Response> totalResponses = new ArrayList<Response>();
//
//		Object criteria = request.getRequest();
//
//		for (LexEVSDAO dao : daoList) {		
//			//only keep looking for more results if we haven't hit the max results per query number
//			if(returnedResults < maxToReturn){
//				int resultsInDao = getResultsCountInIndividualDAOForQuery(criteria, dao);
//				
//				//don't start getting results until we reach the start row
//				if(resultsInDao + skippedRows > startRow){
//					//now we're in the DAO we want to start with
//					Response daoResponse = queryIndividualDAO(criteria, dao, startRow - skippedRows, 
//							maxToReturn);
//					totalResponses.add(daoResponse);
//					int returnedRows = daoResponse.getRowCount();
//					returnedResults += returnedRows;		
//				} else {
//					skippedRows += resultsInDao;
//				}
//			}
//		}
//		return combineIndividualDAOResponses(totalResponses);			
//	}
	

	
	/**
	 * Get the results in a given DAO for a given Criteria Query.
	 * 
	 * @param criteria The Criteria to query.
	 * @param dao DAO used with this Criteria
	 * @param startRow The row to start the Query on.
	 * @param maxToReturn Page size
	 * @return Paged results
	 * @throws ApplicationException
	 */
//	private Response queryIndividualDAO(Object criteria, LexEVSDAO dao, int startRow, int maxToReturn) throws ApplicationException {
//		try {
//			Request eachDaoRequest = new Request(criteria);
//			eachDaoRequest.setIsCount(false);
//			eachDaoRequest.setFirstRow(startRow);
//			Response eachDaoResponse = lexEVSApplicationService.query(eachDaoRequest, dao, false, maxToReturn);
//			return eachDaoResponse;
//		} catch (Exception e) {
//			throw new ApplicationException(e);
//		}
//	}
	
	/**
	 * Combine the responses from multipe DAOs into one Response.
	 * 
	 * @param responses Individual responses
	 * @return Combined responses
	 */
	private Response combineIndividualDAOResponses(List<Response> responses){
		Response totalResponse = new Response();
		List totalResults = new ArrayList();
		for (Response response : responses) {
			List result = (List)response.getResponse();
			totalResults.addAll(result);
		}
		totalResponse.setResponse(totalResults);
		totalResponse.setRowCount(totalResults.size());
		return totalResponse;
	}

	public LexEVSApplicationServiceImpl getLexEVSApplicationService() {
		return lexEVSApplicationService;
	}

	public void setLexEVSApplicationService(
			LexEVSApplicationServiceImpl lexEVSApplicationService) {
		this.lexEVSApplicationService = lexEVSApplicationService;
	}

	public int getMaxResultsPerQuery() {
		return maxResultsPerQuery;
	}

	public void setMaxResultsPerQuery(int maxResultsPerQuery) {
		this.maxResultsPerQuery = maxResultsPerQuery;
	}
	
}
