package test.gov.nih.nci.cacoresdk.domain.other.primarykey;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import junit.framework.Assert;
import gov.nih.nci.cacoresdk.domain.other.primarykey.NoIdKey;

import test.gov.nih.nci.cacoresdk.SDKWritableApiBaseTest;

public class NoIdKeyWritableApiTest extends SDKWritableApiBaseTest {
	private static Logger log = LogManager.getLogger(NoIdKeyWritableApiTest.class);
	public static String getTestCaseName() {
		return "NoId Pk WritableApi Test Case";
	}
	
	public void testSaveNoIdkeyWithHiloGenerator() {
		log.debug("----saveNoIdkey() -----");
		NoIdKey idKey = new NoIdKey();
		idKey.setName("pkGeneratorTest");
		
		save(idKey);
		
		NoIdKey resultNoIdKey=(NoIdKey)getObject(NoIdKey.class, idKey.getMykey());
		Assert.assertEquals(idKey.getName(), resultNoIdKey.getName());
	}
}