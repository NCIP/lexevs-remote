package org.LexGrid.LexBIG.caCore.security;

import org.LexGrid.LexBIG.caCore.security.properties.LexEVSProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gov.nih.nci.evs.security.SecurityToken;
import gov.nih.nci.system.dao.security.DAOSecurity;
import gov.nih.nci.system.dao.security.SecurityKey;
import gov.nih.nci.system.dao.security.UserCredentials;

public class Validator {

	private LexEVSProperties lexEVSprops;
	private Logger log = LogManager.getLogger(Validator.class);
	
	
	public Validator(LexEVSProperties lexEVSprops){
		this.lexEVSprops = lexEVSprops;
	}
	
	/*
	 * Determine if the Vocab is Secured
	 */
	public boolean isSecured(String urn){
		return lexEVSprops.isInProperties(urn);
	}
	
	/**
	 * Validate the security token for a given licensed vocabulary using the following method:
	 *
	 */
	public boolean validate(String vocabularyName, SecurityToken securityToken) throws SecurityException
	{
		if(vocabularyName == null) {
			throw new SecurityException("Vocabulary Name must not be null");
		}

		try {
			String value = lexEVSprops.getProperty(vocabularyName);
			if (value == null) {
				log.debug("Vocab: " + vocabularyName + " is not secured");
				return true;
			}
		}
		catch(Exception e) {
			throw new SecurityException(e);
		}

		DAOSecurity security = null;

		// Get SecurityAdapter
		security = getSecurityAdapter(vocabularyName);

		if(securityToken == null){
			Exception ex = new Exception("Permission denied - Please set SecurityToken for "+ vocabularyName);
			log.error(ex.getMessage());
			return false;
		}
		else {
			SecurityKey key = getSecurityKey(security, securityToken);
			if (key == null) {
				return false;
			}
		}

		return true;
	}


	/**
	 * Returns the SecurityKey for the specified securityToken.
	 *
	 * @param security the security
	 * @param securityToken the security token
	 *
	 * @return the security key
	 */
	private SecurityKey getSecurityKey(DAOSecurity security, SecurityToken securityToken){
		SecurityKey key = null;
		try{
			key = security.getSecurityKey(new UserCredentials((Object)securityToken.getAccessToken()));
		}catch(Exception ex){
			throw new SecurityException(ex);
		}
		return key;
	}

	/**
	 * Once the DAOAdapter class names are loaded to the application at initialization as system properties,
	 * DAOSecurity class can be obtained using the following method:
	 *
	 */
	private DAOSecurity getSecurityAdapter(String vocabularyName) throws SecurityException {
		DAOSecurity security = null;
		try{
			if (lexEVSprops == null) {
				throw new SecurityException("Security Properties File is null");
			}
			String value = lexEVSprops.getProperty(vocabularyName);
			if (value == null) {
				throw new SecurityException("This Vocabulary does not have a Security Stategy assigned to it.");
			}
			security = (DAOSecurity) Class.forName(value).newInstance();
		} catch(Exception e){
			throw new SecurityException(e);
		}
		return security;
	}
}
