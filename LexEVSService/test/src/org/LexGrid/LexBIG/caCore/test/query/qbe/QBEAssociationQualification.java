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

import java.util.List;

import gov.nih.nci.system.applicationservice.ApplicationService;

import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.caCore.applicationservice.QueryOptions;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.codingSchemes.CodingScheme;
import org.LexGrid.naming.Mappings;
import org.LexGrid.commonTypes.types.EntityTypes;
import org.LexGrid.naming.SupportedAssociation;
import org.LexGrid.naming.SupportedAssociationQualifier;
import org.LexGrid.naming.SupportedCodingScheme;
import org.LexGrid.relations.Association;
import org.LexGrid.relations.AssociationQualification;
import org.LexGrid.relations.AssociationSource;
import org.LexGrid.relations.AssociationTarget;

import junit.framework.TestCase;

public class QBEAssociationQualification extends ServiceTestCase
{
	private final String test_id = "QBE Tests";
	
	@Override
	protected String getTestID() {
		return test_id;
	}
	
	public void testGetAssociationQualifications() throws Exception {
		LexEVSApplicationService service = LexEVSServiceHolder.instance().getLexEVSAppService();
		QueryOptions options = new QueryOptions();
		options.setCodingScheme(ServiceTestCase.ZEBRAFISH_SCHEME);
		CodingSchemeVersionOrTag csvt = new CodingSchemeVersionOrTag();
		csvt.setVersion(ServiceTestCase.ZEBRAFISH_VERSION);
		options.setCodingSchemeVersionOrTag(csvt);
		
		AssociationSource ai = new AssociationSource();	
		ai.setSourceEntityCode("ZFA_0000035");
		
		List<AssociationSource> assocList = service.search(AssociationSource.class, ai, options);	
		
		assertTrue(assocList.size() > 0);
		
		boolean found = false;
		
		for(AssociationSource instance : assocList){
			if(instance.getTarget()[0].getTargetEntityCode().equals("ZFS_0000027")){
				found = true;
				AssociationQualification[] quals = instance.getTarget()[0].getAssociationQualification();
				assertTrue(quals.length == 1);
				AssociationQualification foundQual = quals[0];
				assertTrue(foundQual.getAssociationQualifier().equals("owl:someValuesFrom"));
			}
		}		
		assertTrue(found);
	}	
}