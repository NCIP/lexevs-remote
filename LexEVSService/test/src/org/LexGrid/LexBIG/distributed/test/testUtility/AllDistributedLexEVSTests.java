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
package org.LexGrid.LexBIG.distributed.test.testUtility;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.LexGrid.LexBIG.distributed.test.bugs.GForge17233;
import org.LexGrid.LexBIG.distributed.test.bugs.GForge19716;
import org.LexGrid.LexBIG.distributed.test.bugs.TestBugFixes;
import org.LexGrid.LexBIG.distributed.test.dataAccess.ConvenienceMethodsSecurityTest;
import org.LexGrid.LexBIG.distributed.test.dataAccess.DistributedSecurityTest;
import org.LexGrid.LexBIG.distributed.test.dataAccess.SecurityTest;
import org.LexGrid.LexBIG.distributed.test.function.metadata.TestMetaDataSearch;
import org.LexGrid.LexBIG.distributed.test.function.query.TestApproximateStringMatch;
import org.LexGrid.LexBIG.distributed.test.function.query.TestAttributePresenceMatch;
import org.LexGrid.LexBIG.distributed.test.function.query.TestAttributeValueMatch;
import org.LexGrid.LexBIG.distributed.test.function.query.TestCodingSchemesWithSupportedAssociation;
import org.LexGrid.LexBIG.distributed.test.function.query.TestContentExtraction;
import org.LexGrid.LexBIG.distributed.test.function.query.TestDAGWalking;
import org.LexGrid.LexBIG.distributed.test.function.query.TestDescribeSearchTechniques;
import org.LexGrid.LexBIG.distributed.test.function.query.TestDescribeSupportedSearchCriteria;
import org.LexGrid.LexBIG.distributed.test.function.query.TestDiscoverAvailableVocabulariesandVersions;
import org.LexGrid.LexBIG.distributed.test.function.query.TestEnumerateAllConcepts;
import org.LexGrid.LexBIG.distributed.test.function.query.TestEnumerateAssociationNames;
import org.LexGrid.LexBIG.distributed.test.function.query.TestEnumerateConceptsbyRelationship;
import org.LexGrid.LexBIG.distributed.test.function.query.TestEnumerateProperties;
import org.LexGrid.LexBIG.distributed.test.function.query.TestEnumerateRelationsbyRange;
import org.LexGrid.LexBIG.distributed.test.function.query.TestEnumerateRelationships;
import org.LexGrid.LexBIG.distributed.test.function.query.TestEnumerateSourceConceptsforRelationandTarget;
import org.LexGrid.LexBIG.distributed.test.function.query.TestGenerateDAG;
import org.LexGrid.LexBIG.distributed.test.function.query.TestLexicalMatchingTechniques;
import org.LexGrid.LexBIG.distributed.test.function.query.TestLimitReturnedValues;
import org.LexGrid.LexBIG.distributed.test.function.query.TestMapAttributestoTypes;
import org.LexGrid.LexBIG.distributed.test.function.query.TestMapPreferredNametoCode;
import org.LexGrid.LexBIG.distributed.test.function.query.TestMapSynonymtoPreferredNames;
import org.LexGrid.LexBIG.distributed.test.function.query.TestMembershipinVocabulary;
import org.LexGrid.LexBIG.distributed.test.function.query.TestOtherMatchingTechniques;
import org.LexGrid.LexBIG.distributed.test.function.query.TestPagedReturns;
import org.LexGrid.LexBIG.distributed.test.function.query.TestQuerybyRelationshipDomain;
import org.LexGrid.LexBIG.distributed.test.function.query.TestRelationshipInquiry;
import org.LexGrid.LexBIG.distributed.test.function.query.TestRetrieveConceptandAttributesbyCode;
import org.LexGrid.LexBIG.distributed.test.function.query.TestRetrieveConceptandAttributesbyPreferredName;
import org.LexGrid.LexBIG.distributed.test.function.query.TestRetrieveMostRecentVersionofConcept;
import org.LexGrid.LexBIG.distributed.test.function.query.TestRetrieveRelationsforConcept;
import org.LexGrid.LexBIG.distributed.test.function.query.TestSearchbyStatus;
import org.LexGrid.LexBIG.distributed.test.function.query.TestSetofVocabulariesforSearch;
import org.LexGrid.LexBIG.distributed.test.function.query.TestSpecifyReturnOrder;
import org.LexGrid.LexBIG.distributed.test.function.query.TestSubsetExtraction;
import org.LexGrid.LexBIG.distributed.test.function.query.TestTraverseGraphviaRoleLinks;
import org.LexGrid.LexBIG.distributed.test.function.query.TestVersionChanges;
import org.LexGrid.LexBIG.distributed.test.function.query.TestVersioningandAuthorityEnumeration;
import org.LexGrid.LexBIG.distributed.test.function.query.TestforCurrentOrObsoleteConcept;
import org.LexGrid.LexBIG.distributed.test.services.CodedNodeGraphImplTest;
import org.LexGrid.LexBIG.distributed.test.services.CodedNodeSetImplTest;
import org.LexGrid.LexBIG.distributed.test.services.LexBIGServiceConvenienceMethodsTest;
import org.LexGrid.LexBIG.distributed.test.services.LexBIGServiceTest;
import org.LexGrid.LexBIG.distributed.test.services.ResolvedConceptReferenceIteratorTest;

/**
 * The Class AllTestsRemoteConfig.
 */
public class AllDistributedLexEVSTests
{
	/**
	 * Suite.
	 * 
	 * @return the test
	 * 
	 * @throws Exception the exception
	 */
	public static Test suite() throws Exception
	{
		TestSuite mainSuite = new TestSuite("LexBIG validation tests");

		TestSuite bugTests = new TestSuite("Bug Regression Tests");
		bugTests.addTestSuite(TestBugFixes.class);
		bugTests.addTestSuite(GForge19716.class);
		bugTests.addTestSuite(GForge17233.class);
		mainSuite.addTest(bugTests);

		TestSuite securityTests = new TestSuite("Security Tests");
		securityTests.addTestSuite(DistributedSecurityTest.class);
		securityTests.addTestSuite(SecurityTest.class);
		securityTests.addTestSuite(ConvenienceMethodsSecurityTest.class);
		mainSuite.addTest(securityTests);

		TestSuite metadataTests = new TestSuite("Metadata Tests");
		metadataTests.addTestSuite(TestMetaDataSearch.class);
		mainSuite.addTest(metadataTests);

		TestSuite functionalTests = new TestSuite("Functional Tests");
		functionalTests.addTestSuite(TestApproximateStringMatch.class);
		functionalTests.addTestSuite(TestAttributePresenceMatch.class);
		functionalTests.addTestSuite(TestAttributeValueMatch.class);
		functionalTests.addTestSuite(TestCodingSchemesWithSupportedAssociation.class);
		functionalTests.addTestSuite(TestContentExtraction.class);
		functionalTests.addTestSuite(TestDAGWalking.class);
		functionalTests.addTestSuite(TestDescribeSearchTechniques.class);
		functionalTests.addTestSuite(TestDescribeSupportedSearchCriteria.class);
		functionalTests.addTestSuite(TestDiscoverAvailableVocabulariesandVersions.class);
		functionalTests.addTestSuite(TestEnumerateAllConcepts.class);
		functionalTests.addTestSuite(TestEnumerateAssociationNames.class);
		functionalTests.addTestSuite(TestEnumerateConceptsbyRelationship.class);
		functionalTests.addTestSuite(TestEnumerateProperties.class);
		functionalTests.addTestSuite(TestEnumerateRelationsbyRange.class);
		functionalTests.addTestSuite(TestEnumerateRelationships.class);
		functionalTests.addTestSuite(TestEnumerateSourceConceptsforRelationandTarget.class);
		functionalTests.addTestSuite(TestforCurrentOrObsoleteConcept.class);
		functionalTests.addTestSuite(TestGenerateDAG.class);
		functionalTests.addTestSuite(TestLexicalMatchingTechniques.class);
		functionalTests.addTestSuite(TestLimitReturnedValues.class);
		functionalTests.addTestSuite(TestMapAttributestoTypes.class);
		functionalTests.addTestSuite(TestMapPreferredNametoCode.class);
		functionalTests.addTestSuite(TestMapSynonymtoPreferredNames.class);
		functionalTests.addTestSuite(TestMembershipinVocabulary.class);
		functionalTests.addTestSuite(TestOtherMatchingTechniques.class);
		functionalTests.addTestSuite(TestPagedReturns.class);
		functionalTests.addTestSuite(TestQuerybyRelationshipDomain.class);
		functionalTests.addTestSuite(TestRelationshipInquiry.class);
		functionalTests.addTestSuite(TestRetrieveConceptandAttributesbyCode.class);
		functionalTests.addTestSuite(TestRetrieveConceptandAttributesbyPreferredName.class);
		functionalTests.addTestSuite(TestRetrieveMostRecentVersionofConcept.class);
		functionalTests.addTestSuite(TestRetrieveRelationsforConcept.class);
		functionalTests.addTestSuite(TestSearchbyStatus.class);
		functionalTests.addTestSuite(TestSetofVocabulariesforSearch.class);
		functionalTests.addTestSuite(TestSpecifyReturnOrder.class);
		functionalTests.addTestSuite(TestSubsetExtraction.class);
		functionalTests.addTestSuite(TestTraverseGraphviaRoleLinks.class); 
		functionalTests.addTestSuite(TestVersionChanges.class);
		functionalTests.addTestSuite(TestVersioningandAuthorityEnumeration.class);	
		mainSuite.addTest(functionalTests);

		TestSuite serviceTests = new TestSuite("Service Tests");
		serviceTests.addTestSuite(CodedNodeGraphImplTest.class);
		serviceTests.addTestSuite(CodedNodeSetImplTest.class);
		serviceTests.addTestSuite(LexBIGServiceConvenienceMethodsTest.class);
		serviceTests.addTestSuite(LexBIGServiceTest.class);
		serviceTests.addTestSuite(ResolvedConceptReferenceIteratorTest.class);
		mainSuite.addTest(serviceTests);

		return mainSuite;
    }
}