package test.gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.sametable;

import org.apache.log4j.Logger;

import junit.framework.Assert;
import gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.sametable.Designer;
import gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.sametable.DesignerShoes;
import gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.sametable.SportsShoes;
import test.gov.nih.nci.cacoresdk.SDKWritableApiBaseTest;

public class ChildWithAssociationSametableWritableApiTest extends SDKWritableApiBaseTest {
	private static Logger log = Logger.getLogger(ChildWithAssociationSametableWritableApiTest.class);
	public static String getTestCaseName() {
		return "Child With Association Same Table Writable Test Case";
	}
	
	public void testSaveObjectWithInheritAssociatedObjects(){
		log.debug("\n------testSaveObjectSameTableInheritance()--------\n");
		SportsShoes sportsShoes=new SportsShoes();
		sportsShoes.setColor("sportsColor");
		sportsShoes.setSportsType("Sports");
		
		DesignerShoes designerShoes=new DesignerShoes();
		designerShoes.setColor("designerColor");
		Designer designer=new Designer();
		designer.setName("designer");
		designerShoes.setDesigner(designer);
		
		save(designer);
		save(sportsShoes);
		save(designerShoes);
		
		SportsShoes resultSportShoes=(SportsShoes)getObject(SportsShoes.class, sportsShoes.getId());
		DesignerShoes resultDesignerShoes=(DesignerShoes)getObjectAndLazyObject(DesignerShoes.class, designerShoes.getId(),"designer");
		Assert.assertEquals(sportsShoes.getColor(), resultSportShoes.getColor());
		Assert.assertEquals(designerShoes.getDesigner().getName(), resultDesignerShoes.getDesigner().getName());
	}
	
	public void testUpdateObjectWithInheritAssociatedObjects(){
		log.debug("\n------testSaveObjectSameTableInheritance()--------\n");
		SportsShoes sportsShoes=new SportsShoes();
		sportsShoes.setColor("sportsColor");
		sportsShoes.setSportsType("Sports");
		
		DesignerShoes designerShoes=new DesignerShoes();
		designerShoes.setColor("designerColor");
		Designer designer=new Designer();
		designer.setName("designer");
		designerShoes.setDesigner(designer);
		
		save(designer);
		save(sportsShoes);
		save(designerShoes);
		
		SportsShoes updateSportShoes=(SportsShoes)getObject(SportsShoes.class, sportsShoes.getId());
		DesignerShoes updateDesignerShoes=(DesignerShoes)getObjectAndLazyObject(DesignerShoes.class, designerShoes.getId(),"designer");
		
		updateSportShoes.setColor("updateColor");
		Designer updateDesigner=updateDesignerShoes.getDesigner();
		updateDesigner.setName("updateDesigner");
		
		update(updateDesigner);
		update(updateSportShoes);
		update(updateDesignerShoes);
		
		SportsShoes resultSportShoes=(SportsShoes)getObject(SportsShoes.class, updateSportShoes.getId());
		DesignerShoes resultDesignerShoes=(DesignerShoes)getObjectAndLazyObject(DesignerShoes.class, updateDesignerShoes.getId(),"designer");
		Assert.assertEquals(updateSportShoes.getColor(), resultSportShoes.getColor());
		Assert.assertEquals(updateDesignerShoes.getDesigner().getName(), resultDesignerShoes.getDesigner().getName());
	}
	
	public void testDeleteObjectWithInheritAssociatedObjects(){
		log.debug("\n------testDeleteObjectWithInheritAssociatedObjects()--------\n");
		SportsShoes sportsShoes=new SportsShoes();
		sportsShoes.setColor("sportsColor");
		sportsShoes.setSportsType("Sports");
		
		DesignerShoes designerShoes=new DesignerShoes();
		designerShoes.setColor("designerColor");
		Designer designer=new Designer();
		designer.setName("designer");
		designerShoes.setDesigner(designer);
		
		save(designer);
		save(sportsShoes);
		save(designerShoes);
		
		SportsShoes deleteSportShoes=(SportsShoes)getObject(SportsShoes.class, sportsShoes.getId());
		DesignerShoes deleteDesignerShoes=(DesignerShoes)getObjectAndLazyObject(DesignerShoes.class, designerShoes.getId(),"designer");
		
		delete(deleteSportShoes);
		delete(deleteDesignerShoes);
		
		SportsShoes resultSportShoes=(SportsShoes)getObject(SportsShoes.class, deleteSportShoes.getId());
		DesignerShoes resultDesignerShoes=(DesignerShoes)getObjectAndLazyObject(DesignerShoes.class, deleteDesignerShoes.getId(),"designer");
		Designer resultDesigner=(Designer)getObject(Designer.class, designer.getId());
		Assert.assertNull(resultSportShoes);
		Assert.assertNull(resultDesignerShoes);
		Assert.assertEquals(designer.getName(), resultDesigner.getName());
	}
}
