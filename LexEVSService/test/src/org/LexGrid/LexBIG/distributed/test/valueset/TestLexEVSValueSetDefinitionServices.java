/*
 * Copyright: (c) 2004-2006 Mayo Foundation for Medical Education and
 * Research (MFMER).  All rights reserved.  MAYO, MAYO CLINIC, and the
 * triple-shield Mayo logo are trademarks and service marks of MFMER.
 *
 * Except as contained in the copyright notice above, the trade names, 
 * trademarks, service marks, or product names of the copyright holder shall
 * not be used in advertising, promotion or otherwise in connection with
 * this Software without prior written authorization of the copyright holder.
 * 
 * Licensed under the Eclipse Public License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 * 
 * 		http://www.eclipse.org/legal/epl-v10.html
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.LexGrid.LexBIG.distributed.test.valueset;

import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Utility.Iterators.ResolvedConceptReferencesIterator;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.valueSets.DefinitionEntry;
import org.LexGrid.valueSets.EntityReference;
import org.LexGrid.valueSets.ValueSetDefinition;
import org.LexGrid.valueSets.types.DefinitionOperator;
import org.lexgrid.valuesets.LexEVSValueSetDefinitionServices;

/**
 * The Class TestLexEVSValueSetDefinitionServices.
 */
public class TestLexEVSValueSetDefinitionServices extends ServiceTestCase
{
    final static String testID = "testApproximateStringMatch";

    @Override
    protected String getTestID()
    {
        return testID;
    }

    public void testResolve() throws LBException {

    	LexEVSValueSetDefinitionServices vds = 
    		LexEVSServiceHolder.instance().getLexEVSAppService().getLexEVSValueSetDefinitionServices();

    	ValueSetDefinition vsd = new ValueSetDefinition();
    	vsd.setValueSetDefinitionName("vsName");
    	vsd.setValueSetDefinitionURI("vsUri");
    	vsd.setDefaultCodingScheme(ServiceTestCase.THES_SCHEME);

    	DefinitionEntry entry1 = new DefinitionEntry();
    	entry1.setRuleOrder(new java.lang.Long(0));
    	entry1.setOperator(DefinitionOperator.OR);

    	EntityReference entity = new EntityReference();
    	entity.setEntityCode("C12727");
    	entry1.setEntityReference(entity);

    	vsd.addDefinitionEntry(entry1);

    	ResolvedConceptReferencesIterator itr = 
    		vds.resolveValueSetDefinition(vsd, null, null, null).getResolvedConceptReferenceIterator();
    	
    	assertEquals(1,itr.numberRemaining());

    	assertEquals("C12727", itr.next().getEntity().getEntityCode());
    }
}