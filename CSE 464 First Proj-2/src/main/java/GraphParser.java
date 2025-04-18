import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.nio.dot.DOTImporter;
import java.util.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
//test commit Branch Test bfs

public class GraphParser {
    //Refactoring 3: Encapsulation and Data Hiding
    //private final List<Integer> container = new ArrayList<>();
    private final List<Integer> container = new ArrayList<>();

    public List<Integer> getContainer() {
        return Collections.unmodifiableList(container);
    }

    private final Graph<String, DefaultEdge> graph;

    public GraphParser() {
        this.graph = new DefaultDirectedGraph<>(DefaultEdge.class);
    }
    //test commit

    public void push(int i) {
        container.add(i);
    }

    public int size() {
        return container.size();
    }


    //project part 3  Strategy Interface for BFS and DFS
    public interface GraphSearchStrategy {
        GraphParser.Path search(String srcLabel, String dstLabel);
    }




    public abstract class GraphSearchTemplate {
        int var = 1;
        protected Graph<String, DefaultEdge> graph;
        protected String srcLabel, dstLabel;
        protected Map<String, String> prev;
        protected Set<String> visited;

        public GraphSearchTemplate(Graph<String, DefaultEdge> graph) {
            this.graph = graph;
        }

        protected abstract void initializeSearch(String srcLabel, String dstLabel);

        protected abstract String getNextNode();

        protected abstract boolean hasNextNode();

        protected abstract boolean isDestinationReached(String node);

        public GraphParser.Path search(String srcLabel, String dstLabel) {
            initializeSearch(srcLabel, dstLabel);
            while (hasNextNode()) {
                String current = getNextNode();
                if (isDestinationReached(current)) {
                    return reconstructPath(prev, dstLabel);
                }
            }
            return null;
        }

        protected GraphParser.Path reconstructPath(Map<String, String> prev, String dstLabel) {
            LinkedList<String> path = new LinkedList<>();
            for (String at = dstLabel; at != null; at = prev.get(at)) {
                path.addFirst(at);
            }
            return new GraphParser.Path(path);
        }
    }

//Implement the Strategy Interface in BFS and DFS Classes implements  GraphSearchStrategy
    public class BFS extends GraphSearchTemplate implements GraphSearchStrategy {
        private Queue<String> queue;

        public BFS(Graph<String, DefaultEdge> graph) {
            super(graph);
        }

        @Override
        protected void initializeSearch(String srcLabel, String dstLabel) {
            this.srcLabel = srcLabel;
            this.dstLabel = dstLabel;
            prev = new HashMap<>();
            visited = new HashSet<>();
            queue = new LinkedList<>();
            queue.add(srcLabel);
            visited.add(srcLabel);
            prev.put(srcLabel, null); // Source node has no predecessor
        }

        @Override
        protected String getNextNode() {
            return queue.poll();
        }

        @Override
        protected boolean hasNextNode() {
            return !queue.isEmpty();
        }
        public Path search(String srcLabel, String dstLabel) {
            initializeSearch(srcLabel, dstLabel);
            while (hasNextNode()) {
                String current = getNextNode();
                if (isDestinationReached(current)) {
                    return reconstructPath(prev, dstLabel);
                }
                addNeighborsToQueue(current);
            }
            return null;
        }

        @Override
        protected boolean isDestinationReached(String node) {
            return node.equals(dstLabel);
        }

        // Additional method to add neighbors to the queue
        protected void addNeighborsToQueue(String current) {
            for (DefaultEdge edge : graph.outgoingEdgesOf(current)) {
                String neighbor = graph.getEdgeTarget(edge);
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                    prev.put(neighbor, current);
                }
            }
        }
    }


//Implement the Strategy Interface in BFS and DFS Classes implements  GraphSearchStrategy
    public class DFS extends GraphSearchTemplate implements GraphSearchStrategy{
        private Stack<String> stack;

        public DFS(Graph<String, DefaultEdge> graph) {
            super(graph);
        }

        @Override
        protected void initializeSearch(String srcLabel, String dstLabel) {
            this.srcLabel = srcLabel;
            this.dstLabel = dstLabel;
            prev = new HashMap<>();
            visited = new HashSet<>();
            stack = new Stack<>();
            stack.push(srcLabel);
            visited.add(srcLabel);
            prev.put(srcLabel, null);
        }

        @Override
        protected String getNextNode() {
            return stack.pop();
        }

        @Override
        protected boolean hasNextNode() {
            return !stack.isEmpty();
        }

        @Override
        protected boolean isDestinationReached(String node) {
            return node.equals(dstLabel);
        }

        public Path search(String srcLabel, String dstLabel) {
            initializeSearch(srcLabel, dstLabel);
            while (hasNextNode()) {
                String current = getNextNode();
                if (isDestinationReached(current)) {
                    return reconstructPath(prev, dstLabel);
                }
                addNeighborsToStack(current);
            }
            return null;
        }

        // Additional method to add neighbors to the stack
        protected void addNeighborsToStack(String current) {
            for (DefaultEdge edge : graph.outgoingEdgesOf(current)) {
                String neighbor = graph.getEdgeTarget(edge);
                if (!visited.contains(neighbor)) {
                    stack.push(neighbor);
                    visited.add(neighbor);
                    prev.put(neighbor, current);
                }
            }
        }
    }





    public class RandomWalk extends GraphSearchTemplate implements GraphSearchStrategy {
        private Random random = new Random();
        private List<String> currentPath = new ArrayList<>();

        public RandomWalk(Graph<String, DefaultEdge> graph) {
            super(graph);
        }

        @Override
        protected void initializeSearch(String srcLabel, String dstLabel) {
            this.srcLabel = srcLabel;
            this.dstLabel = dstLabel;
            prev = new HashMap<>();
            visited = new HashSet<>();
            visited.add(srcLabel);
            prev.put(srcLabel, null);
            currentPath.add(srcLabel); // Initialize the path with the source
        }

        @Override
        protected String getNextNode() {
            List<String> neighbors = new ArrayList<>();
            for (DefaultEdge edge : graph.outgoingEdgesOf(srcLabel)) {
                neighbors.add(graph.getEdgeTarget(edge));
            }
            if (neighbors.isEmpty()) return null;

            String nextNode = neighbors.get(random.nextInt(neighbors.size()));
            currentPath.add(nextNode); // Add next node to the path
            printCurrentPath(); // Print the current path
            srcLabel = nextNode; // Update the current node
            return nextNode;
        }

        @Override
        protected boolean hasNextNode() {
            return !visited.contains(dstLabel);
        }

        @Override
        protected boolean isDestinationReached(String node) {
            visited.add(node); // Add to visited
            return node.equals(dstLabel);
        }

        private void printCurrentPath() {
            System.out.print("visiting Path{nodes=[");
            for (int i = 0; i < currentPath.size(); i++) {
                if (i > 0) System.out.print(", ");
                System.out.print("Node{" + currentPath.get(i) + "}");
            }
            System.out.println("]}");
        }
    }




    // In GraphParser class


    public boolean parseGraph(String filepath) {
        if (filepath == null || filepath.isEmpty()) {
            return false;
        }
        File fpath = new File(filepath);
        if (!fpath.exists()) {
            return false;
        }
        DOTImporter<String, DefaultEdge> myImporter = new DOTImporter<>();
        myImporter.setVertexFactory(id -> id);
        myImporter.importGraph(graph, fpath);
        return true;
    }

    // Feature 2 - API to add a node
    //Project 3
    //Refactoring 1: Method Reusability
    // Checks for duplicates Nodes
    private boolean isNodeDuplicate(String label) {
        return graph.containsVertex(label);
    }
    public boolean addNode(String label) {
        if (isNodeDuplicate(label)) {
            System.out.println("-Node " + label + " is a duplicate. Cannot add node");
            return false;
        }
        return graph.addVertex(label);
    }




    // Feature 2 - API to add a list of nodes
    // Just reuse the addNode function
    public void addNodes(String[] labels) {
        for (String label : labels) {
            addNode(label);
        }

        //part 2 code adding paths





    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Graph:\n");
        sb.append("Num of Nodes: ").append(getNumNodes()).append("\n");
        sb.append("Labels of Nodes:\n");
        for (String node : graph.vertexSet()) {
            sb.append(node).append("\n");
        }
        sb.append("Num of Edges: ").append(getNumEdges()).append("\n");
        sb.append("Nodes and Edge Directions:\n");
        for (DefaultEdge edge : graph.edgeSet()) {
            String source = graph.getEdgeSource(edge);
            String target = graph.getEdgeTarget(edge);
            sb.append(source).append(" -> ").append(target).append("\n");
        }

        return sb.toString();
    }

    public int getNumNodes() {
        return graph.vertexSet().size();
    }

    public int getNumEdges() {
        return graph.edgeSet().size();
    }

    public boolean outputDOTGraph(String filepath) {
        if (graph == null) {
            System.out.println("Graph is empty");
            return false;
        }

        File outputFile = new File(filepath);


        try {
            DOTExporter<String, DefaultEdge> exporter = new DOTExporter<>(v -> v.toString());
            exporter.exportGraph(graph, outputFile);
            System.out.println("Graph has been successfully exported to: " + filepath);
        } catch (Exception ex) {
            System.out.println("Exception while exporting DOT file " + ex);
            return false;
        }
        return true;
    }
    public boolean addEdge(String srcLabel, String dstLabel) {
        if (graph.containsEdge(srcLabel, dstLabel)) {
            System.out.println("Duplicate edge from: " + srcLabel + "->" + dstLabel);
            return false;
        }

        try {
            graph.addEdge(srcLabel, dstLabel);
        } catch (Exception ex) {
            System.out.println("Exception while adding edge " + ex.getMessage());
            return false;
        }

        return true;
    }


    // Method to remove a single node
    public boolean removeNode(String label) {
        if (!graph.containsVertex(label)) {
            System.out.println("-Node " + label + " does not exist. Cannot remove node.");
            return false;
        }//etst code commit
        return graph.removeVertex(label);
    }

    // Method to remove multiple nodes
    public void removeNodes(String[] labels) {
        for (String label : labels) {
            removeNode(label);
        }
    }

    // Method to remove an edge
    public boolean removeEdge(String srcLabel, String dstLabel) {
        DefaultEdge edge = graph.getEdge(srcLabel, dstLabel);
        if (edge == null) {
            System.out.println("Edge from: " + srcLabel + " to " + dstLabel + " does not exist.");
            return false;
        }
        return graph.removeEdge(edge);
    }

    // Method to perform BFS and find a path from srcLabel to dstLabel
    // Helper method to reconstruct the path from the source to the destination
    private Path reconstructPath(Map<String, String> prev, String dstLabel) {
        LinkedList<String> path = new LinkedList<>();
        for (String at = dstLabel; at != null; at = prev.get(at)) {
            path.addFirst(at);
        }
        return new Path(path);

    }


    // Inner Path class
    public static class Path {
        boolean ab = false;
        private final List<String> nodes;

        public Path(List<String> nodes) {
            this.nodes = nodes;
        }

        public List<String> getNodes() {
            return nodes;
        }

        @Override
        public String toString() {
            return String.join(" -> ", nodes);
        }
    }

    // Helper method for recursive DFS
    private boolean dfsHelper(String current, String destination, Set<String> visited, List<String> pathList) {
        if (current.equals(destination)) {
            return true;
        }

        visited.add(current);

        for (DefaultEdge edge : graph.outgoingEdgesOf(current)) {
            String neighbor = graph.getEdgeTarget(edge);
            if (!visited.contains(neighbor)) {
                pathList.add(neighbor);
                boolean found = dfsHelper(neighbor, destination, visited, pathList);
                if (found) {
                    return true;
                }
                pathList.remove(pathList.size() - 1);
            }
        }

        return false;
    }
    //end part 4 dfs

    public enum Algorithm {
        BFS,
        RANDOM_WALK, DFS
    }

    //Part 5
    public Path graphSearch(String srcLabel, String dstLabel, Algorithm algo) {
        //GraphSearchTemplate searchTemplate;
        GraphSearchStrategy strategy;


        switch (algo) {
            case BFS:
                strategy = new BFS(this.graph);
                break;

            case DFS:
                strategy = new DFS(this.graph);
                break;


            case RANDOM_WALK:
                strategy = new RandomWalk(this.graph);
                break;
            default:
                throw new IllegalArgumentException("Unsupported search algorithm");
        }
        //return searchTemplate.search(srcLabel, dstLabel);
        return strategy.search(srcLabel, dstLabel);
    }





    public static void main(String[] args) {
        System.out.println("Feature 1: Parse a DOT graph file to create a graph");

        // Instantiate GraphParser
        GraphParser parser = new GraphParser();

        // Path to your DOT file (change it to your actual path)
        String filePath = "/Users/gabe/IdeaProjects/CSE 464 First Proj/input.dot";
        String outputFilePath = "test_output.dot";  // Path for the output DOT file

        // Parse the DOT graph file
        boolean isParsed = parser.parseGraph(filePath);
        if (isParsed) {
            // Print the results
            System.out.println(parser.toString());

            // Demonstrate Feature 2: Adding nodes
            System.out.println("Feature 2: Adding nodes");
            parser.addNode("e");  // Add a single node
            String[] newNodes = {"f", "g", "h"};  // Define new nodes
            parser.addNodes(newNodes);  // Add multiple nodes
            System.out.println("After adding nodes:");
            System.out.println(parser.toString());

            // Feature 3 demonstration
            System.out.println("Feature 3: Adding edges");
            String[] edgeSources = {"e", "f", "g", "h"};
            String[] edgeTargets = {"f", "g", "h", "e"}; // this makes a cycle e->f->g->h->e
            for (int i = 0; i < edgeSources.length; i++) {
                if (parser.addEdge(edgeSources[i], edgeTargets[i])) {
                    System.out.println("Edge from " + edgeSources[i] + " to " + edgeTargets[i] + " added successfully.");
                } else {
                    System.out.println("Failed to add edge from " + edgeSources[i] + " to " + edgeTargets[i]);
                }
            }


            // Output the graph to a file
            boolean isOutputSuccessful = parser.outputDOTGraph(outputFilePath);
            if (isOutputSuccessful) {
                System.out.println("Graph has been successfully exported to: " + outputFilePath);
            } else {
                System.out.println("Failed to export the graph.");
            }
        } else {
            System.out.println("Failed to parse the DOT graph file. ");
        }
    }

}