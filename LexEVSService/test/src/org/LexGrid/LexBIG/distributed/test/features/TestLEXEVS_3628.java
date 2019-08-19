package org.LexGrid.LexBIG.distributed.test.features;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.LexGrid.valueSets.ValueSetDefinition;
import org.junit.Before;
import org.junit.Test;

public class TestLEXEVS_3628 extends ServiceTestCase {
	LexEVSApplicationService svc;
	Map<String, ValueSetDefinition> map;
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		svc = LexEVSServiceHolder.instance().getLexEVSAppService();
		map = svc.getLexEVSValueSetDefinitionServices().getURIToValueSetDefinitionsMap();
			
	}

	@Test
	public void test() {
		
		assertEquals(map.values().size(), 1094);
		Iterator<Entry<String, ValueSetDefinition>> itr = map.entrySet().iterator();
		assertTrue(itr.hasNext());
		Entry<String, ValueSetDefinition> entry = (Entry<String, ValueSetDefinition>)itr.next();
		assertEquals(entry.getKey(), entry.getValue().getValueSetDefinitionURI());
		assertNotNull(entry.getValue().getMappings().getSupportedCodingScheme(0));
		assertNotNull(entry.getValue().getMappings().getSupportedCodingScheme(0).getLocalId());
		assertNotNull(entry.getValue().getMappings().getSupportedCodingScheme(0).getContent());
		assertNotNull(entry.getValue().getMappings().getSupportedNamespace(0));
		assertNotNull(entry.getValue().getMappings().getSupportedNamespace(0).getLocalId());
		assertNotNull(entry.getValue().getMappings().getSupportedNamespace(0).getContent());
		assertNotNull(entry.getValue().getMappings().getSupportedSource(0));
		assertNotNull(entry.getValue().getMappings().getSupportedSource(0).getLocalId());
		assertNotNull(entry.getValue().getMappings().getSupportedSource(0).getContent());
	}

}
