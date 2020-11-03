/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.distributed.test.function.query;

// LexBIG Test ID: T1_FNC_29	TestEnumerateProperties

import org.LexGrid.LexBIG.DataModel.Collections.ConceptReferenceList;
import org.LexGrid.LexBIG.DataModel.Collections.ResolvedConceptReferenceList;
import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Utility.ConvenienceMethods;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.commonTypes.Property;
import org.LexGrid.concepts.Entity;

/**
 * The Class TestEnumerateProperties.
 */
public class TestEnumerateProperties extends ServiceTestCase
{
    final static String testID = "testEnumerateProperties";

    @Override
    protected String getTestID()
    {
        return testID;
    }

    /**
     * Enumerate properties for the concept code.
     * 
     * @param code the concept code
     * @param properties the properties array
     * 
     * @throws LBException the LB exception
     */
    protected void enumerateProps(String code, String[] properties) throws LBException
    {
        // Perform the query ...
        ConceptReferenceList crefs = ConvenienceMethods.createConceptReferenceList(new String[]{code}, THES_SCHEME);

        ResolvedConceptReferenceList matches = LexEVSServiceHolder.instance().getLexEVSAppService()
                .getCodingSchemeConcepts(THES_SCHEME, null).restrictToCodes(crefs).resolveToList(null, null, null, 1);

        // Analyze the result ...
        assertTrue(matches.getResolvedConceptReferenceCount() > 0);
        ResolvedConceptReference ref = (ResolvedConceptReference) matches.enumerateResolvedConceptReference()
                .nextElement();
        Entity entry = ref.getReferencedEntry();

        // Print properties (definitions, comments, presentations
        // are not printed but would be similarly handled)
        Property[] props = entry.getProperty();
        assertTrue(props.length > 0);
        assertTrue(contains(props, properties));

    }
    
    /**
     * Test if any of the property names in the String array match
     *     a property in the Property array
     * 
     * @param props the array of type Property[]
     * @param property the array of property names as String[]
     * 
     * @return true, if successful
     */
    public boolean contains(Property[] props, String[] property)
    {
        if (props.length <  property.length)
        {
            return false;
        }
        for (int i = 0; i < property.length; i++)
        {
            boolean found = false;
            for (int j = 0; j < props.length; j++)
            {
                if (props[j].getPropertyName().equals(property[i]))
                {
                    found = true;
                    break;
                }
            }
            if (!found)
            {
                return false;
            }
        }
        
        return true;
    }

    /**
     * Test enumerate propertiesa.
     * 
     * @throws LBException the LB exception
     */
    public void testEnumeratePropertiesa() throws LBException
    {
        enumerateProps("C12366", new String[] {"label", "Contributing_Source"});
    }

    /**
     * Test enumerate propertiesb.
     * 
     * @throws LBException the LB exception
     */
    public void testEnumeratePropertiesb() throws LBException
    {
        enumerateProps("C12366", new String[] {"Semantic_Type", "UMLS_CUI"});
    }

}