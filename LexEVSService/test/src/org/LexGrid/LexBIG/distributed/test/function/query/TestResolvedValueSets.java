package org.LexGrid.LexBIG.distributed.test.function.query;

import java.util.List;

import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.codingSchemes.CodingScheme;
import org.lexgrid.resolvedvalueset.LexEVSResolvedValueSetService;
import org.lexgrid.resolvedvalueset.impl.LexEVSResolvedValueSetServiceImpl;

public class TestResolvedValueSets extends ServiceTestCase {
final static String testID = "testResolvedValueSets";

@Override
protected String getTestID()
{
    return testID;
}

public void testgetListofResolvedValueSets() throws LBException{
	LexEVSResolvedValueSetService service = new LexEVSResolvedValueSetServiceImpl();
	List<CodingScheme> schemes = service.listAllResolvedValueSets();
	assertTrue(schemes.size() > 0);
	
//	for(CodingScheme cs: schemes){
//		cs.getCodingSchemeName();
//	}
	
}
}
