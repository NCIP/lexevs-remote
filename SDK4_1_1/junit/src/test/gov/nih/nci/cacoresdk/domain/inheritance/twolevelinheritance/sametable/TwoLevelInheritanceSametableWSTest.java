package test.gov.nih.nci.cacoresdk.domain.inheritance.twolevelinheritance.sametable;

import gov.nih.nci.cacoresdk.domain.inheritance.twolevelinheritance.sametable.CommunistGovt;
import gov.nih.nci.cacoresdk.domain.inheritance.twolevelinheritance.sametable.DemocraticGovt;
import gov.nih.nci.cacoresdk.domain.inheritance.twolevelinheritance.sametable.Goverment;
import gov.nih.nci.cacoresdk.domain.inheritance.twolevelinheritance.sametable.ParliamantaryGovt;
import gov.nih.nci.cacoresdk.domain.inheritance.twolevelinheritance.sametable.PresidentialGovt;

import java.util.ArrayList;
import java.util.Collection;

import test.gov.nih.nci.cacoresdk.SDKWSTestBase;

public class TwoLevelInheritanceSametableWSTest extends SDKWSTestBase
{
	public static String getTestCaseName()
	{
		return "Two Level Inheritance Same Table WS Test Case";
	}
	
	protected Collection<Class> getClasses() throws Exception
	{	
		Collection<Class> mappedKlasses = new ArrayList<Class>();
		
		mappedKlasses.add(CommunistGovt.class);
		mappedKlasses.add(DemocraticGovt.class);
		mappedKlasses.add(Goverment.class);
		mappedKlasses.add(ParliamantaryGovt.class);
		mappedKlasses.add(PresidentialGovt.class);
		
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
		Class targetClass = Goverment.class;
		Goverment criteria = new Goverment();

		Object[] results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(3,results.length);
		
		for (Object obj : results){
			Goverment result = (Goverment)obj;
			assertNotNull(result);
			assertNotNull(result.getId());
			assertNotNull(result.getCountry());	
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
		Class targetClass = CommunistGovt.class;
		CommunistGovt criteria = new CommunistGovt();

		Object[] results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(1,results.length);
		
		for (Object obj : results){
			CommunistGovt result = (CommunistGovt)obj;
			assertNotNull(result);
			assertNotNull(result.getId());
			assertNotNull(result.getCountry());	
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
		Class targetClass = DemocraticGovt.class;
		DemocraticGovt criteria = new DemocraticGovt();

		Object[] results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(2,results.length);
		
		for (Object obj : results){
			DemocraticGovt result = (DemocraticGovt)obj;
			assertNotNull(result);
			assertNotNull(result.getId());
			assertNotNull(result.getCountry());	
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
		Class targetClass = ParliamantaryGovt.class;
		ParliamantaryGovt criteria = new ParliamantaryGovt();

		Object[] results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(1,results.length);
		
		for (Object obj : results){
			ParliamantaryGovt result = (ParliamantaryGovt)obj;
			assertNotNull(result);
			assertNotNull(result.getId());
			assertNotNull(result.getCountry());	
			assertNotNull(result.getPrimeMinister());	
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
	public void testEntireObjectNestedSearch5() throws Exception
	{
		Class targetClass = PresidentialGovt.class;
		PresidentialGovt criteria = new PresidentialGovt();

		Object[] results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(1,results.length);
		
		for (Object obj : results){
			PresidentialGovt result = (PresidentialGovt)obj;
			assertNotNull(result);
			assertNotNull(result.getId());
			assertNotNull(result.getCountry());	
			assertNotNull(result.getPresident());
		}		
	}
	
	/**
	 * Uses Nested Search Criteria for inheritance as association in search
	 * Verifies that the result set is empty
	 * 
	 * @throws Exception
	 */
	public void testZeroAssociationNestedSearch() throws Exception
	{
		Class targetClass = DemocraticGovt.class;
		ParliamantaryGovt criteria = new ParliamantaryGovt();
		criteria.setId(1);

		Object[] results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(0,results.length);
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
		Class targetClass = CommunistGovt.class;
//		Goverment criteria = new Goverment();		
		CommunistGovt criteria = new CommunistGovt();

		Object[] results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(1,results.length);
		
		CommunistGovt result = (CommunistGovt)results[0];
		assertNotNull(result);
		assertNotNull(result.getId());		
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
		Class targetClass = DemocraticGovt.class;
		ParliamantaryGovt criteria = new ParliamantaryGovt();

		Object[] results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(1,results.length);
		
		DemocraticGovt result = (DemocraticGovt)results[0];
		assertNotNull(result);
		assertNotNull(result.getId());			
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
		Class targetClass = PresidentialGovt.class;
		//DemocraticGovt criteria = new DemocraticGovt();		
		PresidentialGovt criteria = new PresidentialGovt();

		Object[] results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(1,results.length);
		
		PresidentialGovt result = (PresidentialGovt)results[0];
		assertNotNull(result);
		assertNotNull(result.getId());			
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
		Class targetClass = DemocraticGovt.class;
		PresidentialGovt criteria = new PresidentialGovt();

		Object[] results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(1,results.length);
		
		DemocraticGovt result = (DemocraticGovt)results[0];
		assertNotNull(result);
		assertNotNull(result.getId());	
	}
}
