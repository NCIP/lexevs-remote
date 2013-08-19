/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
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
