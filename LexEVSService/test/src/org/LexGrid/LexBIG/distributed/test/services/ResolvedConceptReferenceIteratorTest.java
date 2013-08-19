/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.distributed.test.services;

import org.LexGrid.LexBIG.DataModel.Collections.ResolvedConceptReferenceList;
import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.Utility.Iterators.ResolvedConceptReferencesIterator;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

public class ResolvedConceptReferenceIteratorTest extends ServiceTestCase{

	public void testNext(){
		try {
			LexBIGService lbs = LexEVSServiceHolder.instance().getLexEVSAppService();
			CodedNodeSet cns = lbs.getCodingSchemeConcepts(THES_SCHEME, null);
			cns = cns.restrictToMatchingDesignations("heart", null, "startsWith", null);
			
			ResolvedConceptReferencesIterator rcrl = cns.resolve(null, null, null);
			
			String conceptCode = null;
			
			while (rcrl.hasNext()) {
				ResolvedConceptReference ref = rcrl.next();
				String currentCode = ref.getConceptCode();	
				assertFalse(currentCode.equals(conceptCode));
				conceptCode = currentCode;
			}
		} catch (Exception e) {
			fail("Exception Thrown");
		}
	}
	
	public void testNextInt(){
		try {
			LexBIGService lbs = LexEVSServiceHolder.instance().getLexEVSAppService();
			CodedNodeSet cns = lbs.getCodingSchemeConcepts(ServiceTestCase.THES_SCHEME, null);
			cns = cns.restrictToMatchingDesignations("heart", null, "startsWith", null);

			ResolvedConceptReferencesIterator rcrl = cns.resolve(null, null, null);

			ResolvedConceptReferenceList list1 = rcrl.next(10);
			ResolvedConceptReferenceList list2 = rcrl.next(10);

			assertFalse(compareResolvedConceptReferenceList(list1, list2));
		} catch (Exception e) {
			fail("Exception Thrown");
		}
	}

	private boolean compareResolvedConceptReferenceList(ResolvedConceptReferenceList list1, ResolvedConceptReferenceList list2){
		ResolvedConceptReference[] rcr1 = list1.getResolvedConceptReference();
		ResolvedConceptReference[] rcr2 = list2.getResolvedConceptReference();

		boolean found = false;
		for (int i = 0; i < rcr1.length; i++) {
			ResolvedConceptReference ref = rcr1[i];
			String searchCode = ref.getConceptCode();
			for (int j = 0; j < rcr2.length; j++) {
				ResolvedConceptReference searchRef = rcr2[j];
				String foundCode = searchRef.getConceptCode();
				if(searchCode.equals(foundCode)){
					found = true;
				}
			}
		}
		return found;
	}
}
