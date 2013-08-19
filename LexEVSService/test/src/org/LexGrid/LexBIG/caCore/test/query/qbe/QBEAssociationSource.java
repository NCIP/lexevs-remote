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
import org.LexGrid.relations.AssociationSource;
import org.LexGrid.relations.AssociationTarget;

public class QBEAssociationSource extends ServiceTestCase
{
	private final String test_id = "QBE Tests";
	
	@Override
	protected String getTestID() {
		return test_id;
	}
	
	public void stestRetrieveAssociationSource() throws Exception {
		LexEVSApplicationService service = LexEVSServiceHolder.instance().getLexEVSAppService();
		QueryOptions options = new QueryOptions();
		options.setCodingScheme(ServiceTestCase.GO_SCHEME);
		CodingSchemeVersionOrTag csvt = new CodingSchemeVersionOrTag();
		csvt.setVersion(ServiceTestCase.GO_VERSION);
		options.setCodingSchemeVersionOrTag(csvt);
		
		AssociationSource as = new AssociationSource();
		
		//EntityTypes
		
		List<AssociationSource> sourceList = service.search(AssociationSource.class, as, options);	
		
		assertTrue(sourceList != null);
		assertTrue(sourceList.size() > 0);
	}
	
	public void testGetAssociatedCodes() throws Exception {
		LexEVSApplicationService service = LexEVSServiceHolder.instance().getLexEVSAppService();
		QueryOptions options = new QueryOptions();
		options.setCodingScheme(ServiceTestCase.GO_SCHEME);
		CodingSchemeVersionOrTag csvt = new CodingSchemeVersionOrTag();
		csvt.setVersion(ServiceTestCase.GO_VERSION);
		options.setCodingSchemeVersionOrTag(csvt);
		
		AssociationSource as = new AssociationSource();	
		as.setSourceEntityCode("GO:0015157");
				
		List<AssociationSource> assocList = service.search(AssociationSource.class, as, options);	
		
		//should find 1 associations where "GO_0015478" is the source
		//If there are more versions of "GO" loaded, it will return more,
		//that is why it checks '> 0'
		assertTrue(assocList.size() > 0);
		
		AssociationSource i = assocList.get(0);
		assertTrue(i.getSourceEntityCodeNamespace().equals("gene_ontology"));
		assertTrue(i.getSourceEntityCode().equals("GO:0015157"));
		

		AssociationTarget[] t = i.getTarget();
		assertTrue(t.length == 1);
		AssociationTarget target = t[0];
		assertTrue(target.getTargetEntityCodeNamespace().equals("gene_ontology"));
		assertTrue(target.getTargetEntityCode().equals("GO:0015144"));		
	}	
}