package test.gov.nih.nci.cacoresdk.domain.manytoone.unidirectional.withjoin;

import gov.nih.nci.cacoresdk.domain.manytoone.unidirectional.withjoin.Album;
import gov.nih.nci.cacoresdk.domain.manytoone.unidirectional.withjoin.Song;
import gov.nih.nci.system.applicationservice.ApplicationException;
import gov.nih.nci.system.query.cql.CQLAssociation;
import gov.nih.nci.system.query.cql.CQLAttribute;
import gov.nih.nci.system.query.cql.CQLObject;
import gov.nih.nci.system.query.cql.CQLPredicate;
import gov.nih.nci.system.query.cql.CQLQuery;

import java.util.Collection;
import java.util.Iterator;

import test.gov.nih.nci.cacoresdk.SDKTestBase;

public class M2OUnidirectionalWJoinTest extends SDKTestBase
{
	public static String getTestCaseName()
	{
		return "Many to One Unidirectional With Join Test Case";
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
		Album searchObject = new Album();
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.manytoone.unidirectional.withjoin.Album",searchObject );

		assertNotNull(results);
		assertEquals(2,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Album result = (Album)i.next();
			assertNotNull(result);
			assertNotNull(result.getId());
			assertNotNull(result.getTitle());
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
		Song searchObject = new Song();
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.manytoone.unidirectional.withjoin.Song",searchObject );

		assertNotNull(results);
		assertEquals(12,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Song result = (Song)i.next();
			assertNotNull(result);
			assertNotNull(result.getId());
			assertNotNull(result.getTitle());
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
		Song searchObject = new Song();
		searchObject.setId(new Integer(12));
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.manytoone.unidirectional.withjoin.Song",searchObject );

		assertNotNull(results);
		assertEquals(1,results.size());
		
		Iterator i = results.iterator();
		Song result = (Song)i.next();
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getTitle());
		
		assertNull(result.getAlbum());
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
		Song searchObject = new Song();
		searchObject.setId(new Integer(12));
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.manytoone.unidirectional.withjoin.Album",searchObject );

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
		Song searchObject = new Song();
		searchObject.setId(new Integer(1));
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.manytoone.unidirectional.withjoin.Song",searchObject );

		assertNotNull(results);
		assertEquals(1,results.size());
		
		Iterator i = results.iterator();
		Song result = (Song)i.next();
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getTitle());
		
		Album Album = result.getAlbum();
		assertNotNull(Album);
		assertNotNull(Album.getId());
		assertNotNull(Album.getTitle());
		assertEquals(new Integer(1),Album.getId());
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
		Song searchObject = new Song();
		searchObject.setId(new Integer(1));
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.manytoone.unidirectional.withjoin.Album",searchObject );

		assertNotNull(results);
		assertEquals(1,results.size());
		
		Iterator i = results.iterator();
		Album album = (Album)i.next();
		assertNotNull(album);
		assertNotNull(album.getId());
		assertNotNull(album.getTitle());
		assertEquals(new Integer(1),album.getId());
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
			association.setName("gov.nih.nci.cacoresdk.domain.manytoone.unidirectional.withjoin.Song");
			association.setAttribute(new CQLAttribute("id",CQLPredicate.EQUAL_TO,"12"));
			
			target.setName("gov.nih.nci.cacoresdk.domain.manytoone.unidirectional.withjoin.Album");
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
		association.setName("gov.nih.nci.cacoresdk.domain.manytoone.unidirectional.withjoin.Album");
		association.setAttribute(new CQLAttribute("id",CQLPredicate.EQUAL_TO,"2"));
		association.setSourceRoleName("Song");
		
		target.setName("gov.nih.nci.cacoresdk.domain.manytoone.unidirectional.withjoin.Song");
		target.setAssociation(association);
		cqlQuery.setTarget(target);

		Collection results = getApplicationService().query(cqlQuery);

		assertNotNull(results);
		assertEquals(5,results.size());
		
		Iterator i = results.iterator();
		Song Song = (Song)i.next();
		assertNotNull(Song);
		assertNotNull(Song.getId());
		assertNotNull(Song.getTitle());
		assertEquals(new Integer(2),Song.getAlbum().getId());
	}
	
	public void testGetAssociation() throws ApplicationException
	{

		Song searchObject = new Song();
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.manytoone.unidirectional.withjoin.Song",searchObject );

		assertNotNull(results);
		assertEquals(12,results.size());
		
		Album album;
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Song result = (Song)i.next();
			assertNotNull(result);
			assertNotNull(result.getId());
			assertNotNull(result.getTitle());
			
			if (result.getId() != 12) { //Song id=12 has no Album associated with it
				album = result.getAlbum();
				assertNotNull(album);
				assertNotNull(album.getId());
				assertNotNull(album.getTitle());
			}

		}
	}	
}
