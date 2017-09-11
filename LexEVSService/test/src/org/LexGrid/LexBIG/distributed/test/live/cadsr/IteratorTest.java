package org.LexGrid.LexBIG.distributed.test.live.cadsr;

import org.LexGrid.LexBIG.DataModel.Collections.LocalNameList;
import org.LexGrid.LexBIG.DataModel.Collections.ResolvedConceptReferenceList;
import org.LexGrid.LexBIG.DataModel.Collections.SortOptionList;
import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.Utility.LBConstants.MatchAlgorithms;
import org.LexGrid.LexBIG.Utility.Iterators.ResolvedConceptReferencesIterator;
import org.LexGrid.LexBIG.distributed.test.live.testutil.RemoteServerUtil;

public class IteratorTest {
	LexBIGService lbSvc = null;
	
	public IteratorTest() throws Exception{
		lbSvc = RemoteServerUtil.createLexBIGService();
	}
	
	public void run() throws Exception {
		
		String searchItem = "Blood";
		String vocabName = RemoteServerUtil.NCIT_SCHEME_NAME;

		CodedNodeSet cns = lbSvc.getNodeSet(vocabName, null, null);
		cns = cns.restrictToMatchingDesignations(searchItem, CodedNodeSet.SearchDesignationOption.PREFERRED_ONLY,
				MatchAlgorithms.exactMatch.name(), null);

		CodedNodeSet.PropertyType propTypes[] = new CodedNodeSet.PropertyType[2];
		propTypes[0] = CodedNodeSet.PropertyType.PRESENTATION;
		propTypes[1] = CodedNodeSet.PropertyType.DEFINITION;

		SortOptionList sortCriteria = Constructors.createSortOptionList(new String[] { "matchToQuery" });
		ResolvedConceptReferenceList rcrl = cns.resolveToList(sortCriteria, null, new LocalNameList(), propTypes, true,
				10);
		System.out.println("Printing from list: ");
		for (int i = 0; i < rcrl.getResolvedConceptReferenceCount(); i++) {
			ResolvedConceptReference rcr = rcrl.getResolvedConceptReference(i);
			System.out.println(rcr.getCode());
		}
		ResolvedConceptReferencesIterator rcri = cns.resolve(sortCriteria, null, new LocalNameList(), propTypes, true);
		if (rcri != null) {
			System.out.println("Printing from iterator: ");
			while (rcri.hasNext()) {
				ResolvedConceptReference rcr = rcri.next();
				System.out.println(rcr.getCode());
			}
		}

	}

}
