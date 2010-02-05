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
package org.LexGrid.LexBIG.caCore.test.queryOptions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.encoding.TypeMappingRegistry;

import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.caCore.applicationservice.QueryOptions;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSDataService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.codingSchemes.CodingScheme;
import org.LexGrid.commonTypes.EntityDescription;
import org.LexGrid.commonTypes.Text;
import org.LexGrid.concepts.Concept;
import org.LexGrid.naming.Mappings;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.commons.lang.ArrayUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

import edu.mayo.cagrid.encoding.CastorBeanDeserializerFactory;
import edu.mayo.cagrid.encoding.CastorBeanSerializerFactory;

import gov.nih.nci.cagrid.cqlquery.Predicate;
import gov.nih.nci.evs.security.SecurityToken;
import gov.nih.nci.system.applicationservice.ApplicationException;
import gov.nih.nci.system.client.ApplicationServiceProvider;
import gov.nih.nci.system.query.cql.CQLAttribute;
import gov.nih.nci.system.query.cql.CQLObject;
import gov.nih.nci.system.query.cql.CQLPredicate;
import gov.nih.nci.system.query.cql.CQLQuery;
import gov.nih.nci.system.query.hibernate.HQLCriteria;
import junit.framework.TestCase;

public class QueryOptionsTest extends ServiceTestCase {
	String testId = "LexEVS DataService Web Service Test (SOAP)";

	QueryOptions queryOptions;
	LexEVSDataService service;
	String csClassName = CodingScheme.class.getName();
	
	public void setUp(){
		queryOptions = new QueryOptions();
		queryOptions.setCodingScheme(ServiceTestCase.THES_SCHEME);
		queryOptions.setCodingScheme(ServiceTestCase.THES_SCHEME);
		CodingSchemeVersionOrTag csvt = new CodingSchemeVersionOrTag();
		csvt.setVersion(ServiceTestCase.THES_VERSION);
		queryOptions.setCodingSchemeVersionOrTag(csvt);
		
		service = LexEVSServiceHolder.instance().getLexEVSAppService();
	}
	
	protected String getTestID() {	
		return testId;
	}
	
	public void testQueryCQLQueryStringQueryOptions() throws Exception {
		CQLQuery query = new CQLQuery();
		CQLObject target = new CQLObject();
		target.setName(csClassName);
		query.setTarget(target);
		List<CodingScheme> returnList = service.query(query, csClassName, queryOptions);
		checkReturnList(returnList);
	}
	
	public void testQueryCQLQueryQueryOptions() throws Exception {
		CQLQuery query = new CQLQuery();
		CQLObject target = new CQLObject();
		target.setName(csClassName);
		query.setTarget(target);
		List<CodingScheme> returnList = service.query(query, queryOptions);
		checkReturnList(returnList);
	}
	
	public void testQueryDetachedCriteriaStringQueryOptions() throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CodingScheme.class);
		List<CodingScheme> returnList = service.query(detachedCriteria, csClassName, queryOptions);
		checkReturnList(returnList);
	}
	
	
	public void testQueryDetachedCriteriaQueryOptions() throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CodingScheme.class);
		List<CodingScheme> returnList = service.query(detachedCriteria, queryOptions);
		checkReturnList(returnList);
	}
	
	public void testQueryHQLCriteriaStringQueryOptions() throws Exception {
		HQLCriteria hqlCriteria = new HQLCriteria("FROM org.LexGrid.codingSchemes.CodingScheme");
		List<CodingScheme> returnList = service.query(hqlCriteria, csClassName, queryOptions);
	}
	
	public void testQueryHQLCriteriaQueryOptions() throws Exception {
		HQLCriteria hqlCriteria = new HQLCriteria("FROM org.LexGrid.codingSchemes.CodingScheme");
		List<CodingScheme> returnList = service.query(hqlCriteria, csClassName, queryOptions);
		checkReturnList(returnList);
	}
	
	public void testSearchClassListQueryOptions() throws Exception {
		List list = new ArrayList();
		list.add(new CodingScheme());	
		List<CodingScheme> returnList = service.search(CodingScheme.class, list, queryOptions);
		checkReturnList(returnList);
	}
	
	public void testSearchClassObjectQueryOptions() throws Exception {
		List<CodingScheme> returnList = service.search(CodingScheme.class, new CodingScheme(), queryOptions);
		checkReturnList(returnList);
	}
	
	public void testSearchStringListQueryOptions() throws Exception {
		List list = new ArrayList();
		list.add(new CodingScheme());	
		List<CodingScheme> returnList = service.search(csClassName, list, queryOptions);
		checkReturnList(returnList);
	}

	public void testSearchStringObjectQueryOptions() throws Exception {
		List<CodingScheme> returnList = service.search(csClassName, new CodingScheme(), queryOptions);
		checkReturnList(returnList);
	}
	
	public void testQueryObjectIntegerStringQueryOptions() throws Exception {
		DetachedCriteria dc = DetachedCriteria.forClass(CodingScheme.class);
		List<CodingScheme> returnList = service.query(dc, 0, csClassName, queryOptions);
		checkReturnList(returnList);		
	}
	public void testGetQueryRowCount() throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CodingScheme.class);
		int rowCount = service.getQueryRowCount(detachedCriteria, csClassName, queryOptions);
		assertTrue(rowCount == 1);
	}

	public void testGetAssociationObjectStringQueryOptions() throws Exception {
		CodingScheme cs = new CodingScheme();	
		List<Text> copyright = service.getAssociation(cs, "_copyright", queryOptions);
		assertTrue(copyright != null);
		assertTrue(copyright.size() == 1);
		assertTrue(copyright.get(0).getContent().equals(ServiceTestCase.THES_COPYRIGHT));
	}

	public void testQueryCQLQueryGridQueryOptions() throws Exception {
		gov.nih.nci.cagrid.cqlquery.CQLQuery query = new gov.nih.nci.cagrid.cqlquery.CQLQuery();
		gov.nih.nci.cagrid.cqlquery.Object target = new gov.nih.nci.cagrid.cqlquery.Object();
		target.setName(csClassName);
		query.setTarget(target);
		List<CodingScheme> returnList = service.query(query, queryOptions);
		checkReturnList(returnList);
	}
	
	private void checkReturnList(List<CodingScheme> list){
		assertTrue(list != null);
		assertTrue(list.size() == 1);
		CodingScheme cs = list.get(0);
		assertTrue(cs.getCodingSchemeName().equals(ServiceTestCase.THES_SCHEME));
	}
}

