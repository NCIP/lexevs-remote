/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.test.exampleUseCases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.caCore.applicationservice.QueryOptions;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.relations.AssociationSource;

public class GetAssociations extends ServiceTestCase
{
	private final String test_id = "Get Associations Tests";
	
	private QueryOptions queryOptions;
	
	@Override
	protected String getTestID() {
		return test_id;
	}
	
	public void setUp(){
		//Set up some Query Options so we're always working with the NCI Thes.
		queryOptions = new QueryOptions();
		queryOptions.setCodingScheme(ServiceTestCase.THES_SCHEME);
		
		
		CodingSchemeVersionOrTag csvt = new CodingSchemeVersionOrTag();
		csvt.setVersion(ServiceTestCase.THES_VERSION);
		queryOptions.setCodingSchemeVersionOrTag(csvt);
	}
	
	public void testGetAllAssociatedConcepts() throws Exception {
		LexEVSApplicationService service = LexEVSServiceHolder.instance().getLexEVSAppService();
		
		AssociationSource source = new AssociationSource();
		source.setSourceEntityCode("C33309");
		
		List<AssociationSource> results = service.search(AssociationSource.class, source, queryOptions);
		
		assertEquals(5,results.size());	
		
		String[] expectedCodes = new String[]{"C32735", "C12392", "C43612"};
		
		List<String> returnedCodes = new ArrayList<String>();
		
		
		for(int i=0;i < results.size() ; i++){
			String targetCode = results.get(i).getTarget()[0].getTargetEntityCode();
			if(! targetCode.startsWith("@")){
				returnedCodes.add( targetCode);
			}
		}
		String[] sortedCodes = Arrays.copyOf(returnedCodes.toArray(), returnedCodes.toArray().length, String[].class);
		Arrays.sort(expectedCodes);
		Arrays.sort(sortedCodes);
		
		for(int j=0;j<expectedCodes.length;j++){
			assertTrue(expectedCodes[j].equals(sortedCodes[j]));
		}
	}
	
}