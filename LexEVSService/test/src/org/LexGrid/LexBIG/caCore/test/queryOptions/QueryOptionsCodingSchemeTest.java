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
import java.util.HashMap;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.encoding.TypeMappingRegistry;

import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
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

public class QueryOptionsCodingSchemeTest extends ServiceTestCase {
	String testId = "LexEVS DataService Web Service Test (SOAP)";
	
	protected String getTestID() {	
		return testId;
	}

	public void testQueryOptionGoodCodingSchemeRestriction() throws Exception {
		QueryOptions options = new QueryOptions();
		options.setCodingScheme(ServiceTestCase.GO_SCHEME);
		CodingSchemeVersionOrTag csvt = new CodingSchemeVersionOrTag();
		csvt.setVersion(ServiceTestCase.GO_VERSION);
		options.setCodingSchemeVersionOrTag(csvt);
		
		CodingScheme cs = new CodingScheme();
		LexEVSDataService svc = LexEVSServiceHolder.instance().getLexEVSAppService();
		List<CodingScheme> results = svc.search(CodingScheme.class, cs, options);
		assertTrue(results.size() == 1);
		assertTrue(results.get(0).getCodingSchemeName()
				.equals(ServiceTestCase.GO_SCHEME));
	}
	
	public void testQueryOptionBadCodingSchemeRestriction() {
		QueryOptions options = new QueryOptions();
		options.setCodingScheme("INVALID_CODING_SCHEME");
		CodingScheme cs = new CodingScheme();
		LexEVSDataService svc = LexEVSServiceHolder.instance().getLexEVSAppService();
		try{
			List<CodingScheme> results = svc.search(CodingScheme.class, cs, options);
			//if this resolves, fail
			fail("Should not resolve with an invalid QueryOption.");
		} catch (Exception e){
			//this is good -- should throw an exception.
		}
	}
}

