package org.LexGrid.LexBIG.caCore.security.test.util;

import java.io.FileInputStream;
import java.util.Properties;

import org.LexGrid.LexBIG.caCore.security.test.SecurityTestConstants;

public class TokenUtil {

	private static String MEDDRA_KEY = "medDRA_Token";
	
	public static String getMedDRATokenFromPropsFile() throws Exception {
		Properties props = new Properties();
		props.load(new FileInputStream(System.getProperty(SecurityTestConstants.TOKEN_PROP)));
		return (String)props.get(MEDDRA_KEY);
	}
}
