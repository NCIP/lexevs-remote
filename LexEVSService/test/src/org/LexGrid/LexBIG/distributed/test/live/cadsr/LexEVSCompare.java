package org.LexGrid.LexBIG.distributed.test.live.cadsr;
// Rob Wynne, MSC

import java.util.HashMap;

import org.LexGrid.LexBIG.DataModel.Collections.LocalNameList;
import org.LexGrid.LexBIG.DataModel.Collections.ResolvedConceptReferenceList;
import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Exceptions.LBInvocationException;
import org.LexGrid.LexBIG.Exceptions.LBParameterException;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.Utility.LBConstants;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSService;
import org.LexGrid.LexBIG.distributed.test.live.testutil.RemoteServerUtil;
import org.LexGrid.concepts.Entity;
import org.LexGrid.concepts.Presentation;



public class LexEVSCompare {
	
	static LexEVSService lbSvc;

    public LexEVSCompare() throws Exception{
    	configure();
    }
	
	public void configure () throws Exception {
			lbSvc = (LexEVSService) RemoteServerUtil.createLexBIGService();
	}
	
	public int runCompare(){
		int codesize = 0;
		try {
			HashMap<String, String> countCodes = new HashMap<String,String>();			

				String searchTerm = "1994 Data Acquisition Manual (Interim/Revised)";
				CodedNodeSet nodeSet = lbSvc.getNodeSet("NCI_Thesaurus", null, null);

				//Tell the api that you want to search only the PRESENTATION type properties                  
				CodedNodeSet.PropertyType[] types = new CodedNodeSet.PropertyType[1];               
				types[0] = CodedNodeSet.PropertyType.PRESENTATION;

				//Only want to search properties with the property name of "FULL_SYN"
				LocalNameList propLnL = new LocalNameList();
				propLnL.addEntry("FULL_SYN");
				
				System.out.print(searchTerm);
				nodeSet = nodeSet.restrictToMatchingProperties(propLnL,types,null,null, null,searchTerm,LBConstants.MatchAlgorithms.contains.name(),null);
	
				ResolvedConceptReferenceList rcl = nodeSet.resolveToList(null, null, null, 100);
				int count = rcl.getResolvedConceptReferenceCount();

	
				//Now iterate through the returned entities and display the FULL_SYN PT property with source=CAHUB
				for (int i=0; i < rcl.getResolvedConceptReferenceCount(); i++){
					ResolvedConceptReference rcr = rcl.getResolvedConceptReference(i);
					Entity entity = rcr.getReferencedEntry();
					Presentation[] presProps = entity.getPresentation();
					for(int y=0;y<presProps.length;y++){
						Presentation pres = presProps[y];
						if(pres.getPropertyName().equals("FULL_SYN") && (pres.getRepresentationalForm().equals("PT") || pres.getRepresentationalForm().equals("SY")) && pres.getSource(0).getContent().equals("NCI")){
							System.out.print("\t" + pres.getValue().getContent() + "\t" + entity.getEntityCode()); 
							countCodes.put(entity.getEntityCode(), "blah");
						}
					}
				}
				System.out.print("\n");
			codesize = countCodes.keySet().size();
			System.out.println("Number of matched codes: " + codesize);

		} catch (IndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LBInvocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LBParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return codesize;
	}	
	
	
}
