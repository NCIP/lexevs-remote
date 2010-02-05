/*******************************************************************************
 * Copyright: (c) 2004-2009 Mayo Foundation for Medical Education and 
 * Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
 * triple-shield Mayo logo are trademarks and service marks of MFMER.
 * 
 * Except as contained in the copyright notice above, or as used to identify 
 * MFMER as the author of this software, the trade names, trademarks, service
 * marks, or product names of the copyright holder shall not be used in
 * advertising, promotion or otherwise in connection with this software without
 * prior written authorization of the copyright holder.
 *   
 * Licensed under the Eclipse Public License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *   
 *  		http://www.eclipse.org/legal/epl-v10.html
 * 
 *  		
 *******************************************************************************/
package org.LexGrid.LexBIG.caCore.interfaces;

import java.lang.annotation.Annotation;
import java.util.HashMap;

public interface LexEVSApplicationService extends LexEVSService {

    /**
     * Execute securely. Used for internal remote method calls. Not to be called directly.
     * 
     * @param object the object
     * @param annotations any annotions of the method to be executed.
     * @param methodName the method name
     * @param parameterClasses the parameter classes
     * @param args the args
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
      
}
