package org.LexGrid.LexBIG.caCore.security.test;

import gov.nih.nci.evs.security.SecurityToken;

import org.LexGrid.LexBIG.caCore.security.Validator;
import org.LexGrid.LexBIG.caCore.security.properties.LexEVSProperties;
import org.LexGrid.LexBIG.caCore.security.test.util.TokenUtil;

import junit.framework.TestCase;

public class SecurityTokenTest extends TestCase {
	
	
	
	private Validator validator;
	
	
	public void setUp(){
		try {
			LexEVSProperties props = new LexEVSProperties(SecurityTestConstants.LEXEVS_PROP);
			validator = new Validator(props);
		} catch (Exception e) {
			fail("Error setting up.");
		}
	}
	
	public void testIsSecured(){
		assertTrue(validator.isSecured(SecurityTestConstants.MEDDRA_URN));
	}
	
	public void testIsNotSecured(){
		assertFalse(validator.isSecured("unsecuredCodingScheme"));
	}
	
	public void testValidateNull(){
		//Should throw a Security Exception
		boolean securityExceptionThrown = false;
		try {
			validator.validate(null, null);
		} catch (SecurityException e) {
			securityExceptionThrown = true;
		}
		assertTrue(securityExceptionThrown);
	}
	
	public void testValidateMedDRANullToken(){
		assertFalse(validator.validate(SecurityTestConstants.MEDDRA_URN, null));
	}

	public void testValidateMedDRAEmptyToken(){
		SecurityToken token = new SecurityToken();

		//Should throw a Security Exception
		boolean securityExceptionThrown = false;
		try {
			validator.validate(SecurityTestConstants.MEDDRA_URN, token);
		} catch (SecurityException e) {
			securityExceptionThrown = true;
		}
		assertTrue(securityExceptionThrown);
	}
	
	public void testValidateMedDRAWrongToken(){
		SecurityToken token = new SecurityToken();
		token.setAccessToken("THIS_IS_WRONG");

		//Should throw a Security Exception
		boolean securityExceptionThrown = false;
		try {
			validator.validate(SecurityTestConstants.MEDDRA_URN, token);
		} catch (SecurityException e) {
			securityExceptionThrown = true;
		}
		assertTrue(securityExceptionThrown);
	}
	
	public void testValidateMedDRACorrectToken(){
		SecurityToken token = new SecurityToken();
		try {
			token.setAccessToken(TokenUtil.getMedDRATokenFromPropsFile());
		} catch (Exception e1) {
			fail("Exception setting token -- is a token specified in token.properties?");
		}
		
		assertTrue(validator.validate(SecurityTestConstants.MEDDRA_URN, token));	
	}
}
