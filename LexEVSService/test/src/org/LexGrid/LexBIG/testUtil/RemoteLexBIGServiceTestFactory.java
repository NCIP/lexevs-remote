package org.LexGrid.LexBIG.testUtil;

import org.LexGrid.LexBIG.Impl.testUtility.LexBIGServiceTestFactory;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;

public class RemoteLexBIGServiceTestFactory implements LexBIGServiceTestFactory {

    @Override
    public LexBIGService getLbs() {
        return LexEVSServiceHolder.instance().getLexEVSAppService();
    }
}
