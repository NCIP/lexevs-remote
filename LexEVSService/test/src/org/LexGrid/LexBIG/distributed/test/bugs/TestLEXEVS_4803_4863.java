package org.LexGrid.LexBIG.distributed.test.bugs;

import java.util.stream.Stream;

import org.LexGrid.LexBIG.DataModel.Collections.ResolvedConceptReferenceList;
import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Extensions.Generic.NodeGraphResolutionExtension.AlgorithmMatch;
import org.LexGrid.LexBIG.Impl.function.LexBIGServiceTestCase;
import org.LexGrid.LexBIG.Impl.testUtility.ServiceHolder;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.PropertyType;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.junit.Before;
import org.junit.Test;

public class TestLEXEVS_4803_4863 extends ServiceTestCase {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() throws LBException {
        CodedNodeSet cns = LexEVSServiceHolder.instance().getFreshLexEVSAppService()
        		.getCodingSchemeConcepts(META_URN, 
        				Constructors.createCodingSchemeVersionOrTagFromVersion(META_VERSION));
        cns = cns.restrictToProperties(null, 
        		new PropertyType[]{PropertyType.PRESENTATION}, 
        		null, 
        		null, 
        		Constructors.createNameAndValueList("source-code", "BLOOD DYSCRASIA"));
        ResolvedConceptReferenceList nodeRefs = cns.resolveToList(null, null, null, 10);
        assertNotNull(nodeRefs);
        assertTrue(nodeRefs.getResolvedConceptReferenceCount() > 0);
        assertEquals(1, nodeRefs.getResolvedConceptReferenceCount());
        assertTrue(Stream.of(nodeRefs.getResolvedConceptReference())
        		.anyMatch(x -> entityPropertyQualifierExistsForName(x , "source-code")));
        assertTrue(Stream.of(nodeRefs.getResolvedConceptReference())
        		.anyMatch(x -> entityPropertyQualifierExistsForValue(x , "BLOOD DYSCRASIA")));
	}
	
	@Test
	public void testToo() throws LBException {
    CodedNodeSet cns = LexEVSServiceHolder.instance().getFreshLexEVSAppService()
    		.getCodingSchemeConcepts(META_URN, 
    				Constructors.createCodingSchemeVersionOrTagFromVersion(META_VERSION));
    cns = cns.restrictToProperties(null, 
        		new PropertyType[]{PropertyType.PRESENTATION}, 
        		null, 
        		null, 
        		Constructors.createNameAndValueList("source-code", "BLOOD"));
        ResolvedConceptReferenceList nodeRefs = cns.resolveToList(null, null, null, -1);
        assertNotNull(nodeRefs);
        assertTrue(nodeRefs.getResolvedConceptReferenceCount() == 0);
	}
	
	@Test
	public void testBadTestCode() throws LBException {
	    CodedNodeSet cns = LexEVSServiceHolder.instance().getFreshLexEVSAppService()
	    		.getCodingSchemeConcepts(META_URN, 
	    				Constructors.createCodingSchemeVersionOrTagFromVersion(META_VERSION));
	    cns = cns.restrictToProperties(null, 
        		new PropertyType[]{PropertyType.PRESENTATION}, 
        		null, 
        		null, 
        		Constructors.createNameAndValueList("source-code", "BLACKER"));
        ResolvedConceptReferenceList nodeRefs = cns.resolveToList(null, null, null, -1);
        assertNotNull(nodeRefs);
        assertFalse(nodeRefs.getResolvedConceptReferenceCount() > 0);
	}
	
	@Test
	public void testMatchToo() throws LBException {
	    CodedNodeSet cns = LexEVSServiceHolder.instance().getFreshLexEVSAppService()
	    		.getCodingSchemeConcepts(META_URN, 
	    				Constructors.createCodingSchemeVersionOrTagFromVersion(META_VERSION));
        cns = cns.restrictToMatchingProperties(null, new PropertyType[]{PropertyType.PRESENTATION},
        		null, null, Constructors.createNameAndValueList("source-code", "BLOOD DYSCRASIA"), "Blood", 
        		AlgorithmMatch.CONTAINS.getMatch(), null);
        ResolvedConceptReferenceList nodeRefs = cns.resolveToList(null, null, null, -1);
        assertNotNull(nodeRefs);
        assertTrue(nodeRefs.getResolvedConceptReferenceCount() > 0);
        assertEquals(1, nodeRefs.getResolvedConceptReferenceCount());
        assertTrue(Stream.of(nodeRefs.getResolvedConceptReference())
        		.anyMatch(x -> entityPropertyQualifierExistsForName(x , "source-code")));
        assertTrue(Stream.of(nodeRefs.getResolvedConceptReference())
        		.anyMatch(x -> entityPropertyQualifierExistsForValue(x , "BLOOD DYSCRASIA")));
	}
	
	@Test
	public void testBadTestCodeBadQualSearch() throws LBException {
	    CodedNodeSet cns = LexEVSServiceHolder.instance().getFreshLexEVSAppService()
	    		.getCodingSchemeConcepts(META_URN, 
	    				Constructors.createCodingSchemeVersionOrTagFromVersion(META_VERSION));
	    cns = cns.restrictToProperties(null, 
        		new PropertyType[]{PropertyType.PRESENTATION}, 
        		null, 
        		null, 
        		Constructors.createNameAndValueList("source-code", "blood dyscrasia"));
        ResolvedConceptReferenceList nodeRefs = cns.resolveToList(null, null, null, -1);
        assertNotNull(nodeRefs);
        assertFalse(nodeRefs.getResolvedConceptReferenceCount() > 0);
	}

	@Override
	protected String getTestID() {
		return "Untokenized Source Code";
	}
	
	private boolean entityPropertyQualifierExistsForName(ResolvedConceptReference ref, String name){
		return Stream.of(ref.getEntity().getPresentation())
		.anyMatch(x -> Stream.of(x.getPropertyQualifier())
				.anyMatch(y -> y.getPropertyQualifierName().equals(name)));
	}
	
	private boolean entityPropertyQualifierExistsForValue(ResolvedConceptReference ref, String value){
		return Stream.of(ref.getEntity().getPresentation())
		.anyMatch(x -> Stream.of(x.getPropertyQualifier())
				.anyMatch(y -> y.getValue().getContent().equals(value)));
	}
	

	

}
