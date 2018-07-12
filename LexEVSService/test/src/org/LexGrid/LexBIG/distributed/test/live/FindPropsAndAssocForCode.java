package org.LexGrid.LexBIG.distributed.test.live;

/*
 * Copyright: (c) 2004-2009 Mayo Foundation for Medical Education and
 * Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
 * triple-shield Mayo logo are trademarks and service marks of MFMER.
 *
 * Except as contained in the copyright notice above, or as used to identify
 * MFMER as the author of this software, the trade names, trademarks, service
 * marks, or product names of the copyright holder shall not be used in
 * advertising, promotion or otherwise in connection with this software without
 * prior written authorization of the copyright holder.
 *
 * Licensed under the Eclipse Public License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 		http://www.eclipse.org/legal/epl-v10.html
 *
 */
//package org.LexGrid.LexBIG.example;


import java.io.*;
import java.net.URI;
import java.text.*;
import java.util.*;
import java.sql.*;

import gov.nih.nci.system.client.ApplicationServiceProvider;
import org.LexGrid.LexBIG.DataModel.Collections.AbsoluteCodingSchemeVersionReferenceList;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.Utility.Iterators.ResolvedConceptReferencesIterator;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSDistributed;
import org.LexGrid.LexBIG.distributed.test.live.testutil.RemoteServerUtil;
import org.LexGrid.naming.Mappings;
import org.LexGrid.naming.SupportedCodingScheme;
import org.LexGrid.util.PrintUtility;
import org.LexGrid.valueSets.DefinitionEntry;
import org.LexGrid.valueSets.EntityReference;
import org.LexGrid.valueSets.PropertyMatchValue;
import org.LexGrid.valueSets.PropertyReference;
import org.LexGrid.valueSets.ValueSetDefinition;
import org.LexGrid.valueSets.types.DefinitionOperator;
import org.lexgrid.valuesets.LexEVSValueSetDefinitionServices;
import org.lexgrid.valuesets.dto.ResolvedValueSetDefinition;

import org.LexGrid.LexBIG.DataModel.Collections.*;
import org.LexGrid.LexBIG.DataModel.Core.*;
import org.LexGrid.LexBIG.Exceptions.*;
import org.LexGrid.LexBIG.History.*;
import org.LexGrid.LexBIG.LexBIGService.*;
import org.LexGrid.LexBIG.Utility.*;
import org.LexGrid.concepts.*;
import org.LexGrid.LexBIG.DataModel.InterfaceElements.*;
import org.LexGrid.LexBIG.Utility.Iterators.*;
import org.LexGrid.codingSchemes.*;
import org.LexGrid.commonTypes.*;
import org.LexGrid.relations.Relations;
import org.LexGrid.versions.*;
import org.LexGrid.naming.*;
import org.LexGrid.LexBIG.DataModel.Core.types.*;
import org.LexGrid.LexBIG.Extensions.Generic.*;


import org.LexGrid.LexBIG.Extensions.Generic.MappingExtension;
import org.LexGrid.LexBIG.Extensions.Generic.MappingExtension.Direction;
import org.LexGrid.LexBIG.Extensions.Generic.MappingExtension.MappingSortOption;
import org.LexGrid.LexBIG.Extensions.Generic.MappingExtension.MappingSortOptionName;
import org.LexGrid.LexBIG.Extensions.Generic.MappingExtension.QualifierSortOption;
import org.LexGrid.LexBIG.Utility.Iterators.ResolvedConceptReferencesIterator;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSDistributed;

import org.LexGrid.LexBIG.Extensions.Generic.SupplementExtension;
import org.LexGrid.relations.AssociationPredicate;

import org.LexGrid.LexBIG.caCore.interfaces.LexEVSDistributed;
import org.lexgrid.valuesets.LexEVSValueSetDefinitionServices;

import org.LexGrid.valueSets.ValueSetDefinition;
import org.LexGrid.commonTypes.Source;


import org.apache.log4j.*;
import org.LexGrid.LexBIG.Extensions.Generic.MappingExtension.Mapping;
import org.LexGrid.LexBIG.Extensions.Generic.MappingExtension.Mapping.SearchContext;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.lexgrid.valuesets.impl.LexEVSValueSetDefinitionServicesImpl;


import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.Impl.LexBIGServiceImpl;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.ActiveOption;



/*
import java.util.Enumeration;

import org.LexGrid.commonTypes.PropertyQualifier;
import org.LexGrid.commonTypes.Source;


import org.LexGrid.LexBIG.DataModel.Collections.AssociationList;
import org.LexGrid.LexBIG.DataModel.Collections.ConceptReferenceList;
import org.LexGrid.LexBIG.DataModel.Collections.LocalNameList;
import org.LexGrid.LexBIG.DataModel.Collections.ResolvedConceptReferenceList;
import org.LexGrid.LexBIG.DataModel.Core.AssociatedConcept;
import org.LexGrid.LexBIG.DataModel.Core.Association;
import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeSummary;
import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Impl.LexBIGServiceImpl;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.ActiveOption;
import org.LexGrid.LexBIG.Utility.ConvenienceMethods;
import org.LexGrid.commonTypes.EntityDescription;
import org.LexGrid.commonTypes.Property;
import org.LexGrid.concepts.Entity;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;

import org.LexGrid.concepts.Comment;
import org.LexGrid.concepts.Definition;
import org.LexGrid.concepts.Presentation;

import org.LexGrid.LexBIG.DataModel.Core.NameAndValue;

import gov.nih.nci.system.applicationservice.ApplicationException;
import gov.nih.nci.system.client.ApplicationServiceProvider;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
*/

/**
 * Example showing how to find concept properties and associations based on a code.
 */
public class FindPropsAndAssocForCode {
    //static String serviceUrl = "http://ncias-d488-v.nci.nih.gov:29080/lexevsapi60";
    static String serviceUrl = "https://lexevsapi65.nci.nih.gov";
    //static String serviceUrl = "https://lexevsapi6.nci.nih.gov/lexevsapi64";
//    static String serviceUrl = "https://lexevsapi65.nci.nih.gov";
    LexBIGService lbSvc = null;

    public FindPropsAndAssocForCode() {
        //super();
    }


    public FindPropsAndAssocForCode(LexBIGService lbSvc) {
        this.lbSvc = lbSvc;
    }

    public static LexBIGService createLexBIGService() throws Exception {
		return RemoteServerUtil.createLexBIGService();
	}


    public static LexBIGService createLexBIGService(String serviceUrl)
    {
        try {
            LexEVSApplicationService lexevsService = (LexEVSApplicationService)ApplicationServiceProvider.getApplicationServiceFromUrl(serviceUrl, "EvsServiceInfo");
            return (LexBIGService) lexevsService;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }



    static void displayAndLogError(String s, Exception e) {
		System.out.println(s);
		e.printStackTrace();
	}

    /**
     * Process the provided code.
     *
     * @param code
     * @throws LBException
     */
    public void run(String scheme, String version, String code) throws LBException {
	    //LexBIGService lbSvc = createLexBIGService();

		CodingSchemeVersionOrTag csvt = new CodingSchemeVersionOrTag();
		if (version != null) csvt.setVersion(version);
		printProps(code, lbSvc, scheme, csvt);
		printFrom(code, lbSvc, scheme, csvt);
		printTo(code, lbSvc, scheme, csvt);
    }


    void displayMessage(String s) {
	   System.out.println(s);
	}


    /**
     * Display properties for the given code.
     *
     * @param code
     * @param lbSvc
     * @param scheme
     * @param csvt
     * @return
     * @throws LBException
     */
    protected boolean printProps(String code, LexBIGService lbSvc, String scheme, CodingSchemeVersionOrTag csvt)
            throws LBException {
        // Perform the query ...

        System.out.println("======================================================================");
        System.out.println("Coding scheme: " + scheme);
        System.out.println("Coding scheme version: " + csvt.getVersion());
        System.out.println("code: " + code);
        System.out.println("======================================================================");

        ConceptReferenceList crefs = ConvenienceMethods.createConceptReferenceList(new String[] { code }, scheme);

        CodedNodeSet cns = lbSvc.getCodingSchemeConcepts(scheme, csvt);

if (cns == null) {
System.out.println("CNS == NULL???");
return false;
}


        cns = cns.restrictToStatus(ActiveOption.ALL, null);
        cns = cns.restrictToCodes(crefs);
        ResolvedConceptReferenceList matches = cns.resolveToList(null, null, null, 1);

        // Analyze the result ...
        if (matches.getResolvedConceptReferenceCount() > 0) {
            ResolvedConceptReference ref = (ResolvedConceptReference) matches.enumerateResolvedConceptReference()
                    .nextElement();

            Entity node = ref.getEntity();

            Presentation[] prsentations = node.getPresentation();
            for (int i = 0; i < prsentations.length; i++) {
                 Presentation presentation = prsentations[i];
                 displayMessage(new StringBuffer().append("\tPresentation name: ").append(presentation.getPropertyName())
                         .append(" text: ").append(presentation.getValue().getContent()).toString());

                 PropertyQualifier[] qualifiers = presentation.getPropertyQualifier();
                 for (int k=0; k<qualifiers.length; k++) {
                      PropertyQualifier qualifier = qualifiers[k];
						 displayMessage(new StringBuffer().append("\t\tQualifier name: ").append(qualifier.getPropertyQualifierName())
								 .append(" text: ").append(qualifier.getValue().getContent()).toString());
				 }

                 Source[] sources = presentation.getSource();
                 for (int k=0; k<sources.length; k++) {
                      Source source = sources[k];
						 displayMessage(new StringBuffer().append("\t\tSource: ").append(source.getContent()).toString());
				 }

            }
            System.out.println("\n");

            Definition[] definitions = node.getDefinition();
            for (int i = 0; i < definitions.length; i++) {
                Definition definition = definitions[i];
                displayMessage(new StringBuffer().append("\tDefinition name: ").append(definition.getPropertyName())
                        .append(" text: ").append(definition.getValue().getContent()).toString());
            }
            System.out.println("\n");

            Comment[] comments = node.getComment();
            for (int i = 0; i < comments.length; i++) {
                Comment comment = comments[i];
                displayMessage(new StringBuffer().append("\tComment name: ").append(comment.getPropertyName())
                        .append(" text: ").append(comment.getValue().getContent()).toString());
            }
            System.out.println("\n");

            Property[] props = node.getProperty();
            for (int i = 0; i < props.length; i++) {
                Property prop = props[i];
                displayMessage(new StringBuffer().append("\tProperty name: ").append(prop.getPropertyName())
                        .append(" text: ").append(prop.getValue().getContent()).toString());
            }
            System.out.println("\n");


            System.out.println("\n=========================================================================");

            props = node.getAllProperties();
            for (int i = 0; i < props.length; i++) {
                Property prop = props[i];
                displayMessage(new StringBuffer().append("\tProperty name: ").append(prop.getPropertyName())
                        .append(" text: ").append(prop.getValue().getContent()).toString());
            }
            System.out.println("\n");

        } else {
            displayMessage("No match found!");
            return false;
        }

        return true;
    }

    /**
     * Display relations to the given code from other concepts.
     *
     * @param code
     * @param lbSvc
     * @param scheme
     * @param csvt
     * @throws LBException
     */
    @SuppressWarnings("unchecked")
    protected void printFrom(String code, LexBIGService lbSvc, String scheme, CodingSchemeVersionOrTag csvt)
            throws LBException {
        displayMessage("Pointed at by ...");

        // Perform the query ...
        ResolvedConceptReferenceList matches = lbSvc.getNodeGraph(scheme, csvt, null).resolveAsList(
                ConvenienceMethods.createConceptReference(code, scheme), false, true, 1, 1, new LocalNameList(), null,
                null, 1024);

        // Analyze the result ...
        if (matches.getResolvedConceptReferenceCount() > 0) {
            Enumeration<? extends ResolvedConceptReference> refEnum = matches.enumerateResolvedConceptReference();

            while (refEnum.hasMoreElements()) {
                ResolvedConceptReference ref = refEnum.nextElement();
                AssociationList targetof = ref.getTargetOf();

                if (targetof != null) {
					Association[] associations = targetof.getAssociation();

					for (int i = 0; i < associations.length; i++) {
						Association assoc = associations[i];
						displayMessage("\t" + assoc.getAssociationName());

						AssociatedConcept[] acl = assoc.getAssociatedConcepts().getAssociatedConcept();
						for (int j = 0; j < acl.length; j++) {
							AssociatedConcept ac = acl[j];
							String rela = replaceAssociationNameByRela(ac, assoc.getAssociationName());
							EntityDescription ed = ac.getEntityDescription();
							displayMessage("\t\t" + ac.getConceptCode() + "/"
									+ (ed == null ? "**No Description**" : ed.getContent()) + " --> (" + rela + ") --> " + code);
						}
					}
			    }
            }
        }

    }

    /**
     * Display relations from the given code to other concepts.
     *
     * @param code
     * @param lbSvc
     * @param scheme
     * @param csvt
     * @throws LBException
     */
    @SuppressWarnings("unchecked")
    protected void printTo(String code, LexBIGService lbSvc, String scheme, CodingSchemeVersionOrTag csvt)
            throws LBException {
        displayMessage("Points to ...");

        // Perform the query ...
        ResolvedConceptReferenceList matches = lbSvc.getNodeGraph(scheme, csvt, null).resolveAsList(
                ConvenienceMethods.createConceptReference(code, scheme), true, false, 1, 1, new LocalNameList(), null,
                null, 1024);

        // Analyze the result ...
        if (matches.getResolvedConceptReferenceCount() > 0) {
            Enumeration<? extends ResolvedConceptReference> refEnum = matches.enumerateResolvedConceptReference();

            while (refEnum.hasMoreElements()) {
                ResolvedConceptReference ref = refEnum.nextElement();
                AssociationList sourceof = ref.getSourceOf();
                Association[] associations = sourceof.getAssociation();

                for (int i = 0; i < associations.length; i++) {
                    Association assoc = associations[i];
                    displayMessage("\t" + assoc.getAssociationName());

                    AssociatedConcept[] acl = assoc.getAssociatedConcepts().getAssociatedConcept();
                    for (int j = 0; j < acl.length; j++) {
                        AssociatedConcept ac = acl[j];
                        String rela = replaceAssociationNameByRela(ac, assoc.getAssociationName());

                        EntityDescription ed = ac.getEntityDescription();
                        displayMessage("\t\t" + code + " --> (" + rela + ") --> " + ac.getConceptCode() + "/"
                                + (ed == null ? "**No Description**" : ed.getContent()));
                    }
                }
            }
        }
    }



    private String replaceAssociationNameByRela(AssociatedConcept ac, String associationName) {
		if (ac.getAssociationQualifiers() == null) return associationName;
		if (ac.getAssociationQualifiers().getNameAndValue() == null) return associationName;

		for(NameAndValue qual : ac.getAssociationQualifiers().getNameAndValue()){
			String qualifier_name = qual.getName();
			String qualifier_value = qual.getContent();
			if (qualifier_name.compareToIgnoreCase("rela") == 0) {
				return qualifier_value; // replace associationName by Rela value
			}
		}
		return associationName;
	}


    public static void main(String[] args) {

		String scheme = null;
		String version = null;
		String code = null;	

		scheme = "NCI_Thesaurus";
		version = "17.05b4";
		code = "C37193";



        try {
			LexBIGService lbSvc = RemoteServerUtil.createLexBIGService();
			System.out.println("version: " + version);
            new FindPropsAndAssocForCode(lbSvc).run(scheme, version, code);
        } catch (Exception e) {
            displayAndLogError("REQUEST FAILED !!!", e);
        }
    }
}

/*

C97911 (NCIt equivalence class)


Coding scheme: NDF-RT
Coding scheme version: February2013
code: N0000148464
======================================================================
        Presentation name: Display_Name text: ALBENDAZOLE
        Presentation name: RxNorm_Name text: Albendazole
        Presentation name: label text: ALBENDAZOLE






        Property name: FDA_UNII text: F4216019LN
        Property name: Level text: Ingredient
        Property name: NUI text: N0000148464
        Property name: RxNorm_CUI text: 430
        Property name: UMLS_CUI text: C0001911
        Property name: VANDF_Record text: <VANDF_Record>50.6^3379^Active/Master<
/VANDF_Record><VA_File>50.6</VA_File><VA_IEN>3379</VA_IEN><VA_Status>Active/Mast
er</VA_Status>
        Property name: VANDF_Record text: <VANDF_Record>50.416^3662^Active/Maste
r</VANDF_Record><VA_File>50.416</VA_File><VA_IEN>3662</VA_IEN><VA_Status>Active/
Master</VA_Status>
        Property name: VUID text: 4020995
        Property name: code text: C9828
        Property name: primitive text: true



=========================================================================
        Property name: Display_Name text: ALBENDAZOLE
        Property name: RxNorm_Name text: Albendazole
        Property name: label text: ALBENDAZOLE
        Property name: FDA_UNII text: F4216019LN
        Property name: Level text: Ingredient
        Property name: NUI text: N0000148464
        Property name: RxNorm_CUI text: 430
        Property name: UMLS_CUI text: C0001911
        Property name: VANDF_Record text: <VANDF_Record>50.6^3379^Active/Master<
/VANDF_Record><VA_File>50.6</VA_File><VA_IEN>3379</VA_IEN><VA_Status>Active/Mast
er</VA_Status>
        Property name: VANDF_Record text: <VANDF_Record>50.416^3662^Active/Maste
r</VANDF_Record><VA_File>50.416</VA_File><VA_IEN>3662</VA_IEN><VA_Status>Active/
Master</VA_Status>
        Property name: VUID text: 4020995
        Property name: code text: C9828
        Property name: primitive text: true


Pointed at by ...
        Product_Component
                N0000160825/ALBENDAZOLE 200MG TAB -- (Product_Component) --> N00
00148464
        subClassOf
                N0000160825/ALBENDAZOLE 200MG TAB -- (subClassOf) --> N000014846
4
Points to ...
        subClassOf
                N0000148464 -- (subClassOf) --> @A418_b9cbad04_e6c8_4de3_8a06_b3
5a7cead5e1/has_PE some N0000008707
                N0000148464 -- (subClassOf) --> @A419_b9cbad04_e6c8_4de3_8a06_b3
5a7cead5e1/has_PE some N0000008574
                N0000148464 -- (subClassOf) --> @A420_b9cbad04_e6c8_4de3_8a06_b3
5a7cead5e1/has_PE some N0000009021
                N0000148464 -- (subClassOf) --> @A421_b9cbad04_e6c8_4de3_8a06_b3
5a7cead5e1/has_PE some N0000008647
                N0000148464 -- (subClassOf) --> @A422_b9cbad04_e6c8_4de3_8a06_b3
5a7cead5e1/has_Ingredient some N0000006466
                N0000148464 -- (subClassOf) --> @A423_b9cbad04_e6c8_4de3_8a06_b3
5a7cead5e1/has_MoA some N0000010260
                N0000148464 -- (subClassOf) --> @A424_b9cbad04_e6c8_4de3_8a06_b3
5a7cead5e1/CI_with some N0000000999
                N0000148464 -- (subClassOf) --> @A425_b9cbad04_e6c8_4de3_8a06_b3
5a7cead5e1/may_treat some N0000000795
                N0000148464 -- (subClassOf) --> @A426_b9cbad04_e6c8_4de3_8a06_b3
5a7cead5e1/may_treat some N0000001572
                N0000148464 -- (subClassOf) --> @A427_b9cbad04_e6c8_4de3_8a06_b3
5a7cead5e1/may_treat some N0000003557
                N0000148464 -- (subClassOf) --> @A428_b9cbad04_e6c8_4de3_8a06_b3
5a7cead5e1/may_treat some N0000003985
                N0000148464 -- (subClassOf) --> @A429_b9cbad04_e6c8_4de3_8a06_b3
5a7cead5e1/may_treat some N0000001331
                N0000148464 -- (subClassOf) --> @A430_b9cbad04_e6c8_4de3_8a06_b3
5a7cead5e1/may_treat some N0000002115
                N0000148464 -- (subClassOf) --> @A431_b9cbad04_e6c8_4de3_8a06_b3
5a7cead5e1/may_treat some N0000002224
                N0000148464 -- (subClassOf) --> @A432_b9cbad04_e6c8_4de3_8a06_b3
5a7cead5e1/may_treat some N0000002988
                N0000148464 -- (subClassOf) --> @A433_b9cbad04_e6c8_4de3_8a06_b3
5a7cead5e1/may_treat some N0000003014
                N0000148464 -- (subClassOf) --> @A434_b9cbad04_e6c8_4de3_8a06_b3
5a7cead5e1/may_treat some N0000001041
                N0000148464 -- (subClassOf) --> @A435_b9cbad04_e6c8_4de3_8a06_b3
5a7cead5e1/may_treat some N0000000383
                N0000148464 -- (subClassOf) --> N0000010583/A [Preparations]

*/