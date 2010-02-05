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
package org.LexGrid.LexBIG.caCore.web.struts.action;

import gov.nih.nci.system.web.struts.action.BaseActionSupport;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.LexGrid.LexBIG.DataModel.InterfaceElements.CodingSchemeRendering;
import org.LexGrid.LexBIG.caCore.web.util.LexEVSJSPUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.util.ServletContextAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class Result extends BaseActionSupport {

    private static final long serialVersionUID = 1234567890L;
    
    private static Logger log = Logger.getLogger(Result.class.getName());    
    
    //Query parameters
    private String query;
    private String btnSearch;
    private String searchObj;
    private String selectedDomain;

	public String execute() throws Exception {
		
		//log.debug("Result.action instance: " + this);
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		SessionMap session = (SessionMap) ActionContext.getContext().get(ActionContext.SESSION);

		LexEVSJSPUtils jspUtils = LexEVSJSPUtils.getJSPUtils(request.getSession().getServletContext());
		
		debugSessionAttributes(session);
		
		// BEGIN - build query
		String codingSchemeName = null;
		String codingSchemeVersion = null;
		
		String selectedSearchDomain=null;
		String query=null;

		String submitValue = getBtnSearch();
		log.debug("submitValue: " + submitValue);

		String className = getSelectedDomain();
		
		log.debug("className (selectedDomain): "+ getSelectedDomain());
		
		if(submitValue != null && submitValue.equalsIgnoreCase("Submit"))
		{
		   
		    query = "GetHTML?query=";
		   	
		   	selectedSearchDomain = getSearchObj();
		   	log.debug("selectedSearchDomain: "+ selectedSearchDomain);
		   	   	
		   	if(selectedSearchDomain != null && !selectedSearchDomain.equals("Please choose"))
		   	{ query +=selectedSearchDomain + "&";
		   	
			   	if(className != null && !className.equals("Please choose"))
			   	{   query += className;	
			   		log.debug("query with search object = " + query);
			   		Enumeration parameters = request.getParameterNames();
		     		while(parameters.hasMoreElements())
		     		{
		         		String parameterName = (String)parameters.nextElement();
		         		log.debug("param = " + parameterName);
		         		if(!parameterName.equals("selectedCodingScheme") && !parameterName.equals("klassName") && !parameterName.equals("searchObj") && !parameterName.equals("BtnSearch") && !parameterName.equals("username") && !parameterName.equals("password") && !parameterName.equals("selectedDomain"))
		         		{
		         			String parameterValue = (request.getParameter(parameterName)).trim();
		         			if(parameterValue.length() > 0)
		         			{
		         				log.debug("parameterValue= " + parameterValue); 
		         				query +="[@" + parameterName + "=" + urlEncode(parameterValue) + "]";
		         			}
		         		}  
		         		if(parameterName.equals("selectedCodingScheme")){
		         			String paramValue = request.getParameter(parameterName).trim();
		         			if(paramValue != null && !paramValue.equals("")){
		         				int selectedCodingScheme  = Integer.valueOf(paramValue);
		         				CodingSchemeRendering codingScheme = jspUtils.getLoadedCodingSchemes()[selectedCodingScheme];
		         				codingSchemeName = codingScheme.getCodingSchemeSummary().getLocalName();
		         				codingSchemeVersion = codingScheme.getCodingSchemeSummary().getRepresentsVersion();
		         			}         			
		         		}		         		
		         	}    			   	
			   	 }
		   	} 
		   	else
		   	{
		   		if(className != null && !className.equals("Please choose"))
			   	{   query += className + "&" + className;	
			   	    log.debug("query no search object = " + query);
			   		Enumeration parameters = request.getParameterNames();
		     		while(parameters.hasMoreElements())
		     		{
		         		String parameterName = (String)parameters.nextElement();
		         		log.debug("param = " + parameterName);
		         		if(!parameterName.equals("selectedCodingScheme") && !parameterName.equals("klassName") && !parameterName.equals("searchObj") && !parameterName.equals("BtnSearch")&& !parameterName.equals("username") && !parameterName.equals("password") && !parameterName.equals("selectedDomain"))
		         		{
		         			String parameterValue = (request.getParameter(parameterName)).trim();
		         			if(parameterValue.length() > 0)
		         			{
		         				log.debug("parameterValue= " + parameterValue); 
		         				query +="[@" + parameterName + "=" + urlEncode(parameterValue) + "]";
		         			}
		         		}  
		         		if(parameterName.equals("selectedCodingScheme")){
		         			String paramValue = request.getParameter(parameterName).trim();
		         			if(paramValue != null && !paramValue.equals("")){
		         				int selectedCodingScheme  = Integer.valueOf(paramValue);
		         				CodingSchemeRendering codingScheme = jspUtils.getLoadedCodingSchemes()[selectedCodingScheme];
		         				codingSchemeName = codingScheme.getCodingSchemeSummary().getLocalName();
		         				codingSchemeVersion = codingScheme.getCodingSchemeSummary().getRepresentsVersion();
		         			}
		         		}		      
		         	}     	
			   	
			   	 }
		   	
		   	}
//		   	if ((request.getParameter("username") != null) && ((String)request.getParameter("username").trim()).length() > 0)
//		   		query = query + "&username=" + request.getParameter("username");
//		   	if ((request.getParameter("password") != null) && ((String)request.getParameter("password").trim()).length() > 0)
//		   		query = query + "&password=" + request.getParameter("password");
		   	
		   	String username = (String) session.get("username");
		   	String password = (String) session.get("password");
		   	
		   	if ((username != null) && (username.trim()).length() > 0)
		   		query = query + "&username=" + username;
		   	if ((password != null) && (password.trim()).length() > 0)
		   		query = query + "&password=" + password;		   	
		   		
			if(codingSchemeName != null){
		   		query = query + "&codingSchemeName=" + codingSchemeName + "&codingSchemeVersion=" + codingSchemeVersion;
		   	}
		   	log.debug("query: " + query);	
		   	
		 setQuery(query);
		}
//		  END - build query

		return SUCCESS; 		
	}
	
	public static String urlEncode(String param) throws UnsupportedEncodingException {
		return URLEncoder.encode(param, "UTF-8");
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}	

	public String getBtnSearch() {
		return btnSearch;
	}

	public void setBtnSearch(String btnSearch) {
		this.btnSearch = btnSearch;
	}

	public String getSearchObj() {
		return searchObj;
	}

	public void setSearchObj(String searchObj) {
		this.searchObj = searchObj;
	}

	public String getSelectedDomain() {
		return selectedDomain;
	}

	public void setSelectedDomain(String selectedDomain) {
		this.selectedDomain = selectedDomain;
	}
	
}


