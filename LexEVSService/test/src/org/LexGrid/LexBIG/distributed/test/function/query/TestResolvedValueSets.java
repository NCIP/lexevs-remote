/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
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
		if(cs.getCodingSchemeURI().equals(RESOLVEDVS_URI)){
			pass = true;
		}
	}
	assertTrue(pass);
}

public void testGetEntitiesForURI(){
	boolean pass = false;
	ResolvedConceptReferenceList list = service.getValueSetEntitiesForURI(RESOLVEDVS_URI);
	assertTrue(list.getResolvedConceptReferenceCount() > 0);
	for(ResolvedConceptReference ref: list.getResolvedConceptReference()){
		if(ref.getCode().equals(RESOLVEDVS_CONCEPTA)){
			pass = true;
		}
	}
	
	assertTrue(pass);
}

public void testGetVersionsInResolutions() throws URISyntaxException{
	URI uri = new URI(RESOLVEDVS_URI);
	CodingScheme cs = service.getResolvedValueSetForValueSetURI(uri);
	assertNotNull(cs);
	AbsoluteCodingSchemeVersionReferenceList list = service.getListOfCodingSchemeVersionsUsedInResolution(cs);
	assertTrue(list.getAbsoluteCodingSchemeVersionReferenceCount() > 0 );
	for(AbsoluteCodingSchemeVersionReference ref: list.getAbsoluteCodingSchemeVersionReference()){
		assertTrue(ref.getCodingSchemeURN().equals(THES_URN));
		ref.getCodingSchemeVersion().equals(RESOLVEDVS_THES_VERSION);
	}
}

public void testGetResovlveValueSetsForConceptReference(){
	ConceptReference cs = new ConceptReference();
	cs.setCode(RESOLVEDVS_CONCEPTB);
	cs.setCodingSchemeName(THES_SCHEME);
	List<CodingScheme> schemes = service.getResolvedValueSetsForConceptReference(cs );
	assertTrue(schemes.size() > 0);
	boolean pass1 = false;
	boolean pass2 = false;
	for(CodingScheme c: schemes){
		if(c.getCodingSchemeURI().equals(TARGETRVS_URIA)){
			pass1 = true;
		}
		if(c.getCodingSchemeURI().equals(TARGETRVS_URIB)){
			pass2 = true;
		}
	}
	assertTrue(pass1);
	assertTrue(pass2);

}
}
