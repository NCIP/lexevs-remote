package test.gov.nih.nci.cacoresdk.domain.onetomany.unidirectional.withjoin;

import java.util.Collection;
import java.util.Iterator;

import gov.nih.nci.cacoresdk.domain.onetomany.bidirectional.Computer;
import gov.nih.nci.cacoresdk.domain.onetomany.unidirectional.withjoin.Button;
import gov.nih.nci.cacoresdk.domain.onetomany.unidirectional.withjoin.Shirt;

import test.gov.nih.nci.cacoresdk.SDKXMLDataTestBase;

public class O2MUnidirectionalWJoinXMLDataTest extends SDKXMLDataTestBase
{
	public static String getTestCaseName()
	{
		return "One to Many Unidirectional With Join XML Data Test Case";
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
		Shirt searchObject = new Shirt();
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.onetomany.unidirectional.withjoin.Shirt",searchObject );

		assertNotNull(results);
		assertEquals(3,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Shirt result = (Shirt)i.next();
			toXML(result);
			
			validateClassElements(result);
			validateAttribute(result,"id",result.getId());
			validateAttribute(result,"style",result.getStyle());
			
			assertTrue(validateXMLData(result, searchObject.getClass()));

			Shirt result2 = (Shirt)fromXML(result);
			
			assertNotNull(result2);
			assertNotNull(result2.getId());
			assertNotNull(result2.getStyle());			
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
		Button searchObject = new Button();
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.onetomany.unidirectional.withjoin.Button",searchObject );

		assertNotNull(results);
		assertEquals(2,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Button result = (Button)i.next();
			toXML(result);
			
			validateClassElements(result);
			validateAttribute(result,"id",result.getId());
			validateAttribute(result,"holes",result.getHoles());
			
			assertTrue(validateXMLData(result, searchObject.getClass()));

			Button result2 = (Button)fromXML(result);
			
			assertNotNull(result2);
			assertNotNull(result2.getId());
			assertNotNull(result2.getHoles());				
		}
	}

	/**
	 * Uses Nested Search Criteria for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * erifies that the associated object is null
	 * 
	 * @throws Exception
	 */
	public void testZeroAssociatedObjectsNestedSearch1() throws Exception
	{
		Shirt searchObject = new Shirt();
		searchObject.setId(new Integer(3));
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.onetomany.unidirectional.withjoin.Shirt",searchObject );

		assertNotNull(results);
		assertEquals(1,results.size());
		
		Iterator i = results.iterator();
		Shirt result = (Shirt)i.next();
		toXML(result);
		Shirt result2 = (Shirt)fromXML(result);
		
		assertNotNull(result2);
		assertNotNull(result2.getId());
		assertNotNull(result2.getStyle());
		
		Collection buttonCollection = result2.getButtonCollection();
		assertNull(buttonCollection);
	}


	/**
	 * Uses Nested Search Criteria for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * Verifies that the associated object has required Id
	 * 
	 * @throws Exception
	 */
	public void testOneAssociatedObjectNestedSearch1() throws Exception
	{
		Shirt searchObject = new Shirt();
		searchObject.setId(new Integer(1));
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.onetomany.unidirectional.withjoin.Shirt",searchObject );

		assertNotNull(results);
		assertEquals(1,results.size());
		
		Iterator i = results.iterator();
		Shirt result = (Shirt)i.next();
		toXML(result);
		Shirt result2 = (Shirt)fromXML(result);
		
		assertNotNull(result2);
		assertNotNull(result2.getId());
		assertNotNull(result2.getStyle());
		
		validateAssociation(result,"Button","buttonCollection");
		
		Collection buttonCollection = result2.getButtonCollection();
		assertEquals(true, buttonCollection.size()>0);
		
		Iterator j = buttonCollection.iterator();
		Button button = (Button)j.next();
		assertNotNull(button);
		assertNotNull(button.getId());
		assertNotNull(button.getHoles());
		assertEquals(new Integer(1),button.getId());
	}

	/**
	 * Uses Nested Search Criteria for search to get associated object
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * Verified the Id attribute's value of the returned object 
	 * 
	 * @throws Exception
	 */
	public void testOneAssociatedObjectNestedSearch2() throws Exception
	{
		Shirt searchObject = new Shirt();
		searchObject.setId(new Integer(1));
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.onetomany.unidirectional.withjoin.Button",searchObject );

		assertNotNull(results);
		assertEquals(1,results.size());
		
		Iterator i = results.iterator();
		Button button = (Button)i.next();
		toXML(button);
		Button result2 = (Button)fromXML(button);
		
		assertNotNull(result2);
		assertNotNull(result2.getId());
		assertNotNull(result2.getHoles());
		assertEquals(new Integer(1),result2.getId());
	}
	
}
