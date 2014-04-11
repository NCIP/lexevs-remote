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
import java.net.URL;
import java.rmi.RemoteException;

import org.LexGrid.LexBIG.caCore.webservice.client.LexEVSWSQueryImpl;
import org.LexGrid.LexBIG.caCore.webservice.client.LexEVSWSQueryImplServiceLocator;
import org.LexGrid.codingSchemes.CodingScheme;


public class WSTestClient{

	private String serviceEndPoint = "http://localhost:8080/lexevsapi50/services/lexevsapi50Service";
	private LexEVSWSQueryImpl lexevsWebService;
	
	public static void main(String args[]) throws Exception {
		WSTestClient client = new WSTestClient();
	
		client.queryLexEVSWebService();
	}
	
	public WSTestClient() throws Exception {
		LexEVSWSQueryImplServiceLocator locator = new LexEVSWSQueryImplServiceLocator();
		lexevsWebService = locator.getlexevsapi61Service(new URL(serviceEndPoint));
	}
	
	public void queryLexEVSWebService() throws RemoteException {
		CodingScheme codingScheme = new CodingScheme();		
		Object[] results = lexevsWebService.queryObject(CodingScheme.class.getName(), codingScheme);
		
		for(Object obj : results){
			CodingScheme cs = (CodingScheme)obj;
			System.out.println("\t\tOutput: " + "Coding Scheme: " + cs.getLocalName());
			System.out.println("\t\tOutput: " + "  -- Version: " + cs.getRepresentsVersion());
			System.out.println("\t\tOutput: " + "  -- URI: " + cs.getCodingSchemeURI());		
		}
	}

}
