package org.LexGrid.LexBIG.caCore.connection;

import java.util.Arrays;

import org.LexGrid.LexBIG.Impl.helpers.SQLConnectionInfo;
import org.LexGrid.LexBIG.caCore.connection.DAOListFactory;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;

public class DAOListFactoryTest extends ServiceTestCase {

	private DAOListFactory factory;
	
	public void setUp(){
		factory = new DAOListFactory();
	}
	
	public void testCanUseSingleDatasource(){
		SQLConnectionInfo con1 = new SQLConnectionInfo();
		con1.server = "dbUrl";
		
		SQLConnectionInfo con2 = new SQLConnectionInfo();
		con2.server = "dbUrl";
		
		assertTrue(
				factory.canUseSingleDatasource(Arrays.asList(new SQLConnectionInfo[]{con1, con2}))
				);
	}
	
	public void testCanUseSingleDatasourceDifferentUrls(){
		SQLConnectionInfo con1 = new SQLConnectionInfo();
		con1.server = "dbUrl1";
		
		SQLConnectionInfo con2 = new SQLConnectionInfo();
		con2.server = "dbUrl2";
		
		assertFalse(
				factory.canUseSingleDatasource(Arrays.asList(new SQLConnectionInfo[]{con1, con2}))
				);
	}
	
	public void testCanUseSingleDatasourceWithDbName(){
		SQLConnectionInfo con1 = new SQLConnectionInfo();
		con1.server = "dbUrl";
		con1.dbName = "dbName1";
		
		SQLConnectionInfo con2 = new SQLConnectionInfo();
		con2.server = "dbUrl";
		con2.dbName = "dbName2";
		
		assertFalse(
				factory.canUseSingleDatasource(Arrays.asList(new SQLConnectionInfo[]{con1, con2}))
				);
	}
}
