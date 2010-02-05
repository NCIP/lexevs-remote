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
package org.LexGrid.LexBIG.caCore.client.proxy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.NotSupportedException;

import org.LexGrid.LexBIG.caCore.applicationservice.QueryOptions;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.apache.log4j.Logger;

import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.client.proxy.ListProxy;

public class LexEVSListProxy extends ListProxy {
	private static final long serialVersionUID = 2698044061654948213L;
	public QueryOptions queryOptions;

	private static Logger log = Logger.getLogger(ListProxy.class.getName());
	
	private transient LexEVSApplicationService lexevsappService;
	
	// ==================================================================================
	// make a inner class, there is no telling what we need to do
	private class ListChunk extends ArrayList {
		private static final long serialVersionUID = -1;
	}

	// end of inner class ListChunk
	// ==============================================================

	/**
	 * Returns the number of elements in this list.
	 * 
	 * @return size of the List
	 */
	public int size() {
		if (getRealSize() == -1) {
			if (this.isHasAllRecords()) {
				this.setRealSize(this.getListChunk().size());
			} else {
				int rowCount = this.getListChunk().size();
				//System.out.println("rowCount: " + rowCount);
// TODO ::				ApplicationService appService = ApplicationServiceProvider.getApplicationService();
				try {
					// Data larger than the max query size.  Must determine the 
					// actual size
					//System.out.println("rowCount: " + rowCount + "; " + "maxRecordsPerQuery_: " + maxRecordsPerQuery_);
					if (rowCount == this.getMaxRecordsPerQuery())
						rowCount = lexevsappService.getQueryRowCount(this.getOriginalCriteria(),
								this.getTargetClassName(), queryOptions);
				} catch (Exception ex) {
					log.error("Exception: ", ex);
					ex.printStackTrace();
				}
				this.setRealSize(rowCount);
				if (rowCount < this.getMaxRecordsPerQuery())
					this.setHasAllRecords(true);
				else
					this.setHasAllRecords(false);
			}
		}
		return this.getRealSize();
	}

	/**
	 * @see java.util.#isEmpty()
	 */
	public boolean isEmpty() {
		return this.getListChunk().isEmpty();
	}

	/**
	 * @see java.util.#contains(java.lang.Object)
	 */
	public boolean contains(Object o) {
		if (this.isHasAllRecords()) {
			return this.getListChunk().contains(o);
		} else {
			// step through the entire set of list chunks from
			// the appservice looking for the result.
			boolean computedResult = false;
			computedResult = this.getListChunk().contains(o);
			if (computedResult)
				return computedResult;
			else {
				int firstResult = 0;
//	TODO ::			ApplicationService appService = ApplicationServiceProvider.getApplicationService();

				for (;;) {
					List ls = new ArrayList();
					try {
						ls = lexevsappService.query(this.getOriginalCriteria(), firstResult, this.getTargetClassName(), queryOptions);
						if (ls.size() <= 0) // there are no more records in
											// database
							break;
						computedResult = ls.contains(o);
						if (computedResult)
							break;
						else
							firstResult += this.getMaxRecordsPerQuery();
					} catch (Exception ex) {
						log.error("Exception: " + ex.getMessage());
					}
				}
			}
			return computedResult;
		}
	}

	/**
	 * @see java.util.#toArray()
	 * 
	 */
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

	/**
	 * @see java.util.List#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection c) {
		if (this.isHasAllRecords()) {
			return this.getListChunk().containsAll(c);
		} else {
			// find if the entire collection is there via appservice and then
			boolean computedResult = false;
			int recordsCount = 0;
			computedResult = this.getListChunk().containsAll(c);

			if (computedResult)
				return computedResult;
			else {
				int collectionSize = c.size();
				if (collectionSize > this.getMaxRecordsPerQuery())
					recordsCount = collectionSize;
				else
					recordsCount = this.getMaxRecordsPerQuery();
				int firstResult = 0;
//	TODO ::			ApplicationService appService = ApplicationServiceProvider.getApplicationService();
				for (;;) {
					List ls = new ArrayList();
					try {
						ls = lexevsappService.query(this.getOriginalCriteria(), firstResult,this.getTargetClassName(), queryOptions);
						if (ls.size() <= 0) // there are no more records in database
							break;
						computedResult = ls.contains(c);
						if (computedResult)
							break;
						else
							firstResult += recordsCount;
					} catch (Exception ex) {
						log.error("Exception: " + ex.getMessage());
					}
				}
			}
			return computedResult;
		}
	}

	/**
	 * @param index
	 * @return Object at this index
	 * 
	 */
	public Object get(int index) {
		if (this.getRealSize() == -1) {
			size();
		}
		if (this.isHasAllRecords()) {
			return this.getListChunk().get(index);
		} else {
			// go through entire list from appservice taking into account
			// removed objects and added objects and get that object
			// log.debug("listChunk_ size is " + currentSize);
			int firstRow = this.getOriginalStart();
//TODO ::			ApplicationService appService = ApplicationServiceProvider.getApplicationService();

			if ((index >= (firstRow + this.getMaxRecordsPerQuery()))
					&& (index < this.getRealSize())) {
				this.setOriginalStart(index);
				try {

					List ls = lexevsappService.query(this.getOriginalCriteria(), this.getOriginalStart(), this.getTargetClassName(), queryOptions);
					this.getListChunk().clear();

					this.getListChunk().addAll(ls);
					return this.getListChunk().get(index - this.getOriginalStart());

				} catch (Exception ex) {
					log.error("Exception: " + ex.getMessage());
				}
			} else if (index < firstRow) {// first row is at 2003, index is 4
				this.setOriginalStart(index);
				try {
					List ls1 = lexevsappService.query(this.getOriginalCriteria(), this.getOriginalStart(),this.getTargetClassName(), queryOptions);
					this.getListChunk().clear();
					this.getListChunk().addAll(ls1);
					return this.getListChunk().get(index - this.getOriginalStart());

				} catch (Exception ex) {
					log.error("Exception: " + ex.getMessage());
				}
			} else // within the currentwindow
			{
				return this.getListChunk().get(index - this.getOriginalStart());
			}
			return new Object();
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
