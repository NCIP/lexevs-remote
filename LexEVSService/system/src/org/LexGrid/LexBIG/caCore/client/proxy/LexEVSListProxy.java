/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.client.proxy;

import gov.nih.nci.system.applicationservice.ApplicationService;
import java.util.ArrayList;

import org.LexGrid.LexBIG.caCore.applicationservice.QueryOptions;
import gov.nih.nci.system.client.proxy.ListProxy;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class LexEVSListProxy extends ListProxy {
	private static final long serialVersionUID = 2698044061654948213L;
	public QueryOptions queryOptions;

	private static Logger log = LogManager.getLogger(ListProxy.class.getName());
	
	private transient LexEVSApplicationService lexevsappService;
	
	// ==================================================================================
	// make a inner class, there is no telling what we need to do
	private class ListChunk extends ArrayList {
		private static final long serialVersionUID = -1;
	}

	// end of inner class ListChunk
	// ==============================================================



	public boolean isEmpty() {
		return this.getListChunk().isEmpty();
	}



	public Object[] toArray() {
		if (this.isHasAllRecords()) {
			return this.getListChunk().toArray();
		} else {
			ArrayList wholeList = new ArrayList();
			try {
				throw new Exception(
						"Object[] toArray(): This feature is not yet implemented in this version.");
			} catch (Exception ex) {
				log.error("Exception: " + ex.getMessage());
			}
			return wholeList.toArray();
		}

	}


	public QueryOptions getQueryOptions() {
		return queryOptions;
	}

	public void setQueryOptions(QueryOptions queryOptions) {
		this.queryOptions = queryOptions;
	}

	public LexEVSApplicationService getAppService() {
		return lexevsappService;
	}

	public void setAppService(LexEVSApplicationService appService) {
		this.lexevsappService = appService;
		super.setAppService(appService);
	}	
	
	public void setAppService(ApplicationService appService) {
		if(appService instanceof LexEVSApplicationService){
			this.lexevsappService = (LexEVSApplicationService)appService;
		} else {
			throw new RuntimeException("LexEVSListProxy must use" +
			"a LexEVSApplicationService.");
		}
	}	
	
	
}
