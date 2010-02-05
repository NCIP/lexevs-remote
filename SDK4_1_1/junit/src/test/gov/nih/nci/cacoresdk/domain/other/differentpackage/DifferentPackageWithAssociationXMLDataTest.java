package test.gov.nih.nci.cacoresdk.domain.other.differentpackage;

import gov.nih.nci.cacoresdk.domain.other.differentpackage.Dessert;
import gov.nih.nci.cacoresdk.domain.other.differentpackage.associations.Pie;
import gov.nih.nci.cacoresdk.domain.other.differentpackage.associations.Utensil;

import java.util.Collection;
import java.util.Iterator;

import test.gov.nih.nci.cacoresdk.SDKXMLDataTestBase;

public class DifferentPackageWithAssociationXMLDataTest extends SDKXMLDataTestBase
{
	public static String getTestCaseName()
	{
		return "Different Package With Association XML Data Test Case";
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
		Dessert searchObject = new Dessert();
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.other.differentpackage.Dessert",searchObject );

		assertNotNull(results);
		assertEquals(4,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Dessert result = (Dessert)i.next();
			toXML(result);
			
			if (useGMETags){
				validateClassElements(result,Class.forName(getClassName(result)).getSimpleName()+"Alias");
				validateAttribute(result,"idAlias",result.getId());
				assertTrue(validateXMLData(result, searchObject.getClass(),"gme_test.test_1.5_domain.differentpackage.associations.xsd"));
			} else{
				validateClassElements(result);
				validateAttribute(result,"id",result.getId());
				//assertTrue(validateXMLData(result, searchObject.getClass()));// fails validation if associated class is in different package
			}
			

			Dessert result2 = (Dessert)fromXML(result);
			
			assertNotNull(result2);
			assertNotNull(result2.getId());
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
		Pie searchObject = new Pie();
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.other.differentpackage.associations.Pie",searchObject );

		assertNotNull(results);
		assertEquals(2,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Pie result = (Pie)i.next();
			toXML(result);
			
			if (useGMETags){
				validateClassElements(result,"PieAlias");
				validateAttribute(result,"idAlias",result.getId());
				validateAttribute(result,"fillingAlias",result.getFilling());
				assertTrue(validateXMLData(result, searchObject.getClass(),"gme_test.test_1.5_domain.differentpackage.associations.xsd"));
			} else{
				validateClassElements(result);
				validateAttribute(result,"id",result.getId());
				validateAttribute(result,"filling",result.getFilling());
				//assertTrue(validateXMLData(result, searchObject.getClass())); // fails validation if associated class is in different package
			}
			


			Pie result2 = (Pie)fromXML(result);
			
			assertNotNull(result2);
			assertNotNull(result2.getId());
			assertNotNull(result2.getFilling());
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
		Utensil searchObject = new Utensil();
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.other.differentpackage.associations.Utensil",searchObject );

		assertNotNull(results);
		assertEquals(3,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Utensil result = (Utensil)i.next();
			toXML(result);
			
			if (useGMETags){
				validateClassElements(result,"UtensilAlias");
				validateAttribute(result,"idAlias",result.getId());
				validateAttribute(result,"nameAlias",result.getName());
				assertTrue(validateXMLData(result, searchObject.getClass(),"gme_test.test_1.5_domain.differentpackage.associations.xsd"));
			} else{
				validateClassElements(result,"Utensil");
				validateAttribute(result,"id",result.getId());
				validateAttribute(result,"name",result.getName());
				//assertTrue(validateXMLData(result, searchObject.getClass()));// fails validation if associated class is in different package
			}

			Utensil result2 = (Utensil)fromXML(result);
			
			assertNotNull(result2);
			assertNotNull(result2.getId());
			assertNotNull(result2.getName());			
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
		Dessert searchObject = new Dessert();
		searchObject.setId(new Integer(3));
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.other.differentpackage.associations.Pie",searchObject );

		assertNotNull(results);
		assertEquals(1,results.size());
		
		Pie result = (Pie)results.iterator().next();
		toXML(result);
		Pie result2 = (Pie)fromXML(result);

		assertNotNull(result2);
		assertNotNull(result2.getId());
		assertNotNull(result2.getFilling());
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
	public void testAssociationNestedSearch2() throws Exception
	{
		Pie searchObject = new Pie();
		searchObject.setId(new Integer(3));
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.other.differentpackage.Dessert",searchObject );

		assertNotNull(results);
		assertEquals(1,results.size());
		
		Dessert result = (Dessert)results.iterator().next();
		toXML(result);
		Dessert result2 = (Dessert)fromXML(result);

		assertNotNull(result2);
		assertNotNull(result2.getId());
		assertEquals(new Integer(3), result2.getId());
	}
	
	public void testGetAssociation1() throws Exception
	{
		Dessert searchObject = new Dessert();
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.other.differentpackage.Dessert",searchObject );

		assertNotNull(results);
		assertEquals(4,results.size());
		
		Collection<Utensil> utensilColl;
		Utensil utensil;
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Dessert result = (Dessert)i.next();
			toXML(result);
			
			if (useGMETags){
				validateAssociation(result,"UtensilAlias","utensilAliasRoleName");
			} else{
				validateAssociation(result,"Utensil","utensilCollection");
			}

			Dessert result2 = (Dessert)fromXML(result);
			utensilColl = result2.getUtensilCollection();
			
			for (Iterator j = utensilColl.iterator();j.hasNext();){
				utensil = (Utensil)j.next();
				assertNotNull(utensil);
				assertNotNull(utensil.getId());
				assertNotNull(utensil.getName());
			}

		}
	}
	
	public void testGetAssociation2() throws Exception
	{
		Utensil searchObject = new Utensil();
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.other.differentpackage.associations.Utensil",searchObject );

		assertNotNull(results);
		assertEquals(3,results.size());
		
		Collection<Dessert> dessertColl;
		Dessert dessert;
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Utensil result = (Utensil)i.next();
			toXML(result);
			
			if (useGMETags){
				validateAssociation(result,"DessertAlias","dessertAliasRoleName");
			} else{
				validateAssociation(result,"Dessert","dessertCollection");
			}

			Utensil result2 = (Utensil)fromXML(result);
			dessertColl = result2.getDessertCollection();
			
			for (Iterator j = dessertColl.iterator();j.hasNext();){
				dessert = (Dessert)j.next();
				assertNotNull(dessert);
				assertNotNull(dessert.getId());
			}

		}
	}

}
