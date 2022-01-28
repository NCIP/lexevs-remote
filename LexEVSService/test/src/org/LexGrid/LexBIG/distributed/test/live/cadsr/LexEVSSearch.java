package org.LexGrid.LexBIG.distributed.test.live.cadsr;

import java.util.HashMap;
import java.util.Vector;

import org.LexGrid.LexBIG.DataModel.Collections.AssociationList;
import org.LexGrid.LexBIG.DataModel.Collections.LocalNameList;
import org.LexGrid.LexBIG.DataModel.Collections.NameAndValueList;
import org.LexGrid.LexBIG.DataModel.Collections.ResolvedConceptReferenceList;
import org.LexGrid.LexBIG.DataModel.Core.AssociatedConcept;
import org.LexGrid.LexBIG.DataModel.Core.Association;
import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.DataModel.Core.ConceptReference;
import org.LexGrid.LexBIG.DataModel.Core.NameAndValue;
import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Extensions.Generic.LexBIGServiceConvenienceMethods;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.ActiveOption;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.PropertyType;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.SearchDesignationOption;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.Utility.ConvenienceMethods;
import org.LexGrid.LexBIG.Utility.LBConstants;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.distributed.test.live.testutil.RemoteServerUtil;
import org.LexGrid.codingSchemes.CodingScheme;
import org.LexGrid.concepts.Definition;
import org.LexGrid.concepts.Entity;
import org.LexGrid.naming.Mappings;
import org.LexGrid.naming.SupportedHierarchy;

import gov.nih.nci.evs.security.SecurityToken;

public class LexEVSSearch {

	// constant variables
	/**
	 * constant value for empty data to get the vocab attribute from the user
	 * bean according to what is needed
	 */
	public static final int VOCAB_NULL = 0;
	/** constant value to return display vocab name */
	public static final int VOCAB_DISPLAY = 1;
	/** constant value to return database vocab orgin */
	public static final int VOCAB_DBORIGIN = 2;
	/** constant value to return database vocab name */
	public static final int VOCAB_NAME = 3;

	public static final String NCIT_SCHEME_NAME = "NCI Thesaurus";

	public static LexBIGServiceConvenienceMethods conMthds = null;
	public static ConceptReference retiredRootCon = null;
	/** constant value to recorgnize meta data */
//	public static final String META_VALUE = "MetaValue";
	LexBIGService evsService = null;

	
	public LexEVSSearch(){
		initialize();
	}
	/**
	 * Initializes the EVS Service.
	 */
	private void initialize() {
		try {

			evsService = RemoteServerUtil.createLexBIGService();		
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public String do_getEVSCode(String prefName) {

		String CCode = null;
			ResolvedConceptReferenceList codes2 = null;
			int codesSize = 0;

			try {
				SecurityToken token = new SecurityToken();
				this.registerSecurityToken((LexEVSApplicationService) evsService, token);
				CodedNodeSet metaNodes = evsService.getNodeSet(RemoteServerUtil.NCIM_SCHEME_NAME, null, null);

				metaNodes = metaNodes.restrictToMatchingDesignations(prefName, 
						CodedNodeSet.SearchDesignationOption.PREFERRED_ONLY, 
						"exactMatch",
						null); 

				codes2 = metaNodes.resolveToList(null, 
						null,
						new CodedNodeSet.PropertyType[] { PropertyType.PRESENTATION }, 
						10 
				);
				codesSize = codes2.getResolvedConceptReferenceCount();

			} catch (Exception ex) {
				throw new RuntimeException("failed to resolve code", ex);
			}

			if (codes2 != null) {
				ResolvedConceptReference conceptReference = new ResolvedConceptReference();

				for (int i = 0; i < codesSize; i++) {
					conceptReference = (ResolvedConceptReference) codes2.getResolvedConceptReference(i);
					CCode = (String) conceptReference.getConceptCode();

				}
			}
		return CCode;
	}

	public HashMap<String, ResolvedConceptReference> returnSubConcepts(String code, String scheme) throws LBException {
		HashMap<String, ResolvedConceptReference> ret = new HashMap<String, ResolvedConceptReference>();

		CodingScheme cs = evsService.resolveCodingScheme(scheme, null);
		boolean forwardNavigable = cs.getMappings().getSupportedHierarchy()[0].getIsForwardNavigable();
		String relation = returnAssociations(cs);

		// Perform the query ...
		NameAndValue nv = new NameAndValue();
		NameAndValueList nvList = new NameAndValueList();
		nv.setName(relation);
		nvList.addNameAndValue(nv);

		ResolvedConceptReferenceList matches = evsService.getNodeGraph(scheme, null, null)
				.restrictToAssociations(nvList, null)
				.resolveAsList(ConvenienceMethods.createConceptReference(code, scheme), forwardNavigable,
						!forwardNavigable, 1, 1, new LocalNameList(), null, null, 1024);

		// Analyze the result ...
		ret = getAssociatedConcepts(matches, true);
		return ret;
	}

	public HashMap<String, ResolvedConceptReference> returnSuperConcepts(String code, String scheme)
			throws LBException {
		HashMap<String, ResolvedConceptReference> ret = new HashMap<String, ResolvedConceptReference>();

		CodingScheme cs = evsService.resolveCodingScheme(scheme, null);
		boolean forwardNavigable = cs.getMappings().getSupportedHierarchy()[0].getIsForwardNavigable();
		String relation = returnAssociations(cs);

		// Perform the query ...
		NameAndValue nv = new NameAndValue();
		NameAndValueList nvList = new NameAndValueList();
		nv.setName(relation);
		nvList.addNameAndValue(nv);

		ResolvedConceptReferenceList matches = evsService.getNodeGraph(scheme, null, null)
				.restrictToAssociations(nvList, null)
				.resolveAsList(ConvenienceMethods.createConceptReference(code, scheme), !forwardNavigable,
						forwardNavigable, 1, 1, new LocalNameList(), null, null, 1024);

		// Analyze the result ...
		ret = getAssociatedConcepts(matches, true);
		return ret;
	}

	private String returnAssociations(CodingScheme cs) throws LBException {

		String ret = new String();

		Mappings mappings = cs.getMappings();
		SupportedHierarchy[] hierarchies = mappings.getSupportedHierarchy();
		SupportedHierarchy hierarchyDefn = hierarchies[0];
		String[] associationsToNavigate = hierarchyDefn.getAssociationNames();// associations

		for (String assn : associationsToNavigate) {
			if (assn.equals("subClassOf")) {
				ret = assn;
				// we prefer this association
				break;
			}
			if (assn.equals("is_a")) {
				ret = assn;
				break;
			}
			if (ret.length() == 0 && hierarchyDefn.getLocalId().equals("is_a"))
				ret = assn;

		}

		return ret;

	}

	private HashMap getAssociatedConcepts(ResolvedConceptReferenceList matches, boolean resolveConcepts) {
		HashMap ret = new HashMap();

		if (matches.getResolvedConceptReferenceCount() > 0) {
			ResolvedConceptReference ref = (ResolvedConceptReference) matches.enumerateResolvedConceptReference()
					.nextElement();

			// Print the associations
			AssociationList targetof = ref.getTargetOf();
			if (targetof != null) {
				Association[] associations = targetof.getAssociation();
				for (int i = 0; i < associations.length; i++) {
					Association assoc = associations[i];
					if (assoc != null && assoc.getAssociatedConcepts() != null
							&& assoc.getAssociatedConcepts().getAssociatedConcept() != null) { 
						AssociatedConcept[] acl = assoc.getAssociatedConcepts().getAssociatedConcept();
						for (int j = 0; j < acl.length; j++) {
							AssociatedConcept ac = acl[j];
							if (resolveConcepts)
								ret.put(ac.getCode(), ac);
							else
								ret.put(ac.getCode(), ac.getEntityDescription().getContent());
						}
					}
				}
			} else {

				AssociationList sourceOf = ref.getSourceOf();

				if (sourceOf != null) {
					Association[] associations = sourceOf.getAssociation();
					for (int i = 0; i < associations.length; i++) {
						Association assoc = associations[i];
						if (assoc != null && assoc.getAssociatedConcepts() != null
								&& assoc.getAssociatedConcepts().getAssociatedConcept() != null) { 
							AssociatedConcept[] acl = assoc.getAssociatedConcepts().getAssociatedConcept();
							for (int j = 0; j < acl.length; j++) {
								AssociatedConcept ac = acl[j];
								if (resolveConcepts)
									ret.put(ac.getCode(), ac);
								else
									ret.put(ac.getCode(), ac.getEntityDescription().getContent());
							}
						}
					}
				}
			}
		}

		return ret;
	}

	public ResolvedConceptReferenceList searchPrefTerm(LexEVSApplicationService evsService, String dtsVocab,
			String prefName, int sMetaLimit, String algorithm, String designation) {
		int codesSize = 0;
		ResolvedConceptReferenceList concepts = new ResolvedConceptReferenceList();
		try {
			CodedNodeSet metaNodes = evsService.getNodeSet(dtsVocab, null, null);

			metaNodes = metaNodes.restrictToMatchingDesignations(prefName, 
					CodedNodeSet.SearchDesignationOption.ALL,
					algorithm,
					null);

			metaNodes = metaNodes.restrictToStatus(ActiveOption.ACTIVE_ONLY, null);

			concepts = metaNodes.resolveToList(null, 
					null,
					new CodedNodeSet.PropertyType[] { PropertyType.DEFINITION, PropertyType.PRESENTATION }, 
					sMetaLimit 
			);
			codesSize = concepts.getResolvedConceptReferenceCount();
		} catch (Exception ex) {
			// ex.printStackTrace();
			System.out.println("Error searchPrefTerm: " + ex.toString());
		}

		return concepts;
	}

	public int searchConceptsName(LexEVSApplicationService lbSvc, String vocabName, String vocabVersion) {
		String searchTerm = "name";

		try {
			CodingSchemeVersionOrTag cvt = new CodingSchemeVersionOrTag();
			cvt.setVersion(vocabVersion);
			CodedNodeSet nodes = lbSvc.getNodeSet(vocabName, cvt, null);
			nodes = nodes.restrictToMatchingDesignations(searchTerm, SearchDesignationOption.ALL,
					LBConstants.MatchAlgorithms.exactMatch.name(), null);
			nodes = nodes.restrictToStatus(ActiveOption.ALL, null);
			ResolvedConceptReferenceList crl = nodes.resolveToList(null, null, null, 20);
			Vector<String> definitions = new Vector<String>();
			for (int i = 0; i < crl.getResolvedConceptReferenceCount(); i++) {
				Entity concept = crl.getResolvedConceptReference(i).getEntity();
				Definition[] defs = concept.getDefinition();
				for (int j = 0; j < defs.length; j++) {
					Definition tempDef = defs[j];
					String defText = tempDef.getValue().getContent();
					definitions.add(concept.getEntityCode() + " " + tempDef.getSource(0).getContent() + " " + defText);
				}
			}

			for (String def : definitions) {
				System.out.println(def);
			}
			return crl.getResolvedConceptReferenceCount();
		} catch (Exception ex) {
			System.out
					.println("searchConcepts_name " + vocabName + " and " + vocabVersion + " throws Exception = " + ex);
		}
		return 0;
	}

	public HashMap<String, String> returnSubConceptNames(String code, String scheme) throws LBException {
		HashMap<String, String> ret = new HashMap<String, String>();

		CodingScheme cs = evsService.resolveCodingScheme(scheme, null);
		boolean forwardNavigable = cs.getMappings().getSupportedHierarchy()[0].getIsForwardNavigable();
		String relation = returnAssociations(cs);

		// Perform the query ...
		NameAndValue nv = new NameAndValue();
		NameAndValueList nvList = new NameAndValueList();
		nv.setName(relation);
		nvList.addNameAndValue(nv);

		ResolvedConceptReferenceList matches = evsService.getNodeGraph(scheme, null, null)
				.restrictToAssociations(nvList, null).resolveAsList(
						ConvenienceMethods.createConceptReference(code, scheme), forwardNavigable, !forwardNavigable, 1,
						1, new LocalNameList(), new PropertyType[] { PropertyType.PRESENTATION }, null, 1024);

		// Analyze the result ...
		ret = getAssociatedConcepts(matches, false);
		return ret;
	}
	
	public HashMap<String, String> returnSuperConceptNames(String code, String scheme) throws LBException {
		HashMap<String, String> ret = new HashMap<String, String>();

		CodingScheme cs = evsService.resolveCodingScheme(scheme, null);
		boolean forwardNavigable = cs.getMappings().getSupportedHierarchy()[0].getIsForwardNavigable();
		String relation = returnAssociations(cs);

		// Perform the query ...
		NameAndValue nv = new NameAndValue();
		NameAndValueList nvList = new NameAndValueList();
		nv.setName(relation);
		nvList.addNameAndValue(nv);

		ResolvedConceptReferenceList matches = evsService.getNodeGraph(scheme, null, null)
				.restrictToAssociations(nvList, null).resolveAsList(
						ConvenienceMethods.createConceptReference(code, scheme), !forwardNavigable, forwardNavigable, 1,
						1, new LocalNameList(), new PropertyType[] { PropertyType.PRESENTATION }, null, 1024);

		// Analyze the result ...
		ret = getAssociatedConcepts(matches, false);
		return ret;
	}

	private void registerSecurityToken(LexEVSApplicationService evsService2, SecurityToken token) {
		// TODO Auto-generated method stub

	}
}
