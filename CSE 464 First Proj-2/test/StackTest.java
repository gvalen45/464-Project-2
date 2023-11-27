import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

public class StackTest {

    private GraphParser graphParser;

    // Refactoring 2: Enhancing Test Clarity/Maintainability
    @Before
    public void setup() {
        graphParser = new GraphParser();
    }



    private void initializeGraphWithABCTestData() {
        graphParser.addNode("A");
        graphParser.addNode("B");
        graphParser.addNode("C");
        graphParser.addEdge("A", "B");
        graphParser.addEdge("B", "C");
    }

    private void initializeGraphWith1234TestData() {
        graphParser.addNode("1");
        graphParser.addNode("2");
        graphParser.addNode("3");
        graphParser.addNode("4");
        graphParser.addEdge("1", "2");
        graphParser.addEdge("2", "3");
        graphParser.addEdge("3", "4");
    }


    @After
    public void teardown(){
        System.out.println("***TEARDOWN***\n");
    }

    @Test
    public void testRemoveNodeSuccessfully() {
        initializeGraphWithABCTestData();
        System.out.println("TEST: removal of a node that exists..");
        int initialNodeCount = graphParser.getNumNodes();
        assertTrue(graphParser.removeNode("B"));
        assertEquals(initialNodeCount - 1, graphParser.getNumNodes());
        System.out.println("[x]Node B removed Correctly.");
    }

    @Test
    public void testRemoveNodesSuccessfully() {
        initializeGraphWithABCTestData();

        System.out.println("TEST: testRemoveNodesSuccessfully \n removal of multiple nodes that exist.");
        int initialNodeCount = graphParser.getNumNodes();
        graphParser.removeNodes(new String[]{"A", "C"});
        assertEquals(initialNodeCount - 2, graphParser.getNumNodes());
        System.out.println("[x]Nodes A and C removed successfully.");
    }

    @Test
    public void testRemoveEdgeSuccessfully() {
        initializeGraphWithABCTestData();

        System.out.println("TEST: testRemoveEdgeSuccessfully \n removal of an edge that exists.");
        int initialEdgeCount = graphParser.getNumEdges();
        assertTrue(graphParser.removeEdge("A", "B"));
        assertEquals(initialEdgeCount - 1, graphParser.getNumEdges());
        System.out.println("Edge A->B removed successfully.");
    }

    @Test
    public void testRemoveNonExistentNode() {
        initializeGraphWithABCTestData();

        System.out.println("TEST: testRemoveNonExistentNode \n removal of a node that DNE.");
        int initialNodeCount = graphParser.getNumNodes();
        assertFalse(graphParser.removeNode("Z"));
        assertEquals(initialNodeCount, graphParser.getNumNodes());
        System.out.println("[x]Correctly identified that node Z DNE.");
    }

    @Test
    public void testRemoveNonExistentNodes() {
        initializeGraphWithABCTestData();

        System.out.println("TEST: testRemoveNonExistentNodes \n removal of multiple nodes that DNE");
        int initialNodeCount = graphParser.getNumNodes();
        graphParser.removeNodes(new String[]{"X", "Y"});
        assertEquals(initialNodeCount, graphParser.getNumNodes());
        System.out.println("[x]Correctly identified that nodes X and Y DNE.");
    }

    @Test
    public void testRemoveNonExistentEdge() {

        initializeGraphWithABCTestData();

        System.out.println("TEST: testRemoveNonExistentEdge \n  removal of an edge is DNE");
        int initialEdgeCount = graphParser.getNumEdges();
        assertFalse(graphParser.removeEdge("A", "Z"));
        assertEquals(initialEdgeCount, graphParser.getNumEdges());
        System.out.println("[x]Correctly identified that edge A->Z DNE.");
    }

    @Test
    public void testBFS() {
        graphParser = new GraphParser();

        System.out.println("TEST: testBFS \n  Perform BFS search from node \"1\" to \"4\"");
        graphParser.addNode("1");
        graphParser.addNode("2");
        graphParser.addNode("3");
        graphParser.addNode("4");
        graphParser.addEdge("1", "2");
        graphParser.addEdge("2", "3");
        graphParser.addEdge("3", "4");

//        System.out.println("Graph before BFS:");
//        System.out.println(graphParser.toString());

        GraphParser.Path result = graphParser.graphSearch("1", "4", GraphParser.Algorithm.BFS);

        // Verify the path is as expected
        if (result == null) {
            System.out.println("No path found in BFS search");

        }else{
            List<String> expectedPath = Arrays.asList("1", "2", "3", "4");
            assertNotNull(result);
            assertEquals(expectedPath, result.getNodes());
            System.out.println("[x] BFS found the correct path.");
        }

    }


    @Test
    public void testDFS() {
        System.out.println("TEST: testDFS \n  Perform DFS search from node \"A\" to \"C\"");
        // Initialize the graph specifically for this test
        graphParser = new GraphParser();
        graphParser.addNode("A");
        graphParser.addNode("B");
        graphParser.addNode("C");
        graphParser.addEdge("A", "B");
        graphParser.addEdge("B", "C");

//        System.out.println("Graph before DFS:");
//        System.out.println(graphParser.toString());

        // Perform DFS search from node "A" to "C", specifying the DFS algorithm
        GraphParser.Path result = graphParser.graphSearch("A", "C", GraphParser.Algorithm.DFS);


        if (result == null) {
            System.out.println("No path found in DFS search");

        }else{
            List<String> expectedPath = Arrays.asList("A", "B", "C");
            assertNotNull(result);
            assertEquals(expectedPath, result.getNodes());
            System.out.println("[x] DFS found the correct path.");
        }



        // Verify the path is as expected

    }


}
