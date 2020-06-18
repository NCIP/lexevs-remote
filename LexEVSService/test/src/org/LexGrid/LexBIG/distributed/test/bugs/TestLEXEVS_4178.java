package org.LexGrid.LexBIG.distributed.test.bugs;

import java.util.Arrays;

import org.LexGrid.LexBIG.DataModel.Collections.ResolvedConceptReferenceList;
import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Exceptions.LBInvocationException;
import org.LexGrid.LexBIG.Exceptions.LBParameterException;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.PropertyType;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.SearchDesignationOption;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.util.assertedvaluesets.AssertedValueSetParameters;
import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.lexgrid.valuesets.sourceasserted.SourceAssertedValueSetService;

public class TestLEXEVS_4178 extends ServiceTestCase {
	LexEVSApplicationService svc;
	CodedNodeSet cns;
	
	@Before
	protected void setUp() throws Exception {
		svc = LexEVSServiceHolder.instance().getFreshLexEVSAppService();

		cns = svc.getCodingSchemeConcepts(META_SCHEME, 
				Constructors.createCodingSchemeVersionOrTagFromVersion(META_VERSION));
		super.setUp();
	}

	@Test
	public void test() throws LBInvocationException, LBParameterException {

		cns = cns.restrictToMatchingDesignations("Adrenergic Agonist", SearchDesignationOption.ALL, "contains", null);     
		cns = cns.restrictToProperties(
		        		null, 
		        		new PropertyType[]{PropertyType.PRESENTATION}, 
		        		Constructors.createLocalNameList("MED-RT"), 
		        		null, 
		        		null);
		        
		        ResolvedConceptReferenceList rcrl = cns.resolveToList(null, null, null, 10);
		        assertTrue(rcrl != null);
		        assertTrue(rcrl.getResolvedConceptReferenceCount() > 0);
		        assertTrue(Arrays.asList(rcrl.getResolvedConceptReference()).stream().anyMatch(x -> x.getEntityDescription().getContent().equals("Adrenergic Agonist")));
	}

}
