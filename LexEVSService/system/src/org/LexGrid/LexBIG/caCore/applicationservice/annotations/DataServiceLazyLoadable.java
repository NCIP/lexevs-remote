/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.applicationservice.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
 
/**
 * Annotation class created on LexEVS side to indicate that a method is Lazy Loadable by
 * the LexEVS Data Service
 * @author <a href="mailto:kevin.peterson@mayo.edu">Kevin Peterson</a>
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DataServiceLazyLoadable {

}
