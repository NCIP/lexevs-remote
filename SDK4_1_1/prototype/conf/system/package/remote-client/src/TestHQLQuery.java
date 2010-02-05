import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import gov.nih.nci.cacoresdk.domain.other.levelassociation.Suit;
import gov.nih.nci.system.client.ApplicationServiceProvider;
import gov.nih.nci.system.query.hibernate.HQLCriteria;
import gov.nih.nci.system.applicationservice.ApplicationService;


public class TestHQLQuery extends TestClient
{
	public static void main(String args[])
	{
		TestHQLQuery client = new TestHQLQuery();
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

		String hql = "from gov.nih.nci.cacoresdk.domain.inheritance.implicit.Tank";
		System.out.println("HQL Query: " + hql);
		HQLCriteria hqlCrit = new HQLCriteria(hql);

		Collection results = appService.query(hqlCrit);
		System.out.println("Number of qualifying records: " + results.size());
		for(Object obj : results)
		{
			printObject(obj, Suit.class);
		}
	}
	
	
	
}











