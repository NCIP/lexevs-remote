package gov.nih.nci.system.dao.security;
/**
 * @author Shaziya Muhsin
 * 
 */
public class UserCredentials {
	private Object credentials;
	public UserCredentials(){
		credentials = new UserCredentials();
	}
	public UserCredentials(Object userCredentials){
		setCredentials(userCredentials);		
	}
	public Object getCredentials(){
		return credentials;
	}
	public void setCredentials(Object userCredentials){
		this.credentials = userCredentials;
	}

}
