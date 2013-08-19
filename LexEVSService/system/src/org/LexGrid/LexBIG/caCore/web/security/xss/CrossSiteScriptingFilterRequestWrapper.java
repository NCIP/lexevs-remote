/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.web.security.xss;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * The Class CrossSiteScriptingFilterRequestWrapper.
 *
 * @author NCI Browser Team
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
		
		if(iter!=null) {
			while(iter.hasNext()) {

				String key = null;
				String[] values = null;
				
				key = (String) iter.next();
				
				if(key != null) {
					if(this.checkForScript(key)){
						throw new RuntimeException("Invalid text detected in URL: " +
								"Invalid Parameter key string.");
					}
					
					values = (String[])map.get(key);
					
					//clean values where possible
					for(int i=0; i<values.length; i++)
						values[i] = cleanXSS(values[i]);
				}
			}
		}

		return map;
	}
	
	private boolean checkForScript(String string){
		return string.matches(".*<\\s*script\\s*>.*</\\s*script\\s*>.*")
			||
			string.matches(".*[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\'].*");		
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
	    if (value == null){
	        return null;
	    }
	    
	    return cleanXSS(value);   
	}

	private String cleanXSS(String value) {

        if (value == null || value.length() < 1){
            return value;
        }

        try {
            value = URLDecoder.decode(value, "UTF-8");

        } catch (UnsupportedEncodingException e) {

            // Do nothing, just use the input

        } catch (IllegalArgumentException e) {

            // Do nothing, just use the input

            // Note: The following exception was triggered:

            // java.lang.IllegalArgumentException: URLDecoder: Illegal hex

            // characters in escape (%) pattern - For input string: "^&".

        }

        // Remove XSS attacks

        value = value.replaceAll("<\\s*script\\s*>.*</\\s*script\\s*>", "");
        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
        value = value.replaceAll("'", "&#39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("\"", "&quot;");

        return value;
    }
}
