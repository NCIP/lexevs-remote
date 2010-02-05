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
package org.LexGrid.LexBIG.caCore.web.util;

import java.util.Properties;

import javax.servlet.ServletContext;

import org.LexGrid.LexBIG.DataModel.InterfaceElements.CodingSchemeRendering;
import org.LexGrid.LexBIG.caCore.connection.orm.utils.DBConnector;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import gov.nih.nci.system.web.util.JSPUtils;

public class LexEVSJSPUtils extends JSPUtils {
	private static Logger log = Logger.getLogger(LexEVSJSPUtils.class.getName());
	
	private DBConnector dbConnector;
	private static LexEVSJSPUtils jspUtils;

	public LexEVSJSPUtils(ServletContext context) {	
		super(context);
		WebApplicationContext ctx = WebApplicationContextUtils
		.getWebApplicationContext(context);
		dbConnector = (DBConnector)ctx.getBean("DBConnector");
		
	}
	
	/**
	 * Instantiate LexEVSJSPUtils
	 * @param context
	 * @return LexEVSJSPUtils
	 */
	synchronized public static LexEVSJSPUtils getJSPUtils(ServletContext context) {
		try {
			if (jspUtils == null)
				jspUtils = new LexEVSJSPUtils(context);
		} catch (Exception e) {
			log.error("Exception caught getting a handle to JSPUtils: ", e);
		}

		return jspUtils;
	}
	
	public CodingSchemeRendering[] getLoadedCodingSchemes(){
		return dbConnector.getCodingSchemeRenderings();
	}

}
