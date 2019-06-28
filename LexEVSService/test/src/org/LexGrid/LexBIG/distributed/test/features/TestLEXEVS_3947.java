package org.LexGrid.LexBIG.distributed.test.features;

import org.LexGrid.LexBIG.DataModel.Core.AssociatedConcept;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeGraph;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.codingSchemes.CodingScheme;
import org.LexGrid.naming.Mappings;
import org.junit.Before;
import org.junit.Test;

import junit.framework.JUnit4TestAdapter;

public class TestLEXEVS_3947 extends ServiceTestCase {

	LexEVSApplicationService svc;
	private AssociatedConcept[] associatedConcept;
	private CodedNodeGraph cng;
	
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		svc = LexEVSServiceHolder.instance().getLexEVSAppService();
	}
	
	
	@Test
	public void testSupportedQualifierLoad() throws Exception {
		CodingScheme scheme = svc.resolveCodingScheme(SNOMED_SCHEME, 
				Constructors.createCodingSchemeVersionOrTagFromVersion(SNOMED_VERSION));
		Mappings mappings = scheme.getMappings();
		assertTrue(mappings.getSupportedAssociationQualifierAsReference().stream().
		anyMatch(x -> x.getContent().equals("MODIFIER_ID")));
		assertTrue(mappings.getSupportedAssociationQualifierAsReference().stream().
				anyMatch(x -> x.getContent().equals("CHARACTERISTIC_TYPE_ID")));
		
	}
	
	public static junit.framework.Test suite() {  
		return new JUnit4TestAdapter(TestLEXEVS_3947.class);  
	} 

}
