package test.gov.nih.nci.cacoresdk.domain.manytoone.unidirectional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import junit.framework.Assert;
import gov.nih.nci.cacoresdk.domain.manytoone.unidirectional.Chef;
import gov.nih.nci.cacoresdk.domain.manytoone.unidirectional.Restaurant;
import test.gov.nih.nci.cacoresdk.SDKWritableApiBaseTest;

public class M2OUnidirectionalWritableApiTest extends SDKWritableApiBaseTest{
	private static Logger log = LogManager.getLogger(M2OUnidirectionalWritableApiTest.class);
	public static String getTestCaseName() {
		return "Many to One Unidirectional WritableApi Test Case";
	}
	
	public void testSaveObjectChefWithMany2OneCascadeSaveUpdate(){
		log.debug("--------testSaveObjectChef()---------------");
		Chef chef=new Chef();
		chef.setName("chef");
		Restaurant restaurant=new Restaurant();
		restaurant.setName("restaurant");
		chef.setRestaurant(restaurant);
		
		save(chef);
		
		Chef result=(Chef)getObjectAndLazyObject(Chef.class, chef.getId(),"restaurant");
		Assert.assertEquals(chef.getName(), result.getName());
		Assert.assertEquals(restaurant.getName(), result.getRestaurant().getName());
	}
	
	public void testSaveObjectChefWithFkNotNullTrue(){
		log.debug("--------testSaveObjectChef()---------------");
		Chef chef=new Chef();
		chef.setName("chef");
		try{
			save(chef);
			fail("must throw not-null property references exception");
		}catch (Exception e) {
			Assert.assertEquals("gov.nih.nci.system.applicationservice.ApplicationException: Error while querying DAO: ", e.getMessage());
		}
	}
	
	public void testUpdateObjectChefWithMany2OneCascadeSaveUpdate(){
		log.debug("--------testSaveObjectChef()---------------");
		Chef chef=new Chef();
		chef.setName("chef");
		Restaurant restaurant=new Restaurant();
		restaurant.setName("restaurant");
		chef.setRestaurant(restaurant);

		save(chef);
		
		Chef updateChef=(Chef)getObjectAndLazyObject(Chef.class, chef.getId(),"restaurant");
		
		updateChef.setName("updateChef");
		updateChef.getRestaurant().setName("updateRestaurant");
		
		update(updateChef);
		
		Chef resultChef=(Chef)getObjectAndLazyObject(Chef.class, chef.getId(),"restaurant");
		
		Assert.assertEquals(updateChef.getName(), resultChef.getName());
		Assert.assertEquals(updateChef.getRestaurant().getName(), resultChef.getRestaurant().getName());
	}
	
	public void testDeleteObjectChefWithMany2OneCascadeSaveUpdate(){
		log.debug("--------testDeleteObjectChefWithMany2OneCascadePersist()---------------");
		Chef chef=new Chef();
		chef.setName("chef");
		Restaurant restaurant=new Restaurant();
		restaurant.setName("restaurant");
		chef.setRestaurant(restaurant);

		save(chef);
		
		Chef deleteChef=(Chef)getObject(Chef.class, chef.getId());
		deleteChef.getRestaurant();
		delete(deleteChef);
		
		Chef resultChef=(Chef)getObject(Chef.class, deleteChef.getId());
		Restaurant resultRestaurant=(Restaurant)getObject(Restaurant.class, deleteChef.getRestaurant().getId());
		
		Assert.assertNull("Chef object must be deleted",resultChef);
		Assert.assertNotNull("Restaurant must not be deleted",resultRestaurant);
	}
}
