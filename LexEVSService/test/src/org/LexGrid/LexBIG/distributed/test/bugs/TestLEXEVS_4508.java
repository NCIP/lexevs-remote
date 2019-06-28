package org.LexGrid.LexBIG.distributed.test.bugs;

import static org.junit.Assert.*;

import org.LexGrid.LexBIG.DataModel.Collections.ResolvedConceptReferenceList;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Extensions.Generic.LexBIGServiceConvenienceMethods;
import org.LexGrid.LexBIG.Impl.testUtility.ServiceHolder;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.junit.Before;
import org.junit.Test;

public class TestLEXEVS_4508 extends ServiceTestCase{
	LexEVSApplicationService svs;


	@Test
	public void test() throws LBException {
		 svs = LexEVSServiceHolder.instance().getLexEVSAppService();
        
        LexBIGServiceConvenienceMethods lbscm = (LexBIGServiceConvenienceMethods) svs
                    .getGenericExtension("LexBIGServiceConvenienceMethods");
        lbscm.setLexBIGService(svs);
        
            ResolvedConceptReferenceList list = lbscm.getHierarchyRoots(OBIB_SCHEME, Constructors.createCodingSchemeVersionOrTagFromVersion(OBIB_VERSION),
                    null);
            assertNotNull(list);
            assertTrue(list.getResolvedConceptReferenceCount() > 0);
	}

}
