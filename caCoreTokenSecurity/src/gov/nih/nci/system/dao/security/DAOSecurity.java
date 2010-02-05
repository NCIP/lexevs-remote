package gov.nih.nci.system.dao.security;

public interface DAOSecurity {
		
	public SecurityKey getSecurityKey(UserCredentials credentials) throws Exception;
}
