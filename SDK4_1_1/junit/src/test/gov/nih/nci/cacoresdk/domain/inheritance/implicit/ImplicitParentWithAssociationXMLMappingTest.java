package test.gov.nih.nci.cacoresdk.domain.inheritance.implicit;

import gov.nih.nci.cacoresdk.domain.inheritance.implicit.AngelFish;
import gov.nih.nci.cacoresdk.domain.inheritance.implicit.DiscusFish;
import gov.nih.nci.cacoresdk.domain.inheritance.implicit.Fish;
import gov.nih.nci.cacoresdk.domain.inheritance.implicit.FishTank;
import gov.nih.nci.cacoresdk.domain.inheritance.implicit.FreshwaterFishTank;
import gov.nih.nci.cacoresdk.domain.inheritance.implicit.SaltwaterFishTank;
import gov.nih.nci.cacoresdk.domain.inheritance.implicit.Substrate;
import gov.nih.nci.cacoresdk.domain.inheritance.implicit.Tank;
import gov.nih.nci.cacoresdk.domain.inheritance.implicit.TankAccessory;

import org.jdom.Document;

import test.gov.nih.nci.cacoresdk.SDKXMLMappingTestBase;

public class ImplicitParentWithAssociationXMLMappingTest extends SDKXMLMappingTestBase
{
	
	private Document doc = null;
	
	public static String getTestCaseName()
	{
		return "Implicit Parent With Association XML Mapping Test Case";
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		String xmlMappingFileName = "xml-mapping.xml";
		doc = getDocument(xmlMappingFileName);
	}

	public Document getDoc() {
		return doc;
	}
	
	/**
	 * Uses xpath to query XSD
	 * Verifies that common XSD elements are present 
	 * 
	 * @throws Exception
	 */
	public void testCommonSchemaElements() throws Exception
	{
		validateCommonSchemaElements();
	}
	
	/**
	 * Verifies that the 'element' and 'complexType' elements 
	 * corresponding to the Class are present in the XSD
	 * Verifies that the Class attributes are present in the XSD
	 * 
	 * @throws Exception
	 */
	public void testClassElement1() throws Exception
	{
		Class targetClass = Fish.class;

		validateClassElements(targetClass,"id");

		validateFieldElement(targetClass, "id", "Integer");
		validateFieldElement(targetClass, "genera", "String");	
	}		
	
	/**
	 * Verifies that the 'element' and 'complexType' elements 
	 * corresponding to the Class are present in the XSD
	 * Verifies that the Class attributes are present in the XSD
	 * 
	 * @throws Exception
	 */
	public void testClassElement2() throws Exception
	{
		Class targetClass = AngelFish.class;

		validateClassElements(targetClass,"id");
		
		validateSubclassElements(targetClass,"id");

		validateFieldElement(targetClass, "finSize", "Integer");	
	}	
	
	/**
	 * Verifies that the 'element' and 'complexType' elements 
	 * corresponding to the Subclass are present in the XSD
	 * Verifies that the Class attributes are present in the XSD
	 * 
	 * @throws Exception
	 */
	public void testClassElement3() throws Exception
	{
		Class targetClass = DiscusFish.class;

		validateSubclassElements(targetClass,"id");
		
		validateFieldElement(targetClass, "primaryColor","String");

	}	
	
	/**
	 * Verifies that the 'element' and 'complexType' elements 
	 * corresponding to the Subclass are present in the XSD
	 * Verifies that the Class attributes are present in the XSD
	 * 
	 * @throws Exception
	 */
	public void testClassElement4() throws Exception
	{
		Class targetClass = Tank.class;

		validateClassElements(targetClass,"id",false);

	}	
	
	/**
	 * Verifies that the 'element' and 'complexType' elements 
	 * corresponding to the Subclass are present in the XSD
	 * Verifies that the Class attributes are present in the XSD
	 * 
	 * @throws Exception
	 */
	public void testClassElement5() throws Exception
	{
		Class targetClass = FishTank.class;

		validateSubclassElements(targetClass,"id");
		
		validateFieldElement(targetClass, "id","Integer");
		validateFieldElement(targetClass, "shape","String");
		validateFieldElement(targetClass, "numGallons","Integer");

	}
	
	/**
	 * Verifies that the 'element' and 'complexType' elements 
	 * corresponding to the Subclass are present in the XSD
	 * Verifies that the Class attributes are present in the XSD
	 * 
	 * @throws Exception
	 */
	public void testClassElement6() throws Exception
	{
		Class targetClass = FreshwaterFishTank.class;

		validateSubclassElements(targetClass,"id");
		
		validateFieldElement(targetClass, "filterModel","String");

	}	
	
	
	/**
	 * Verifies that the 'element' and 'complexType' elements 
	 * corresponding to the Subclass are present in the XSD
	 * Verifies that the Class attributes are present in the XSD
	 * 
	 * @throws Exception
	 */
	public void testClassElement7() throws Exception
	{
		Class targetClass = SaltwaterFishTank.class;

		validateSubclassElements(targetClass,"id");
		
		validateFieldElement(targetClass, "proteinSkimmerModel","String");

	}	
	
	/**
	 * Verifies that the 'element' and 'complexType' elements 
	 * corresponding to the Subclass are present in the XSD
	 * Verifies that the Class attributes are present in the XSD
	 * 
	 * @throws Exception
	 */
	public void testClassElement8() throws Exception
	{
		Class targetClass = TankAccessory.class;

		validateClassElements(targetClass,"id");
		
		validateFieldElement(targetClass, "id","Integer");
		validateFieldElement(targetClass, "name","String");		

	}	
	
	/**
	 * Verifies that the 'element' and 'complexType' elements 
	 * corresponding to the Subclass are present in the XSD
	 * Verifies that the Class attributes are present in the XSD
	 * 
	 * @throws Exception
	 */
	public void testClassElement9() throws Exception
	{
		Class targetClass = Substrate.class;

		validateClassElements(targetClass,"id");
		
		validateFieldElement(targetClass, "id","Integer");
		validateFieldElement(targetClass, "name","String");		

	}
	
	/**
	 * Verifies that association elements 
	 * corresponding to the Class are present in the XSD
	 * Verifies that the Class attributes are present in the XSD
	 * 
	 * @throws Exception
	 */
	public void testAssociationElements1() throws Exception
	{
		Class targetClass = Fish.class;
		Class associatedClass = Tank.class;

		validateClassAssociationElements(targetClass, associatedClass, "tank",false);
	}	
	
	/**
	 * Verifies that association elements 
	 * corresponding to the Class are present in the XSD
	 * Verifies that the Class attributes are present in the XSD
	 * 
	 * @throws Exception
	 */
	public void testAssociationElements4() throws Exception
	{
		Class targetClass = TankAccessory.class;
		Class associatedClass = Tank.class;

		validateClassAssociationElements(targetClass, associatedClass, "tankCollection",true);
	}	
	
	
	/**
	 * Verifies that association elements 
	 * corresponding to the Class are present in the XSD
	 * Verifies that the Class attributes are present in the XSD
	 * 
	 * @throws Exception
	 */
	public void testAssociationElements5() throws Exception
	{
		Class targetClass = SaltwaterFishTank.class;
		Class associatedClass = Substrate.class;

		validateClassAssociationElements(targetClass, associatedClass, "substrateCollection",true);
	}	
}
