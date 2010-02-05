package test.gov.nih.nci.cacoresdk.domain.interfaze;

import gov.nih.nci.cacoresdk.domain.interfaze.Dog;

import java.util.Collection;
import java.util.Iterator;

import test.gov.nih.nci.cacoresdk.SDKXMLDataTestBase;

public class InterfaceXMLDataTest extends SDKXMLDataTestBase
{
	public static String getTestCaseName()
	{
		return "Interface XML Data Test Case";
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
		Dog searchObject = new Dog();
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.interfaze.Dog",searchObject );

		assertNotNull(results);
		assertEquals(3,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Dog result = (Dog)i.next();
			toXML(result);
			
			validateClassElements(result);
			validateAttribute(result,"id",result.getId());
			validateAttribute(result,"breed",result.getBreed());
			validateAttribute(result,"gender",result.getGender());
			
			assertTrue(validateXMLData(result, searchObject.getClass()));

			Dog result2 = (Dog)fromXML(result);
			
			assertNotNull(result2);
			assertNotNull(result2.getId());
			assertNotNull(result2.getBreed());
			assertNotNull(result2.getGender());
		}
	}
}
