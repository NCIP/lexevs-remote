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
package org.LexGrid.LexBIG.distributed.test.function.query;

// LexBIG Test ID: T1_FNC_06	TestEnumerateSourceConceptsforRelationandTarget

import org.LexGrid.LexBIG.DataModel.Core.AssociatedConcept;
import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeGraph;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.Utility.ConvenienceMethods;

/**
 * The Class TestEnumerateSourceConceptsforRelationandTarget.
 */
public class TestEnumerateSourceConceptsforRelationandTarget extends ServiceTestCase
{
    final static String testID = "testEnumerateSourceConceptsforRelationandTarget";

    @Override
    protected String getTestID()
    {
        return testID;
    }

    /**
     * Test enumerate source conceptsfor relationand target.
     * 
     * @throws LBException the LB exception
     */
    public void testEnumerateSourceConceptsforRelationandTarget() throws LBException
    {

        CodedNodeGraph cng = LexEVSServiceHolder.instance().getLexEVSAppService().getNodeGraph(THES_SCHEME, null, null);

        cng = cng.restrictToAssociations(Constructors.createNameAndValueList("Anatomic_Structure_Has_Location"), null);

        ConvenienceMethods cm = new ConvenienceMethods(LexEVSServiceHolder.instance().getLexEVSAppService());

        cng = cng.restrictToTargetCodes(cm.createCodedNodeSet(new String[]{"C33090"}, THES_SCHEME, null));

        ResolvedConceptReference[] rcr = cng.resolveAsList(Constructors.createConceptReference("C33090", THES_SCHEME),
                                                           false, true, 1, 1, null, null, null, 0)
                .getResolvedConceptReference();
 
    }

}