package org.LexGrid.LexBIG.testUtil;

import org.LexGrid.LexBIG.Impl.testUtility.LexBIGServiceTestFactory;
import org.LexGrid.LexBIG.distributed.test.bugs.TestLEXEVS_3465;
import org.LexGrid.LexBIG.distributed.test.bugs.TestLEXEVS_3994;
import org.LexGrid.LexBIG.distributed.test.bugs.TestLEXEVS_4178;
import org.LexGrid.LexBIG.distributed.test.bugs.TestLEXEVS_4244;
import org.LexGrid.LexBIG.distributed.test.bugs.TestLEXEVS_4309;
import org.LexGrid.LexBIG.distributed.test.bugs.TestLEXEVS_4376;
import org.LexGrid.LexBIG.distributed.test.bugs.TestLEXEVS_4508;
import org.LexGrid.LexBIG.distributed.test.bugs.TestLEXEVS_4535;
import org.LexGrid.LexBIG.distributed.test.bugs.TestLEXEVS_4570;
import org.LexGrid.LexBIG.distributed.test.features.TestLEXEVS_3628;
import org.LexGrid.LexBIG.distributed.test.features.TestLEXEVS_3732;
import org.LexGrid.LexBIG.distributed.test.features.TestLEXEVS_3947;
import org.LexGrid.LexBIG.distributed.test.features.TestLEXEVS_4093;
import org.LexGrid.LexBIG.distributed.test.features.TestLEXEVS_4115;
import org.LexGrid.LexBIG.distributed.test.features.TestLEXEVS_4143;
import org.LexGrid.LexBIG.distributed.test.features.TestLEXEVS_4240;
import org.LexGrid.LexBIG.distributed.test.features.TestLEXEVS_4345;
import org.LexGrid.LexBIG.distributed.test.features.TestLEXEVS_4349;
import org.LexGrid.LexBIG.distributed.test.features.TestLEXEVS_4444;
import org.LexGrid.LexBIG.distributed.test.features.TestLEXEVS_4552;
import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	TestLEXEVS_3465.class,
	TestLEXEVS_3994.class,
	TestLEXEVS_4178.class,
	TestLEXEVS_4244.class,
	TestLEXEVS_4309.class,
	TestLEXEVS_4376.class,
	TestLEXEVS_4508.class,
	TestLEXEVS_4535.class,
	TestLEXEVS_4570.class,
	TestLEXEVS_3628.class,
	TestLEXEVS_3732.class,
	TestLEXEVS_3947.class,
	TestLEXEVS_4093.class,
	TestLEXEVS_4115.class,
	TestLEXEVS_4143.class,
	TestLEXEVS_4240.class,
	TestLEXEVS_4345.class,
	TestLEXEVS_4349.class,
	TestLEXEVS_4444.class,
	TestLEXEVS_4552.class
	})
public class AllLexEVS652Tests{

	@ClassRule
    public static ExternalResource testRule = new ExternalResource(){
            @Override
            protected void before() throws Throwable{
         	   System.setProperty(LexBIGServiceTestFactory.LBS_TEST_FACTORY_ENV, RemoteLexBIGServiceTestFactory.class.getName());
            };

            @Override
            protected void after(){

            };
        };

}
