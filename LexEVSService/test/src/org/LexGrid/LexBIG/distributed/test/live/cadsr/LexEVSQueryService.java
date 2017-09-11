package org.LexGrid.LexBIG.distributed.test.live.cadsr;


import java.util.List;

public interface LexEVSQueryService {

	public List<EVSConcept> findConceptDetailsByName(List<String> conceptNames, boolean includeRetiredConcepts) throws EVSException;
	public List<EVSConcept> findConceptDetailsByName(List<String> conceptNames, boolean includeRetiredConcepts, String vocabName) throws EVSException;
	public List findConceptsByCode(String conceptCode, boolean includeRetiredConcepts, int rowCount) throws EVSException;
	public List findConceptsByCode(String conceptCode, boolean includeRetiredConcepts, int rowCount, String vocabName) throws EVSException;
	public List<EVSConcept> findConceptsByPreferredName(String searchTerm, boolean includeRetiredConcepts) throws EVSException;
	public List<EVSConcept> findConceptsByPreferredName(String searchTerm, boolean includeRetiredConcepts, String vocabName) throws EVSException;
	public List<EVSConcept> findConceptsBySynonym(String searchTerm, boolean includeRetiredConcepts, int rowCount) throws EVSException;
	public List<EVSConcept> findConceptsBySynonym(String searchTerm, boolean includeRetiredConcepts, int rowCount, String vocabName) throws EVSException;
	
}
