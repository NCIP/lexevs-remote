package org.LexGrid.LexBIG.distributed.test.features;

import static org.junit.Assert.*;

import org.LexGrid.LexBIG.DataModel.Collections.NameAndValueList;
import org.LexGrid.LexBIG.DataModel.Core.AssociatedConcept;
import org.LexGrid.LexBIG.Impl.testUtility.DataTestUtils;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeGraph;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.junit.Before;
import org.junit.Test;

public class TestLEXEVS_3464 extends ServiceTestCase {

	LexEVSApplicationService svc;
	private AssociatedConcept[] associatedConcept;
	private CodedNodeGraph cng;
	
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		svc = LexEVSServiceHolder.instance().getLexEVSAppService();
		cng =	svc.getNodeGraph(META_SCHEME, Constructors.createCodingSchemeVersionOrTagFromVersion(META_VERSION), null);
		
		associatedConcept = cng.resolveAsList(Constructors.createConceptReference("DIABT", "NCI Metathesaurus"), true, true, 1, 1, null, null, null, -1)
				.getResolvedConceptReference(0)
				.getSourceOf()
				.getAssociation()[0]
				.getAssociatedConcepts()
				.getAssociatedConcept();
	}
	
	@Test
	public void testQualsNotNull() throws Exception {	
		NameAndValueList quals = DataTestUtils.getConceptReference(associatedConcept, "U000010").getAssociationQualifiers();
		assertNotNull(quals);
	}

	@Test
	public void testRelaQualMODID() throws Exception {	
		NameAndValueList quals = DataTestUtils.getConceptReference(associatedConcept, "U000010").getAssociationQualifiers();
		assertTrue(DataTestUtils.isQualifierNameAndValuePresent("MODIFIER_ID", "900000000000451002", quals));
	}

	@Test
	public void testRelaQualCHARTYPEID() throws Exception {	
		NameAndValueList quals = DataTestUtils.getConceptReference(associatedConcept, "U000010").getAssociationQualifiers();
		assertTrue(DataTestUtils.isQualifierNameAndValuePresent("CHARACTERISTIC_TYPE_ID", "900000000000011006", quals));
	}
}
