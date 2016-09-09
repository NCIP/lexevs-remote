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
import org.LexGrid.LexBIG.DataModel.Collections.CodingSchemeRenderingList;
import org.LexGrid.LexBIG.DataModel.InterfaceElements.CodingSchemeRendering;
import org.LexGrid.LexBIG.Exceptions.LBInvocationException;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSDistributed;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSService;

import gov.nih.nci.system.client.ApplicationServiceProvider;

public class LocalTestClient {
	
	private String serviceUrl = "http://localhost:8080/lexevsapi50";
	private LexEVSService lexevsService;
	
	public static void main(String args[]) throws Exception {
		LocalTestClient client = new LocalTestClient();
	
		client.queryLexEVSDistributed();
//		client.queryLexEVSDataService();
	}
	
	public LocalTestClient() throws Exception {
		lexevsService = (LexEVSService)ApplicationServiceProvider.getApplicationServiceFromUrl(serviceUrl);
	}
	
	public void queryLexEVSDistributed() throws LBInvocationException {
		LexEVSDistributed distributedSvc = lexevsService;	
		CodingSchemeRenderingList csrl = distributedSvc.getSupportedCodingSchemes();	
		CodingSchemeRendering[] csr = csrl.getCodingSchemeRendering();

		for(int i=0;i<csr.length;i++){

			System.out.println("\t\tOutput: " + "Coding Scheme: " + csr[i].getCodingSchemeSummary().getLocalName());
			System.out.println("\t\tOutput: " + "  -- Version: " + csr[i].getCodingSchemeSummary().getRepresentsVersion());
			System.out.println("\t\tOutput: " + "  -- URI: " + csr[i].getCodingSchemeSummary().getCodingSchemeURI());		
		}
	}
	
//	public void queryLexEVSDataService() throws ApplicationException {
//		LexEVSDataService dataSvc = lexevsService;
//		CodingScheme codingScheme = new CodingScheme();		
//		List<CodingScheme> results = dataSvc.search(CodingScheme.class, codingScheme);
//		
//		for(CodingScheme cs : results){
//			System.out.println("\t\tOutput: " + "Coding Scheme: " + cs.getLocalName());
//			System.out.println("\t\tOutput: " + "  -- Version: " + cs.getRepresentsVersion());
//			System.out.println("\t\tOutput: " + "  -- URI: " + cs.getCodingSchemeURI());		
//		}
//	}
}