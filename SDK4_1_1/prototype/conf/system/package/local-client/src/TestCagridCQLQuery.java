import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

import gov.nih.nci.cacoresdk.domain.other.levelassociation.Suit;
import gov.nih.nci.system.client.ApplicationServiceProvider;
//import gov.nih.nci.system.query.cql.CQLAttribute;
//import gov.nih.nci.system.query.cql.CQLObject;
//import gov.nih.nci.system.query.cql.CQLPredicate;
//import gov.nih.nci.system.query.cql.CQLQuery;

import gov.nih.nci.cagrid.cqlquery.Attribute;
import gov.nih.nci.cagrid.cqlquery.Object;
import gov.nih.nci.cagrid.cqlquery.Predicate;
import gov.nih.nci.cagrid.cqlquery.CQLQuery;
import gov.nih.nci.system.query.hibernate.HQLCriteria;
import gov.nih.nci.system.applicationservice.ApplicationService;


public class TestCagridCQLQuery extends TestClient
{
	public static void main(String args[])
	{
		TestCagridCQLQuery client = new TestCagridCQLQuery();
		try
		{
			client.testSearch();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void testSearch() throws Exception
	{
		ApplicationService appService = ApplicationServiceProvider.getApplicationService();

		CQLQuery cqlQuery = new CQLQuery();
		
		Object target = new Object();
		target.setName("gov.nih.nci.cacoresdk.domain.other.levelassociation.Suit");
		
		Attribute attribute = new Attribute();
		attribute.setName("id");
		attribute.setValue("3");  // Suit = Diamond
		attribute.setPredicate(Predicate.EQUAL_TO);
		
		target.setAttribute(attribute);
		
		cqlQuery.setTarget(target);

		Collection results = appService.query(cqlQuery);
		System.out.println("Number of qualifying records: " + results.size());
		for(java.lang.Object obj : results)
		{
			printObject(obj, Suit.class);
			break;
		}
	}
}