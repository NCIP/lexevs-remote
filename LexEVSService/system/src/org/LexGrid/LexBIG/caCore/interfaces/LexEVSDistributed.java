/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.interfaces;

import java.util.List;

import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.caCore.security.interfaces.TokenSecurableApplicationService;
import org.LexGrid.codingSchemes.CodingScheme;
import org.LexGrid.util.assertedvaluesets.AssertedValueSetParameters;
import org.lexgrid.conceptdomain.LexEVSConceptDomainServices;
import org.lexgrid.resolvedvalueset.LexEVSResolvedValueSetService;
import org.lexgrid.valuesets.LexEVSPickListDefinitionServices;
import org.lexgrid.valuesets.LexEVSValueSetDefinitionServices;
import org.lexgrid.valuesets.sourceasserted.impl.SourceAssertedValueSetHierarchyServicesImpl;
import org.lexgrid.valuesets.sourceasserted.impl.SourceAssertedValueSetServiceImpl;

/**
 * The Distributed LexEVS Portion of LexEVSAPI. This interface is a framework for calling
 * LexBIG API methods remotely, along with enforcing security measures.
 * 
 * @author <a href="mailto:kevin.peterson@mayo.edu">Kevin Peterson</a>
 */
public interface LexEVSDistributed extends TokenSecurableApplicationService, LexBIGService {
	
	public LexEVSValueSetDefinitionServices getLexEVSValueSetDefinitionServices();
	
	public LexEVSConceptDomainServices getLexEVSConceptDomainServices();
	
	public LexEVSPickListDefinitionServices getLexEVSPickListDefinitionServices();
	
	public SourceAssertedValueSetHierarchyServicesImpl getLexEVSSourceAssertedValueSetHierarchyServices();

	public LexEVSResolvedValueSetService getLexEVSResolvedVSService(AssertedValueSetParameters params);

	public SourceAssertedValueSetServiceImpl getLexEVSSourceAssertedValueSetServices(AssertedValueSetParameters params);

	List<CodingScheme> getSourceAssertedResolvedVSCodingSchemes(AssertedValueSetParameters params);
}
