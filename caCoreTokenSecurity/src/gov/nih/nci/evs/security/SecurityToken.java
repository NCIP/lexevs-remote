package gov.nih.nci.evs.security;

import java.io.Serializable;
import java.lang.annotation.*;
	/**
	* SecurityToken class implements security policy to EVS Vocabularies. Access request information can be specified in this class. This information will be used to grant or deny permission to access a specific vocabulary. 	**/
public class SecurityToken  implements Serializable
{
	/**
	* An attribute to allow serialization of the domain objects
	*/
	private static final long serialVersionUID = 1234567890L;

	
	
		/**
	* Specifies the code for this token	**/
	private String accessToken;
	/**
	* Retreives the value of accessToken attribue
	* @return accessToken
	**/

	  public String getAccessToken(){
		return accessToken;
	}

	/**
	* Sets the value of accessToken attribue
	**/

	public void setAccessToken(String accessToken){
		this.accessToken = accessToken;
	}
	
	
		/**
	* Specifies the password for this token	**/
	private String password;
	/**
	* Retreives the value of password attribue
	* @return password
	**/

	  public String getPassword(){
		return password;
	}

	/**
	* Sets the value of password attribue
	**/

	public void setPassword(String password){
		this.password = password;
	}
	
	
		/**
	* Specifies the user name	**/
	private String userName;
	/**
	* Retreives the value of userName attribue
	* @return userName
	**/

	  public String getUserName(){
		return userName;
	}

	/**
	* Sets the value of userName attribue
	**/

	public void setUserName(String userName){
		this.userName = userName;
	}
	
}