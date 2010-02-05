package gov.nih.nci.system.dao.security;

/**
 * @author Shaziya Muhsin *
 */

public class SecurityKey {
	private Object key;
	private UserCredentials credentials;
	
	public SecurityKey(){
		key = new Object();
		credentials = new UserCredentials();
	}	
	public SecurityKey(Object key, UserCredentials credentials){
		setSecurityKey(key);
		setCredentials(credentials);
	}
	public void setCredentials(UserCredentials credentials){
		this.credentials = credentials;
	}
	public void setSecurityKey(Object key){
		this.key = key;
	}
	public Object getSecurityKey(){
		return this.key;
	}
	public UserCredentials getCredentials(){
		return this.credentials;
	}
	


}
