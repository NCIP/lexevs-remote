package org.LexGrid.LexBIG.testUtil;

import java.util.Arrays;

import org.LexGrid.LexBIG.DataModel.Collections.ResolvedConceptReferenceList;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Exceptions.LBInvocationException;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.PropertyType;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.Utility.Iterators.ResolvedConceptReferencesIterator;

public class LexEVSRemoteSmokeTest {
	
	public void run(){
		LexBIGService svc = LexEVSServiceHolder.instance().getFreshLexEVSAppService();
		try {
//			Arrays.asList(svc.getSupportedCodingSchemes().
//					getCodingSchemeRendering()).stream().forEach(x -> System.out.println(
//					x.getCodingSchemeSummary().getFormalName() + " : " + x.getCodingSchemeSummary().getRepresentsVersion()));
			CodedNodeSet set = svc.getCodingSchemeConcepts("NCI Metathesaurus", null);
			//set = set.restrictToCodes(Constructors.createConceptReferenceList("C4123"));
			set = set.restrictToProperties(null, 
					new PropertyType[]{PropertyType.PRESENTATION}, Constructors.createLocalNameList("NDFRT"), null, null);
			ResolvedConceptReferencesIterator list = set.resolve(null, null, null);
			if(list.hasNext()){Arrays.asList(list.next().
					getEntity().getPresentation()).
					stream().forEach(x -> 
					System.out.println(x.getSource()[0].getContent()));}
		} catch (LBInvocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new LexEVSRemoteSmokeTest().run();

	}

}
