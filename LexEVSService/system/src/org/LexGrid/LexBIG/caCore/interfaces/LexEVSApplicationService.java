/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.interfaces;

import gov.nih.nci.system.applicationservice.ApplicationService;
import java.lang.annotation.Annotation;
import java.util.HashMap;

import org.LexGrid.LexBIG.caCore.security.interfaces.TokenSecurableApplicationService;
import org.LexGrid.util.assertedvaluesets.AssertedValueSetParameters;


public interface LexEVSApplicationService extends LexEVSService, ApplicationService, TokenSecurableApplicationService {

    /**
     * Execute securely. Used for internal remote method calls. Not to be called directly.
     *
     * @param annotations any annotions of the method to be executed.
     * @param methodName the method name
     * @param parameterClasses the parameter classes
     * @param args the args
     * @param tokens the tokens
     * 
     * @return the object
     * 
     * @throws Exception the exception
     */
    public Object executeSecurely(String methodName, Annotation[] annotations,
                    String[] parameterClasses, Object[] args, HashMap tokens) 
                    throws Exception;
  
    
    /**
     * Execute remotely. Used for internal remote method calls. Not to be called directly.
     *
     * @param object the object
     * @param methodName the method name
     * @param parameterClasses the parameter classes
     * @param args the args
     *
     * @return the object
     *
     * @throws Exception the exception
     */
    public Object executeRemotely(Object object, String methodName,
                    String[] parameterClasses, Object[] args)
                    throws Exception;
    
    
    public void setAssertedValueSetConfiguration(AssertedValueSetParameters params);
      
}
