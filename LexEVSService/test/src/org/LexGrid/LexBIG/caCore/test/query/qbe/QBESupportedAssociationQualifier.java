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
import org.LexGrid.naming.SupportedAssociationQualifier;

public class QBESupportedAssociationQualifier extends ServiceTestCase
{
	private final String test_id = "QBE Tests";
	
	@Override
	protected String getTestID() {
		return test_id;
	}
	
	public void testGetSupportedAssociationQualifierByName() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		SupportedAssociationQualifier saq = new SupportedAssociationQualifier();
		saq.setLocalId("owl:allValuesFrom");
		
		List<SupportedAssociationQualifier> sourceList = service.search(SupportedAssociationQualifier.class, saq);	
		
		assertTrue(sourceList != null);	
		
		SupportedAssociationQualifier value = sourceList.get(0);
		assertTrue(value.getLocalId().equals("owl:allValuesFrom"));	
		assertTrue(value.getUri().equals("http://www.w3.org/2002/07/owl#allValuesFrom"));	
	}
	
	public void testGetSupportedAssociationQualifierByWrongName() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		SupportedAssociationQualifier saq = new SupportedAssociationQualifier();
		saq.setLocalId("allValuesFrom_WRONG_NAME");
		
		List<SupportedAssociationQualifier> sourceList = service.search(SupportedAssociationQualifier.class, saq);	
		
		assertTrue(sourceList != null);	
		
		assertTrue(sourceList != null);	
		assertTrue(sourceList.size() == 0);			
	}
	
	public void testGetSupportedAssociationQualifierByWildCard() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		SupportedAssociationQualifier saq = new SupportedAssociationQualifier();
		saq.setLocalId("*ValuesFrom");
		
		List<SupportedAssociationQualifier> sourceList = service.search(SupportedAssociationQualifier.class, saq);	
		
		assertTrue(sourceList != null);	
		
		//Should return at least allValuesFrom and someValuesFrom
		assertTrue(sourceList.size() >= 2);	
		
	}
		
	public void testSearchCodingSchemeBySupportedAssociationQualifier() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CodingScheme cs = new CodingScheme();
		cs.setCodingSchemeName(ServiceTestCase.META_SCHEME);
		cs.setRepresentsVersion(ServiceTestCase.META_VERSION);
		
		Mappings mappings = new Mappings();
		SupportedAssociationQualifier saq = new SupportedAssociationQualifier();
		saq.setLocalId("HCD");
		
		mappings.addSupportedAssociationQualifier(saq);
		cs.setMappings(mappings);

		List<CodingScheme> sourceList = service.search(CodingScheme.class, cs);	
	
		assertTrue(sourceList != null);	
		assertTrue(sourceList.size() == 1);
				
		CodingScheme scheme = sourceList.get(0);
		assertTrue(scheme.getCodingSchemeName().equals(ServiceTestCase.META_SCHEME));
		assertTrue(scheme.getRepresentsVersion().equals(ServiceTestCase.META_VERSION));
	}
	
	public void testSearchCodingSchemeBySupportedAssociationQualifierWrongName() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CodingScheme cs = new CodingScheme();
		cs.setCodingSchemeName(ServiceTestCase.META_SCHEME);
		cs.setRepresentsVersion(ServiceTestCase.META_VERSION);
		
		Mappings mappings = new Mappings();
		SupportedAssociationQualifier saq = new SupportedAssociationQualifier();
		saq.setLocalId("HCD_WRONG_NAME");
		
		mappings.addSupportedAssociationQualifier(saq);
		cs.setMappings(mappings);

		List<CodingScheme> sourceList = service.search(CodingScheme.class, cs);	
	
		assertTrue(sourceList != null);	
		assertTrue(sourceList.size() == 0);				
	}
	
	public void testSearchCodingSchemeBySupportedAssociationQualifierWildCard() throws Exception {
		ApplicationService service = LexEVSServiceHolder.instance().getAppService();
		
		CodingScheme cs = new CodingScheme();
		cs.setCodingSchemeName(ServiceTestCase.META_SCHEME);
		cs.setRepresentsVersion(ServiceTestCase.META_VERSION);
		
		Mappings mappings = new Mappings();
		SupportedAssociationQualifier saq = new SupportedAssociationQualifier();
		saq.setLocalId("H*D");
		
		mappings.addSupportedAssociationQualifier(saq);
		cs.setMappings(mappings);

		List<CodingScheme> sourceList = service.search(CodingScheme.class, cs);	
	
		assertTrue(sourceList != null);	
		assertTrue(sourceList.size() == 1);
				
		CodingScheme scheme = sourceList.get(0);
		assertTrue(scheme.getCodingSchemeName().equals(ServiceTestCase.META_SCHEME));
		assertTrue(scheme.getRepresentsVersion().equals(ServiceTestCase.META_VERSION));
	}
}