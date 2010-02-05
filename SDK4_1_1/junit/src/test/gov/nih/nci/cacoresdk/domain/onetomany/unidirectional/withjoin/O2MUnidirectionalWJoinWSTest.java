package test.gov.nih.nci.cacoresdk.domain.onetomany.unidirectional.withjoin;

import gov.nih.nci.cacoresdk.domain.onetomany.unidirectional.withjoin.Button;
import gov.nih.nci.cacoresdk.domain.onetomany.unidirectional.withjoin.Shirt;

import java.util.ArrayList;
import java.util.Collection;

import test.gov.nih.nci.cacoresdk.SDKWSTestBase;

public class O2MUnidirectionalWJoinWSTest extends SDKWSTestBase
{
	public static String getTestCaseName()
	{
		return "One to Many Unidirectional With Join WS Test Case";
	}
	
	protected Collection<Class> getClasses() throws Exception
	{	
		Collection<Class> mappedKlasses = new ArrayList<Class>();
		
		mappedKlasses.add(Button.class);
		mappedKlasses.add(Shirt.class);
		
		return mappedKlasses;
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
		Class targetClass = Shirt.class;
		Shirt criteria = new Shirt();

		Object[] results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(3,results.length);
		
		for (Object obj : results){
			Shirt result = (Shirt)obj;
			assertNotNull(result);
			assertNotNull(result.getId());
			assertNotNull(result.getStyle());	
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
		Class targetClass = Button.class;
		Button criteria = new Button();

		Object[] results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(2,results.length);
		
		for (Object obj : results){
			Button result = (Button)obj;
			assertNotNull(result);
			assertNotNull(result.getId());
			assertNotNull(result.getHoles());	
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

		Class targetClass = Shirt.class;
		Shirt criteria = new Shirt();
		criteria.setId(new Integer(3));

		Object[] results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(1,results.length);
		
		Shirt result = (Shirt)results[0];
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getStyle());	
		
		Object[] buttonCollection = getAssociationResults(result, "buttonCollection", 0);
		assertEquals(0,buttonCollection.length);		
	}

	/**
	 * Uses Nested Search Criteria for search to get associated object
	 * Verifies that the results are returned 
	 * Verifies size of the result set is 0
	 * 
	 * @throws Exception
	 */
	public void testZeroAssociatedObjectsNestedSearch2() throws Exception
	{

		Class targetClass = Button.class;
		Shirt criteria = new Shirt();
		criteria.setId(new Integer(3));

		Object[] results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(0,results.length);			
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
	
		Class targetClass = Shirt.class;
		Shirt criteria = new Shirt();
		criteria.setId(new Integer(1));

		Object[] results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(1,results.length);
		
		Shirt result = (Shirt)results[0];
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getStyle());	
		
		Object[] buttonCollection = getAssociationResults(result, "buttonCollection", 0);
		assertEquals(1,buttonCollection.length);
		
		Button button = (Button)buttonCollection[0];
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
		Class targetClass = Button.class;
		Shirt criteria = new Shirt();
		criteria.setId(new Integer(1));

		Object[] results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(1,results.length);
		
		Button result = (Button)results[0];
		assertNotNull(result);
		assertNotNull(result.getId());
		assertEquals(new Integer(1),result.getId());
	}	
}
