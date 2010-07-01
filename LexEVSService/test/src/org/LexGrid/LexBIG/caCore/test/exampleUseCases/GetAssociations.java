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
package org.LexGrid.LexBIG.caCore.test.exampleUseCases;

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
		
		assertTrue(results.size() == 3);	
		
		String[] expectedCodes = new String[]{"C32735", "C12392", "C43612"};
		
		String[] returnedCodes = new String[3];
		
		int i=0;
		for(AssociationSource assocSource : results){
			returnedCodes[i] = assocSource.getTarget()[0].getTargetEntityCode();
			i++;
		}
		
		Arrays.sort(expectedCodes);
		Arrays.sort(returnedCodes);
		
		for(int j=0;j<expectedCodes.length;j++){
			assertTrue(expectedCodes[j].equals(returnedCodes[j]));
		}
	}
	
}