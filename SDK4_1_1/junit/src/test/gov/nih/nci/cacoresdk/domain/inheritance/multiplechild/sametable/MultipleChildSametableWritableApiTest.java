package test.gov.nih.nci.cacoresdk.domain.inheritance.multiplechild.sametable;

import org.apache.log4j.Logger;

import gov.nih.nci.cacoresdk.domain.inheritance.multiplechild.sametable.GovtOrganization;
import gov.nih.nci.cacoresdk.domain.inheritance.multiplechild.sametable.Organization;
import gov.nih.nci.cacoresdk.domain.inheritance.multiplechild.sametable.PvtOrganization;
import test.gov.nih.nci.cacoresdk.SDKWritableApiBaseTest;

import junit.framework.Assert;


public class MultipleChildSametableWritableApiTest extends SDKWritableApiBaseTest{
	private static Logger log = Logger.getLogger(MultipleChildSametableWritableApiTest.class);
	public static String getTestCaseName() {
		return "Multiple Child Same Table Test Case";
	}
	
	public void testSaveMultipleChildSameTableInheritance() {
		log.debug("\n--------testSaveMultipleChildSameTableInheritance()----------\n");
		Organization organization = new Organization();
		organization.setName("organization");

		GovtOrganization govtOrganization = new GovtOrganization();
		govtOrganization.setName("govtOrganization");
		govtOrganization.setAgencyBudget(1000);

		PvtOrganization pvtOrganization = new PvtOrganization();
		pvtOrganization.setName("pvtOrganization");
		pvtOrganization.setCeo("pvtceo");

		save(organization);
		save(govtOrganization);
		save(pvtOrganization);

		Organization resultOrganization = (Organization) getObject(Organization.class, organization.getId());
		GovtOrganization resultGovtOrganization = (GovtOrganization) getObject(GovtOrganization.class, govtOrganization.getId());
		PvtOrganization resultPvtOrganization = (PvtOrganization) getObject(PvtOrganization.class, pvtOrganization.getId());

		Assert.assertEquals(organization.getName(), resultOrganization.getName());
		Assert.assertEquals(govtOrganization.getName(), resultGovtOrganization.getName());
		Assert.assertEquals(pvtOrganization.getName(), resultPvtOrganization.getName());
	}
	
	public void testUpdateMultipleChildSameTableInheritance() {
		log.debug("\n--------testUpdateMultipleChildSameTableInheritance()----------\n");
		Organization organization = new Organization();
		organization.setName("organization");

		GovtOrganization govtOrganization = new GovtOrganization();
		govtOrganization.setName("govtOrganization");
		govtOrganization.setAgencyBudget(1000);

		PvtOrganization pvtOrganization = new PvtOrganization();
		pvtOrganization.setName("pvtOrganization");
		pvtOrganization.setCeo("pvtceo");

		save(organization);
		save(govtOrganization);
		save(pvtOrganization);

		Organization updateOrganization = (Organization) getObject(Organization.class, organization.getId());
		GovtOrganization updateGovtOrganization = (GovtOrganization) getObject(GovtOrganization.class, govtOrganization.getId());
		PvtOrganization updatePvtOrganization = (PvtOrganization) getObject(PvtOrganization.class, pvtOrganization.getId());

		update(updateOrganization);
		update(updateGovtOrganization);
		update(updatePvtOrganization);
		
		Organization resultOrganization = (Organization) getObject(Organization.class, organization.getId());
		GovtOrganization resultGovtOrganization = (GovtOrganization) getObject(GovtOrganization.class, govtOrganization.getId());
		PvtOrganization resultPvtOrganization = (PvtOrganization) getObject(PvtOrganization.class, pvtOrganization.getId());

		Assert.assertEquals(updateOrganization.getName(), resultOrganization.getName());
		Assert.assertEquals(updateGovtOrganization.getName(), resultGovtOrganization.getName());
		Assert.assertEquals(updatePvtOrganization.getName(), resultPvtOrganization.getName());
	}
	
	public void testDeleteMultipleChildSameTableInheritance() {
		log.debug("\n--------testUpdateMultipleChildSameTableInheritance()----------\n");
		Organization organization = new Organization();
		organization.setName("organization");

		GovtOrganization govtOrganization = new GovtOrganization();
		govtOrganization.setName("govtOrganization");
		govtOrganization.setAgencyBudget(1000);

		PvtOrganization pvtOrganization = new PvtOrganization();
		pvtOrganization.setName("pvtOrganization");
		pvtOrganization.setCeo("pvtceo");

		save(organization);
		save(govtOrganization);
		save(pvtOrganization);

		Organization deleteOrganization = (Organization) getObject(Organization.class, organization.getId());
		GovtOrganization deleteGovtOrganization = (GovtOrganization) getObject(GovtOrganization.class, govtOrganization.getId());
		PvtOrganization deletePvtOrganization = (PvtOrganization) getObject(PvtOrganization.class, pvtOrganization.getId());

		delete(deleteOrganization);
		delete(deleteGovtOrganization);
		delete(deletePvtOrganization);
		
		Organization resultOrganization = (Organization) getObject(Organization.class, organization.getId());
		GovtOrganization resultGovtOrganization = (GovtOrganization) getObject(GovtOrganization.class, govtOrganization.getId());
		PvtOrganization resultPvtOrganization = (PvtOrganization) getObject(PvtOrganization.class, pvtOrganization.getId());

		Assert.assertNull(resultOrganization);
		Assert.assertNull(resultGovtOrganization);
		Assert.assertNull(resultPvtOrganization);
	}
}
