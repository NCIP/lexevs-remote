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
import gov.nih.nci.system.query.cql.CQLAttribute;
import gov.nih.nci.system.query.cql.CQLObject;
import gov.nih.nci.system.query.cql.CQLPredicate;
import gov.nih.nci.system.query.cql.CQLQuery;
import gov.nih.nci.system.query.hibernate.HQLCriteria;
import gov.nih.nci.system.applicationservice.ApplicationService;


public class TestCQLQuery extends TestClient
{
	public static void main(String args[])
	{
		TestCQLQuery client = new TestCQLQuery();
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
		
		CQLObject target = new CQLObject();
		target.setName("gov.nih.nci.cacoresdk.domain.other.levelassociation.Suit");
		
		CQLAttribute attribute = new CQLAttribute();
		attribute.setName("id");
		attribute.setValue("3");  // Suit = Diamond
		attribute.setPredicate(CQLPredicate.EQUAL_TO);
		
		target.setAttribute(attribute);
		
		cqlQuery.setTarget(target);

		Collection results = appService.query(cqlQuery);
		System.out.println("Number of qualifying records: " + results.size());
		for(Object obj : results)
		{
			printObject(obj, Suit.class);
			break;
		}
	}
}