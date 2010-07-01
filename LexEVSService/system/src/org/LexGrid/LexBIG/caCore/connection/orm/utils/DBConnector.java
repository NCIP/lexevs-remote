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

import org.LexGrid.LexBIG.DataModel.Collections.CodingSchemeRenderingList;
import org.LexGrid.LexBIG.DataModel.Collections.CodingSchemeTagList;
import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeSummary;
import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.DataModel.Core.types.CodingSchemeVersionStatus;
import org.LexGrid.LexBIG.DataModel.InterfaceElements.CodingSchemeRendering;
import org.LexGrid.LexBIG.Exceptions.LBParameterException;
import org.LexGrid.LexBIG.Impl.LexBIGServiceImpl;
import org.LexGrid.LexBIG.caCore.applicationservice.impl.LexEVSApplicationServiceImpl;
import org.LexGrid.LexBIG.caCore.security.properties.LexEVSProperties;
import org.apache.log4j.Logger;
import org.lexevs.locator.LexEvsServiceLocator;
import org.lexevs.system.service.SystemResourceService;

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
    
	private CodingSchemeRendering[] csr;
	
	private SystemResourceService systemResourceService;

	/**
	 * Create a connection to a local LexBIG installation, given a set of properties.
	 * 
	 * @param properties LexEVSProperties used to establish a connection to the local LexBIG installation
	 */
	public DBConnector(LexEVSProperties properties){
		 
		try {
			System.setProperty(LEXBIG_SYSPROPERTY, properties.getLexBigConfigFileLocation());
			systemResourceService = LexEvsServiceLocator.getInstance().getSystemResourceService();
			CodingSchemeRenderingList csrl = LexBIGServiceImpl.defaultInstance().getSupportedCodingSchemes();

			CodingSchemeRendering[] allCodingSchemes = csrl.getCodingSchemeRendering();
			ArrayList<CodingSchemeRendering> activeCodingSchemes = new ArrayList<CodingSchemeRendering>();
			
			//Only use Active CodingSchemes
			for (int i = 0; i < allCodingSchemes.length; i++) {
				CodingSchemeRendering rendering = allCodingSchemes[i];
				
				CodingSchemeVersionStatus status = rendering.getRenderingDetail().getVersionStatus();
				
				if(status.equals(CodingSchemeVersionStatus.ACTIVE)){
					activeCodingSchemes.add(rendering);
				}
			}
		
			csr = activeCodingSchemes.toArray(new CodingSchemeRendering[activeCodingSchemes.size()]);
			
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
		String uri = this.systemResourceService.getUriForUserCodingSchemeName(name);
		String version = getInternalVersionString(name, csvt);
		
		CodingSchemeRendering rendering = this.getCodingSchemeRenderingForURIAndVersion(uri, version);
		
		return rendering.getRenderingDetail().getVersionStatus().equals(CodingSchemeVersionStatus.ACTIVE);
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
	public String getURIFromCodingSchemeName(String codingSchemeName) throws LBParameterException {
		return this.systemResourceService.getUriForUserCodingSchemeName(codingSchemeName);
	}
	
	private String getInternalVersionString(String codingSchemeName, CodingSchemeVersionOrTag tagOrVersion) throws LBParameterException {
		String version = null;
		if (tagOrVersion == null || tagOrVersion.getVersion() == null || tagOrVersion.getVersion().length() == 0) {
	            version = systemResourceService.getInternalVersionStringForTag(codingSchemeName,
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
}
