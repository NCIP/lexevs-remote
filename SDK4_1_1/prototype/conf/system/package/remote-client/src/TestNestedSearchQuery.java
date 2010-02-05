import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

import gov.nih.nci.cacoresdk.domain.other.levelassociation.Card;
import gov.nih.nci.cacoresdk.domain.other.levelassociation.Deck;
import gov.nih.nci.cacoresdk.domain.other.levelassociation.Suit;
import gov.nih.nci.system.client.ApplicationServiceProvider;
import gov.nih.nci.system.query.cql.CQLAssociation;
import gov.nih.nci.system.query.cql.CQLAttribute;
import gov.nih.nci.system.query.cql.CQLObject;
import gov.nih.nci.system.query.cql.CQLPredicate;
import gov.nih.nci.system.query.cql.CQLQuery;
import gov.nih.nci.system.query.hibernate.HQLCriteria;
import gov.nih.nci.system.applicationservice.ApplicationService;


public class TestNestedSearchQuery extends TestClient
{
	public static void main(String args[])
	{
		TestNestedSearchQuery client = new TestNestedSearchQuery();
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

		Card card1 = new Card();
		card1.setId(6);
		
		Card card2 = new Card();
		card2.setId(2);
		
		List<Card> cardCollection = new ArrayList<Card>();
		cardCollection.add(card1);
		cardCollection.add(card2);
		
		String path = "gov.nih.nci.cacoresdk.domain.other.levelassociation.Suit," +
					  "gov.nih.nci.cacoresdk.domain.other.levelassociation.Card";
		
		Collection results = appService.search(path, cardCollection);
		System.out.println("Number of qualifying records: " + results.size());
		for(Object obj : results)
		{
			printObject(obj, Suit.class);
		}
	}
}