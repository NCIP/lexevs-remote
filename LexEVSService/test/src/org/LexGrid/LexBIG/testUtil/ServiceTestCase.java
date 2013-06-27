package org.LexGrid.LexBIG.testUtil;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import junit.framework.TestCase;

/**
 * The Class ServiceTestCase.
 */
abstract public class ServiceTestCase extends TestCase{
	
	/** The sys prop. */
	private static Properties sysProp = System.getProperties();
//	private static final String DEFAULT_SAX_DRIVER_CLASS ="org.apache.xerces.parsers.SAXParser";
//	private static SchemaInfo info = readConfig();
//	readConfig();
		
	/** The dom. */
	private static Document dom;

	/** The properties. */
	private static Properties properties = loadProperties();


	/** The Constant serviceUrl. */
	public final static String serviceUrl = properties.getProperty("serviceUrl");
	
	/** The Constant endpointUrl. */
	public final static String endpointUrl = properties.getProperty("endpointUrl");
	
	/** #of loaded CodingSchemes */
	public final static String LOADED_CODING_SCHEMES = properties.getProperty("LOADED_CODING_SCHEMES");//#of loaded CodingSchemes
	
	/** The Constant THES_SCHEME. */
	public final static String THES_SCHEME =  properties.getProperty("THES_SCHEME"); //NCI_Thesaurus";
	
	/** The Constant THES_LOINC. */
	public final static String LOINC_SCHEME =  properties.getProperty("LOINC_SCHEME");
	
	/** The Constant THES_URN. */
	public final static String THES_URN = properties.getProperty("THES_URN"); //"urn:oid:2.16.840.1.113883.3.26.1.1";
	
	/** The Constant THES_LOCAL. */
	public final static String THES_LOCAL = properties.getProperty("THES_LOCAL"); //"NCI Thesaurus";
	
	/** The Constant THES_FORMAL. */
	public final static String THES_FORMAL = properties.getProperty("THES_FORMAL"); //"NCI Thesaurus";
	
	/** The Constant THES_ISNATIVE. */
	public final static String THES_ISNATIVE = properties.getProperty("THES_ISNATIVE"); //"NCI Thesaurus";
	
	/** The Constant THES_APPROX_NUM_CONCEPTS. */
	public final static String THES_APPROX_NUM_CONCEPTS = properties.getProperty("THES_APPROX_NUM_CONCEPTS"); //"NCI Thesaurus";
	
	/** The Constant THES_ENT_DESC. */
	public final static String THES_ENT_DESC = properties.getProperty("THES_ENT_DESC"); //"NCI Thesaurus";
	
	/** The Constant THES_COPYRIGHT. */
	public final static String THES_COPYRIGHT = properties.getProperty("THES_COPYRIGHT"); //"NCI Thesaurus";
	
	/** The Constant THES_COPYRIGHT. */
	public final static String THES_DEFAULT_LANG = properties.getProperty("THES_DEFAULT_LANG"); //"NCI Thesaurus";
	
	/** The Constant THES_LOCAL_NSDI. */
	public final static String THES_LOCAL_NSDI = properties.getProperty("THES_LOCAL_NSDI"); //"40010";
	
	/** The Constant THES_TAG. */
	public final static String THES_TAG = properties.getProperty("THES_TAG"); //"PRODUCTION";
	
	/** The Constant THES_VERSION. */
	public final static String THES_VERSION = properties.getProperty("THES_VERSION"); 

	/** The Constant LOINC_VERSION. */
	public final static String LOINC_VERSION = properties.getProperty("LOINC_VERSION");
	
	public final static String THES_METADATA_VERSION = properties.getProperty("THES_METADATA_VERSION"); 
	
	/** Concept for testing root nodes */
	public final static String THES_ROOT_CONCEPT = properties.getProperty("THES_ROOT_CONCEPT");
	
	/** Concept for testing leaf nodes */
	public final static String THES_LEAF_CONCEPT = properties.getProperty("THES_LEAF_CONCEPT");
	
	/** Concept with both parent and child */
	public final static String THES_PARENT_CHILD_TEST_CONCEPT = properties.getProperty("THES_PARENT_CHILD_TEST_CONCEPT");
	
	
	
	
	
	
	/** The Constant META_SCHEME. */
	public final static String META_SCHEME = properties.getProperty("META_SCHEME"); //"NCI MetaThesaurus";
	
	/** The Constant META_URN. */
	public final static String META_URN = properties.getProperty("META_URN"); //"urn:oid:2.16.840.1.113883.3.26.1.2";
	
	/** The Constant META_VERSION. */
	public final static String META_VERSION = properties.getProperty("META_VERSION");
	
	public final static String META_METADATA_VERSION = properties.getProperty("TMETA_METADATA_VERSION"); 
	
	/** The Constant META_TOKEN. */
	public final static String META_TOKEN = properties.getProperty("META_TOKEN");
	
	/** The Constant MGED_SCHEME. */
	public final static String MGED_SCHEME = properties.getProperty("MGED_SCHEME"); //"The MGED Ontology";

	/** The Constant ZEBRAFISH_SCHEME. */
	public final static String ZEBRAFISH_SCHEME = properties.getProperty("ZEBRAFISH_SCHEME"); //"Zebrafish";

	/** The Constant ZEBRAFISH_VERSION. */
	public final static String ZEBRAFISH_VERSION = properties.getProperty("ZEBRAFISH_VERSION"); //"1";

	/** The Constant SNOMED_SCHEME. */
	public final static String SNOMED_SCHEME = properties.getProperty("SNOMED_SCHEME"); //"SNOMED Clinical Terms";

	/** The Constant SNOMED_VERSION. */
	public final static String SNOMED_VERSION = properties.getProperty("SNOMED_VERSION");
	
	/** Concept in Snomed that has parent and child  **/
	public final static String SNOMED_PC_CONCEPT = properties.getProperty("SNOMED_PC_CONCEPT");

	/** The Constant GO_SCHEME. */
	public final static String GO_SCHEME = properties.getProperty("GO_SCHEME"); //"Gene Ontology";

	/** The Constant GO_VERSION. */
	public final static String GO_VERSION = properties.getProperty("GO_VERSION");

	/** The Constant MEDDRA_SCHEME. */
	public final static String MEDDRA_SCHEME = properties.getProperty("MEDDRA_SCHEME"); //"MedDRA";
	
	/** The Constant MEDDRA_URN. */
	public final static String MEDDRA_URN = properties.getProperty("MEDDRA_URN"); //"MedDRA URN";

	/** The Constant MEDDRA_VERSION. */
	public final static String MEDDRA_VERSION = properties.getProperty("MEDDRA_VERSION"); 

	/** The Constant MEDDRA_TOKEN. */
	public final static String MEDDRA_TOKEN = properties.getProperty("MEDDRA_TOKEN");

	//The following are all source names in Meta
	/** The Constant meddraMeta. */
	public final static String meddraMeta = properties.getProperty("meddraMeta"); //"MDR";

	/** The Constant loincMeta. */
	public final static String loincMeta = properties.getProperty("loincMeta"); //"LNC";

	/** The Constant ncitMeta. */
	public final static String ncitMeta = properties.getProperty("ncitMeta"); //"NCI";

	/** The Constant nciMetaVersion. */
	public final static String nciMetaVersion = properties.getProperty("nciMetaVersion"); 

	/** The Constant snomedMeta. */
	public final static String snomedMeta = properties.getProperty("snomedMeta"); //"SNOMEDCT";

	/** The Constant meshMeta. */
	public final static String meshMeta = properties.getProperty("meshMeta"); //"MSH";

    /** The Constant NCIT_CONCODE. */
    public final static String NCIT_CONCODE = properties.getProperty("NCIT_CONCODE"); // Concept code from NCI Thesaurus
    
    /** The Constant SNOMED_CONCODE. */
    public final static String SNOMED_CONCODE = properties.getProperty("SNOMED_CONCODE"); // Concept code from SNOMED
    
    /** The Constant GO_CONCODE. */
    public final static String GO_CONCODE = properties.getProperty("GO_CONCODE"); // Concept code from GO
    
    /** The Constant MEDDRA_CONCODE. */
    public final static String MEDDRA_CONCODE = properties.getProperty("MEDDRA_CONCODE"); // Concept code from MEDDRA
    
    /** The Constant NDF_CONCODE. */
    public final static String NDF_CONCODE = properties.getProperty("NDF_CONCODE"); // Concept code from NDF
    
    /** The Constant MGED_CONCODE. */
    public final static String MGED_CONCODE = properties.getProperty("MGED_CONCODE"); // Concept code from MGED
    
    /** The Constant UMLS_CONCODE. */
    public final static String UMLS_CONCODE = properties.getProperty("UMLS_CONCODE"); // Concept code from UMLS
    
    /** The Constant ZF_CONCODE. */
    public final static String ZF_CONCODE = properties.getProperty("ZF_CONCODE"); // Concept code from ZEBRAFISH

    /** The Constant DomainConceptCode. */
    public final static String DOMAIN_CONCEPT_CODE = properties.getProperty("DOMAIN_CONCEPT_CODE"); // NCIt concept code
   
    /** The Constant DomainConceptName. */
    public final static String DOMAIN_CONCEPT_NAME = properties.getProperty("DOMAIN_CONCEPT_NAME"); // NCIt concept name
 
    /** The Constant DomainConceptRole. */
    public final static String DOMAIN_CONCEPT_ROLE = properties.getProperty("DOMAIN_CONCEPT_ROLE"); // NCIt concept role
 
    /** The Constant DomainConceptAssoc. */
    public final static String DOMAIN_CONCEPT_ASSOC = properties.getProperty("DOMAIN_CONCEPT_ASSOC"); // NCIt concept association
 
    /** The Constant DomainConceptRole. */
    public final static String DOMAIN_QUAL_NAME = properties.getProperty("DOMAIN_QUAL_NAME"); // NCIt concept role
 
    /** The Constant DomainConceptAssoc. */
    public final static String DOMAIN_QUAL_VALUE = properties.getProperty("DOMAIN_QUAL_VALUE"); // NCIt concept association
 
    /** The Constant DOMAIN_CUI. */
    public final static String DOMAIN_CUI = properties.getProperty("DOMAIN_CUI"); //  cui for domain object testing

    /** The Constant DOMAIN_ATOMCODE. */
    public final static String DOMAIN_ATOMCODE = properties.getProperty("DOMAIN_ATOMCODE"); //  atom code for domain object testing

    /** The Constant DOMAIN_LUI. */
    public final static String DOMAIN_LUI = properties.getProperty("DOMAIN_LUI"); //  atom lui for domain object testing

    /** The Constant DOMAIN_ATOMNAME. */
    public final static String DOMAIN_ATOMNAME = properties.getProperty("DOMAIN_ATOMNAME"); //  atom name for domain object testing

    /** The Constant DOMAIN_ATOMSOURCE. */
    public final static String DOMAIN_ATOMSOURCE = properties.getProperty("DOMAIN_ATOMSOURCE"); //  atom name for domain object testing

    /** The Constant DOMAIN_SEMANTIC_TYPE. */
    public final static String DOMAIN_SEMANTIC_TYPE = properties.getProperty("DOMAIN_SEMANTIC_TYPE"); //  semantic type name for domain object testing

    /** The Constant DOMAIN_SEMANTIC_TYPE. */
    public final static String DOMAIN_SEMANTIC_ID = properties.getProperty("DOMAIN_SEMANTIC_ID"); //  semantic type id for domain object testing
   
    
    /** The Constant DOMAIN_AVAILABLE_SOURCE. */
    public final static String DOMAIN_AVAILABLE_SOURCE = properties.getProperty("DOMAIN_AVAILABLE_SOURCE"); //  atom name for domain object testing

    /** The Constant DOMAIN_SOURCE_DEF. */
    public final static String DOMAIN_SOURCE_DEF = properties.getProperty("DOMAIN_SOURCE_DEF"); //  atom name for domain object testing

    public final static String DOMAIN_CONCEPT_CODE_INVERSE_ROLE = properties.getProperty("DOMAIN_CONCEPT_CODE_INVERSE_ROLE");  //concept that has an inverse role for testing
    
    public final static String DOMAIN_CONCEPT_NAME_INVERSE_ROLE = properties.getProperty("DOMAIN_CONCEPT_NAME_INVERSE_ROLE");//name of concept that has an inverse role
    
    public final static String DOMAIN_CONCEPT_CODE_INVERSE_ASSOC = properties.getProperty("DOMAIN_CONCEPT_CODE_INVERSE_ASSOC");//concept that has an inverse association
    
    public final static String DOMAIN_CONCEPT_NAME_INVERSE_ASSOC = properties.getProperty("DOMAIN_CONCEPT_NAME_INVERSE_ASSOC");//name of concept with inverse association
    
    public final static String RESOLVEDVS_URI = properties.getProperty("RESOLVEDVS_URI");
    public final static String TARGETRVS_URIA = properties.getProperty("TARGETRVS_URIA");
    public final static String TARGETRVS_URIB = properties.getProperty("TARGETRVS_URIB");
    public final static String RESOLVEDVS_THES_VERSION = properties.getProperty("RESOLVEDVS_THES_VERSION");
    public final static String RESOLVEDVS_CONCEPTA = properties.getProperty("RESOLVEDVS_CONCEPTA");
    public final static String RESOLVEDVS_CONCEPTB = properties.getProperty("RESOLVEDVS_CONCEPTB");
    
	/**
	 * To be implemented by each descendant testcase.
	 * 
	 * @return String
	 */
	protected String getTestID(){
		return "LexEVS Service Test Case";
	}


	/**
	 * Load properties.
	 * 
	 * @return the properties
	 */
	private static Properties loadProperties(){

		try{
			String propertyFile = sysProp.getProperty("test.property");
			
			//For running single tests in Eclipse
			if(propertyFile == null){
				propertyFile = "test/resources/Test.properties";
			}
			
			Properties lproperties = new Properties();
			if(propertyFile != null && propertyFile.length() > 0){
				FileInputStream fis = new FileInputStream(new File(propertyFile));
				lproperties.load(fis);				
			}

			//cycle through to see if it throws any errors.
			for(Iterator i = lproperties.keySet().iterator(); i.hasNext();){
				String key = (String)i.next();
				String value  = lproperties.getProperty(key);
			}
			return lproperties;}
		catch (Exception e){
			System.out.println("Error reading properties file");
			e.printStackTrace();
			return null;
		}
	} 


	/**
	 * Parses the xml file.
	 * 
	 * @param filename the filename
	 */
	private static void parseXMLFile(String filename)
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try{
			DocumentBuilder db = dbf.newDocumentBuilder();

			dom=db.parse(filename);

		}
		catch (Exception e){
			e.printStackTrace();
		}

	}
}
