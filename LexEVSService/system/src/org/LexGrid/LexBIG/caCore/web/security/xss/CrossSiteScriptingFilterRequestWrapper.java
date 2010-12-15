/*
 * Copyright: (c) 2004-2010 Mayo Foundation for Medical Education and 
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
 * 		http://www.eclipse.org/legal/epl-v10.html
 * 
 */
package org.LexGrid.LexBIG.caCore.web.security.xss;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * The Class CrossSiteScriptingFilterRequestWrapper.
 * 
 * @author <a href="mailto:kevin.peterson@mayo.edu">Kevin Peterson</a>
 */
public class CrossSiteScriptingFilterRequestWrapper extends HttpServletRequestWrapper {
	
	public CrossSiteScriptingFilterRequestWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
	}

	@SuppressWarnings("unchecked")
	public Map getParameterMap() {

		Map map = super.getParameterMap();

		Iterator iter = (map.keySet() != null)? map.keySet().iterator() : null;
		
		String key = null;
		String[] values = null;
		
		if(iter!=null) {
			while(iter.hasNext()) {
				key = (String) iter.next();
				if(key != null) {
					values = (String[])map.get(key);
					for(int i=0; i<values.length; i++)
						values[i] = cleanXSS(values[i]);
				}
			}
		}

		return map;
	}
	
	public String[] getParameterValues(String parameter) {

		String[] values = super.getParameterValues(parameter);
		if (values == null)  {
			return null;
		}
		
		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			encodedValues[i] = cleanXSS(values[i]);
		}  
		return encodedValues; 
	}
	
	public String getParameter(String parameter) {
		  String value = super.getParameter(parameter);
		  if (value == null) {
		         return null; 
                  }
		  return cleanXSS(value);
	}
	
	public String getHeader(String name) {
	    String value = super.getHeader(name);
	    if (value == null)
	        return null;
	    return cleanXSS(value);   
	}

	private String cleanXSS(String value) {

		value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
		value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
		value = value.replaceAll("'", "& #39;");		  
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		value = value.replaceAll("(?i)script", "");
		return value;
	}
}
