package org.LexGrid.LexBIG.distributed.test.features;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.Impl.Extensions.tree.model.LexEvsTree;
import org.LexGrid.LexBIG.Impl.Extensions.tree.model.LexEvsTreeNode;
import org.LexGrid.LexBIG.Impl.Extensions.tree.service.TreeService;
import org.LexGrid.LexBIG.Impl.Extensions.tree.service.TreeServiceFactory;
import org.LexGrid.LexBIG.Impl.Extensions.tree.utility.PrintUtility;
import org.LexGrid.LexBIG.caCore.interfaces.LexEVSApplicationService;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.LexGrid.LexBIG.testUtil.ServiceTestCase;
import org.junit.Test;

import com.google.gson.Gson;

public class TestLEXEVS_2707 extends ServiceTestCase{

	@Test
	public void testGetTreeWithMoreThanFiveChildren(){
        LexEVSApplicationService lbsi = LexEVSServiceHolder.instance().getFreshLexEVSAppService();
        TreeService service = TreeServiceFactory.getInstance().getTreeService(lbsi);
        LexEvsTree tree = null;
        CodingSchemeVersionOrTag csvt = new CodingSchemeVersionOrTag();
        csvt.setVersion(THES_VERSION);
        tree = service.getTree(THES_SCHEME, csvt, "C35741", "ncit", "is_a");	            
            LexEvsTreeNode focusNode = tree.getCurrentFocus();
            List<LexEvsTreeNode> listForTree = service.getEvsTreeConverter().buildEvsTreePathFromRootTree(focusNode);
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
            //Drilling down to the "process" node which has more than the default 5 children
            OntologyNode processNode = Arrays.asList(nodes).stream().filter(x -> x.getOntology_node_name().
            		equals("Myocardial Disorder")).findFirst().get();
            //child count just indicates there is or is not children
            assertTrue(processNode.getOntology_node_child_count() > 0);
            assertTrue(processNode.getOntology_node_child_count() == 1);
            //checking actual size
            assertTrue(processNode.getChildren_nodes().size() > 0);
            assertTrue(processNode.getChildren_nodes().size() == 16);
            //this wouldn't be seen using default children sizing
            assertTrue(processNode.getChildren_nodes().stream().anyMatch(x -> x.getOntology_node_name().equals("Cor Pulmonale")));
      
	}
	
	@Test
	public void testGetTreeWithFiveOrLessDefaultChildren(){
        LexEVSApplicationService lbsi = LexEVSServiceHolder.instance().getFreshLexEVSAppService();
        TreeService service = TreeServiceFactory.getInstance().getTreeService(lbsi);
        LexEvsTree tree = null;
        CodingSchemeVersionOrTag csvt = new CodingSchemeVersionOrTag();
        csvt.setVersion(THES_VERSION);
        tree = service.getTree(THES_SCHEME, csvt, "C34831", "ncit", "is_a");	            
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
            OntologyNode myocardNode = Arrays.asList(nodes).stream().filter(x -> x.getOntology_node_name().equals("Acute Myocarditis")).findFirst().get();
            assertTrue(myocardNode.getChildren_nodes() != null);
            assertTrue(myocardNode.getChildren_nodes().size() > 0);
            assertEquals(myocardNode.getChildren_nodes().size(), 5);
          
	}
	
	@Test
	public void testGetTreeWithFiveOrLessEnforcedChildren(){
        LexEVSApplicationService lbsi = LexEVSServiceHolder.instance().getFreshLexEVSAppService();
        TreeService service = TreeServiceFactory.getInstance().getTreeService(lbsi);
        LexEvsTree tree = null;
        CodingSchemeVersionOrTag csvt = new CodingSchemeVersionOrTag();
        csvt.setVersion(THES_VERSION);
        tree = service.getTree(THES_SCHEME, csvt, "C34831", "ncit", "is_a");	            
            LexEvsTreeNode focusNode = tree.getCurrentFocus();
            List<LexEvsTreeNode> listForTree = service.getEvsTreeConverter().buildEvsTreePathFromRootTree(focusNode);
            String json = service.getJsonConverter(4).buildJsonPathFromRootTree(focusNode);	        ;
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
            OntologyNode myocardNode = Arrays.asList(nodes).stream().filter(x -> x.getOntology_node_name().equals("Acute Myocarditis")).findFirst().get();
            assertTrue(myocardNode.getChildren_nodes() != null);
            assertTrue(myocardNode.getChildren_nodes().size() > 0);
            assertEquals(myocardNode.getChildren_nodes().size(), 5);
          
	}
	@Test
	public void testGetTreeWithLessThanFiveChildren(){
        LexEVSApplicationService lbsi = LexEVSServiceHolder.instance().getFreshLexEVSAppService();
        TreeService service = TreeServiceFactory.getInstance().getTreeService(lbsi);
        LexEvsTree tree = null;
        CodingSchemeVersionOrTag csvt = new CodingSchemeVersionOrTag();
        csvt.setVersion(THES_VERSION);
        tree = service.getTree(THES_SCHEME, csvt, "C35741", "ncit", "is_a");	            
            LexEvsTreeNode focusNode = tree.getCurrentFocus();
            List<LexEvsTreeNode> listForTree = service.getEvsTreeConverter().buildEvsTreePathFromRootTree(focusNode);
            String json = service.getJsonConverter(5).buildJsonPathFromRootTree(focusNode);	        
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
            //Drilling down to the "process" node which has more than the default 5 children
            OntologyNode processNode = Arrays.asList(nodes).stream().filter(x -> x.getOntology_node_name().
            		equals("Myocardial Disorder")).findFirst().get();
            //child count just indicates there is or is not children
            assertTrue(processNode.getOntology_node_child_count() > 0);
            assertTrue(processNode.getOntology_node_child_count() == 1);
            //checking actual size
            assertTrue(processNode.getChildren_nodes().size() > 0);
            assertTrue(processNode.getChildren_nodes().size() == 6);
            //this wouldn't be seen using default children sizing
            assertTrue(processNode.getChildren_nodes().stream().anyMatch(x -> x.getOntology_node_name().equals("Cor Pulmonale")));
      
	}
	
	@Test
	public void testGetTreeWithJustMoreThanFiveChildren(){
        LexEVSApplicationService lbsi = LexEVSServiceHolder.instance().getFreshLexEVSAppService();
        TreeService service = TreeServiceFactory.getInstance().getTreeService(lbsi);
        LexEvsTree tree = null;
        CodingSchemeVersionOrTag csvt = new CodingSchemeVersionOrTag();
        csvt.setVersion(THES_VERSION);
        tree = service.getTree(THES_SCHEME, csvt, "NPO_1607", "npo", "is_a");	            
            LexEvsTreeNode focusNode = tree.getCurrentFocus();
            String json = service.getJsonConverter(6).buildJsonPathFromRootTree(focusNode);	        
            Gson gson = new Gson();
            OntologyNode[] nodes = gson.fromJson(json, OntologyNode[].class);
            assertTrue(nodes.length > 0);
            //Drilling down to the "process" node which has more than the default 5 children
            OntologyNode processNode = Arrays.asList(nodes).stream().filter(x -> x.getOntology_node_name().equals("entity")).findFirst().get()
            .getChildren_nodes().stream().filter(x -> x.getOntology_node_name().equals("occurrent")).findFirst().get()
            .getChildren_nodes().stream().filter(x -> x.getOntology_node_name().equals("processual_entity")).findFirst().get()
            .getChildren_nodes().stream().filter(x -> x.getOntology_node_name().equals("process")).findFirst().get();
            assertNotNull(processNode);
            assertEquals(processNode.getOntology_node_name(), "process");
            //child count just indicates there is or is not children
            assertTrue(processNode.getOntology_node_child_count() > 0);
            assertTrue(processNode.getOntology_node_child_count() == 1);
            //checking actual size
            assertTrue(processNode.getChildren_nodes().size() > 0);
            assertTrue(processNode.getChildren_nodes().size() == 7);
            //this wouldn't be seen using default children sizing
            assertTrue(processNode.getChildren_nodes().stream().anyMatch(x -> x.getOntology_node_name().equals("biological process")));
            
            OntologyNode emulsificationNode = Arrays.asList(nodes).stream().filter(x -> x.getOntology_node_name().equals("entity")).findFirst().get()
                    .getChildren_nodes().stream().filter(x -> x.getOntology_node_name().equals("occurrent")).findFirst().get()
                    .getChildren_nodes().stream().filter(x -> x.getOntology_node_name().equals("processual_entity")).findFirst().get()
                    .getChildren_nodes().stream().filter(x -> x.getOntology_node_name().equals("process")).findFirst().get()
                     .getChildren_nodes().stream().filter(x -> x.getOntology_node_name().equals("emulsification")).findFirst().get();
            assertNotNull(emulsificationNode);
            assertEquals(emulsificationNode.getOntology_node_name(), "emulsification");
            //child count just indicates there is or is not children
            assertTrue(emulsificationNode.getOntology_node_child_count() == 0);
            assertTrue(emulsificationNode.getOntology_node_child_count() < 1);
            //checking actual size
            assertTrue(emulsificationNode.getChildren_nodes().size() == 0);
            
            OntologyNode occurrentNode = Arrays.asList(nodes).stream().filter(x -> x.getOntology_node_name().equals("entity")).findFirst().get()
            .getChildren_nodes().stream().filter(x -> x.getOntology_node_name().equals("occurrent")).findFirst().get();
            assertNotNull(occurrentNode);
            assertEquals(occurrentNode.getOntology_node_name(), "occurrent");
            //child count just indicates there is or is not children
            assertTrue(occurrentNode.getOntology_node_child_count() > 0);
            assertTrue(occurrentNode.getOntology_node_child_count() == 1);
            //checking actual size
            assertTrue(occurrentNode.getChildren_nodes().size() == 3);
	}
	
}
