package test.gov.nih.nci.cacoresdk.domain.onetoone.bidirectional.withjoin;

import gov.nih.nci.cacoresdk.domain.onetoone.bidirectional.withjoin.Chain;
import gov.nih.nci.cacoresdk.domain.onetoone.bidirectional.withjoin.Pendant;

import java.util.ArrayList;
import java.util.Collection;

import test.gov.nih.nci.cacoresdk.SDKWSTestBase;

public class O2OBidirectionalWJoinWSTest extends SDKWSTestBase
{
	public static String getTestCaseName()
	{
		return "One to One Bidirectional With Join WS Test Case";
	}
	
	protected Collection<Class> getClasses() throws Exception
	{	
		Collection<Class> mappedKlasses = new ArrayList<Class>();
		
		mappedKlasses.add(Chain.class);
		mappedKlasses.add(Pendant.class);
		
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
		Class targetClass = Pendant.class;
		Pendant criteria = new Pendant();

		Object[] results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(3,results.length);
		
		for (Object obj : results){
			Pendant result = (Pendant)obj;
			assertNotNull(result);
			assertNotNull(result.getId());
			assertNotNull(result.getShape());	
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
		Class targetClass = Chain.class;
		Chain criteria = new Chain();

		Object[] results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(3,results.length);
		
		for (Object obj : results){
			Chain result = (Chain)obj;
			assertNotNull(result);
			assertNotNull(result.getId());
			assertNotNull(result.getMetal());	
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
		Class targetClass = Pendant.class;
		Pendant criteria = new Pendant();
		criteria.setId(new Integer(3));

		Object[] results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(1,results.length);
		
		Pendant result = (Pendant)results[0];
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getShape());	
		
		Object[] chainResults = getAssociationResults(result, "chain", 0);
		assertEquals(0,chainResults.length);		
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
		Class targetClass = Chain.class;
		Pendant criteria = new Pendant();
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
		Class targetClass = Pendant.class;
		Pendant criteria = new Pendant();
		criteria.setId(new Integer(1));

		Object[] results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(1,results.length);
		
		Pendant result = (Pendant)results[0];
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getShape());	
		
		Object[] chainResults = getAssociationResults(result, "chain", 0);
		assertEquals(1,chainResults.length);
		
		Chain chain = (Chain)chainResults[0];
		assertNotNull(chain);
		assertNotNull(chain.getId());
		assertNotNull(chain.getMetal());
		assertEquals(new Integer(1),chain.getId());
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
		Class targetClass = Chain.class;
		Pendant criteria = new Pendant();
		criteria.setId(new Integer(1));

		Object[] results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(1,results.length);
		
		Chain chain = (Chain)results[0];
		assertNotNull(chain);
		assertNotNull(chain.getId());
		assertNotNull(chain.getMetal());
		assertEquals(new Integer(1),chain.getId());
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
	public void testOneAssociatedObjectNestedSearch3() throws Exception
	{
		Class targetClass = Pendant.class;
		Chain criteria = new Chain();
		criteria.setId(new Integer(1));

		Object[] results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(1,results.length);
		
		Pendant pendant = (Pendant)results[0];
		assertNotNull(pendant);
		assertNotNull(pendant.getId());
		assertNotNull(pendant.getShape());
		assertEquals(new Integer(1),pendant.getId());		
	}	

	public void testGetMethods1() throws Exception
	{
		Class targetClass = Pendant.class;
		Pendant criteria = new Pendant();
		criteria.setId(new Integer(1));

		Object[] results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(1,results.length);
		
		Pendant result = (Pendant)results[0];
		Object[] chainResults = getAssociationResults(result, "chain", 0);
		assertEquals(new Integer(1),((Chain)chainResults[0]).getId());
		
		criteria.setId(new Integer(2));
		results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(1,results.length);
		
		result = (Pendant)results[0];
		chainResults = getAssociationResults(result, "chain", 0);
		assertEquals(new Integer(2),((Chain)chainResults[0]).getId());
		
		criteria.setId(new Integer(3));
		results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(1,results.length);
		
		result = (Pendant)results[0];
		chainResults = getAssociationResults(result, "chain", 0);
		assertEquals(0,chainResults.length);
	}


	public void testGetMethods2() throws Exception
	{
		Class targetClass = Chain.class;
		Chain criteria = new Chain();
		criteria.setId(new Integer(1));

		Object[] results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(1,results.length);
		
		Chain result = (Chain)results[0];
		Object[] pendantResults = getAssociationResults(result, "pendant", 0);
		assertEquals(new Integer(1),((Pendant)pendantResults[0]).getId());
		
		criteria.setId(new Integer(2));
		results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(1,results.length);
		
		result = (Chain)results[0];
		pendantResults = getAssociationResults(result, "pendant", 0);
		assertEquals(new Integer(2),((Pendant)pendantResults[0]).getId());
		
		criteria.setId(new Integer(3));
		results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(1,results.length);
		
		result = (Chain)results[0];
		pendantResults = getAssociationResults(result, "pendant", 0);
		assertEquals(0,pendantResults.length);
		
	}
	
	public void testGetAssociation1() throws Exception
	{
		Class targetClass = Pendant.class;
		Pendant criteria = new Pendant();
		criteria.setId(new Integer(1));

		Object[] results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(1,results.length);
		
		Pendant result = (Pendant)results[0];
		Object[] chainResults = getAssociationResults(result, "chain", 0);
		Chain chain = (Chain)chainResults[0];
		assertNotNull(chain);
		assertNotNull(chain.getId());
		assertNotNull(chain.getMetal());
		assertEquals(new Integer(1),chain.getId());
	}
	
	public void testGetAssociation2() throws Exception
	{
		Class targetClass = Chain.class;
		Chain criteria = new Chain();
		criteria.setId(new Integer(1));

		Object[] results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(1,results.length);
		
		Chain result = (Chain)results[0];
		Object[] pendantResults = getAssociationResults(result, "pendant", 0);
		Pendant pendant = (Pendant)pendantResults[0];
		assertNotNull(pendant);
		assertNotNull(pendant.getId());
		assertNotNull(pendant.getShape());		
		assertEquals(new Integer(1),pendant.getId());
	}		
}
