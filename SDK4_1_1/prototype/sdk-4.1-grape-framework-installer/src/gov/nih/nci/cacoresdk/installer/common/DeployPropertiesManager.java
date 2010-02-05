package gov.nih.nci.cacoresdk.installer.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class DeployPropertiesManager {
	
	static Properties deployProperties=null;
    
    public static String getDeployPropertyValue(String propertyKey) {
    	
    	if (deployProperties == null){
    		deployProperties = new Properties();
    		try {
    			deployProperties.load(new FileInputStream("conf/deploy.properties.template"));
    		} catch (IOException e) {
    			e.printStackTrace();
    			return null;
    		}
    	}
        return deployProperties.getProperty(propertyKey);
    }

    
}
