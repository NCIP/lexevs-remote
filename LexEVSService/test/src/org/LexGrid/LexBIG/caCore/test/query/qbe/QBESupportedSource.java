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
package org.LexGrid.LexBIG.caCore.test.query.qbe;

import gov.nih.nci.system.applicationservice.ApplicationService;

import java.util.List;

import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.codingSchemes.CodingScheme;
import org.LexGrid.naming.Mappings;
import org.LexGrid.naming.SupportedSource;

public class QBESupportedSource extends ServiceTestCase
{
	private final String test_id = "QBE Tests";
	
	@Override
	protected String getTestID() {
		return test_id;
	}
	
	public void testGetSupportedSourceByName() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		SupportedSource ss = new SupportedSource();
		ss.setLocalId("MSH2004_2003_12_12");
		
		List<SupportedSource> sourceList = service.search(SupportedSource.class, ss);	
		
		assertTrue(sourceList != null);				
		
		SupportedSource source = sourceList.get(0);
		assertTrue(source.getLocalId().equals("MSH2004_2003_12_12"));
	}
	
	public void testGetSupportedSourceByWrongName() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		SupportedSource ss = new SupportedSource();
		ss.setLocalId("MSH2004_2003_12_12_WRONG_NAME");
		
		List<SupportedSource> sourceList = service.search(SupportedSource.class, ss);	
		
		assertTrue(sourceList != null);	
		assertTrue(sourceList.size() == 0);			
	}
	
	public void testGetSupportedSourceByWildCard() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		SupportedSource ss = new SupportedSource();
		ss.setLocalId("CSP200*");
		
		List<SupportedSource> sourceList = service.search(SupportedSource.class, ss);	
		
		assertTrue(sourceList != null);	
		
		//Should return CSP2000, CSP2002, and CSP2003
		System.out.println(sourceList.size());
		assertTrue("Size: " + sourceList.size(), sourceList.size() == 3);			
	}
		
	public void testSearchCodingSchemeBySupportedSource() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CodingScheme cs = new CodingScheme();
		cs.setCodingSchemeName(ServiceTestCase.THES_SCHEME);
		cs.setRepresentsVersion(ServiceTestCase.THES_VERSION);
		
		Mappings mappings = new Mappings();
		SupportedSource scs = new SupportedSource();
		scs.setLocalId("CDISC");
		
		mappings.addSupportedSource(scs);
		cs.setMappings(mappings);

		List<CodingScheme> sourceList = service.search(CodingScheme.class, cs);	
	
		assertTrue(sourceList != null);	
		assertTrue(sourceList.size() == 1);
				
		CodingScheme scheme = sourceList.get(0);
		assertTrue(scheme.getCodingSchemeName().equals(ServiceTestCase.THES_SCHEME));
		assertTrue(scheme.getRepresentsVersion().equals(ServiceTestCase.THES_VERSION));
	}
	
	public void testSearchCodingSchemeBySupportedSourceWrongName() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CodingScheme cs = new CodingScheme();
		cs.setCodingSchemeName(ServiceTestCase.META_SCHEME);
		cs.setRepresentsVersion(ServiceTestCase.META_VERSION);
		
		Mappings mappings = new Mappings();
		SupportedSource scs = new SupportedSource();
		scs.setLocalId("ICD10AE_WRONG_NAME");
		
		mappings.addSupportedSource(scs);
		cs.setMappings(mappings);

		List<CodingScheme> sourceList = service.search(CodingScheme.class, cs);	
	
		assertTrue(sourceList != null);	
		assertTrue(sourceList.size() == 0);
	}
	
	public void testSearchCodingSchemeBySupportedSourceWildCard() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CodingScheme cs = new CodingScheme();
		cs.setCodingSchemeName(ServiceTestCase.THES_SCHEME);
		cs.setRepresentsVersion(ServiceTestCase.THES_VERSION);
		
		Mappings mappings = new Mappings();
		SupportedSource scs = new SupportedSource();
		scs.setLocalId("CD*SC");
		
		mappings.addSupportedSource(scs);
		cs.setMappings(mappings);

		List<CodingScheme> sourceList = service.search(CodingScheme.class, cs);	
	
		assertTrue(sourceList != null);	
		assertTrue(sourceList.size() == 1);
				
		CodingScheme scheme = sourceList.get(0);
		assertTrue(scheme.getCodingSchemeName().equals(ServiceTestCase.THES_SCHEME));
		assertTrue(scheme.getRepresentsVersion().equals(ServiceTestCase.THES_VERSION));
	}
	
}