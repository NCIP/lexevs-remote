/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.distributed.test.function;

import java.util.Enumeration;

import org.LexGrid.LexBIG.DataModel.Collections.CodingSchemeRenderingList;
import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeSummary;
import org.LexGrid.LexBIG.DataModel.Core.types.CodingSchemeVersionStatus;
import org.LexGrid.LexBIG.DataModel.InterfaceElements.CodingSchemeRendering;
import org.LexGrid.LexBIG.DataModel.InterfaceElements.RenderingDetail;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Exceptions.LBInvocationException;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.LexBIGService.LexBIGServiceManager;
import org.LexGrid.LexBIG.Utility.ConvenienceMethods;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;

/**
 * The Class TestUtil.
 */
public class TestUtil
{

/**
 * Indicates whether a scheme is registered with the specified state.
 * 
 * @param localName Local name to match; null to match any.
 * @param urn URN to match; null to match any.
 * @param version Version represented by scheme; null to match any.
 * @param status One of values enumerated on CodingSchemeVersionStatus; <0 to match any.
 * 
 * @return true if scheme exists with given status; false otherwise.
 * 
 * @throws LBInvocationException the LB invocation exception
 */
    public static synchronized boolean verifyScheme(String localName, String urn, String version, CodingSchemeVersionStatus status)
            throws LBInvocationException
    {
        // Verify vocabulary is loaded and active ...
        boolean verified = false;

        LexBIGService lbSvc = LexEVSServiceHolder.instance().getLexEVSAppService();
        CodingSchemeRenderingList csrl = lbSvc.getSupportedCodingSchemes();
        Enumeration<? extends CodingSchemeRendering> csrlEnum = csrl.enumerateCodingSchemeRendering();
        while (csrlEnum.hasMoreElements() && !verified)
        {
            CodingSchemeRendering csr = csrlEnum.nextElement();
            CodingSchemeSummary css = csr.getCodingSchemeSummary();
            RenderingDetail rd = csr.getRenderingDetail();
            if ((localName == null || localName.equalsIgnoreCase(css.getLocalName()))
                    && (urn == null || urn.equalsIgnoreCase(css.getCodingSchemeURI()))
                    && (version == null || version.equalsIgnoreCase(css.getRepresentsVersion()))
                    && (csr.getRenderingDetail().getVersionStatus().equals(rd.getVersionStatus())))
            {
                verified = true;
                break;
            }
        }

        return verified;
    }

    /**
     * Activate a coding scheme.
     * 
     * @param urn the urn
     * @param version the version
     * 
     * @return true if successful; false otherwise
     * 
     * @throws LBException the LB exception
     */
    public static synchronized boolean activateScheme(String urn, String version) throws LBException
    {
        boolean result = false;

        LexBIGServiceManager lbsm = LexEVSServiceHolder.instance().getLexEVSAppService().getServiceManager(null);
        lbsm.activateCodingSchemeVersion(ConvenienceMethods.createAbsoluteCodingSchemeVersionReference(urn, version));

        // Verify vocabulary is active ...
        result = verifyScheme(null, urn, version, CodingSchemeVersionStatus.ACTIVE);

        return result;
    }

    /**
     * Deactivate a coding scheme.
     * 
     * @param urn
     * @param version
     * @return true if successful; false otherwise
     * @throws LBException
     */
    public static synchronized boolean deactivateScheme(String urn, String version) throws LBException
    {
        boolean result = false;

        LexBIGServiceManager lbsm = LexEVSServiceHolder.instance().getLexEVSAppService().getServiceManager(null);
        lbsm.deactivateCodingSchemeVersion(ConvenienceMethods.createAbsoluteCodingSchemeVersionReference(urn, version),
                                           null);

        // Verify vocabulary is active ...
        result = verifyScheme(null, urn, version, CodingSchemeVersionStatus.INACTIVE);

        return result;

    }

    /**
     * Remove a coding scheme.
     * 
     * @param urn
     * @param version
     * @return true if successful; false otherwise
     * @throws LBException 
     */
    public static synchronized boolean removeScheme(String urn, String version) throws LBException
    {
        boolean result = false;

        LexBIGServiceManager lbsm = LexEVSServiceHolder.instance().getLexEVSAppService().getServiceManager(null);
        lbsm.removeCodingSchemeVersion(ConvenienceMethods.createAbsoluteCodingSchemeVersionReference(urn, version));

        // Verify vocabulary is active ...
        result = !verifyScheme(null, urn, version, CodingSchemeVersionStatus.ACTIVE);

        return result;
    }
}