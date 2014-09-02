/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.test.query.qbe;

import java.util.List;

import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.caCore.applicationservice.QueryOptions;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.relations.AssociationQualification;
import org.LexGrid.relations.AssociationSource;

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
		options.setCodingScheme(ServiceTestCase.SNOMED_SCHEME);
		CodingSchemeVersionOrTag csvt = new CodingSchemeVersionOrTag();
		csvt.setVersion(ServiceTestCase.SNOMED_VERSION);
		options.setCodingSchemeVersionOrTag(csvt);
		
		AssociationSource ai = new AssociationSource();	
		ai.setSourceEntityCode("12300005");
		
		List<AssociationSource> assocList = service.search(AssociationSource.class, ai, options);	
		
		assertTrue(assocList.size() > 0);
		
		
		boolean found = false;
		
		for(int i = 0; i < assocList.size(); i++){
			if(assocList.get(i).getTarget()[0].getTargetEntityCode().equals("90264002")){
				found = true;
				AssociationQualification[] quals = assocList.get(i).getTarget()[0].getAssociationQualification();
				assertTrue(quals.length == 1);
				AssociationQualification foundQual = quals[0];
				assertTrue(foundQual.getAssociationQualifier().equals("rela"));
				assertTrue(foundQual.getQualifierText().getContent().equals("isa"));
			}
		}		
		assertTrue(found);
	}	
}