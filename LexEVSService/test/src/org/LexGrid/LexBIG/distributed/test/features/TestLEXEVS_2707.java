package org.LexGrid.LexBIG.distributed.test.features;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.Impl.Extensions.tree.model.LexEvsTree;
import org.LexGrid.LexBIG.Impl.Extensions.tree.model.LexEvsTreeNode;
import org.LexGrid.LexBIG.Impl.Extensions.tree.model.LexEvsTreeNode.ExpandableStatus;
import org.LexGrid.LexBIG.Impl.Extensions.tree.service.TreeService;
import org.LexGrid.LexBIG.Impl.Extensions.tree.service.TreeServiceFactory;
import org.LexGrid.LexBIG.Impl.Extensions.tree.utility.OntologyNode;
import org.LexGrid.LexBIG.Impl.Extensions.tree.utility.PrintUtility;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.google.gson.Gson;

public class TestLEXEVS_2707 extends ServiceTestCase{

	@Test
	public void testGetTreeWithAllPathToRootNodes(){
        LexEVSApplicationService lbsi = LexEVSServiceHolder.instance().getFreshLexEVSAppService();
        TreeService service = TreeServiceFactory.getInstance().getTreeService(lbsi);
        LexEvsTree tree = null;
        CodingSchemeVersionOrTag csvt = new CodingSchemeVersionOrTag();
        csvt.setVersion(THES_VERSION);
        tree = service.getTree(THES_SCHEME, csvt, "C1000", "ncit", "is_a");	            
            LexEvsTreeNode focusNode = tree.getCurrentFocus();
            List<LexEvsTreeNode> listForTree = service.getEvsTreeConverter().buildEvsTreePathFromRootTree(focusNode);
            //Testing a requirement to return all values when the value is -1
            String json = service.getJsonConverter(-1).buildJsonPathFromRootTree(focusNode);	        
            List <LexEvsTreeNode> listOfone = new ArrayList<LexEvsTreeNode>();
            listOfone.add(focusNode);
            System.out.println("Printing from focus Node");
            PrintUtility.print(focusNode);
            System.out.println("Printing tree");
            PrintUtility.print(listForTree);
            System.out.println("Printing Json");
            System.out.println(json);
            Gson gson = new Gson();
            OntologyNode[] nodes = gson.fromJson(json, OntologyNode[].class);
            assertTrue(nodes.length > 0);
            assertTrue(Arrays.stream(nodes).anyMatch(x -> x.getOntology_node_name().
            		equals("Manufactured Object")));
            assertEquals(nodes.length, 19);

      
	}
	
	@Test
	public void testGetTreeWithFiveOrLessDefaultChildren(){
        LexEVSApplicationService lbsi = LexEVSServiceHolder.instance().getFreshLexEVSAppService();
        TreeService service = TreeServiceFactory.getInstance().getTreeService(lbsi);
        LexEvsTree tree = null;
        CodingSchemeVersionOrTag csvt = new CodingSchemeVersionOrTag();
        csvt.setVersion(THES_VERSION);
        tree = service.getTree(THES_SCHEME, csvt, "C1000", "ncit", "is_a");	            
            LexEvsTreeNode focusNode = tree.getCurrentFocus();
            List<LexEvsTreeNode> listForTree = service.getEvsTreeConverter().buildEvsTreePathFromRootTree(focusNode);
            String json = service.getJsonConverter().buildJsonPathFromRootTree(focusNode);	        ;
            System.out.println("Printing from focus Node");
            PrintUtility.print(focusNode);
            System.out.println("Printing tree");
            PrintUtility.print(listForTree);
            System.out.println("Printing Json");
            System.out.println(json);
            Gson gson = new Gson();
            OntologyNode[] nodes = gson.fromJson(json, OntologyNode[].class);
            assertTrue(nodes.length > 0);
            //Drilling down to the "process" node which could have more than the default 5 children
            OntologyNode myocardNode = Arrays.asList(nodes).stream().filter(x -> x.getOntology_node_name().equals("Drug, Food, Chemical or Biomedical Material")).findFirst().get();
            assertTrue(myocardNode.getChildren_nodes() != null);
            assertTrue(myocardNode.getChildren_nodes().size() > 0);
            assertEquals(myocardNode.getChildren_nodes().size(), 6);
          
	}
	
	@Test
	public void testGetTreeWithFiveOrLessEnforcedPathToRootNodes(){
        LexEVSApplicationService lbsi = LexEVSServiceHolder.instance().getFreshLexEVSAppService();
        TreeService service = TreeServiceFactory.getInstance().getTreeService(lbsi);
        LexEvsTree tree = null;
        CodingSchemeVersionOrTag csvt = new CodingSchemeVersionOrTag();
        csvt.setVersion(THES_VERSION);
        tree = service.getTree(THES_SCHEME, csvt, "C34831", "ncit", "is_a");	            
            LexEvsTreeNode focusNode = tree.getCurrentFocus();
            List<LexEvsTreeNode> listForTree = service.getEvsTreeConverter().buildEvsTreePathFromRootTree(focusNode);
            String json = service.getJsonConverter(3).buildJsonPathFromRootTree(focusNode);	        ;
            System.out.println("Printing from focus Node");
            PrintUtility.print(focusNode);
            System.out.println("Printing tree");
            PrintUtility.print(listForTree);
            System.out.println("Printing Json");
            System.out.println(json);
            Gson gson = new Gson();
            OntologyNode[] nodes = gson.fromJson(json, OntologyNode[].class);
            assertTrue(nodes.length > 0);
            assertEquals(nodes.length, 4);
            assertFalse(Arrays.stream(nodes).anyMatch(x -> x.getOntology_node_name().equals("Acute Myocarditis")));
          
	}
	@Test
	public void testGetTreeWithLessThanFiveChildren() throws JSONException{
        LexEVSApplicationService lbsi = LexEVSServiceHolder.instance().getFreshLexEVSAppService();
        TreeService service = TreeServiceFactory.getInstance().getTreeService(lbsi);
        LexEvsTree tree = null;
        CodingSchemeVersionOrTag csvt = new CodingSchemeVersionOrTag();
        csvt.setVersion(THES_VERSION);
        tree = service.getTree(THES_SCHEME, csvt, "C100021", "ncit", "is_a");	            
            LexEvsTreeNode focusNode = tree.getCurrentFocus();
            String json = service.getJsonConverter(5).buildChildrenNodes(focusNode);
            Iterator<LexEvsTreeNode> itr = focusNode.getChildIterator();
            @SuppressWarnings("unchecked")
			List<LexEvsTreeNode> treenodes = (List<LexEvsTreeNode>) StreamSupport.stream(
            Spliterators.spliteratorUnknownSize(itr, Spliterator.ORDERED),
            false).collect(Collectors.toList());
            assertTrue(treenodes.size() > 0);
            assertEquals(focusNode.getExpandableStatus(), ExpandableStatus.IS_EXPANDABLE);
            assertEquals(treenodes.size(), 4);
            System.out.println("Printing Json");
            System.out.println(json);
            System.out.println("Printing ChildNodes");
            PrintUtility.print(treenodes);
            JSONObject jo = new JSONObject(json);
			JSONArray ja = jo.getJSONArray("nodes");
			//no false nodes are added at this length -- validates as less than 5
			assertEquals(ja.length(), 4);
			//make sure the last one in the list is a regular node
			assertTrue(ja.getJSONObject(3).getString("ontology_node_name").equals("Thrombolysis in Myocardial Infarction Flow-3"));
 
	}
	
	@Test
	public void testGetTreeWithJustMoreThanFiveOnPathToRoot(){
        LexEVSApplicationService lbsi = LexEVSServiceHolder.instance().getFreshLexEVSAppService();
        TreeService service = TreeServiceFactory.getInstance().getTreeService(lbsi);
        LexEvsTree tree = null;
        CodingSchemeVersionOrTag csvt = new CodingSchemeVersionOrTag();
        csvt.setVersion(THES_VERSION);
        tree = service.getTree(THES_SCHEME, csvt, "C1000", "ncit", "is_a");	            
            LexEvsTreeNode focusNode = tree.getCurrentFocus();
            String json = service.getJsonConverter(6).buildJsonPathFromRootTree(focusNode);	        
            Gson gson = new Gson();
            OntologyNode[] nodes = gson.fromJson(json, OntologyNode[].class);
            assertTrue(nodes.length > 0);

            assertTrue(Arrays.stream(nodes).anyMatch(x -> x.getOntology_node_name().equals("Abnormal Cell")));
            // would not have been seen with default converter settings
            assertTrue(Arrays.stream(nodes).anyMatch(x -> x.getOntology_node_name().equals("Gene")));
	}
	
}
