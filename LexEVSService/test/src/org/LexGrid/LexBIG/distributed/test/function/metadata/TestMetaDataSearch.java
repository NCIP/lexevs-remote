/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.distributed.test.function.metadata;

import java.util.HashSet;

import org.LexGrid.LexBIG.DataModel.Core.AbsoluteCodingSchemeVersionReference;
import org.LexGrid.LexBIG.DataModel.Core.MetadataProperty;
import org.LexGrid.LexBIG.LexBIGService.LexBIGServiceMetadata;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

/**
 * Class to test the MetaData search capabilities.
 * 
 * @author <A HREF="mailto:armbrust.daniel@mayo.edu">Dan Armbrust</A>
 * @version subversion $Revision: 1.8 $ checked in on $Date: 2008/09/30 20:05:57 $
 */
public class TestMetaDataSearch extends ServiceTestCase
{
    
    /**
     * Test list coding schemes.
     * 
     * @throws Exception the exception
     */
    public void testListCodingSchemes() throws Exception
    {
        LexBIGServiceMetadata md = LexEVSServiceHolder.instance().getLexEVSAppService().getServiceMetadata();
        AbsoluteCodingSchemeVersionReference[] acsvrl = md.listCodingSchemes()
                .getAbsoluteCodingSchemeVersionReference();

        assertTrue(acsvrl.length >= 1);
        assertTrue(contains(acsvrl, Constructors.createAbsoluteCodingSchemeVersionReference(THES_URN, THES_METADATA_VERSION)));
    }

    private boolean contains(AbsoluteCodingSchemeVersionReference[] acsvr, AbsoluteCodingSchemeVersionReference acsvr2)
    {
        for (int i = 0; i < acsvr.length; i++)
        {
            if (acsvr[i].getCodingSchemeURN().equals(acsvr2.getCodingSchemeURN())
                    && acsvr[i].getCodingSchemeVersion().equals(acsvr2.getCodingSchemeVersion()))
            {
                return true;
            }
        }
        return false;
    }

    private boolean contains(MetadataProperty[] mdp, String urn, String version, String name, String value)
    {
        for (int i = 0; i < mdp.length; i++)
        {
            if (mdp[i].getCodingSchemeURI().equals(urn)
                    && mdp[i].getCodingSchemeVersion().equals(version)
                    && mdp[i].getName().equals(name)
                    && mdp[i].getValue().equals(value))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Test container restriction search.
     * 
     * @throws Exception the exception
     */
    public void testContainerRestrictionSearch() throws Exception
    {
        LexBIGServiceMetadata md = LexEVSServiceHolder.instance().getLexEVSAppService().getServiceMetadata();
        
        md = md.restrictToValue("OWL-FULL", "LuceneQuery");
        md = md.restrictToCodingScheme(Constructors.createAbsoluteCodingSchemeVersionReference(THES_URN, THES_METADATA_VERSION));

        MetadataProperty[] result = md.resolve().getMetadataProperty();
        assertTrue(result.length >= 1);
        assertTrue(contains(result, THES_URN, THES_METADATA_VERSION, "format", "OWL-FULL"));
    }
    
    /**
     * Test property restriction search.
     * 
     * @throws Exception the exception
     */
    public void testPropertyRestrictionSearch() throws Exception
    {
        LexBIGServiceMetadata md = LexEVSServiceHolder.instance().getLexEVSAppService().getServiceMetadata();
        md = md.restrictToValue("OWL-FULL", "LuceneQuery");
        md = md.restrictToCodingScheme(Constructors.createAbsoluteCodingSchemeVersionReference(THES_URN, THES_METADATA_VERSION));

        MetadataProperty[] result = md.resolve().getMetadataProperty();
        assertTrue(result.length >= 1);
        assertTrue(contains(result, THES_URN, THES_METADATA_VERSION, "format", "OWL-FULL"));

        md = LexEVSServiceHolder.instance().getLexEVSAppService().getServiceMetadata();
        md = md.restrictToCodingScheme(Constructors.createAbsoluteCodingSchemeVersionReference(THES_URN, THES_METADATA_VERSION));
        md = md.restrictToProperties(new String[]{"format"});
        md = md.restrictToValue("OWL-FULL", "LuceneQuery");
        result = md.resolve().getMetadataProperty();
        assertTrue(result.length >= 1);
        assertTrue(contains(result, THES_URN, THES_METADATA_VERSION, "format", "OWL-FULL"));
    }
    
    /**
     * Test coding scheme restriction search.
     * 
     * @throws Exception the exception
     */
    public void testCodingSchemeRestrictionSearch() throws Exception
    {
        LexBIGServiceMetadata md = LexEVSServiceHolder.instance().getLexEVSAppService().getServiceMetadata();
        md = md.restrictToValue("English", "LuceneQuery");
        MetadataProperty[] result = md.resolve().getMetadataProperty();
        
        HashSet<String> temp = new HashSet<String>();
        for (int i = 0; i < result.length; i++)
        {
            temp.add(result[i].getCodingSchemeURI() + ":" + result[i].getCodingSchemeVersion());
        }

        //should contain this
        assertTrue(temp.contains(THES_URN + ":" + THES_METADATA_VERSION));
        
        //now do the restriction, and retest.
        
        md = LexEVSServiceHolder.instance().getLexEVSAppService().getServiceMetadata();
        md = md.restrictToValue("English", "LuceneQuery");
        md = md.restrictToCodingScheme(Constructors.createAbsoluteCodingSchemeVersionReference(THES_URN , THES_METADATA_VERSION));
        result = md.resolve().getMetadataProperty();
        
        temp = new HashSet<String>();
        for (int i = 0; i < result.length; i++)
        {
            temp.add(result[i].getCodingSchemeURI() + ":" + result[i].getCodingSchemeVersion());
        }
        
        //should be more than 1 unique code system.
        assertTrue(temp.size() >= 1);
        
        //should contain this
        assertTrue(temp.contains(THES_URN + ":" + THES_METADATA_VERSION));
    }
    
    /**
     * Test value restriction.
     * 
     * @throws Exception the exception
     */
    public void testValueRestriction() throws Exception
    {
        LexBIGServiceMetadata md = LexEVSServiceHolder.instance().getLexEVSAppService().getServiceMetadata();
        md = md.restrictToCodingScheme(Constructors.createAbsoluteCodingSchemeVersionReference(THES_URN, THES_METADATA_VERSION));
        md = md.restrictToValue(".*core.*", "RegExp");
 
        MetadataProperty[] result = md.resolve().getMetadataProperty();
        assertTrue(result.length >= 2);
        assertTrue(contains(result, THES_URN, THES_METADATA_VERSION, "homepage", "http://ncicb.nci.nih.gov/core/EVS"));
    }
}