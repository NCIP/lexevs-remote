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

import gov.nih.nci.cacoresdk.domain.other.levelassociation.Suit;
import gov.nih.nci.system.client.ApplicationServiceProvider;
import gov.nih.nci.system.query.cql.CQLAssociation;
import gov.nih.nci.system.query.cql.CQLAttribute;
import gov.nih.nci.system.query.cql.CQLGroup;
import gov.nih.nci.system.query.cql.CQLLogicalOperator;
import gov.nih.nci.system.query.cql.CQLObject;
import gov.nih.nci.system.query.cql.CQLPredicate;
import gov.nih.nci.system.query.cql.CQLQuery;
import gov.nih.nci.system.query.hibernate.HQLCriteria;
import gov.nih.nci.system.applicationservice.ApplicationService;


public class TestCQLwAssociationQuery extends TestClient
{
	public static void main(String args[])
	{
		TestCQLwAssociationQuery client = new TestCQLwAssociationQuery();
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
		
		//Create an Association to a Card instance in the Spade Suit
		CQLAssociation association1 = new CQLAssociation();
		association1.setName("gov.nih.nci.cacoresdk.domain.other.levelassociation.Card");
		CQLAttribute attribute1 = new CQLAttribute();
		attribute1.setName("id");
		attribute1.setValue("2"); //Part of the Spade suit
		attribute1.setPredicate(CQLPredicate.EQUAL_TO);
		
		association1.setTargetRoleName("cardCollection");
		association1.setSourceRoleName("suit");
		association1.setAttribute(attribute1);
		
		//Create a second Association to a Card instance in the Diamond Suit
		CQLAssociation association2 = new CQLAssociation();
		association2.setName("gov.nih.nci.cacoresdk.domain.other.levelassociation.Card");
		CQLAttribute attribute2 = new CQLAttribute();
		attribute2.setName("id");
		attribute2.setValue("32");  //Part of the Diamond suit
		attribute2.setPredicate(CQLPredicate.EQUAL_TO);
		
		association2.setTargetRoleName("cardCollection");
		association2.setSourceRoleName("suit");
		association2.setAttribute(attribute2);
		
		//Add both associations to a Group
		CQLGroup group = new CQLGroup();
		group.addAssociation(association1);
		group.addAssociation(association2);
		group.setLogicOperator(CQLLogicalOperator.OR);
 		
		target.setGroup(group);
		
		cqlQuery.setTarget(target);

		Collection results = appService.query(cqlQuery);
		System.out.println("Number of qualifying records: " + results.size());
		for(Object obj : results)
		{
			printObject(obj, Suit.class);
		}
	}
}