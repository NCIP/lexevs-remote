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

import gov.nih.nci.system.dao.DAO;
import gov.nih.nci.system.dao.DAOException;
import gov.nih.nci.system.dao.QueryException;
import gov.nih.nci.system.dao.Request;
import gov.nih.nci.system.util.ClassCache;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.LexGrid.LexBIG.caCore.applicationservice.QueryOptions;
import org.LexGrid.LexBIG.caCore.connection.DAOListFactory;
import org.LexGrid.LexBIG.caCore.dao.orm.LexEVSDAO;
import org.LexGrid.LexBIG.caCore.dao.orm.selectionStrategy.DAOSelectionStrategy;
import org.LexGrid.LexBIG.caCore.dao.orm.selectionStrategy.exceptions.SelectionStrategyException;
import org.apache.log4j.Logger;

public class LexEVSClassCache extends ClassCache {
	private static Logger log = Logger.getLogger(LexEVSClassCache.class.getName());
	
	private List<LexEVSDAO> daoList = new ArrayList<LexEVSDAO>();

	private List<DAOSelectionStrategy> selectionStrategies;

	/**
	 * Constructs a Cache of Class and Hibernate information given.
	 * 
	 * @param listFactory The DAO List Factory that is providing the DAOs for this Cache.
	 * @throws DAOException
	 */
	public LexEVSClassCache(DAOListFactory listFactory) throws DAOException {
		try {
			listFactory.buildDAOs();
		} catch (Exception e) {
			throw new DAOException("Error Building DAO List", e);
		}
		List<LexEVSDAO> daos = listFactory.getDaoList();
		
		this.daoList = daos;
		
		//Only init superclass with one DAO
		//Do this because DAOs are essentially copies of
		//each other.
		//TODO: Maybe a better way
		//to do this.	
		if(daoList.size() > 0){
			List oneDao = new ArrayList();
			oneDao.add(daoList.get(0));		
			super.setDaoList(oneDao);	
		} else {
			log.warn("No DAOs were built. Make sure the LexBIG installation you are connecting to has content loaded.");
		}
	}
	
	/**
	 * Constructor for creating an empty ClassCache.
	 * 
	 * Note: To populate this Cache, you must explicitly call 'setDaoList' -- otherwise
	 * this Cache will not be initialized. For most purposes, passing in the DAOListFactory
	 * is the preferred way of constructing this class, that way it will automatically be
	 * initialized. Use this constructor if you want to populate this Cache later, or not
	 * at all (for example, for disabling caCORE SDK functionality in LexEVSAPI).
	 * 
	 * @throws DAOException
	 */
	public LexEVSClassCache() throws DAOException {
		log.warn("Constructing an empty ClassCache -- No caCORE SDK queries will be available.");
	}
	
	@Override
	public void setDaoList(List daoList)  {
		this.daoList = daoList;
	}	
	
	@Override
	public List getDaoList() {
		//no tokens - but always apply Security Selection Strategy
		try {
			return applySelectionStrategy(getAllAvailableDAOs(), null, null);
		} catch (SelectionStrategyException e) {
			//We can't change the Exception signature on this override -- so the best
			//we can do is warn an return all DAOs.
			log.warn("Error Applying Selection Strategies -- no restrictions made.", e);
			return getAllAvailableDAOs();
		}
	}
	
	public List getDaoList(Request request, QueryOptions queryOptions) throws SelectionStrategyException {
		//get the tokens from the request
		return applySelectionStrategy(getAllAvailableDAOs(), request, queryOptions);
	}
	
	public List getDaoList(Request request) throws SelectionStrategyException {
		//no tokens - but always apply Security Selection Strategy
		return applySelectionStrategy(getAllAvailableDAOs(), request, null);
	}
	
	@Override
	public boolean isCollection(String className, String attribName) throws QueryException
	{
		Field[] classFields;
		try
		{
			classFields = getFields(getClassFromCache(className));
			for (int i=0; i<classFields.length;i++)
			{
				if(classFields[i].getName().equals(attribName))
				{
					Class type = classFields[i].getType();
					if("java.util.Collection".equals(type.getName()) ||
							"java.util.List".equals(type.getName()))
						return true;

					return false;
				}
			} 
			return false;
		} 
		catch (ClassNotFoundException e)
		{
			throw new QueryException("Could not determine type of attribute "+attribName+" in class "+className,e);
		}
	}
	
	/*
	 * Apply the Selection Strategies
	 */
	private List<LexEVSDAO> applySelectionStrategy(List<LexEVSDAO> daoList, Request request, QueryOptions queryOptions) throws SelectionStrategyException {
		List<LexEVSDAO> usableDAOs = new ArrayList<LexEVSDAO>();
		
		//initialize it with all possible at first
		usableDAOs.addAll(getAllAvailableDAOs());
		
		//if the user has provided Selection Strategies, apply them. If not, return everything.
		if(this.getSelectionStrategies() != null){
			for(DAOSelectionStrategy strategy : this.getSelectionStrategies()){
				usableDAOs = strategy.getDAOList(usableDAOs, request, queryOptions);
			}
		}
		
		return usableDAOs;
	}
		
	private List<LexEVSDAO> getAllAvailableDAOs(){
		return this.daoList;
	}

	public List<DAOSelectionStrategy> getSelectionStrategies() {
		return selectionStrategies;
	}

	public void setSelectionStrategies(
			List<DAOSelectionStrategy> selectionStrategies) {
		this.selectionStrategies = selectionStrategies;
	}


	public DAO getDAOForClass(String qualClassName) {
		throw new RuntimeException("Not supported for LexEVS DataService");
	}
}
