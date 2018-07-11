/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package org.LexGrid.LexBIG.distributed.test.live.cadsr;


import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.LexGrid.LexBIG.DataModel.Collections.LocalNameList;
import org.LexGrid.LexBIG.DataModel.Collections.NameAndValueList;
import org.LexGrid.LexBIG.DataModel.Collections.SortOptionList;
import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.SearchDesignationOption;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.Utility.LBConstants.MatchAlgorithms;
import org.LexGrid.LexBIG.Utility.Iterators.ResolvedConceptReferencesIterator;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSService;
import org.LexGrid.LexBIG.distributed.test.live.testutil.RemoteServerUtil;
import org.LexGrid.commonTypes.Property;
import org.LexGrid.concepts.Definition;
import org.LexGrid.concepts.Entity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gov.nih.nci.system.client.ApplicationServiceProvider;

public class LexEVSQueryServiceImpl implements LexEVSQueryService {

	private static LexBIGService service;
	private static final String NCIT_SCHEME_NAME = RemoteServerUtil.NCIT_SCHEME_NAME;
	
	static {
		try {
			service =  RemoteServerUtil.createLexBIGService();

		} catch (Exception e) {

		}
	}
	
	 public ResolvedConceptReferencesIterator resolveNodeSet(CodedNodeSet cns, boolean includeRetiredConcepts) throws Exception {
			
			if (!includeRetiredConcepts) {
				cns.restrictToStatus(CodedNodeSet.ActiveOption.ACTIVE_ONLY, null);
			}
			CodedNodeSet.PropertyType propTypes[] = new CodedNodeSet.PropertyType[2];
			propTypes[0] = CodedNodeSet.PropertyType.PRESENTATION;
			propTypes[1] = CodedNodeSet.PropertyType.DEFINITION;
			
			SortOptionList sortCriteria = Constructors.createSortOptionList(new String[]{"matchToQuery"});
			
			ResolvedConceptReferencesIterator results = cns.resolve(sortCriteria, null,new LocalNameList(), propTypes, true);
			
			return results;
		}

	public List findConceptsByCode(String conceptCode, boolean includeRetiredConcepts, int rowCount) throws EVSException {
		return findConceptsByCode(conceptCode, includeRetiredConcepts, rowCount, NCIT_SCHEME_NAME);
	}
	
	public List<EVSConcept> findConceptDetailsByName(List<String> conceptNames, boolean includeRetiredConcepts, String vocabName) throws EVSException {
		List<EVSConcept> evsConcepts = new ArrayList<EVSConcept>();
		
		if (conceptNames != null) {
			for (String conceptName: conceptNames) {
				List<EVSConcept> evsConceptsChunk = (List<EVSConcept>)findConceptsByCode(conceptName, includeRetiredConcepts, 0, vocabName);
				evsConcepts.addAll(evsConceptsChunk);
			}
		}
		return evsConcepts;
	}

	public List<EVSConcept> findConceptDetailsByName(List<String> conceptNames, boolean includeRetiredConcepts) throws EVSException {
		
		return findConceptDetailsByName(conceptNames, includeRetiredConcepts, NCIT_SCHEME_NAME);
	}
	
	public List<EVSConcept> findConceptsByPreferredName(String searchTerm, boolean includeRetiredConcepts, String vocabName) throws EVSException {
		List<EVSConcept> evsConcepts = new ArrayList<EVSConcept>();
		try {
			CodedNodeSet cns = service.getNodeSet(vocabName, null, null);
			cns = cns.restrictToMatchingDesignations(
					searchTerm, 
					CodedNodeSet.SearchDesignationOption.PREFERRED_ONLY, 
					MatchAlgorithms.exactMatch.name(), 
					null);
			
			ResolvedConceptReferencesIterator results = resolveNodeSet(cns, includeRetiredConcepts);
			evsConcepts = getEVSConcepts(results);
		} catch (Exception e) {
			throw new EVSException("Error finding concepts for synonym ["+searchTerm+"]", e);
		}
		return evsConcepts;
	}
	
	public List<EVSConcept> findConceptsByPreferredName(String searchTerm, boolean includeRetiredConcepts) throws EVSException {
		return findConceptsByPreferredName(searchTerm, includeRetiredConcepts, NCIT_SCHEME_NAME);
	}
	
	public List<EVSConcept> findConceptsBySynonym(String searchTerm,
		boolean includeRetiredConcepts, int rowCount, String vocabName)
			throws EVSException {
		List<EVSConcept> evsConcepts = new ArrayList<EVSConcept>();
		try {
			CodedNodeSet cns = service.getNodeSet(vocabName, null, null);

			String[][] termAndMatchAlgorithmName = getTermAndMatchAlgorithmName(searchTerm);
			cns = cns.restrictToMatchingDesignations(
					termAndMatchAlgorithmName[0][0], 
					SearchDesignationOption.ALL, 
					termAndMatchAlgorithmName[0][1],
					null
				);

			ResolvedConceptReferencesIterator results = resolveNodeSet(cns, includeRetiredConcepts);
			evsConcepts = getEVSConcepts(results);
			
			
		} catch (Exception e) {
			throw new EVSException("Error finding concepts for synonym ["+searchTerm+"]", e);
		}
		return evsConcepts;
	}
	
	public List findConceptsByCode(String conceptCode, boolean includeRetiredConcepts, int rowCount, String vocabName)
			throws EVSException {
		List<EVSConcept> evsConcepts = new ArrayList<EVSConcept>();
		try {
			CodedNodeSet cns = service.getNodeSet(vocabName, null, null);
			cns = cns.restrictToCodes(Constructors.createConceptReferenceList(conceptCode));
			ResolvedConceptReferencesIterator results = resolveNodeSet(cns, includeRetiredConcepts);
			evsConcepts = getEVSConcepts(results);
		} catch (Exception e) {
			throw new EVSException("Error finding concept for code ["+conceptCode+"]", e);
		}
		return evsConcepts;
	}	
	
	
	public List<EVSConcept> findConceptsBySynonym(String searchTerm,
			boolean includeRetiredConcepts, int rowCount) throws EVSException {
		return findConceptsBySynonym(searchTerm, includeRetiredConcepts, rowCount, NCIT_SCHEME_NAME);
	}

	private List<EVSConcept> getEVSConcepts(ResolvedConceptReferencesIterator rcRefIter) throws Exception {
		List<EVSConcept> evsConcepts = new ArrayList<EVSConcept>();
		if (rcRefIter != null) {
			while (rcRefIter.hasNext()) {
				ResolvedConceptReference conceptRef = rcRefIter.next();
				
				EVSConcept evsConcept = getEVSConcept(conceptRef);
				
				evsConcepts.add(evsConcept);
			}
		}
		return evsConcepts;
	}
	
	private EVSConcept getEVSConcept(ResolvedConceptReference rcRef) {
		EVSConcept evsConcept = new EVSConcept();
		evsConcept.setCode(rcRef.getCode());
		
		Entity entity = rcRef.getEntity();
		evsConcept.setDefinitions(getEntityDefinitions(entity));
		evsConcept = setProperties(evsConcept, entity);
		
		return evsConcept;
	}
	
	private List<Definition> getEntityDefinitions(Entity entity) {
		List<Definition> definitions = new ArrayList<Definition>();
		
		if (entity != null) {
			Definition[] defs = entity.getDefinition();
			for (Definition def: defs) {
				definitions.add(def);
			}
		}
		
		return definitions;
	}
	
	private EVSConcept setProperties(EVSConcept evsConcept, Entity entity) {
		if (entity != null) {
			List<String> synonyms = new ArrayList<String>();
			
			Property[] entityProps = entity.getAllProperties();
			for (Property entityProp: entityProps) {
				String propName = entityProp.getPropertyName();
				String propValue = entityProp.getValue().getContent();
				
				if (propName.equalsIgnoreCase("FULL_SYN")) {
					synonyms.add(propValue);
				}
				else if (propName.equalsIgnoreCase("Preferred_Name")) {
					evsConcept.setPreferredName(propValue);
					evsConcept.setName(propValue);
				}
			}
			evsConcept.setSynonyms(synonyms);
		}
		
		return evsConcept;
	}
		
	public static CodedNodeSet restrictToSource(CodedNodeSet cns, String source) {
		if (cns == null) return cns;
		if (source == null || source.compareTo("*") == 0 || source.compareTo("") == 0 || source.compareTo("ALL") == 0) return cns;

		LocalNameList contextList = null;
		LocalNameList sourceLnL = null;
		NameAndValueList qualifierList = null;

		Vector<String> w2 = new Vector<String>();
		w2.add(source);
		sourceLnL = vector2LocalNameList(w2);
		LocalNameList propertyLnL = null;
		CodedNodeSet.PropertyType[] types = new CodedNodeSet.PropertyType[] {CodedNodeSet.PropertyType.PRESENTATION};
		try {
			cns = cns.restrictToProperties(propertyLnL, types, sourceLnL, contextList, qualifierList);
		} catch (Exception ex) {
			System.out.println("restrictToSource throws exceptions.");
			return null;
		}
		return cns;
	}
	
	public static LocalNameList vector2LocalNameList(Vector<String> v) {
		if (v == null)
			return null;
		LocalNameList list = new LocalNameList();
		for (int i = 0; i < v.size(); i++) {
			String vEntry = (String) v.elementAt(i);
			list.addEntry(vEntry);
		}
		return list;
	}
	
	private String[][] getTermAndMatchAlgorithmName(String searchTerm) {
		String[][] termAndMatchAlgorithm = new String[1][2];
		if (searchTerm.startsWith("*")) {
			if (searchTerm.endsWith("*")) {
				termAndMatchAlgorithm[0][0] = searchTerm.substring(1, searchTerm.length()-1);
			}
			else {
				termAndMatchAlgorithm[0][0] = searchTerm.substring(1);
			}
			termAndMatchAlgorithm[0][1] = findBestContainsAlgorithm(searchTerm);
		}
		else if (searchTerm.endsWith("*")) {
			termAndMatchAlgorithm[0][0] = searchTerm.substring(0, searchTerm.length()-1);
			termAndMatchAlgorithm[0][1] = MatchAlgorithms.startsWith.name();
		}
		else if (searchTerm.contains("*")) {
			termAndMatchAlgorithm[0][0] = searchTerm;
			termAndMatchAlgorithm[0][1] = MatchAlgorithms.LuceneQuery.name();
		}
		else {
			termAndMatchAlgorithm[0][0] = searchTerm;
			termAndMatchAlgorithm[0][1] = MatchAlgorithms.exactMatch.name();
		}
		
		return termAndMatchAlgorithm;
	}
	
	private String findBestContainsAlgorithm(String matchText) {
		if (matchText == null) return "nonLeadingWildcardLiteralSubString";
		matchText = matchText.trim();
		if (matchText.length() == 0) return "nonLeadingWildcardLiteralSubString"; // or null
		if (matchText.length() > 1) return "nonLeadingWildcardLiteralSubString";
		char ch = matchText.charAt(0);
		if (Character.isDigit(ch)) return "literal";
		else if (Character.isLetter(ch)) return "LuceneQuery";
		else return "literalContains";
	}
	
}
