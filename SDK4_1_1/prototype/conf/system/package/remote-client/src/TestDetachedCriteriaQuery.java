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
import gov.nih.nci.system.query.hibernate.HQLCriteria;
import gov.nih.nci.system.applicationservice.ApplicationService;


public class TestDetachedCriteriaQuery extends TestClient
{
	public static void main(String args[])
	{
		TestDetachedCriteriaQuery client = new TestDetachedCriteriaQuery();
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

		DetachedCriteria detachedCrit = DetachedCriteria.forClass(Suit.class)
				.add( Property.forName("id").eq(4) ); // Suit = Heart

		Collection results = appService.query(detachedCrit);
		System.out.println("Number of qualifying records: " + results.size());
		for(Object obj : results)
		{
			printObject(obj, Suit.class);
		}

	}
	
	
}