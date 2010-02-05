package test.gov.nih.nci.cacoresdk.domain.onetoone.unidirectional.withjoin;

import gov.nih.nci.cacoresdk.domain.onetoone.unidirectional.withjoin.Bag;
import gov.nih.nci.cacoresdk.domain.onetoone.unidirectional.withjoin.Handle;

import java.util.ArrayList;
import java.util.Collection;

import test.gov.nih.nci.cacoresdk.SDKWSTestBase;

public class O2OUnidirectionalWJoinWSTest extends SDKWSTestBase
{
	public static String getTestCaseName()
	{
		return "One to One Unidirectional With Join WS Test Case";
	}
	
	protected Collection<Class> getClasses() throws Exception
	{	
		Collection<Class> mappedKlasses = new ArrayList<Class>();
		
		mappedKlasses.add(Handle.class);
		mappedKlasses.add(Bag.class);
		
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
		Class targetClass = Bag.class;
		Bag criteria = new Bag();

		Object[] results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(11,results.length);
		
		for (Object obj : results){
			Bag result = (Bag)obj;
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
		Class targetClass = Handle.class;
		Handle criteria = new Handle();

		Object[] results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(12,results.length);
		
		Handle result = (Handle)results[0];
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getColor());	
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
		Class targetClass = Bag.class;
		Bag criteria = new Bag();
		criteria.setId(new Integer(11));

		Object[] results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(1,results.length);
		
		Bag result = (Bag)results[0];
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getStyle());	
		
		Object[] handleResults = getAssociationResults(result, "handle", 0);
		assertEquals(0,handleResults.length);
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
		Class targetClass = Handle.class;
		Bag criteria = new Bag();
		criteria.setId(new Integer(11));

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
		Class targetClass = Bag.class;
		Bag criteria = new Bag();
		criteria.setId(new Integer(1));

		Object[] results = getQueryObjectResults(targetClass, criteria);			

		assertNotNull(results);
		assertEquals(1,results.length);
		
		Bag result = (Bag)results[0];
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getStyle());
		
		Object[] handleResults = getAssociationResults(result, "handle", 0);
		assertEquals(1,handleResults.length);
		
		Handle handle = (Handle)handleResults[0];
		assertNotNull(handle);
		assertNotNull(handle.getId());
		assertNotNull(handle.getColor());	
		assertEquals(new Integer(1),handle.getId());
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
		Class targetClass = Handle.class;
		Bag criteria = new Bag();
		criteria.setId(new Integer(1));

		Object[] results = getQueryObjectResults(targetClass, criteria);			

		assertNotNull(results);
		assertEquals(1,results.length);
		
		Handle handle = (Handle)results[0];
		assertNotNull(handle);
		assertNotNull(handle.getId());
		assertNotNull(handle.getColor());
		assertEquals(new Integer(1),handle.getId());		
	}

	public void testGetAssociation() throws Exception
	{
		Class targetClass = Bag.class;
		Bag criteria = new Bag();

		Object[] results = getQueryObjectResults(targetClass, criteria);			

		assertNotNull(results);
		assertEquals(11,results.length);
		
		Bag result = (Bag)results[0];
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getStyle());
		
		Object[] handleResults = getAssociationResults(result, "handle", 0);
		assertEquals(1,handleResults.length);
		
		Handle handle = (Handle)handleResults[0];
		assertNotNull(handle);
		assertNotNull(handle.getId());
		assertNotNull(handle.getColor());	
		assertEquals(new Integer(1),handle.getId());			
	}	
}
