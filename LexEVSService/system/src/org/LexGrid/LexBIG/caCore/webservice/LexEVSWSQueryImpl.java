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
package org.LexGrid.LexBIG.caCore.webservice;

import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.client.proxy.ListProxy;
import gov.nih.nci.system.query.hibernate.HQLCriteria;
import gov.nih.nci.system.query.nestedcriteria.NestedCriteriaPath;
import gov.nih.nci.system.util.ClassCache;
import gov.nih.nci.system.webservice.WSQuery;
import gov.nih.nci.system.webservice.util.WSUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.rpc.ServiceException;

import org.LexGrid.LexBIG.caCore.dao.orm.translators.NestedObjectToCriteria;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.remoting.jaxrpc.ServletEndpointSupport;


public class LexEVSWSQueryImpl extends ServletEndpointSupport implements WSQuery{
	private static Logger log = Logger.getLogger(LexEVSWSQueryImpl.class);

	private static ApplicationService applicationService;	
	private static ClassCache classCache;
	private NestedObjectToCriteria nestedObjectToCriteriaTranslator;

	private String version = "4.1";
	
	public void destroy() {
		applicationService = null;
		classCache = null;
	}

	protected void onInit() throws ServiceException {
		classCache = (ClassCache)getWebApplicationContext().getBean("ClassCache");
		applicationService = (ApplicationService)getWebApplicationContext().getBean("ApplicationServiceImpl");
		nestedObjectToCriteriaTranslator = (NestedObjectToCriteria)getWebApplicationContext().getBean("NestedObjectToCriteria");
	}

	public String getVersion(){
        return version;
    }

    public int getRecordsPerQuery(){
        return applicationService.getMaxRecordsCount();
    }
    public int getMaximumRecordsPerQuery(){
        return applicationService.getMaxRecordsCount();
    }
	
	public int getTotalNumberOfRecords(String targetClassName, Object criteria) throws Exception {
		DetachedCriteria dc = nestedObjectToCriteriaTranslator.translate(Class.forName(targetClassName), criteria);
		Integer queryRowCount = applicationService.getQueryRowCount(dc, getTargetClassName(targetClassName));
		return queryRowCount;
	}

	public List queryObject(String targetClassName, Object criteria) throws Exception {
		DetachedCriteria dc = nestedObjectToCriteriaTranslator.translate(Class.forName(targetClassName), criteria);
		return applicationService.query(dc, 0, getTargetClassName(targetClassName));
	}

	public List query(String targetClassName, Object criteria, int startIndex) throws Exception
	{
		DetachedCriteria dc = nestedObjectToCriteriaTranslator.translate(Class.forName(targetClassName), criteria);
		return applicationService.query(dc, startIndex, getTargetClassName(targetClassName));
	}
	
	public List getAssociation(Object source, String associationName, int startIndex) throws Exception
	{
		List<Object> results = applicationService.getAssociation(source, associationName);
		
		return results.subList(startIndex, results.size());	
	}	

	private String getTargetClassName(String path){
		
		
		if (path.indexOf(',') <= 0){ 
			return path;
		}
		
		// We have a comma-delimited Nested Search criteria path
		String targetClassName = "";
		StringTokenizer tokens = new StringTokenizer(path, ",");
		targetClassName = tokens.nextToken().trim();
		
		return targetClassName;
	}
}
