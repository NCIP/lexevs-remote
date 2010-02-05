package test.gov.nih.nci.cacoresdk.domain.inheritance.parentwithassociation.sametable;

import gov.nih.nci.cacoresdk.domain.inheritance.parentwithassociation.sametable.HardTop;
import gov.nih.nci.cacoresdk.domain.inheritance.parentwithassociation.sametable.Luggage;
import gov.nih.nci.cacoresdk.domain.inheritance.parentwithassociation.sametable.SoftTop;
import gov.nih.nci.cacoresdk.domain.inheritance.parentwithassociation.sametable.Wheel;

import java.util.Collection;
import java.util.Iterator;

import test.gov.nih.nci.cacoresdk.SDKXMLDataTestBase;

public class ParentWithAssociationSametableXMLDataTest extends SDKXMLDataTestBase
{
	public static String getTestCaseName()
	{
		return "Parent With Association Same Table Test Case";
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
		Luggage searchObject = new Luggage();
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.inheritance.parentwithassociation.sametable.Luggage",searchObject );

		assertNotNull(results);
		assertEquals(5,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Luggage result = (Luggage)i.next();
			toXML(result);
			
			validateClassElements(result);
			validateAttribute(result,"id",result.getId());
			validateAttribute(result,"capacity",result.getCapacity());
			
			assertTrue(validateXMLData(result, searchObject.getClass()));

			Luggage result2 = (Luggage)fromXML(result);
			
			assertNotNull(result2);
			assertNotNull(result2.getId());
			assertNotNull(result2.getCapacity());	
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
		HardTop searchObject = new HardTop();
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.inheritance.parentwithassociation.sametable.HardTop",searchObject );

		assertNotNull(results);
		assertEquals(3,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			HardTop result = (HardTop)i.next();
			toXML(result);
			
			validateClassElements(result);
			validateAttribute(result,"id",result.getId());
			validateAttribute(result,"capacity",result.getCapacity());
			validateAttribute(result,"keyCode",result.getKeyCode());
			
			assertTrue(validateXMLData(result, searchObject.getClass()));

			HardTop result2 = (HardTop)fromXML(result);
			
			assertNotNull(result2);
			assertNotNull(result2.getId());
			assertNotNull(result2.getCapacity());
			assertNotNull(result2.getKeyCode());
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
		SoftTop searchObject = new SoftTop();
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.inheritance.parentwithassociation.sametable.SoftTop",searchObject );

		assertNotNull(results);
		assertEquals(2,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			SoftTop result = (SoftTop)i.next();
			toXML(result);
			
			validateClassElements(result);
			validateAttribute(result,"id",result.getId());
			validateAttribute(result,"capacity",result.getCapacity());
			validateAttribute(result,"expandable",result.getExpandable());
			
			assertTrue(validateXMLData(result, searchObject.getClass()));

			SoftTop result2 = (SoftTop)fromXML(result);
			
			assertNotNull(result2);
			assertNotNull(result2.getId());
			assertNotNull(result2.getCapacity());
			assertNotNull(result2.getExpandable());
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
		Wheel searchObject = new Wheel();
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.inheritance.parentwithassociation.sametable.Wheel",searchObject );

		assertNotNull(results);
		assertEquals(3,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Wheel result = (Wheel)i.next();
			toXML(result);
			
			validateClassElements(result);
			validateAttribute(result,"id",result.getId());
			validateAttribute(result,"radius",result.getRadius());
			
			assertTrue(validateXMLData(result, searchObject.getClass()));

			Wheel result2 = (Wheel)fromXML(result);
			
			assertNotNull(result2);
			assertNotNull(result2.getId());
			assertNotNull(result2.getRadius());
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
		Luggage searchObject = new Luggage();
		searchObject.setId(new Integer(1));
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.inheritance.parentwithassociation.sametable.HardTop",searchObject );

		assertNotNull(results);
		assertEquals(1,results.size());
		
		HardTop result = (HardTop)results.iterator().next();
		toXML(result);
		HardTop result2 = (HardTop)fromXML(result);

		assertNotNull(result2);
		assertNotNull(result2.getId());
		assertEquals(new Integer(1), result2.getId());
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
		HardTop searchObject = new HardTop();
		searchObject.setId(new Integer(2));
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.inheritance.parentwithassociation.sametable.Luggage",searchObject );

		assertNotNull(results);
		assertEquals(1,results.size());
		
		Luggage result = (Luggage)results.iterator().next();
		toXML(result);
		Luggage result2 = (Luggage)fromXML(result);

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
		Luggage searchObject = new Luggage();
		searchObject.setId(new Integer(3));
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.inheritance.parentwithassociation.sametable.SoftTop",searchObject );

		assertNotNull(results);
		assertEquals(1,results.size());
		
		SoftTop result = (SoftTop)results.iterator().next();
		toXML(result);
		SoftTop result2 = (SoftTop)fromXML(result);

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
		SoftTop searchObject = new SoftTop();
		searchObject.setId(new Integer(3));
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.inheritance.parentwithassociation.sametable.Luggage",searchObject );

		assertNotNull(results);
		assertEquals(1,results.size());
		
		Luggage result = (Luggage)results.iterator().next();
		toXML(result);
		Luggage result2 = (Luggage)fromXML(result);

		assertNotNull(result2);
		assertNotNull(result2.getId());
		assertEquals(new Integer(3), result2.getId());
	}
	
	public void testGetAssociation() throws Exception
	{
		Luggage searchObject = new Luggage();
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.inheritance.parentwithassociation.sametable.Luggage",searchObject );

		assertNotNull(results);
		assertEquals(5,results.size());
		
		Wheel wheel;
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Luggage result = (Luggage)i.next();
			toXML(result);
			
			validateAssociation(result,"Wheel","wheel");

			Luggage result2 = (Luggage)fromXML(result);
			wheel = result2.getWheel();
			
			assertNotNull(wheel);
			assertNotNull(wheel.getId());
			assertNotNull(wheel.getRadius());
		}
	}
}
