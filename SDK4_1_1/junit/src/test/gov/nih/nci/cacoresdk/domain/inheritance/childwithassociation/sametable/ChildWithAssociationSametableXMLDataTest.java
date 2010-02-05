package test.gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.sametable;

import gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.sametable.Designer;
import gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.sametable.DesignerShoes;
import gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.sametable.Shoes;
import gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.sametable.SportsShoes;

import java.util.Collection;
import java.util.Iterator;

import test.gov.nih.nci.cacoresdk.SDKXMLDataTestBase;

public class ChildWithAssociationSametableXMLDataTest extends SDKXMLDataTestBase
{
	public static String getTestCaseName()
	{
		return "Child With Association Same Table XML Data Test Case";
	}
	
	/**
	 * Uses Nested Search Criteria for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */
	public void testEntireObjectNestedSearch1() throws Exception
	{
		Shoes searchObject = new Shoes();
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.sametable.Shoes",searchObject );

		assertNotNull(results);
		assertEquals(3,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Shoes result = (Shoes)i.next();
			
			toXML(result);
			
			validateClassElements(result);
			validateAttribute(result,"id",result.getId());
			validateAttribute(result,"color",result.getColor());
			
			assertTrue(validateXMLData(result, searchObject.getClass()));

			Shoes result2 = (Shoes)fromXML(result);
			
			assertNotNull(result2);
			assertNotNull(result2.getId());
			assertNotNull(result2.getColor());
			
		}
	}

	/**
	 * Uses Nested Search Criteria for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */
	public void testEntireObjectNestedSearch2() throws Exception
	{
		SportsShoes searchObject = new SportsShoes();
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.sametable.SportsShoes",searchObject );

		assertNotNull(results);
		assertEquals(1,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			SportsShoes result = (SportsShoes)i.next();
			
			toXML(result);
			
			validateClassElements(result);
			validateAttribute(result,"id",result.getId());
			validateAttribute(result,"color",result.getColor());
			validateAttribute(result,"sportsType",result.getSportsType());
			
			assertTrue(validateXMLData(result, searchObject.getClass()));

			SportsShoes result2 = (SportsShoes)fromXML(result);
			
			assertNotNull(result2);
			assertNotNull(result2.getId());
			assertNotNull(result2.getColor());
			assertNotNull(result2.getSportsType());
		}
	}

	/**
	 * Uses Nested Search Criteria for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */
	public void testEntireObjectNestedSearch3() throws Exception
	{
		DesignerShoes searchObject = new DesignerShoes();
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.sametable.DesignerShoes",searchObject );

		assertNotNull(results);
		assertEquals(2,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			DesignerShoes result = (DesignerShoes)i.next();
			
			toXML(result);
			
			validateClassElements(result);
			validateAttribute(result,"id",result.getId());
			validateAttribute(result,"color",result.getColor());
			
			assertTrue(validateXMLData(result, searchObject.getClass()));

			DesignerShoes result2 = (DesignerShoes)fromXML(result);
			
			assertNotNull(result2);
			assertNotNull(result2.getId());
			assertNotNull(result2.getColor());
		}
	}
	
	/**
	 * Uses Nested Search Criteria for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */
	public void testEntireObjectNestedSearch4() throws Exception
	{
		Designer searchObject = new Designer();
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.sametable.Designer",searchObject );

		assertNotNull(results);
		assertEquals(3,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Designer result = (Designer)i.next();
			
			toXML(result);
			
			validateClassElements(result);
			validateAttribute(result,"id",result.getId());
			validateAttribute(result,"name",result.getName());
			
			assertTrue(validateXMLData(result, searchObject.getClass()));

			Designer result2 = (Designer)fromXML(result);
			
			assertNotNull(result2);
			assertNotNull(result2.getId());
			assertNotNull(result2.getName());
		}
	}
		
		
	/**
	 * Uses Nested Search Criteria for inheritance as association in search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */
	public void testAssociationNestedSearch1() throws Exception
	{
		Shoes searchObject = new Shoes();
		searchObject.setColor("Red");
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.sametable.SportsShoes",searchObject );

		assertNotNull(results);
		assertEquals(1,results.size());
		
		SportsShoes result = (SportsShoes)results.iterator().next();
		toXML(result);
		SportsShoes result2 = (SportsShoes)fromXML(result);

		assertNotNull(result2);
		assertNotNull(result2.getId());
		assertEquals(new Integer(2), result2.getId());
	}

	/**
	 * Uses Nested Search Criteria for inheritance as association in search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */
	public void testAssociationNestedSearch2() throws Exception
	{
		SportsShoes searchObject = new SportsShoes();
		searchObject.setColor("Red");
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.sametable.Shoes",searchObject );

		assertNotNull(results);
		assertEquals(1,results.size());
		
		Shoes result = (Shoes)results.iterator().next();
		toXML(result);
		Shoes result2 = (Shoes)fromXML(result);

		assertNotNull(result2);
		assertNotNull(result2.getId());
		assertEquals(new Integer(2), result2.getId());
	}

	/**
	 * Uses Nested Search Criteria for inheritance as association in search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */
	public void testAssociationNestedSearch3() throws Exception
	{
		Shoes searchObject = new Shoes();
		searchObject.setColor("Black");
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.sametable.DesignerShoes",searchObject );

		assertNotNull(results);
		assertEquals(1,results.size());
		
		DesignerShoes result = (DesignerShoes)results.iterator().next();
		toXML(result);
		DesignerShoes result2 = (DesignerShoes)fromXML(result);

		assertNotNull(result2);
		assertNotNull(result2.getId());
		assertEquals(new Integer(3), result2.getId());
	}

	/**
	 * Uses Nested Search Criteria for inheritance as association in search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */
	public void testAssociationNestedSearch4() throws Exception
	{
		DesignerShoes searchObject = new DesignerShoes();
		searchObject.setColor("Black");
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.sametable.Shoes",searchObject );

		assertNotNull(results);
		assertEquals(1,results.size());
		
		Shoes result = (Shoes)results.iterator().next();
		toXML(result);
		Shoes result2 = (Shoes)fromXML(result);

		assertNotNull(result2);
		assertNotNull(result2.getId());
		assertEquals(new Integer(3), result2.getId());

	}
	
	/**
	 * Uses Nested Search Criteria for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */
	public void testGetAssociation() throws Exception
	{
		DesignerShoes searchObject = new DesignerShoes();
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.sametable.DesignerShoes",searchObject );

		assertNotNull(results);
		assertEquals(2,results.size());
		
		Designer designer;
		for(Iterator i = results.iterator();i.hasNext();)
		{
			DesignerShoes result = (DesignerShoes)i.next();
			toXML(result);
			
			validateAssociation(result,"Designer","designer");

			DesignerShoes result2 = (DesignerShoes)fromXML(result);
			designer = result2.getDesigner();
			
			assertNotNull(designer);
			assertNotNull(designer.getId());
			assertNotNull(designer.getName());
		}
	}	

}
