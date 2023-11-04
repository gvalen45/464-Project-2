import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class StackTest {

    private GraphParser graphParser;

    @Before
    public void setup() {
        graphParser = new GraphParser();
        //  a graph with nodes and edges
        graphParser.addNode("A");
        graphParser.addNode("B");
        graphParser.addNode("C");
        graphParser.addEdge("A", "B");
        graphParser.addEdge("B", "C");
        System.out.println("Setup complete ");
    }

    @Test
    public void testRemoveNodeSuccessfully() {
        System.out.println("TEST: removal of a node that exists.");
        int initialNodeCount = graphParser.getNumNodes();
        assertTrue(graphParser.removeNode("B"));
        assertEquals(initialNodeCount - 1, graphParser.getNumNodes());
        System.out.println("[x]Node B removed Correctly.\n");
    }

    @Test
    public void testRemoveNodesSuccessfully() {
        System.out.println("TEST: testRemoveNodesSuccessfully \n removal of multiple nodes that exist.");
        int initialNodeCount = graphParser.getNumNodes();
        graphParser.removeNodes(new String[]{"A", "C"});
        assertEquals(initialNodeCount - 2, graphParser.getNumNodes());
        System.out.println("[x]Nodes A and C removed successfully.\n");
    }

    @Test
    public void testRemoveEdgeSuccessfully() {
        System.out.println("TEST: testRemoveEdgeSuccessfully \n removal of an edge that exists.");
        int initialEdgeCount = graphParser.getNumEdges();
        assertTrue(graphParser.removeEdge("A", "B"));
        assertEquals(initialEdgeCount - 1, graphParser.getNumEdges());
        System.out.println("Edge A->B removed successfully.\n");
    }

    @Test
    public void testRemoveNonExistentNode() {
        System.out.println("TEST: testRemoveNonExistentNode \n removal of a node that DNE.");
        int initialNodeCount = graphParser.getNumNodes();
        assertFalse(graphParser.removeNode("Z"));
        assertEquals(initialNodeCount, graphParser.getNumNodes());
        System.out.println("[x]Correctly identified that node Z DNE.\n");
    }

    @Test
    public void testRemoveNonExistentNodes() {
        System.out.println("TEST: testRemoveNonExistentNodes \n removal of multiple nodes that DNE");
        int initialNodeCount = graphParser.getNumNodes();
        graphParser.removeNodes(new String[]{"X", "Y"});
        assertEquals(initialNodeCount, graphParser.getNumNodes());
        System.out.println("[x]Correctly identified that nodes X and Y DNE.\n");
    }

    @Test
    public void testRemoveNonExistentEdge() {
        System.out.println("TEST: testRemoveNonExistentEdge \n  removal of an edge is DNE");
        int initialEdgeCount = graphParser.getNumEdges();
        assertFalse(graphParser.removeEdge("A", "Z"));
        assertEquals(initialEdgeCount, graphParser.getNumEdges());
        System.out.println("[x]Correctly identified that edge A->Z DNE.\n");
    }

    @Test
    public void testBFS() {
        System.out.println("TEST: testBFS \n  Perform BFS search from node \"1\" to \"4\"");

        //  the graph with nodes and edges
        graphParser.addNode("1");
        graphParser.addNode("2");
        graphParser.addNode("3");
        graphParser.addNode("4");
        graphParser.addEdge("1", "2");
        graphParser.addEdge("2", "3");
        graphParser.addEdge("3", "4");

        // Perform BFS search from node "1" to "4"
        GraphParser.Path result = graphParser.graphSearchBFS("1", "4");

        // Verify the path is as expected
        List<String> expectedPath = Arrays.asList("1", "2", "3", "4");
        assertNotNull(result);
        assertEquals(expectedPath, result.getNodes());
        System.out.println();
    }
}
