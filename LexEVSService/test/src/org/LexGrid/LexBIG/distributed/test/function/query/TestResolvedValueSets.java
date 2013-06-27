package org.LexGrid.LexBIG.distributed.test.function.query;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.LexGrid.LexBIG.DataModel.Collections.AbsoluteCodingSchemeVersionReferenceList;
import org.LexGrid.LexBIG.DataModel.Collections.ResolvedConceptReferenceList;
import org.LexGrid.LexBIG.DataModel.Core.AbsoluteCodingSchemeVersionReference;
import org.LexGrid.LexBIG.DataModel.Core.ConceptReference;
import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.codingSchemes.CodingScheme;
import org.lexgrid.resolvedvalueset.LexEVSResolvedValueSetService;
import org.lexgrid.resolvedvalueset.impl.LexEVSResolvedValueSetServiceImpl;

public class TestResolvedValueSets extends ServiceTestCase {
final static String testID = "testResolvedValueSets";
LexEVSResolvedValueSetService service;

@Override
protected String getTestID()
{
    return testID;
}

public void setUp(){
	service  = new LexEVSResolvedValueSetServiceImpl(
			LexEVSServiceHolder.instance().getLexEVSAppService());
}
public void testgetListofResolvedValueSets() throws LBException{
	List<CodingScheme> schemes = service.listAllResolvedValueSets();
	assertTrue(schemes.size() > 0);
	boolean pass = false;
	for(CodingScheme cs: schemes){
		if(cs.getCodingSchemeURI().equals("http://ncit:C81224")){
			pass = true;
		}
	}
	assertTrue(pass);
}

public void testGetEntitiesForURI(){
	boolean pass = false;
	ResolvedConceptReferenceList list = service.getValueSetEntitiesForURI("http://ncit:C81224");
	assertTrue(list.getResolvedConceptReferenceCount() > 0);
	for(ResolvedConceptReference ref: list.getResolvedConceptReference()){
		if(ref.getCode().equals("C81204")){
			pass = true;
		}
	}
	
	assertTrue(pass);
}

public void testGetVersionsInResolutions() throws URISyntaxException{
	URI uri = new URI("http://ncit:C81224");
	CodingScheme cs = service.getResolvedValueSetForValueSetURI(uri);
	assertNotNull(cs);
	AbsoluteCodingSchemeVersionReferenceList list = service.getListOfCodingSchemeVersionsUsedInResolution(cs);
	assertTrue(list.getAbsoluteCodingSchemeVersionReferenceCount() > 0 );
	for(AbsoluteCodingSchemeVersionReference ref: list.getAbsoluteCodingSchemeVersionReference()){
		assertTrue(ref.getCodingSchemeURN().equals("http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#"));
		ref.getCodingSchemeVersion().equals("13.03d");
	}
}

public void testGetResovlveValueSetsForConceptReference(){
	ConceptReference cs = new ConceptReference();
	cs.setCode("C81211");
	cs.setCodingSchemeName("NCI_Thesaurus");
	List<CodingScheme> schemes = service.getResolvedValueSetsForConceptReference(cs );
	assertTrue(schemes.size() > 0);
}
}
