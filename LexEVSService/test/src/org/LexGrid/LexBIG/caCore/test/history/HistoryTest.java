/*
 * Copyright: (c) 2004-2006 Mayo Foundation for Medical Education and
 * Research (MFMER).  All rights reserved.  MAYO, MAYO CLINIC, and the
 * triple-shield Mayo logo are trademarks and service marks of MFMER.
 *
 * Except as contained in the copyright notice above, the trade names, 
 * trademarks, service marks, or product names of the copyright holder shall
 * not be used in advertising, promotion or otherwise in connection with
 * this Software without prior written authorization of the copyright holder.
 * 
 * Licensed under the Eclipse Public License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 * 
 * 		http://www.eclipse.org/legal/epl-v10.html
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.LexGrid.LexBIG.caCore.test.history;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import gov.nih.nci.system.applicationservice.ApplicationService;

import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.caCore.applicationservice.QueryOptions;
import org.LexGrid.LexBIG.caCore.connection.orm.interceptors.EVSHibernateInterceptor;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.codingSchemes.CodingScheme;
import org.LexGrid.commonTypes.EntityDescription;
import org.LexGrid.naming.Mappings;
import org.LexGrid.naming.SupportedAssociation;
import org.LexGrid.naming.SupportedAssociationQualifier;
import org.LexGrid.naming.SupportedCodingScheme;
import org.LexGrid.relations.Association;
import org.LexGrid.relations.AssociationSource;
import org.LexGrid.versions.SystemRelease;
import org.apache.commons.lang.ArrayUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import junit.framework.TestCase;

public class HistoryTest extends ServiceTestCase
{
	private final String test_id = "History Tests";
	
	public void testGetHistoryDAO() throws Exception {
		//Will throw an Exception if it tries to search on a non-History DAO
		LexEVSApplicationService service = LexEVSServiceHolder.instance().getLexEVSAppService();
		service.search(SystemRelease.class, new SystemRelease());
	}
	
	public void testGetSystemRelease() throws Exception {
		//Will throw an Exception if it tries to search on a non-History DAO
		LexEVSApplicationService service = LexEVSServiceHolder.instance().getLexEVSAppService();
		
		List<Object> returnList = service.search(SystemRelease.class, new SystemRelease());
		assertTrue(returnList.size() > 0);
	}
	
	public void testGetSystemReleaseByID() throws Exception {
		//Will throw an Exception if it tries to search on a non-History DAO
		LexEVSApplicationService service = LexEVSServiceHolder.instance().getLexEVSAppService();
		SystemRelease sr = new SystemRelease();
		sr.setReleaseId("03.10j");
		
		List<SystemRelease> returnList = service.search(SystemRelease.class, sr);
		assertTrue(returnList.size() == 1);
		
		assertTrue(returnList.get(0).getReleaseId().equals("03.10j"));
	}
	
	public void testGetSystemReleaseByIDWildCard() throws Exception {
		//Will throw an Exception if it tries to search on a non-History DAO
		LexEVSApplicationService service = LexEVSServiceHolder.instance().getLexEVSAppService();
		SystemRelease sr = new SystemRelease();
		sr.setReleaseId("03.1*");
		
		List<SystemRelease> returnList = service.search(SystemRelease.class, sr);
		assertTrue(returnList.size() > 1);
	}
	
	public void testGetSystemReleaseByReleaseDateRange() throws Exception {
		//Will throw an Exception if it tries to search on a non-History DAO
		LexEVSApplicationService service = LexEVSServiceHolder.instance().getLexEVSAppService();
		
		SimpleDateFormat date = new SimpleDateFormat("MM-dd-yyyy");
		Date startDate = date.parse("04-21-2003");

		Date endDate = date.parse("05-21-2007");
			
		DetachedCriteria dc = DetachedCriteria.forClass(SystemRelease.class);
		dc.add(Restrictions.between("_releaseDate", startDate, endDate));
		
		List<SystemRelease> returnList = service.query(dc);
		System.out.println(returnList.size());
		assertTrue(returnList.size() > 1);
	}
	
	public void testGetSystemReleaseByEntityDescription() throws Exception {
		//Will throw an Exception if it tries to search on a non-History DAO
		LexEVSApplicationService service = LexEVSServiceHolder.instance().getLexEVSAppService();
		EntityDescription ed = new EntityDescription();
		ed.setContent("*04.09a*");	
		
		SystemRelease sr = new SystemRelease();
		sr.setEntityDescription(ed);
			
		List<SystemRelease> returnList = service.search(SystemRelease.class, sr);
		assertTrue(returnList.size() == 1);
		
		assertTrue(returnList.get(0).getReleaseId().equals("04.09a"));
	}
	
	public void testGetSystemReleaseQueryOptionRightCodingScheme() throws Exception {
		QueryOptions qo = new QueryOptions();
		qo.setCodingScheme(ServiceTestCase.THES_SCHEME);
		
		LexEVSApplicationService service = LexEVSServiceHolder.instance().getLexEVSAppService();
		
		List<Object> returnList = service.search(SystemRelease.class, new SystemRelease(), qo);
		assertTrue(returnList.size() > 0);
	}
	
	public void testGetSystemReleaseQueryOptionWrongCodingScheme() throws Exception {
		QueryOptions qo = new QueryOptions();
		qo.setCodingScheme(ServiceTestCase.SNOMED_SCHEME);
		
		try {
			LexEVSApplicationService service = LexEVSServiceHolder.instance().getLexEVSAppService();
			List<Object> returnList = service.search(SystemRelease.class, new SystemRelease(), qo);
			//should throw an error if you request History from a Coding Scheme that doesn't have any
			//loaded. If it gets here, fail.
			fail("Retrieved History from a Coding Scheme with no History loaded");
		} catch (Exception e) {
			//this is good -- should throw an Exception here.
		}
	}	
}