/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.applicationservice.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
 

/**
 * Annotation class created on EVS side to indicate which methods require security token
 * @author <a href="mailto:rajasimhah@mail.nih.gov">Harsha Karur Rajasimha</a>
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DataServiceSecurityTokenRequired {

}
