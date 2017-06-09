/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.testUtil;

import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSDistributed;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSService;

import gov.nih.nci.evs.security.SecurityToken;
import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.client.ApplicationServiceProvider;

public class LexEVSServiceHolder {
    private static LexEVSServiceHolder sh_;
//    private ApplicationService appService = null;
	private static final String serviceUrl = ServiceTestCase.serviceUrl;
	private LexEVSApplicationService lexevsAppService;
	public static final String _service = "EvsServiceInfo";
	private SecurityToken goodToken;
	
    /**
     * Use this to get an instance of the LexEVSServiceHolder. If 'configureForSingleConfig' has not been called,
     * this will run the tests on all configured databases in the testConfig.props file.
     * 
     * @return the EVS query service holder
     */
    public static LexEVSServiceHolder instance()
    {
        if (sh_ == null)
        { 
            configure();
        }
        return sh_;

    }


	/**
	 * Use this to set up the tests for end user enviroment validation. Only runs the tests once, using their
	 * normal config file.
	 * 
	 */
	public static void configure()
	{

		sh_ = new LexEVSServiceHolder();

	}
	
	/**
	 * Instantiates a new eVS query service holder.
	 * 
	 */
	private LexEVSServiceHolder()
	{
		try{
//			appService = (ApplicationService)ApplicationServiceProvider.getApplicationServiceFromUrl(serviceUrl, "EvsServiceInfo");	
			lexevsAppService = (LexEVSApplicationService)ApplicationServiceProvider.getApplicationServiceFromUrl(serviceUrl, "EvsServiceInfo");
			goodToken = new SecurityToken();
			goodToken.setAccessToken(ServiceTestCase.MEDDRA_TOKEN);
		}
		catch (Exception e)
		{
			System.err.println("Problem initiating Test config");
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**
	 * Gets the uRL.
	 * 
	 * @return the uRL
	 */
	public static String getURL()
	{
		return serviceUrl;
	}
	
	/**
	 * Gets the app service.
	 * 
	 * @return an instance of the app service
	 */
//	public ApplicationService getAppService()
//	{
//		return appService;
//	}
//	
	/**
	 * Gets the app service.
	 * 
	 * @return an instance of the app service
	 */
	public LexEVSApplicationService getLexEVSAppService()
	{
		return lexevsAppService;
	}
	
	public LexEVSApplicationService getFreshLexEVSAppService()
	{
		try {
			return lexevsAppService = (LexEVSApplicationService)ApplicationServiceProvider.getApplicationServiceFromUrl(serviceUrl, "EvsServiceInfo");
		} catch (Exception e) {	
			e.printStackTrace();
			return null;
		}
	}
	
	public LexEVSApplicationService getSecureLexEVSAppService()
	{
		try {
			LexEVSApplicationService svc = (LexEVSApplicationService)ApplicationServiceProvider.getApplicationServiceFromUrl(serviceUrl, "EvsServiceInfo");
			svc.registerSecurityToken(ServiceTestCase.MEDDRA_SCHEME, goodToken);
			svc.registerSecurityToken(ServiceTestCase.MEDDRA_URN, goodToken);
			return svc;
		} catch (Exception e) {	
			e.printStackTrace();
			return null;
		}
	}


}
