/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.test.security;

import gov.nih.nci.cagrid.cqlquery.Predicate;
import gov.nih.nci.evs.security.SecurityToken;
import gov.nih.nci.system.applicationservice.ApplicationException;
import gov.nih.nci.system.client.ApplicationServiceProvider;
import gov.nih.nci.system.query.cql.CQLAttribute;
import gov.nih.nci.system.query.cql.CQLObject;
import gov.nih.nci.system.query.cql.CQLPredicate;
import gov.nih.nci.system.query.cql.CQLQuery;
import gov.nih.nci.system.query.hibernate.HQLCriteria;

import java.util.ArrayList;
import java.util.List;

import org.LexGrid.LexBIG.caCore.applicationservice.QueryOptions;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.codingSchemes.CodingScheme;
import org.LexGrid.commonTypes.Text;
import org.LexGrid.concepts.Entity;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class LexEVSDataServiceSecurityTest extends ServiceTestCase {
	String testId = "LexEVSDataServiceSecurityTest";
	
	String serviceName = LexEVSServiceHolder._service;

	@Override
	protected String getTestID() {
		// TODO Auto-generated method stub
		return testId;
	}

	public void testConnectToUnsecuredVocab() {
		LexEVSApplicationService service = null;
		try {
			service = (LexEVSApplicationService) ApplicationServiceProvider
					.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl,
							serviceName);
		} catch (Exception e1) {
			e1.printStackTrace();
			fail("Error Connecting to LexEVS Data Service");
		}

		try {
			CodingScheme cs = new CodingScheme();
			cs.setCodingSchemeName(ServiceTestCase.THES_SCHEME);
			cs.setRepresentsVersion(ServiceTestCase.THES_VERSION);
			List<CodingScheme> sourceList = service.search(CodingScheme.class,
					cs);
			assertTrue(sourceList.size() == 1);
		} catch (Exception e) {
			e.printStackTrace();
			fail("error resolving NCI Thesaurus");
		}
	}

	public void testConnectToSecuredVocabWithToken() {
		SecurityToken token = new SecurityToken();
		token.setAccessToken(MEDDRA_TOKEN);

		LexEVSApplicationService service = null;
		try {
			service = (LexEVSApplicationService) ApplicationServiceProvider
					.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl,
							serviceName);
			service.registerSecurityToken(ServiceTestCase.MEDDRA_URN, token);
		} catch (Exception e1) {
			e1.printStackTrace();
			fail("Error Connecting to LexEVS Data Service");
		}

		try {
			CodingScheme cs = new CodingScheme();
			cs.setCodingSchemeName(ServiceTestCase.MEDDRA_SCHEME);
			cs.setRepresentsVersion(ServiceTestCase.MEDDRA_VERSION);
			List<CodingScheme> sourceList = service.search(CodingScheme.class,
					cs);
			assertTrue(sourceList.size() == 1);
		} catch (Exception e) {
			e.printStackTrace();
			fail("error resolving MedDRA");
		}
	}

	public void testConnectToSecuredVocabWithoutToken() {
		LexEVSApplicationService service = null;
		try {
			service = (LexEVSApplicationService) ApplicationServiceProvider
					.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl,
							serviceName);
		} catch (Exception e1) {
			e1.printStackTrace();
			fail("Error Connecting to LexEVS Data Service");
		}
		try {
			CodingScheme cs = new CodingScheme();
			cs.setCodingSchemeName(ServiceTestCase.MEDDRA_SCHEME);
			cs.setRepresentsVersion(ServiceTestCase.MEDDRA_VERSION);
			List<CodingScheme> sourceList = service.search(CodingScheme.class,
					cs);
			assertTrue(sourceList.size() == 0);
		} catch (Exception e) {
			e.printStackTrace();
			fail("error resolving MedDRA");
		}
	}

	public void testMultipleSessions() {
		// Set up two sessions
		LexEVSApplicationService service1 = null;
		LexEVSApplicationService service2 = null;
		try {
			service1 = (LexEVSApplicationService) ApplicationServiceProvider
					.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl,
							serviceName);
			service2 = (LexEVSApplicationService) ApplicationServiceProvider
					.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl,
							serviceName);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Error Connecting to LexEVS Data Service");
		}

		// try the first one (unsecured)
		try {
			CodingScheme cs = new CodingScheme();
			cs.setCodingSchemeName(ServiceTestCase.MEDDRA_SCHEME);
			cs.setRepresentsVersion(ServiceTestCase.MEDDRA_VERSION);
			List<CodingScheme> sourceList = service1.search(CodingScheme.class,
					cs);
			assertTrue(sourceList.size() == 0);
		} catch (Exception e) {
			e.printStackTrace();
			fail("error resolving MedDRA");
		}

		// try the second one (unsecured)
		try {
			CodingScheme cs = new CodingScheme();
			cs.setCodingSchemeName(ServiceTestCase.MEDDRA_SCHEME);
			cs.setRepresentsVersion(ServiceTestCase.MEDDRA_VERSION);
			List<CodingScheme> sourceList = service2.search(CodingScheme.class,
					cs);
			assertTrue(sourceList.size() == 0);
		} catch (Exception e) {
			e.printStackTrace();
			fail("error resolving MedDRA");
		}

		// try the first one again (secured with token)
		SecurityToken token = new SecurityToken();
		token.setAccessToken(MEDDRA_TOKEN);
		try {
			service1.registerSecurityToken(ServiceTestCase.MEDDRA_URN, token);
			CodingScheme cs = new CodingScheme();
			cs.setCodingSchemeName(ServiceTestCase.MEDDRA_SCHEME);
			cs.setRepresentsVersion(ServiceTestCase.MEDDRA_VERSION);
			List<CodingScheme> sourceList = service1.search(CodingScheme.class,
					cs);
			assertTrue(sourceList.size() == 1);
		} catch (Exception e) {
			e.printStackTrace();
			fail("error resolving MedDRA");
		}

		// try the second one (secured WITHOUT token)
		try {
			CodingScheme cs = new CodingScheme();
			cs.setCodingSchemeName(ServiceTestCase.MEDDRA_SCHEME);
			cs.setRepresentsVersion(ServiceTestCase.MEDDRA_VERSION);
			List<CodingScheme> sourceList = service2.search(CodingScheme.class,
					cs);
			assertTrue(sourceList.size() == 0);
		} catch (Exception e) {
			e.printStackTrace();
			fail("error resolving MedDRA");
		}
	}

	public void testGetAssociationSecurityNoToken() {
		LexEVSApplicationService service = null;
		try {
			service = (LexEVSApplicationService) ApplicationServiceProvider
					.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl,
							serviceName);
		} catch (Exception e1) {
			e1.printStackTrace();
			fail("Error Connecting to LexEVS Data Service");
		}

		CodingScheme cs = new CodingScheme();
		cs.setCodingSchemeName(ServiceTestCase.MEDDRA_SCHEME);
		cs.setRepresentsVersion(ServiceTestCase.MEDDRA_VERSION);

		try {
			// try to get the mappings from a secured coding scheme
			List mappings = service.getAssociation(cs, "_mappings");
			// should not return anything without a token
			assertTrue(mappings.size() == 0);
		} catch (ApplicationException e) {
			e.printStackTrace();
			fail("error resolving MedDRA");
		}
	}

	public void testGetAssociationSecurityWithToken() {
		SecurityToken token = new SecurityToken();
		token.setAccessToken(MEDDRA_TOKEN);

		LexEVSApplicationService service = null;
		try {
			service = (LexEVSApplicationService) ApplicationServiceProvider
					.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl,
							serviceName);
			service.registerSecurityToken(ServiceTestCase.MEDDRA_URN, token);
		} catch (Exception e1) {
			e1.printStackTrace();
			fail("Error Connecting to LexEVS Data Service");
		}

		CodingScheme cs = new CodingScheme();
		cs.setCodingSchemeName(ServiceTestCase.MEDDRA_SCHEME);

		try {
			// try to get the mappings from a secured coding scheme
			List mappings = service.getAssociation(cs, "_mappings");
			// should not return anything without a token
			assertTrue(mappings.size() >= 1);
		} catch (ApplicationException e) {
			e.printStackTrace();
			fail("error resolving MedDRA");
		}
	}

	public void testQuerySDKCQLSecurityNoToken() {
		LexEVSApplicationService service = null;
		try {
			service = (LexEVSApplicationService) ApplicationServiceProvider
					.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl,
							serviceName);
		} catch (Exception e1) {
			e1.printStackTrace();
			fail("Error Connecting to LexEVS Data Service");
		}

		CQLQuery query = new CQLQuery();
		CQLObject target = new CQLObject();

		target.setName("org.LexGrid.codingSchemes.CodingScheme");
		CQLAttribute at1 = new CQLAttribute();
		at1.setName("_codingSchemeName");
		at1.setValue(ServiceTestCase.MEDDRA_SCHEME);
		at1.setPredicate(CQLPredicate.EQUAL_TO);

		target.setAttribute(at1);
		query.setTarget(target);

		try {
			// try to get the mappings from a secured coding scheme
			List cs = service.query(query);
			// should not return anything without a token
			assertTrue(cs.size() == 0);
		} catch (ApplicationException e) {
			e.printStackTrace();
			fail("error resolving MedDRA");
		}
	}

	public void testQuerySDKCQLSecurityWithToken() {
		SecurityToken token = new SecurityToken();
		token.setAccessToken(MEDDRA_TOKEN);

		LexEVSApplicationService service = null;
		try {
			service = (LexEVSApplicationService) ApplicationServiceProvider
					.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl,
							serviceName);
			service.registerSecurityToken(ServiceTestCase.MEDDRA_URN, token);
		} catch (Exception e1) {
			e1.printStackTrace();
			fail("Error Connecting to LexEVS Data Service");
		}

		CQLQuery query = new CQLQuery();
		CQLObject target = new CQLObject();

		target.setName("org.LexGrid.codingSchemes.CodingScheme");
		CQLAttribute at1 = new CQLAttribute();
		at1.setName("_codingSchemeName");
		at1.setValue(ServiceTestCase.MEDDRA_SCHEME);
		at1.setPredicate(CQLPredicate.EQUAL_TO);

		target.setAttribute(at1);
		query.setTarget(target);

		try {
			// try to get the mappings from a secured coding scheme
			List cs = service.query(query);
			// should not return anything without a token
			assertTrue(cs.size() >= 1);
		} catch (ApplicationException e) {
			e.printStackTrace();
			fail("error resolving MedDRA");
		}
	}
	
	public void testQueryGridCQLSecurityNoToken() {
		LexEVSApplicationService service = null;
		try {
			service = (LexEVSApplicationService) ApplicationServiceProvider
					.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl,
							serviceName);
		} catch (Exception e1) {
			e1.printStackTrace();
			fail("Error Connecting to LexEVS Data Service");
		}

		gov.nih.nci.cagrid.cqlquery.CQLQuery query = new gov.nih.nci.cagrid.cqlquery.CQLQuery();
		gov.nih.nci.cagrid.cqlquery.Object target = new gov.nih.nci.cagrid.cqlquery.Object();

		target.setName("org.LexGrid.codingSchemes.CodingScheme");
		gov.nih.nci.cagrid.cqlquery.Attribute at1 = new gov.nih.nci.cagrid.cqlquery.Attribute();
		at1.setName("_codingSchemeName");
		at1.setValue(ServiceTestCase.MEDDRA_SCHEME);
		at1.setPredicate(Predicate.EQUAL_TO);

		target.setAttribute(at1);
		query.setTarget(target);

		try {
			// try to get the mappings from a secured coding scheme
			List cs = service.query(query);
			// should not return anything without a token
			assertTrue(cs.size() == 0);
		} catch (ApplicationException e) {
			e.printStackTrace();
			fail("error resolving MedDRA");
		}
	}

	public void testQueryGridCQLSecurityWithToken() {
		SecurityToken token = new SecurityToken();
		token.setAccessToken(MEDDRA_TOKEN);

		LexEVSApplicationService service = null;
		try {
			service = (LexEVSApplicationService) ApplicationServiceProvider
					.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl,
							serviceName);
			service.registerSecurityToken(ServiceTestCase.MEDDRA_URN, token);
		} catch (Exception e1) {
			e1.printStackTrace();
			fail("Error Connecting to LexEVS Data Service");
		}

		gov.nih.nci.cagrid.cqlquery.CQLQuery query = new gov.nih.nci.cagrid.cqlquery.CQLQuery();
		gov.nih.nci.cagrid.cqlquery.Object target = new gov.nih.nci.cagrid.cqlquery.Object();

		target.setName("org.LexGrid.codingSchemes.CodingScheme");
		gov.nih.nci.cagrid.cqlquery.Attribute at1 = new gov.nih.nci.cagrid.cqlquery.Attribute();
		at1.setName("_codingSchemeName");
		at1.setValue(ServiceTestCase.MEDDRA_SCHEME);
		at1.setPredicate(Predicate.EQUAL_TO);

		target.setAttribute(at1);
		query.setTarget(target);

		try {
			// try to get the mappings from a secured coding scheme
			List cs = service.query(query);
			// should not return anything without a token
			assertTrue(cs.size() >= 1);
		} catch (ApplicationException e) {
			e.printStackTrace();
			fail("error resolving MedDRA");
		}
	}

	public void testQueryDetachedCriteriaSecurityNoToken() {
		LexEVSApplicationService service = null;
		try {
			service = (LexEVSApplicationService) ApplicationServiceProvider
					.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl,
							serviceName);
		} catch (Exception e1) {
			e1.printStackTrace();
			fail("Error Connecting to LexEVS Data Service");
		}

		DetachedCriteria dc = DetachedCriteria.forClass(CodingScheme.class);
		dc.add(Restrictions.eq("_codingSchemeName", ServiceTestCase.MEDDRA_SCHEME));

		try {
			// try to get the mappings from a secured coding scheme
			List cs = service.query(dc);
			// should not return anything without a token
			assertTrue(cs.size() == 0);
		} catch (ApplicationException e) {
			e.printStackTrace();
			fail("error resolving MedDRA");
		}
	}

	public void testQueryDetachedCriteriaSecurityWithToken() {
		SecurityToken token = new SecurityToken();
		token.setAccessToken(MEDDRA_TOKEN);

		LexEVSApplicationService service = null;
		try {
			service = (LexEVSApplicationService) ApplicationServiceProvider
					.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl,
							serviceName);
			service.registerSecurityToken(ServiceTestCase.MEDDRA_URN, token);
		} catch (Exception e1) {
			e1.printStackTrace();
			fail("Error Connecting to LexEVS Data Service");
		}

		DetachedCriteria dc = DetachedCriteria.forClass(CodingScheme.class);
		dc.add(Restrictions.eq("_codingSchemeName", ServiceTestCase.MEDDRA_SCHEME));

		try {
			// try to get the mappings from a secured coding scheme
			List cs = service.query(dc);
			// should not return anything without a token
			assertTrue(cs.size() >= 1);
		} catch (ApplicationException e) {
			e.printStackTrace();
			fail("error resolving MedDRA");
		}
	}

	public void testQueryHQLSecurityNoToken() {
		LexEVSApplicationService service = null;
		try {
			service = (LexEVSApplicationService) ApplicationServiceProvider
					.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl,
							serviceName);
		} catch (Exception e1) {
			e1.printStackTrace();
			fail("Error Connecting to LexEVS Data Service");
		}

		HQLCriteria hql = new HQLCriteria(
				"FROM org.LexGrid.codingSchemes.CodingScheme"
						+ " as cs WHERE cs._codingSchemeName = '"
						+ ServiceTestCase.MEDDRA_SCHEME + "'");

		try {
			// try to get the mappings from a secured coding scheme
			List cs = service.query(hql);
			// should not return anything without a token
			assertTrue(cs.size() == 0);
		} catch (ApplicationException e) {
			e.printStackTrace();
			fail("error resolving MedDRA");
		}
	}

	public void testQueryHQLSecurityWithToken() {
		SecurityToken token = new SecurityToken();
		token.setAccessToken(MEDDRA_TOKEN);

		LexEVSApplicationService service = null;
		try {
			service = (LexEVSApplicationService) ApplicationServiceProvider
					.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl,
							serviceName);
			service.registerSecurityToken(ServiceTestCase.MEDDRA_URN, token);
		} catch (Exception e1) {
			e1.printStackTrace();
			fail("Error Connecting to LexEVS Data Service");
		}

		HQLCriteria hql = new HQLCriteria(
				"FROM org.LexGrid.codingSchemes.CodingScheme"
						+ " as cs WHERE cs._codingSchemeName = '"
						+ ServiceTestCase.MEDDRA_SCHEME + "'");

		try {
			// try to get the mappings from a secured coding scheme
			List cs = service.query(hql);
			// should not return anything without a token
			assertTrue(cs.size() >= 1);
		} catch (ApplicationException e) {
			e.printStackTrace();
			fail("error resolving MedDRA");
		}
	}

	public void testQueryWithRowMarkerNoToken() {
		LexEVSApplicationService service = null;
		try {
			service = (LexEVSApplicationService) ApplicationServiceProvider
					.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl,
							serviceName);
		} catch (Exception e1) {
			e1.printStackTrace();
			fail("Error Connecting to LexEVS Data Service");
		}

		DetachedCriteria dc = DetachedCriteria.forClass(Entity.class);
		dc.add(Restrictions.like("_entityCode", "1005%"));
		dc.add(Restrictions.eq("_entityCodeNamespace", ServiceTestCase.MEDDRA_SCHEME));
		
		try {
			// try to get concepts from a secured coding scheme
			List cs = service.query(dc, 100, Entity.class.getName());
			// should not return anything without a token
			assertTrue(cs.size() == 0);
		} catch (ApplicationException e) {
			e.printStackTrace();
			fail("error resolving MedDRA");
		}
	}

	//TODO -- Breaks Translator!!
	public void testQueryWithRowMarkerWithToken() {
		SecurityToken token = new SecurityToken();
		token.setAccessToken(MEDDRA_TOKEN);

		LexEVSApplicationService service = null;
		try {
			service = (LexEVSApplicationService) ApplicationServiceProvider
					.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl,
							serviceName);
			service.registerSecurityToken(ServiceTestCase.MEDDRA_URN, token);
		} catch (Exception e1) {
			e1.printStackTrace();
			fail("Error Connecting to LexEVS Data Service");
		}

		DetachedCriteria dc = DetachedCriteria.forClass(Entity.class);
		dc.add(Restrictions.like("_entityCode", "10052%"));
		dc.add(Restrictions.eq("_entityCodeNamespace", ServiceTestCase.MEDDRA_SCHEME));

		try {
			// try to get concepts from a secured coding scheme
			List cs = service.query(dc, 100, Entity.class.getName());
			// should not return anything without a token
			assertTrue(cs.size() > 0);
		} catch (ApplicationException e) {
			e.printStackTrace();
			fail("error resolving MedDRA");
		}
	}

	public void testSearchListSecurityNoToken() {
		LexEVSApplicationService service = null;
		try {
			service = (LexEVSApplicationService) ApplicationServiceProvider
					.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl,
							serviceName);
		} catch (Exception e1) {
			e1.printStackTrace();
			fail("Error Connecting to LexEVS Data Service");
		}

		Entity meddraConcept = new Entity();
		meddraConcept.setEntityCodeNamespace(ServiceTestCase.MEDDRA_SCHEME);
		meddraConcept.setEntityCode("10056389");

		Entity thesConcept = new Entity();
		thesConcept.setEntityCodeNamespace("NCI_Thesaurus");
		thesConcept.setEntityCode("C12727");

		List conceptList = new ArrayList();
		conceptList.add(meddraConcept);
		conceptList.add(thesConcept);

		try {
			// try to get concepts from a secured coding scheme
			List<Entity> cs = service.search(Entity.class.getName(), conceptList);
			// should not return anything without a token
			assertTrue(cs.size() > 0);

			for(Entity c : cs){
				assertTrue(c.getEntityCode().equals("C12727"));
			}		
		} catch (ApplicationException e) {
			e.printStackTrace();
			fail("error resolving MedDRA");
		}
	}

	public void testSearchListSecurityWithToken() {
		SecurityToken token = new SecurityToken();
		token.setAccessToken(MEDDRA_TOKEN);

		LexEVSApplicationService service = null;
		try {
			service = (LexEVSApplicationService) ApplicationServiceProvider
					.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl,
							serviceName);
			service.registerSecurityToken(ServiceTestCase.MEDDRA_URN, token);
		} catch (Exception e1) {
			e1.printStackTrace();
			fail("Error Connecting to LexEVS Data Service");
		}

		Entity meddraConcept = new Entity();
		meddraConcept.setEntityCodeNamespace(ServiceTestCase.MEDDRA_SCHEME);
		meddraConcept.setEntityCode("10056389");

		Entity thesConcept = new Entity();
		thesConcept.setEntityCodeNamespace("NCI_Thesaurus");
		thesConcept.setEntityCode("C12727");

		List conceptList = new ArrayList();
		conceptList.add(meddraConcept);
		conceptList.add(thesConcept);

		try {
			// try to get concepts from a secured coding scheme
			List<Entity> cs = service.search(Entity.class.getName(), conceptList);
			assertTrue("Size: " + cs.size(), cs.size() >= 2);
			
			boolean found = false;
			for(Entity c : cs){
				if(c.getEntityCodeNamespace().equals(ServiceTestCase.MEDDRA_SCHEME) && 
						c.getEntityCode().equals("10056389")){
					found = true;
				}
			}		
			assertTrue(found);
		} catch (ApplicationException e) {
			e.printStackTrace();
			fail("error resolving MedDRA");
		}
	}

	public void testSearchClassSecurityNoToken() {
		LexEVSApplicationService service = null;
		try {
			service = (LexEVSApplicationService) ApplicationServiceProvider
					.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl,
							serviceName);
		} catch (Exception e1) {
			e1.printStackTrace();
			fail("Error Connecting to LexEVS Data Service");
		}

		Entity meddraConcept = new Entity();
		meddraConcept.setEntityCodeNamespace(ServiceTestCase.MEDDRA_SCHEME);
		meddraConcept.setEntityCode("10056389");

		try {
			// try to get concepts from a secured coding scheme
			List cs = service.search(Entity.class.getName(), meddraConcept);
			// should not return anything without a token
			assertTrue(cs.size() == 0);

		} catch (ApplicationException e) {
			e.printStackTrace();
			fail("error resolving MedDRA");
		}
	}

	public void testSearchClassSecurityWithToken() {
		SecurityToken token = new SecurityToken();
		token.setAccessToken(MEDDRA_TOKEN);

		LexEVSApplicationService service = null;
		try {
			service = (LexEVSApplicationService) ApplicationServiceProvider
					.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl,
							serviceName);
			service.registerSecurityToken(ServiceTestCase.MEDDRA_URN, token);
		} catch (Exception e1) {
			e1.printStackTrace();
			fail("Error Connecting to LexEVS Data Service");
		}

		Entity meddraConcept = new Entity();
		meddraConcept.setEntityCodeNamespace(ServiceTestCase.MEDDRA_SCHEME);
		meddraConcept.setEntityCode("10056389");

		try {
			// try to get concepts from a secured coding scheme
			List cs = service.search(Entity.class.getName(), meddraConcept);
			assertTrue(cs.size() >= 1);
		} catch (ApplicationException e) {
			e.printStackTrace();
			fail("error resolving MedDRA");
		}
	}

	public void testSearchPathSecurityNoToken() {
		//Set up some QueryOptions to make this quicker
		QueryOptions options = new QueryOptions();
		options.setCodingScheme(ServiceTestCase.MEDDRA_SCHEME);
		
		LexEVSApplicationService service = null;
		try {
			service = (LexEVSApplicationService) ApplicationServiceProvider
					.getApplicationServiceFromUrl(ServiceTestCase.serviceUrl,
							serviceName);
		} catch (Exception e1) {
			e1.printStackTrace();
			fail("Error Connecting to LexEVS Data Service");
		}

		String path = "org.LexGrid.concepts.Concept,org.LexGrid.commonTypes.Property,org.LexGrid.commonTypes.Text";
		
		Text meddraText = new Text();
		meddraText.setContent("C0158991");

		try {
			// try to get concepts from a secured coding scheme
			List cs = service.search(path, meddraText, options);
			// should not return anything without a token - fail if it does
			fail("Resolved a Secure Vocab without a Token.");

		} catch (ApplicationException e) {
			//Should throw a Security Exception
		}
	}
}
