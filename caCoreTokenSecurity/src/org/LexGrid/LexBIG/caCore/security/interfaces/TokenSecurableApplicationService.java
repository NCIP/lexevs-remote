package org.LexGrid.LexBIG.caCore.security.interfaces;

import gov.nih.nci.evs.security.SecurityToken;

public interface TokenSecurableApplicationService {
	
	public Boolean registerSecurityToken(String vocabulary, SecurityToken token) throws Exception;
}
	