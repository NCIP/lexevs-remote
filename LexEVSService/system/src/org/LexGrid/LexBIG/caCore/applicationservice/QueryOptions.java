/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.applicationservice;

import java.io.Serializable;
import java.util.HashMap;

import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;

/**
 * Options to use during LexEVS Data Service Querying.
 * 
 * @author <a href="mailto:kevin.peterson@mayo.edu">Kevin Peterson</a>
 *
 */
public class QueryOptions implements Serializable {

	private String codingScheme;
	private CodingSchemeVersionOrTag codingSchemeVersionOrTag;
	private HashMap securityTokens;
	private boolean lazyLoad = false;
	private int resultPageSize = -1;
	
	/**
	 * Get the Security Tokens associated with this QueryOptions instance.
	 * 
	 * @return Security Tokens
	 */
	public HashMap getSecurityTokens() {
		return securityTokens;
	}
	
	/**
	 * Set the Security Tokens associated with this QueryOptions instance.
	 * These Security Tokens will be applied to the Query using this QueryOptions
	 * instance and that Query alone.
	 * 
	 * @param securityTokens
	 */
	public void setSecurityTokens(HashMap securityTokens) {
		this.securityTokens = securityTokens;
	}
	
	
	/**
	 * Get the Coding Scheme name that this QueryOptions instance will restrict to.
	 * 
	 * @return The Coding Scheme to Query
	 */
	public String getCodingScheme() {
		return codingScheme;
	}
	
	
	/**
     * Set the Coding Scheme name that this QueryOptions instance will restrict to.
     * 
	 * @param The Coding Scheme to Query
	 */
	public void setCodingScheme(String codingScheme) {
		this.codingScheme = codingScheme;
	}
	
	/**
	 * Get the CodingSchemeVersionOrTag that this QueryOptions instance will restrict to.
	 * 
	 * @return The CodingSchemeVersionOrTag to Query
	 */
	public CodingSchemeVersionOrTag getCodingSchemeVersionOrTag() {
		return codingSchemeVersionOrTag;
	}
	
	/**
     * Set the CodingSchemeVersionOrTag that this QueryOptions instance will restrict to.
     * 
	 * @param The CodingSchemeVersionOrTag to Query
	 */
	public void setCodingSchemeVersionOrTag(
			CodingSchemeVersionOrTag codingSchemeVersionOrTag) {
		this.codingSchemeVersionOrTag = codingSchemeVersionOrTag;
	}
	
	
	/**
	 * Returns whether or not the QueryOptions instance will use LazyLoading.
	 * 
	 * @return Is lazy loading enabled
	 */
	public boolean isLazyLoad() {
		return lazyLoad;
	}
	
	
	/**
	 * Sets whether or not the QueryOptions instance will use LazyLoading. LazyLoading
	 * means that certain associations and properties of returned Objects may not be fully
	 * populated - but request information from the server as needed. Enabling this option
	 * will speed up certain queries.
	 * 
	 * NOTE: This may only be true if you have set the 'CodingScheme' property. There is no
	 * way to LazyLoad an Object without narrowing the Query down to a specific CodingScheme.
	 * 
	 * @param lazyLoad
	 */
	public void setLazyLoad(boolean lazyLoad) {
		this.lazyLoad = lazyLoad;
	}
	
	
	/**
	 * The number of results to be returned to the client at one time.
	 * 
	 * @return the result page size
	 */
	public int getResultPageSize() {
		return resultPageSize;
	}
	
	/**
	 * Specifies the number of results returned to the client at one time. If not specified,
	 * this will default to whatever the Server default page size has been set to. If this is set
	 * high, the server will return large results sets back to the client. If it is low, fewer results
	 * will be returned at a time, but more queries to the server will be made. This parameter is for
	 * specifically tweaking the page size, but and in most cases the server default will be optimal.
	 * 
	 * @param number of results returned per page
	 */
	public void setResultPageSize(int resultPageSize) {
		this.resultPageSize = resultPageSize;
	}
}
