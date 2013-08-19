/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.distributed.test.valueset;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.LexGrid.LexBIG.DataModel.Collections.AbsoluteCodingSchemeVersionReferenceList;
import org.LexGrid.LexBIG.DataModel.Collections.CodingSchemeRenderingList;
import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.DataModel.InterfaceElements.CodingSchemeRendering;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Exceptions.LBInvocationException;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.Utility.Iterators.ResolvedConceptReferencesIterator;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.valueSets.DefinitionEntry;
import org.LexGrid.valueSets.EntityReference;
import org.LexGrid.valueSets.ValueSetDefinition;
import org.LexGrid.valueSets.ValueSetDefinitionReference;
import org.LexGrid.valueSets.types.DefinitionOperator;
import org.lexgrid.valuesets.LexEVSValueSetDefinitionServices;
import org.lexgrid.valuesets.dto.ResolvedValueSetDefinition;

/**
 * The Class TestLexEVSValueSetDefinitionServices.
 */
public class TestLexEVSValueSetDefinitionServices extends ServiceTestCase
{
    final static String testID = "TestLexEVSValueSetDefinitionServices";

    @Override
    protected String getTestID()
    {
        return testID;
    }

    public void testSupportedCS() throws LBInvocationException {
    	CodingSchemeRenderingList csrList = LexEVSServiceHolder.instance().getLexEVSAppService().getSupportedCodingSchemes();
    	for (CodingSchemeRendering csr : csrList.getCodingSchemeRendering()){
    		System.out.println(csr.getCodingSchemeSummary().getFormalName());
    	}
    }
    
    public void testListVSD(){
    	List<String> uris = LexEVSServiceHolder.instance().getLexEVSAppService().getLexEVSValueSetDefinitionServices().listValueSetDefinitionURIs();
    	
    	for (String uri : uris)
    	{
    		System.out.println("VSDs loaded : " + uri);
    	}
    }
    
    public void defertestExportVSResolutionByVSDObjectToWriter() throws LBException, URISyntaxException, IOException{
		
		ValueSetDefinition vsd = new ValueSetDefinition();
		vsd.setValueSetDefinitionURI("SRITEST:AUTO:VSDREF_GM_IMMI_NODE_AND_FORD");
		vsd.setValueSetDefinitionName("VSDREF_GM_IMMI_NODE_AND_FORD");
		vsd.setDefaultCodingScheme("Automobiles");
		vsd.setConceptDomain("Autos");
		
		DefinitionEntry de = new DefinitionEntry();
		de.setRuleOrder(1L);
		de.setOperator(DefinitionOperator.OR);
		
		vsd.addDefinitionEntry(de);
		
		ValueSetDefinitionReference vsdRef = new ValueSetDefinitionReference();
		vsdRef.setValueSetDefinitionURI("SRITEST:AUTO:GM_AND_IMMI_NODE");
		de.setValueSetDefinitionReference(vsdRef);
		
		de = new DefinitionEntry();
		de.setRuleOrder(2L);
		de.setOperator(DefinitionOperator.OR);
		
		EntityReference entityRef = new EntityReference();
		entityRef.setEntityCode("Explorer");
		entityRef.setEntityCodeNamespace("Automobiles");
		entityRef.setLeafOnly(false);
		entityRef.setTransitiveClosure(false);
		de.setEntityReference(entityRef);
		
		vsd.addDefinitionEntry(de);
		
		// Start the value set resolution export
		InputStream reader =  LexEVSServiceHolder.instance().getLexEVSAppService().getLexEVSValueSetDefinitionServices().exportValueSetResolution(vsd, null, null, null, false);
		
		if (reader != null) {
			StringBuffer buf = new StringBuffer(); 
	        try { 
	            for(int c = reader.read(); c != -1; c = reader.read()) { 
	                buf.append((char)c); 
	            } 
	            System.out.println(buf.toString()); 
	        } catch(IOException e) { 
	            throw e; 
	        } finally { 
	            try { 
	                reader.close(); 
	            } catch(Exception e) { 
	                // ignored 
	            } 
	        } 
		}
	}
    
    public void testExportVSDURIStreaming() throws LBException, URISyntaxException, IOException{
    	// Start the value set resolution export
		InputStream reader =  LexEVSServiceHolder.instance().getLexEVSAppService().getLexEVSValueSetDefinitionServices().exportValueSetResolution(new URI("SRITEST:AUTO:EveryThing"), null, null, null, false);
		
		if (reader != null) {
			StringBuffer buf = new StringBuffer(); 
	        try { 
	            for(int c = reader.read(); c != -1; c = reader.read()) { 
	                buf.append((char)c); 
	            } 
	            System.out.println(buf.toString()); 
	        } catch(IOException e) { 
	            throw e; 
	        } finally { 
	            try { 
	                reader.close(); 
	            } catch(Exception e) { 
	                // ignored 
	            } 
	        } 
		}
		
		AbsoluteCodingSchemeVersionReferenceList csvList = new AbsoluteCodingSchemeVersionReferenceList();
		csvList.addAbsoluteCodingSchemeVersionReference(Constructors.createAbsoluteCodingSchemeVersionReference("cell", null));
		reader =  LexEVSServiceHolder.instance().getLexEVSAppService().getLexEVSValueSetDefinitionServices().exportValueSetResolution(new URI("SRITEST:FA:HyphaPropertyRef"), null, null, null, false);
		
		if (reader != null) {
			StringBuffer buf = new StringBuffer(); 
	        try { 
	            for(int c = reader.read(); c != -1; c = reader.read()) { 
	                buf.append((char)c); 
	            } 
	            System.out.println(buf.toString()); 
	        } catch(IOException e) { 
	            throw e; 
	        } finally { 
	            try { 
	                reader.close(); 
	            } catch(Exception e) { 
	                // ignored 
	            } 
	        } 
		}
    }
    public void defertestResolve() throws LBException, URISyntaxException {

    	LexEVSValueSetDefinitionServices vds = 
    		LexEVSServiceHolder.instance().getLexEVSAppService().getLexEVSValueSetDefinitionServices();

//    	ValueSetDefinition vsd = new ValueSetDefinition();
//    	vsd.setValueSetDefinitionName("vsName");
//    	vsd.setValueSetDefinitionURI("vsUri");
//    	vsd.setDefaultCodingScheme(ServiceTestCase.THES_SCHEME);
//
//    	DefinitionEntry entry1 = new DefinitionEntry();
//    	entry1.setRuleOrder(new java.lang.Long(0));
//    	entry1.setOperator(DefinitionOperator.OR);
//
//    	EntityReference entity = new EntityReference();
//    	entity.setEntityCode("C12727");
//    	entry1.setEntityReference(entity);
//
//    	vsd.addDefinitionEntry(entry1);
//
//    	ResolvedConceptReferencesIterator itr = 
//    		vds.resolveValueSetDefinition(vsd, null, null, null).getResolvedConceptReferenceIterator();
//    	
//    	assertEquals(1,itr.numberRemaining());
//
//    	assertEquals("C12727", itr.next().getEntity().getEntityCode());
    	
    	List<String> uris = vds.listValueSetDefinitionURIs();
    	
    	for (String uri : uris){
    		System.out.println("available URI : " + uri);
    	}
    	
    	ResolvedValueSetDefinition rvsd = vds.resolveValueSetDefinition(new URI("SRITEST:AUTO:GM"), null, null, null, null);
    	
    	ResolvedConceptReferencesIterator rcrItr = rvsd.getResolvedConceptReferenceIterator();
    	
    	while (rcrItr.hasNext())
    	{
    		ResolvedConceptReference rcr = rcrItr.next();
    		System.out.println("code : " + rcr.getCode());
    	}
    	uris = vds.listValueSetsWithEntityCode("GM", null, null, null);
    	
    	for (String uri : uris){
    		System.out.println("URI : " + uri);
    	}
    	
    	
    }
}