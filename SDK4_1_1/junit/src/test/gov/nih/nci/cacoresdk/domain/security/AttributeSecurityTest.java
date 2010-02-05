package test.gov.nih.nci.cacoresdk.domain.security;

import gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.Bank;
import gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.Credit;
import gov.nih.nci.system.query.cql.CQLObject;
import gov.nih.nci.system.query.cql.CQLQuery;
import gov.nih.nci.system.query.hibernate.HQLCriteria;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.criterion.DetachedCriteria;

import test.gov.nih.nci.cacoresdk.SDKSecurityTestBase;

public class AttributeSecurityTest extends SDKSecurityTestBase
{
	public static String getTestCaseName()
	{
		return "Attribute Security Test Case";
	}
	
	/**
	 * Uses Query by Example for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */
	public void testClassListSearch() throws Exception
	{
		Credit credit3 = new Credit();
		Credit credit4 = new Credit();
		
		credit3.setId(3);
		credit4.setId(4);
		
		List<Credit> objList = new ArrayList<Credit>();
		
		objList.add(credit3);
		objList.add(credit4);
		
		Collection results = getApplicationService("user1","password").search(Credit.class,objList);

		assertNotNull(results);
		assertEquals(2,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Credit result = (Credit)i.next();
			assertNotNull(result);
			assertNotNull(result.getId());
			assertNotNull(result.getAmount());
			assertNotNull(result.getCardNumber());
		}
	}	
	
	/**
	 * Uses Query by Example for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */
	public void testLimitedAccessClassListSearch() throws Exception
	{
		Credit credit3 = new Credit();
		Credit credit4 = new Credit();
		
		credit3.setId(3);
		credit4.setId(4);
		
		List<Credit> objList = new ArrayList<Credit>();
		
		objList.add(credit3);
		objList.add(credit4);
		
		// Test limited access
		Collection results = getApplicationService("user2","password").search(Credit.class,objList);

		assertNotNull(results);
		assertEquals(2,results.size());

		for(Iterator i = results.iterator();i.hasNext();)
		{
			Credit result = (Credit)i.next();
			assertNotNull(result);
			assertNotNull(result.getId());
			
			// user2 does not have access to amount and cardNumber attributes
			if (enableAttributeLevelSecurity){
				assertNull(result.getAmount());
				assertNull(result.getCardNumber());
			} else {
				assertNotNull(result.getAmount());
				assertNotNull(result.getCardNumber());				
			}
		}
	}	
	
	/**
	 * Uses Query by Example for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */
	public void testPathListSearch() throws Exception
	{
		Credit credit3 = new Credit();
		Credit credit4 = new Credit();
		
		credit3.setId(3);
		credit4.setId(4);
		
		List<Credit> objList = new ArrayList<Credit>();
		
		objList.add(credit3);
		objList.add(credit4);
		
		Collection results = getApplicationService("user1","password").search(Credit.class.getName(),objList);

		assertNotNull(results);
		assertEquals(2,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Credit result = (Credit)i.next();
			assertNotNull(result);
			assertNotNull(result.getId());
			assertNotNull(result.getAmount());
			assertNotNull(result.getCardNumber());
		}
	}
	/**
	 * Uses Query by Example for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */
	public void testLimitedAccessPathListSearch() throws Exception
	{
		Credit credit3 = new Credit();
		Credit credit4 = new Credit();
		
		credit3.setId(3);
		credit4.setId(4);
		
		List<Credit> objList = new ArrayList<Credit>();
		
		objList.add(credit3);
		objList.add(credit4);
		
		Collection results = getApplicationService("user2","password").search(Credit.class.getName(),objList);

		assertNotNull(results);
		assertEquals(2,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Credit result = (Credit)i.next();
			assertNotNull(result);
			assertNotNull(result.getId());
			
			if (enableAttributeLevelSecurity){
				// user2 does not have access to amount and cardNumber attributes
				assertNull(result.getAmount());
				assertNull(result.getCardNumber());
			} else {
				assertNotNull(result.getAmount());
				assertNotNull(result.getCardNumber());				
			}

		}
	}
	
	/**
	 * Uses Query by Example for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */
	public void testClassObjectSearch() throws Exception
	{
		Credit credit1 = new Credit();
		credit1.setId(3);

		Collection results = getApplicationService("user1","password").search(Credit.class,credit1);

		assertNotNull(results);
		assertEquals(1,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Credit result = (Credit)i.next();
			assertNotNull(result);
			assertNotNull(result.getId());
			assertNotNull(result.getAmount());
			assertNotNull(result.getCardNumber());
		}
	}	
		
	
	/**
	 * Uses Query by Example for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */
	public void testLimitedAccessClassObjectSearch() throws Exception
	{
		Credit credit1 = new Credit();
		credit1.setId(3);

		Collection results = getApplicationService("user2","password").search(Credit.class,credit1);

		assertNotNull(results);
		assertEquals(1,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Credit result = (Credit)i.next();
			assertNotNull(result);
			assertNotNull(result.getId());
			
			if (enableAttributeLevelSecurity){
				// user2 does not have access to amount and cardNumber attributes
				assertNull(result.getAmount());
				assertNull(result.getCardNumber());
			} else {
				assertNotNull(result.getAmount());
				assertNotNull(result.getCardNumber());				
			}
		}
	}	
	
	/**
	 * Uses Class for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */
	public void testPathObjectSearch() throws Exception
	{
		Credit searchObject = new Credit();
		Collection results = getApplicationService("user1","password").search(Credit.class.getName(),searchObject );

		assertNotNull(results);
		assertEquals(2,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Credit result = (Credit)i.next();
			assertNotNull(result);
			assertNotNull(result.getId());
			assertNotNull(result.getAmount());
			assertNotNull(result.getCardNumber());
		}
	}	

	/**
	 * Uses Class for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */
	public void testLimitedAccessPathObjectSearch() throws Exception
	{
		Credit searchObject = new Credit();
		Collection results = getApplicationService("user2","password").search(Credit.class.getName(),searchObject );

		assertNotNull(results);
		assertEquals(2,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Credit result = (Credit)i.next();
			assertNotNull(result);
			assertNotNull(result.getId());
			
			if (enableAttributeLevelSecurity){
				// user2 does not have access to amount and cardNumber attributes
				assertNull(result.getAmount());
				assertNull(result.getCardNumber());
			} else {
				assertNotNull(result.getAmount());
				assertNotNull(result.getCardNumber());				
			}
		}
	}
	
	/**
	 * Uses Class for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */
	public void testCritieriaFirstRowPathQuery() throws Exception
	{
		
		DetachedCriteria detachedCrit = DetachedCriteria.forClass(Credit.class);

		Collection results = getApplicationService("user1","password").query(detachedCrit, 1, Credit.class.getName());

		assertNotNull(results);
		assertEquals(1,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Credit result = (Credit)i.next();
			assertNotNull(result);
			assertNotNull(result.getId());
			assertNotNull(result.getAmount());
			assertNotNull(result.getCardNumber());
		}
	}	
	
	/**
	 * Uses Class for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */
	public void testLimitedAccessCritieriaFirstRowPathQuery() throws Exception
	{
		
		DetachedCriteria detachedCrit = DetachedCriteria.forClass(Credit.class);

		Collection results = getApplicationService("user2","password").query(detachedCrit, 1, Credit.class.getName());

		assertNotNull(results);
		assertEquals(1,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Credit result = (Credit)i.next();
			assertNotNull(result);
			assertNotNull(result.getId());
			
			if (enableAttributeLevelSecurity){
				// user2 does not have access to amount and cardNumber attributes
				assertNull(result.getAmount());
				assertNull(result.getCardNumber());
			} else {
				assertNotNull(result.getAmount());
				assertNotNull(result.getCardNumber());				
			}
		}
	}	
	
	/**
	 * Uses DetachedCriteria for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */
	public void testRowCountQuery() throws Exception
	{
		
		DetachedCriteria detachedCrit = DetachedCriteria.forClass(Credit.class);

		int count = getApplicationService("user1","password").getQueryRowCount(detachedCrit, Credit.class.getName());

		assertNotNull(count);
		assertEquals(2,count);
	}	
	
	/**
	 * Uses DetachedCriteria for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */
	public void testLimitedAccessRowCountQuery() throws Exception
	{
		
		DetachedCriteria detachedCrit = DetachedCriteria.forClass(Credit.class);

		int count = getApplicationService("user2","password").getQueryRowCount(detachedCrit, Credit.class.getName());

		assertNotNull(count);
		assertEquals(2,count);
	}

	/**
	 * Uses Class for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */
	public void testGetMaxRecordsQuery() throws Exception
	{
		int count = getApplicationService("user1","password").getMaxRecordsCount();

		assertNotNull(count);
		assertEquals(1000,count);
	}
	
	/**
	 * Uses Class for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */
	public void testLimitedAccessGetMaxRecordsQuery() throws Exception
	{
		int count = getApplicationService("user2","password").getMaxRecordsCount();

		assertNotNull(count);
		assertEquals(1000,count);
	}

	/**
	 * Uses CQL Criteria for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */
	public void testGetAssociationQuery() throws Exception
	{
		Credit searchObject = new Credit();
		Collection results = getApplicationService("user1","password").search("gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.Credit",searchObject );

		assertNotNull(results);
		assertEquals(2,results.size());
		
		Bank bank;
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Credit credit = (Credit)i.next();
			assertNotNull(credit);
			assertNotNull(credit.getId());
			assertNotNull(credit.getAmount());
			assertNotNull(credit.getCardNumber());

			bank = credit.getIssuingBank();
			assertNotNull(bank);
			assertNotNull(bank.getId());
			assertNotNull(bank.getName());
		}
	}	
	
	/**
	 * Uses CQL Criteria for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */
	public void testLimitedAccessGetAssociationQuery() throws Exception
	{
		Credit searchObject = new Credit();
		Collection results = getApplicationService("user2","password").search("gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.Credit",searchObject );

		assertNotNull(results);
		assertEquals(2,results.size());
		
		Bank bank;
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Credit credit = (Credit)i.next();
			assertNotNull(credit);
			assertNotNull(credit.getId());
			
			if (enableAttributeLevelSecurity){
				// user2 does not have access to amount and cardNumber attributes
				assertNull(credit.getAmount());
				assertNull(credit.getCardNumber());
			} else {
				assertNotNull(credit.getAmount());
				assertNotNull(credit.getCardNumber());				
			}


			bank = credit.getIssuingBank();
			assertNotNull(bank);
			assertNotNull(bank.getId());
			assertNotNull(bank.getName());
		}
	}	
	
	/**
	 * Uses CQL Criteria for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */
	public void testCQLQuery() throws Exception
	{
		CQLQuery cqlQuery = new CQLQuery();
		CQLObject target = new CQLObject();
		
		target.setName("gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.Credit");
		cqlQuery.setTarget(target);

		Collection results = getApplicationService("user1","password").query(cqlQuery);

		assertNotNull(results);
		assertEquals(2,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Credit result = (Credit)i.next();
			assertNotNull(result);
			assertNotNull(result.getId());
			assertNotNull(result.getAmount());
			assertNotNull(result.getCardNumber());
		}
	}
	
	/**
	 * Uses CQL Criteria for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */
	public void testLimitedAccessCQLQuery() throws Exception
	{
		CQLQuery cqlQuery = new CQLQuery();
		CQLObject target = new CQLObject();
		
		target.setName("gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.Credit");
		cqlQuery.setTarget(target);

		Collection results = getApplicationService("user2","password").query(cqlQuery);

		assertNotNull(results);
		assertEquals(2,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Credit result = (Credit)i.next();
			assertNotNull(result);
			assertNotNull(result.getId());
			
			if (enableAttributeLevelSecurity){
				// user2 does not have access to amount and cardNumber attributes
				assertNull(result.getAmount());
				assertNull(result.getCardNumber());
			} else {
				assertNotNull(result.getAmount());
				assertNotNull(result.getCardNumber());				
			}
		}
	}	

	/**
	 * Uses HQL Criteria for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */
	public void testHQLQuery() throws Exception
	{
		String hql = "from gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.Credit";
		HQLCriteria hqlCrit = new HQLCriteria(hql);

		Collection results = getApplicationService("user1","password").query(hqlCrit);

		assertNotNull(results);
		assertEquals(2,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Credit result = (Credit)i.next();
			assertNotNull(result);
			assertNotNull(result.getId());
			assertNotNull(result.getAmount());
			assertNotNull(result.getCardNumber());
		}
	}
	
	/**
	 * Uses HQL Criteria for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */
	public void testLimitedAccessHQLQuery() throws Exception
	{
		String hql = "from gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.Credit";
		HQLCriteria hqlCrit = new HQLCriteria(hql);

		Collection results = getApplicationService("user2","password").query(hqlCrit);

		assertNotNull(results);
		assertEquals(2,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Credit result = (Credit)i.next();
			assertNotNull(result);
			assertNotNull(result.getId());
			
			if (enableAttributeLevelSecurity){
				// user2 does not have access to amount and cardNumber attributes
				assertNull(result.getAmount());
				assertNull(result.getCardNumber());
			} else {
				assertNotNull(result.getAmount());
				assertNotNull(result.getCardNumber());				
			}
		}
	}
	
	/**
	 * Uses DetachedCriteria query for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */	
	public void testDetachedCriteriaQuery() throws Exception
	{
		DetachedCriteria detachedCrit = DetachedCriteria.forClass(Credit.class);

		Collection results = getApplicationService("user1","password").query(detachedCrit);

		assertNotNull(results);
		assertEquals(2,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Credit result = (Credit)i.next();
			assertNotNull(result);
			assertNotNull(result.getId());
			assertNotNull(result.getAmount());
			assertNotNull(result.getCardNumber());
		}
	}
	
	/**
	 * Uses DetachedCriteria query for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */	
	public void testLimitedAccessDetachedCriteriaQuery() throws Exception
	{
		DetachedCriteria detachedCrit = DetachedCriteria.forClass(Credit.class);

		Collection results = getApplicationService("user2","password").query(detachedCrit);

		assertNotNull(results);
		assertEquals(2,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Credit result = (Credit)i.next();
			assertNotNull(result);
			assertNotNull(result.getId());
			
			if (enableAttributeLevelSecurity){
				// user2 does not have access to amount and cardNumber attributes
				assertNull(result.getAmount());
				assertNull(result.getCardNumber());
			} else {
				assertNotNull(result.getAmount());
				assertNotNull(result.getCardNumber());				
			}
		}
	}
	
	public void testBasicAuthenticationGetXML() throws Exception
	{
		String serverUrl = "http://localhost:8080/example";
	
		Class creditKlass = Credit.class;

		try
		{
			String searchUrl = serverUrl+"/GetXML?query="+creditKlass.getName()+"&"+creditKlass.getName();
			URL url = new URL(searchUrl);
			URLConnection conn = url.openConnection();

			String base64 = "user1" + ":" + "password";
			conn.setRequestProperty("Authorization", "Basic " + new String(Base64.encodeBase64(base64.getBytes())));

			File myFile = new File("./output/" + creditKlass.getName() + "_test-getxml.xml");						

			FileWriter myWriter = new FileWriter(myFile);
			DataInputStream dis = new DataInputStream(conn.getInputStream());

			String s = null;
			while ((s = dis.readLine()) != null)
				myWriter.write(s);

			myWriter.close();
		} catch(Exception e)
		{
			System.out.println("Exception caught: " + e.getMessage());
			e.printStackTrace();
//			log.error("Exception caught: " + e.getMessage(), e);
			fail();
		}
	}
	
	public void testLimitedAccessBasicAuthenticationGetXML() throws Exception
	{
		String serverUrl = "http://localhost:8080/example";
		
		Class creditKlass = Credit.class;

		try
		{
			String searchUrl = serverUrl+"/GetXML?query="+creditKlass.getName()+"&"+creditKlass.getName();
			URL url = new URL(searchUrl);
			URLConnection conn = url.openConnection();

			String base64 = "user2" + ":" + "password"; //user2 has limited access to Credit class attributes
			conn.setRequestProperty("Authorization", "Basic " + new String(Base64.encodeBase64(base64.getBytes())));

			File myFile = new File("./output/" + creditKlass.getName() + "_test-getxml.xml");						

			FileWriter myWriter = new FileWriter(myFile);
			DataInputStream dis = new DataInputStream(conn.getInputStream());

			String s, buffer = null;
			while ((s = dis.readLine()) != null){
				myWriter.write(s);
				buffer = buffer + s;
			}
	
			myWriter.close();
			
			assertTrue(buffer.indexOf("<recordCounter>2</recordCounter>") > 0);
			
			int recordNumber =0;
			for (int i=3; i<=4; i++ ){
				recordNumber = i-2;
				assertTrue(buffer.indexOf("name=\"gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.Credit\" recordNumber=\"" + recordNumber + "\"") > 0);
				assertTrue(buffer.indexOf("<field name=\"id\">" + i +"</field>") > 0);
				if (enableAttributeLevelSecurity){
					// user2 does not have access to amount and cardNumber attributes
					assertTrue(buffer.indexOf("<field name=\"amount\">-</field>") > 0);
					assertTrue(buffer.indexOf("<field name=\"cardNumber\">-</field>") > 0);
				}
				
			}
			
		} catch(Exception e) {
			System.out.println("Exception caught: " + e.getMessage());
			e.printStackTrace();
			fail();
		}
	}

}
