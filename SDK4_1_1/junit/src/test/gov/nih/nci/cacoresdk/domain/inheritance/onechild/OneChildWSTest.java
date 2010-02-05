package test.gov.nih.nci.cacoresdk.domain.inheritance.onechild;

import gov.nih.nci.cacoresdk.domain.inheritance.onechild.Human;
import gov.nih.nci.cacoresdk.domain.inheritance.onechild.Mammal;

import java.util.ArrayList;
import java.util.Collection;

import test.gov.nih.nci.cacoresdk.SDKWSTestBase;

public class OneChildWSTest extends SDKWSTestBase
{
	public static String getTestCaseName()
	{
		return "One Child WS Test Case";
	}
	
	protected Collection<Class> getClasses() throws Exception
	{	
		Collection<Class> mappedKlasses = new ArrayList<Class>();
		
		mappedKlasses.add(Human.class);
		mappedKlasses.add(Mammal.class);
		
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
		Class targetClass = Mammal.class;
		Mammal criteria = new Mammal();

		Object[] results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(5,results.length);
		
		for (Object obj : results){
			Mammal mammal = (Mammal)obj;
			assertNotNull(mammal);
			assertNotNull(mammal.getId());
			assertNotNull(mammal.getHairColor());	
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
		Class targetClass = Human.class;
		Human criteria = new Human();

		Object[] results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(4,results.length);
		
		for (Object obj : results){
			Human human = (Human)obj;
			assertNotNull(human);
			assertNotNull(human.getId());
			assertNotNull(human.getHairColor());
			assertNotNull(human.getDiet());			
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

		//Class targetClass = Human.class;
		Class targetClass = Mammal.class;
		Mammal criteria = new Mammal();
		criteria.setHairColor("Hair_Color1");

		Object[] results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(1,results.length);
		
		Human human = (Human)results[0];
		assertNotNull(human);
		assertNotNull(human.getId());
		assertNotNull(human.getDiet());
		assertNotNull(human.getHairColor());
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
		Class targetClass = Mammal.class;
		Human criteria = new Human();
		criteria.setHairColor("Hair_Color1");

		Object[] results = getQueryObjectResults(targetClass, criteria);

		assertNotNull(results);
		assertEquals(1,results.length);
		
		Mammal mammal = (Mammal)results[0];
		assertNotNull(mammal);
		assertNotNull(mammal.getId());
		assertNotNull(mammal.getHairColor());		
	}
}
