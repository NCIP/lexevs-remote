/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.distributed.test.dataAccess;

import gov.nih.nci.system.client.ApplicationServiceProvider;

import org.LexGrid.LexBIG.DataModel.Collections.CodingSchemeRenderingList;
import org.LexGrid.LexBIG.DataModel.InterfaceElements.CodingSchemeRendering;
import org.LexGrid.LexBIG.Impl.dataAccess.SQLImplementedMethods;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSDistributed;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.lexevs.system.ResourceManager;

public class SecurityTest extends ServiceTestCase {
	final static String testID = "SecurityTest";
	LexBIGService lbSvc = null;
	
	@Override
	protected String getTestID() {
		return testID;
	}
	
	public SecurityTest() {
		//Setup Distributed LexBIG
		try {
			lbSvc = (LexEVSDistributed)ApplicationServiceProvider.getApplicationServiceFromUrl(serviceUrl, "EvsServiceInfo");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final void testGetSupportedCodingSchemes(){
		//Test GetSupportedCodingSchemes -- this is allowed and should work
		try{
		CodingSchemeRenderingList csrl = lbSvc.getSupportedCodingSchemes();

		CodingSchemeRendering[] csr = csrl.getCodingSchemeRendering();

		for(int i=0;i<csr.length;i++){

			System.out.println("\t\tOutput: " + "Coding Scheme: " + csr[i].getCodingSchemeSummary().getLocalName());
			System.out.println("\t\tOutput: " + "  -- Version: " + csr[i].getCodingSchemeSummary().getRepresentsVersion());
			System.out.println("\t\tOutput: " + "  -- URN: " + csr[i].getCodingSchemeSummary().getCodingSchemeURI());		
		}
		}
		catch (Exception e){
			fail("Exception thrown");
		}
	}
	
	public final void testCallResourceManager(){
		//Try to call a ResourceManager method -- this should throw an Exception
		try {
			ResourceManager manager = ResourceManager.instance();
			String urn = manager.getURNForExternalCodingSchemeName("Zebrafish");
			System.out.println(urn);
			fail("Should have thrown Exception");
		} catch (Exception e) {
			System.out.println("Calling ResourceMananger methods failed");
			assertTrue("Exception thrown", true);
			//e.printStackTrace();
		}
	}	
}
