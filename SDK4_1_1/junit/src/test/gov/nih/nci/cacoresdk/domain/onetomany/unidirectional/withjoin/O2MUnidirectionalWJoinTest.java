package test.gov.nih.nci.cacoresdk.domain.onetomany.unidirectional.withjoin;

import java.util.Collection;
import java.util.Iterator;

import gov.nih.nci.cacoresdk.domain.onetomany.unidirectional.withjoin.Button;
import gov.nih.nci.cacoresdk.domain.onetomany.unidirectional.withjoin.Shirt;
import gov.nih.nci.system.applicationservice.ApplicationException;
import gov.nih.nci.system.query.cql.CQLAssociation;
import gov.nih.nci.system.query.cql.CQLAttribute;
import gov.nih.nci.system.query.cql.CQLObject;
import gov.nih.nci.system.query.cql.CQLPredicate;
import gov.nih.nci.system.query.cql.CQLQuery;
import test.gov.nih.nci.cacoresdk.SDKTestBase;

public class O2MUnidirectionalWJoinTest extends SDKTestBase
{
	public static String getTestCaseName()
	{
		return "One to Many Unidirectional With Join Test Case";
	}
	
	/**
	 * Uses Nested Search Criteria for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws ApplicationException
	 */
	public void testEntireObjectNestedSearch1() throws ApplicationException
	{
		Shirt searchObject = new Shirt();
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.onetomany.unidirectional.withjoin.Shirt",searchObject );

		assertNotNull(results);
		assertEquals(3,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Shirt result = (Shirt)i.next();
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
	 * @throws ApplicationException
	 */
	public void testEntireObjectNestedSearch2() throws ApplicationException
	{
		Button searchObject = new Button();
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.onetomany.unidirectional.withjoin.Button",searchObject );

		assertNotNull(results);
		assertEquals(2,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Button result = (Button)i.next();
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
	 * @throws ApplicationException
	 */
	public void testZeroAssociatedObjectsNestedSearch1() throws ApplicationException
	{
		Shirt searchObject = new Shirt();
		searchObject.setId(new Integer(3));
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.onetomany.unidirectional.withjoin.Shirt",searchObject );

		assertNotNull(results);
		assertEquals(1,results.size());
		
		Iterator i = results.iterator();
		Shirt result = (Shirt)i.next();
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getStyle());
		
		Collection buttonCollection = result.getButtonCollection();
		assertEquals(0, buttonCollection.size());
	}

	/**
	 * Uses Nested Search Criteria for search to get associated object
	 * Verifies that the results are returned 
	 * Verifies size of the result set is 0
	 * 
	 * @throws ApplicationException
	 */
	public void testZeroAssociatedObjectsNestedSearch2() throws ApplicationException
	{
		Shirt searchObject = new Shirt();
		searchObject.setId(new Integer(3));
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.onetomany.unidirectional.withjoin.Button",searchObject );

		assertNotNull(results);
		assertEquals(0,results.size());
	}	
	
	/**
	 * Uses Nested Search Criteria for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * Verifies that the associated object has required Id
	 * 
	 * @throws ApplicationException
	 */
	public void testOneAssociatedObjectNestedSearch1() throws ApplicationException
	{
		Shirt searchObject = new Shirt();
		searchObject.setId(new Integer(1));
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.onetomany.unidirectional.withjoin.Shirt",searchObject );

		assertNotNull(results);
		assertEquals(1,results.size());
		
		Iterator i = results.iterator();
		Shirt result = (Shirt)i.next();
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getStyle());
		
		Collection buttonCollection = result.getButtonCollection();
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
	 * @throws ApplicationException
	 */
	public void testOneAssociatedObjectNestedSearch2() throws ApplicationException
	{
		Shirt searchObject = new Shirt();
		searchObject.setId(new Integer(1));
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.onetomany.unidirectional.withjoin.Button",searchObject );

		assertNotNull(results);
		assertEquals(1,results.size());
		
		Iterator i = results.iterator();
		Button Button = (Button)i.next();
		assertNotNull(Button);
		assertNotNull(Button.getId());
		assertNotNull(Button.getHoles());
		assertEquals(new Integer(1),Button.getId());
	}
	

	/**
	 * Uses CQL Criteria for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * Verifies that the associated object has required Id
	 * 
	 * @throws ApplicationException
	 */
	public void testNoAssociationCQL() throws ApplicationException
	{
		boolean flag = false;
		try
		{
			CQLQuery cqlQuery = new CQLQuery();
			CQLObject target = new CQLObject();
			
			CQLAssociation association = new CQLAssociation();
			association.setName("gov.nih.nci.cacoresdk.domain.onetomany.unidirectional.withjoin.Shirt");
			association.setAttribute(new CQLAttribute("id",CQLPredicate.EQUAL_TO,"1"));
			
			target.setName("gov.nih.nci.cacoresdk.domain.onetomany.unidirectional.withjoin.Button");
			target.setAssociation(association);
			cqlQuery.setTarget(target);
	
			Collection results = getApplicationService().query(cqlQuery);
			assertNotNull(results);
			
		}
		catch(ApplicationException e)
		{
			flag = true;
		}
		
		assertTrue(flag);
	}

	
	/**
	 * Uses CQL Criteria for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * Verifies that the associated object has required Id
	 * 
	 * @throws ApplicationException
	 */
	public void testOneAssociatedObjectCQL() throws ApplicationException
	{
		CQLQuery cqlQuery = new CQLQuery();
		CQLObject target = new CQLObject();
		
		CQLAssociation association = new CQLAssociation();
		association.setName("gov.nih.nci.cacoresdk.domain.onetomany.unidirectional.withjoin.Shirt");
		association.setAttribute(new CQLAttribute("id",CQLPredicate.EQUAL_TO,"1"));
		association.setSourceRoleName("buttonCollection");
		
		target.setName("gov.nih.nci.cacoresdk.domain.onetomany.unidirectional.withjoin.Button");
		target.setAssociation(association);
		cqlQuery.setTarget(target);

		Collection results = getApplicationService().query(cqlQuery);

		assertNotNull(results);
		assertEquals(1,results.size());
		
		Iterator i = results.iterator();
		Button Button = (Button)i.next();
		assertNotNull(Button);
		assertNotNull(Button.getId());
		assertNotNull(Button.getHoles());
		assertEquals(new Integer(1),Button.getId());
	}	
}
