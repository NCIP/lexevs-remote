package org.LexGrid.LexBIG.caCore.security.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Singleton for accessing LexEVS Properties.
 */
public class LexEVSProperties {
    
    private Logger log = Logger.getLogger(LexEVSProperties.class);
    private String lexBigConfigFileLocation = null;
    private Properties properties = new Properties();
    private String fileName = "lexevs.properties";
    private String systemProperty = "org.LexGrid.LexBIG.caCore.Properties";
    
    /**
     * Private constructor for singleton pattern.
     */
	public LexEVSProperties(String fileName, String systemProperty) throws Exception {
		this.fileName = fileName;
		this.systemProperty = systemProperty;
		loadProperties();
	}
	
	public LexEVSProperties(String systemProperty) throws Exception {
		this.systemProperty = systemProperty;
		loadProperties();
	}
	
	public LexEVSProperties() throws Exception {
		loadProperties();
	}
	
    /**
     * Gets the lex big config file location.
     * 
     * @return the lex big config file location
     * 
     * @throws Exception the exception
     */
    public String getLexBigConfigFileLocation() throws Exception{
        return lexBigConfigFileLocation;
    }
    
    public String getProperty(String key) throws Exception{
    	return properties.getProperty(key);
    }
    
    public boolean isInProperties(String key){
    	return properties.containsKey(key);
    }
    
    private void loadProperties() throws Exception {
    	InputStream inputStream = null;
		inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);	
		
		if(inputStream != null){
			log.info("Loading Properties from File: " + fileName + ".");	
			try {
				properties.load(inputStream);
			} catch (IOException e) {
				log.info("Error Loading Properties from File: " + fileName + ".");
				throw e;
			}
		} else {
			log.info("Could not find file: " + fileName + ". Looking for a set System Property...");	
			log.info("Loading Properties from System Property: " + systemProperty + ".");
			String propertyFile = System.getProperty(systemProperty);

			try {
				FileInputStream fis = new FileInputStream(new File(propertyFile));
				properties.load(fis);
			} catch (Exception e) {
				log.info("Error Loading Properties from System Property: " + systemProperty + ".");
				throw e;
			}	
		}
		validateProperties();
	
		lexBigConfigFileLocation = properties.getProperty("LG_CONFIG_FILE");
	} 
    
    private void validateProperties() throws Exception{
    	if(properties.isEmpty()){
    		throw new Exception("Properties file was not read correctly and is empty.");
    	}  	
    	for(Iterator i = properties.keySet().iterator(); i.hasNext();){
			String key = (String)i.next();
			String value  = properties.getProperty(key);
            log.debug("KEY: "+ key +"\t - "+value);
		}
    }

}
