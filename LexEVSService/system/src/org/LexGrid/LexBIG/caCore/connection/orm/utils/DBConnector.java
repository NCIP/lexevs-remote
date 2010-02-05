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
package org.LexGrid.LexBIG.caCore.connection.orm.utils;

import java.util.ArrayList;
import java.util.HashSet;

import org.LexGrid.LexBIG.DataModel.Collections.CodingSchemeRenderingList;
import org.LexGrid.LexBIG.DataModel.Collections.CodingSchemeTagList;
import org.LexGrid.LexBIG.DataModel.Core.AbsoluteCodingSchemeVersionReference;
import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeSummary;
import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.DataModel.InterfaceElements.CodingSchemeRendering;
import org.LexGrid.LexBIG.Exceptions.LBInvocationException;
import org.LexGrid.LexBIG.Exceptions.LBParameterException;
import org.LexGrid.LexBIG.Impl.LexBIGServiceImpl;
import org.LexGrid.LexBIG.Impl.dataAccess.ResourceManager;
import org.LexGrid.LexBIG.Impl.helpers.SQLConnectionInfo;
import org.LexGrid.LexBIG.caCore.applicationservice.impl.LexEVSApplicationServiceImpl;
import org.LexGrid.LexBIG.caCore.security.properties.LexEVSProperties;
import org.apache.log4j.Logger;

/**
 * The junction point between the LexEVSAPI and the local LexBIG installation.
 * Used to obtain information about the loaded vocabularies and their Database
 * Connection information.
 * 
 * @author <a href="mailto:kevin.peterson@mayo.edu">Kevin Peterson</a>
 */
public class DBConnector {	
	private static Logger log = Logger.getLogger(LexEVSApplicationServiceImpl.class.getName());

    /** The Constant LEXBIG_SYSPROPERTY. */
    private static final String LEXBIG_SYSPROPERTY = "LG_CONFIG_FILE";
    
	private SQLConnectionInfo[] codingSchemeConnections;
	private SQLConnectionInfo[] historyConnections;
	private ResourceManager rm;
	private CodingSchemeRendering[] csr;

	/**
	 * Create a connection to a local LexBIG installation, given a set of properties.
	 * 
	 * @param properties LexEVSProperties used to establish a connection to the local LexBIG installation
	 */
	public DBConnector(LexEVSProperties properties){
		 
		try {
			System.setProperty(LEXBIG_SYSPROPERTY, properties.getLexBigConfigFileLocation());
			rm = ResourceManager.instance();
			CodingSchemeRenderingList csrl = LexBIGServiceImpl.defaultInstance().getSupportedCodingSchemes();

			CodingSchemeRendering[] allCodingSchemes = csrl.getCodingSchemeRendering();
			ArrayList<CodingSchemeRendering> activeCodingSchemes = new ArrayList<CodingSchemeRendering>();
			
			//Only use Active CodingSchemes
			for (int i = 0; i < allCodingSchemes.length; i++) {
				CodingSchemeRendering rendering = allCodingSchemes[i];
				String name = rendering.getCodingSchemeSummary().getCodingSchemeURI();
				String version = rendering.getCodingSchemeSummary().getRepresentsVersion();
				if(rm.getRegistry().isActive(name, version)){
					activeCodingSchemes.add(rendering);
				}
			}
		
			csr = activeCodingSchemes.toArray(new CodingSchemeRendering[activeCodingSchemes.size()]);
			codingSchemeConnections = this.getDBCodingSchemeConnectionInfoFromLexBIG(csr);
			historyConnections = this.getDBHistoryConnectionInfoFromLexBIG(csr);
		} catch (Exception e) {
			log.error(e);
		}	
	}

	/**
	 * Determines whether or not the requested Coding Scheme is active in the system.
	 * 
	 * @param name Local Name of the Coding Scheme
	 * @param csvt Version or Tag information
	 * @return true if active, false if inactive
	 * @throws LBParameterException
	 */
	public boolean isCodingSchemeActive(String name, CodingSchemeVersionOrTag csvt) throws LBParameterException {	
		String version = getURIFromCodingSchemeName(name, csvt);
		if(rm.getRegistry().isActive(name, version)){
			return true;
		} else {
			return false;
		}
	}
	
	private SQLConnectionInfo[] getDBCodingSchemeConnectionInfoFromLexBIG(CodingSchemeRendering[] csr) throws LBInvocationException {
		SQLConnectionInfo[] connections = new SQLConnectionInfo[csr.length];

		for (int i = 0; i < csr.length; i++) {
			AbsoluteCodingSchemeVersionReference acvr = new AbsoluteCodingSchemeVersionReference();
			acvr.setCodingSchemeURN(csr[i].getCodingSchemeSummary().getCodingSchemeURI());
			acvr.setCodingSchemeVersion(csr[i].getCodingSchemeSummary().getRepresentsVersion());

			SQLConnectionInfo foundConnection = rm.getRegistry().getSQLConnectionInfoForCodeSystem(acvr);
			connections[i] = foundConnection;
		}
		return connections;
	}
	
	private SQLConnectionInfo[] getDBHistoryConnectionInfoFromLexBIG(CodingSchemeRendering[] csr) throws LBInvocationException {	
		ArrayList<SQLConnectionInfo> connections = new ArrayList<SQLConnectionInfo>();

		//We want to search for History connections based on UNIQUE URIs. That is, if we have two different
		//versions of a Coding Scheme loaded that share a common URI, we don't want to end up finding 2 History
		//connections.
		HashSet<String> uniqueURIs = getUniqueURIs(csr);

		for(String uri : uniqueURIs){
			try{
				SQLConnectionInfo[] foundConnection = rm.getRegistry().getSQLConnectionInfoForHistory(uri);
				for (int j = 0; j < foundConnection.length; j++) {
					connections.add(foundConnection[j]);
				}
			} catch (Exception e) {
				//if there's no History loaded for the urn, just skip it - don't throw the Exception
				log.debug("No History Service for: " + uri);
			}
		}	
		return connections.toArray(new SQLConnectionInfo[connections.size()]);
	}
	
	private HashSet<String> getUniqueURIs(CodingSchemeRendering[] csr){
		HashSet<String> uniqueURIs = new HashSet<String>();
		for(CodingSchemeRendering rendering : csr){
			uniqueURIs.add(rendering.getCodingSchemeSummary().getCodingSchemeURI());
		}
		return uniqueURIs;
	}
	
	/**
	 * Returns the list of Tags associated with this Coding Scheme
	 * 
	 * @param uri URI of the Coding Scheme
	 * @param version Version of the Coding Scheme
	 * @return List of Tags associated with this Coding Scheme
	 * @throws Exception
	 */
	public CodingSchemeTagList getTagList(String uri, String version) throws Exception {
		for(int i=0;i<csr.length;i++){
			CodingSchemeRendering rendering = csr[i];
			CodingSchemeSummary css = rendering.getCodingSchemeSummary();
			if(css.getCodingSchemeURI().equals(uri) &&
					css.getRepresentsVersion().equals(version)){
				return rendering.getRenderingDetail().getVersionTags();
			}
		}
		throw new Exception("Didn't find Coding Scheme: " + uri + " " + version);
	}
	
	/**
	 * Returns the URI of the CodingScheme given a local name.
	 * 
	 * @param codingSchemeName Local Name of the Coding Scheme
	 * @param tagOrVersion Version or Tag information
	 * @return URI of the CodingScheme
	 * @throws LBParameterException
	 */
	public String getURIFromCodingSchemeName(String codingSchemeName, CodingSchemeVersionOrTag tagOrVersion) throws LBParameterException {
		String version = getInternalVersionString(codingSchemeName, tagOrVersion);
		// this throws the necessary exceptions if it can't be mapped / found
		String internalCodingSchemeName_ = rm.getInternalCodingSchemeNameForUserCodingSchemeName(codingSchemeName, version);
		String uri = rm.getURNForInternalCodingSchemeName(internalCodingSchemeName_);
		return uri;
	}
	
	private String getInternalVersionString(String codingSchemeName, CodingSchemeVersionOrTag tagOrVersion) throws LBParameterException {
		String version = null;
		if (tagOrVersion == null || tagOrVersion.getVersion() == null || tagOrVersion.getVersion().length() == 0) {
	            version = rm.getInternalVersionStringFor(codingSchemeName,
	                    (tagOrVersion == null ? null : tagOrVersion.getTag()));
	        } else {
	            version = tagOrVersion.getVersion();
	        }
		return version;
	}
	
	/**
	 * Gets the Complete CodingSchemeRendering of a CodingScheme.
	 * 
	 * @param uri URI of the Coding Scheme
	 * @param version Version of the Coding Scheme
	 * @return the CodingSchemeRendering
	 * @throws LBParameterException
	 */
	public CodingSchemeRendering getCodingSchemeRenderingForURIAndVersion(String uri, String version) throws LBParameterException {
		for(CodingSchemeRendering rendering : csr){
			CodingSchemeSummary css = rendering.getCodingSchemeSummary();
			String csrUri = css.getCodingSchemeURI();
			String csrVersion = css.getRepresentsVersion();
			if(csrUri.equals(uri) && csrVersion.equals(version)){
				return rendering;
			}
		}
		//if it is not found, throw an exception
		throw new LBParameterException("Could not find Rendering information for CodingScheme: " + uri + " Version: " + version);		
	}
	
	/**
	 * Gets the local DB name (in other words, the name that this Coding Scheme uses to
	 * identify itself in the database).
	 * 
	 * @param uri URI of the Coding Scheme
	 * @param version Version of the Coding Scheme
	 * @return the Local DB name
	 * @throws LBParameterException
	 */
	public String getLocalDBNameForURIAndVersion(String uri, String version) throws LBParameterException {
		CodingSchemeRendering csr = getCodingSchemeRenderingForURIAndVersion(uri, version);
		return csr.getCodingSchemeSummary().getLocalName();
	}
	
	public CodingSchemeRendering[] getCodingSchemeRenderings(){
		return csr;
	}

	public SQLConnectionInfo[] getCodingSchemeConnections() {
		return codingSchemeConnections;
	}

	public SQLConnectionInfo[] getHistoryConnections() {
		return historyConnections;
	}
}
