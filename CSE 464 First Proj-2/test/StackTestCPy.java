import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StackTestCPy {

    String filePath = "/Users/gabe/IdeaProjects/CSE 464 First Proj/input.dot";

    private GraphParser s;

    @Before
    public void setup(){
        s = new GraphParser();
        s.push(1);
        System.out.println("setup");
    }

    @After
    public void teardown(){
        System.out.println("TEARDOWN");
    }











    @Test
    public void testBFS() {
        System.out.println("this is testBFS");

        //  the graph with nodes and edges
        s.addNode("1");
        s.addNode("2");
        s.addNode("3");
        s.addNode("4");
        s.addEdge("1", "2");
        s.addEdge("2", "3");
        s.addEdge("3", "4");

        // Perform BFS search from node "1" to "4"
        GraphParser.Path result = s.graphSearchBFS("1", "4");

        // Verify the path is as expected
        List<String> expectedPath = Arrays.asList("1", "2", "3", "4");
        assertNotNull(result);
        assertEquals(expectedPath, result.getNodes());
    }



//    @Test
//    public void testParseGraph() throws IOException {
//        System.out.println("this is testParseGraph");
//
//        s.parseGraph(filePath);
//
//        // Assuming you've implemented the 'getNumNodes' and 'getNumEdges' methods
//        assertEquals(4, s.getNumNodes());
//        //assertEquals(4, s.getNumEdges());
//
//        System.out.println(s.toString());
//
//    }
//    @Test
//    public void testAddNode() {
//        System.out.println("this is testAddNode ");
//
//        assertTrue(s.addNode("a"));
//        assertFalse(s.addNode("a")); // Adding a duplicate node should fail
//    }
//    @Test
//    public void testAddNodes() {
//        System.out.println("this is testAddNodes");
//
//        //System.out.println("Before adding nodes: " + s.getNumNodes());
//
//        String[] newNodes = {"b", "c", "d"};
//        s.addNodes(newNodes);
//        //System.out.println("After adding nodes: " + s.getNumNodes());
//
//        assertEquals(newNodes.length, s.getNumNodes()); // After adding 4 nodes (including "a"), there should be 4 nodes.
//    }
//
//    @Test
//    public void testAddEdge() {
//        System.out.println("this is testAddEdge");
//
//        s.addNode("a");
//        s.addNode("b");
//
//        assertTrue(s.addEdge("a", "b"));
//        assertFalse(s.addEdge("a", "b")); // Adding a duplicate edge should fail
//    }
//
//    @Test
//    public void testAddEdges() {
//        System.out.println("this is testAddEdges");
//
//        // Adding nodes
//        s.addNode("a");
//        s.addNode("b");
//        s.addNode("c");
//        s.addNode("d");
//
//        // Adding edges and asserting they were added
//        assertTrue(s.addEdge("a", "b"));
//        assertTrue(s.addEdge("b", "c"));
//        assertTrue(s.addEdge("c", "d"));
//        assertTrue(s.addEdge("d", "a"));
//
//        // Asserting that adding duplicate edges fail
//        assertFalse(s.addEdge("a", "b"));
//        assertFalse(s.addEdge("b", "c"));
//        assertFalse(s.addEdge("c", "d"));
//        assertFalse(s.addEdge("d", "a"));
//    }
//    @Test
//    public void testExportToDOT() throws IOException {
//        System.out.println("this is testExportToDOT");
//
//        // Modify the file path to "modifiedOutput.dot"
//        String outputFilePath = "/Users/gabe/IdeaProjects/CSE 464 First Proj/modifiedOutput.dot";
//        assertTrue(s.outputDOTGraph(outputFilePath));
//
//        // Read the content of the generated output file
//        String actualOutput = Files.readString(Paths.get(outputFilePath));
//
//        // Read the content of the expected output file
//        String expectedOutput = Files.readString(Paths.get("/Users/gabe/IdeaProjects/CSE 464 First Proj/expectedModifiedOutput.txt"));
//
//        // Compare the two outputs
//        Assert.assertEquals(expectedOutput, actualOutput);
//    }
//
//    // Project test 2 project part 2
//    @Test
//    public void testRemoveNode() {
//        System.out.println("this is testRemoveNode");
//
//        s.addNode("x");
//        assertTrue(s.removeNode("x")); // Successfully remove a node
//        assertFalse(s.removeNode("x")); // Attempt to remove a non-existent node
//    }
//
//    @Test
//    public void testRemoveNodes() {
//        System.out.println("this is testRemoveNodes");
//
//        String[] nodesToRemove = {"y", "z"};
//        s.addNodes(nodesToRemove);
//        s.removeNodes(nodesToRemove); // Remove existing nodes
//        assertEquals(0, s.getNumNodes()); // Check if nodes were removed
//
//        s.removeNodes(nodesToRemove); // Attempt to remove non-existent nodes
//        // No assertion needed here since we're just checking that it doesn't crash
//    }
//
//    @Test
//    public void testRemoveEdge() {
//        System.out.println("this is testRemoveEdge");
//
//        s.addNode("a");
//        s.addNode("b");
//        s.addEdge("a", "b");
//
//    assertTrue(s.removeEdge("a", "b")); // Successfully remove an edge
//    assertFalse(s.removeEdge("a", "b")); // Attempt to remove a non-existent edge
//}







}
