import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.nio.dot.DOTImporter;
import java.util.LinkedList;
import java.util.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
//test commitit Branch Test bfs
public class GraphParser {
    private final List<Integer> container = new ArrayList<>();
    private final Graph<String, DefaultEdge> graph;

    public GraphParser() {
        this.graph = new DefaultDirectedGraph<>(DefaultEdge.class);
    }

    public void push(int i) {
        container.add(i);
    }

    public int size() {
        return container.size();
    }

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
    // Returns true if successful or false for any failures
    // Checks for duplicates too
    public boolean addNode(String label) {
        if (graph.containsVertex(label)) {
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

    //CODE PART 2
    // part  1
    // Remove a single node:
    // removeNode(String label) method.
    // Write tesst cases covering successful removal and attempts to remove non-existent nodes.
    // Remove multiple nodes:
    // removeNodes(String[] labels) method.
    // Write tesst cases covering successsful removal and attempts to remove non existent nodes.
    // Remove an edge:
    // removeEdge(String srcLabel, String dstLabel) method.
    // Write tessttesst cases covering successful removal/ attempts to remove nonexistent edges.

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

    //part 2 code adding paths
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

    //project 2 part 4

    // Method to perform DFS and find a path from srcLabel to dstLabel
//    public Path graphSearchDFS(String srcLabel, String dstLabel) {
//        if (!graph.containsVertex(srcLabel) || !graph.containsVertex(dstLabel)) {
//
//            return null; // Return null if either the source or destination is not in the graph
//        }
//
//        Stack<String> stack = new Stack<>();
//        Map<String, String> prev = new HashMap<>();
//        Set<String> visited = new HashSet<>();
//
//        stack.push(srcLabel);
//        visited.add(srcLabel);
//        prev.put(srcLabel, null); // Source node has no predecessor
//
//        while (!stack.isEmpty()) {
//            String current = stack.pop();
//
//            System.out.println("Visiting Node: " + current);
//
//            if (current.equals(dstLabel)) {
//                Path foundPath = reconstructPath(prev, dstLabel);
//                System.out.println("Path Found: " + foundPath);
//                return foundPath; // Reconstruct the path if destination is found
//            }
//
//            for (DefaultEdge edge : graph.outgoingEdgesOf(current)) {
//                String neighbor = graph.getEdgeTarget(edge);
//                if (!visited.contains(neighbor)) {
//                    stack.push(neighbor);
//                    visited.add(neighbor);
//                    prev.put(neighbor, current);
//                }
//            }
//        }
//
//        return null; // Return null if no path is found
//    }
// Recursive DFS method
    public Path graphSearch(String srcLabel, String dstLabel) {
        Set<String> visited = new HashSet<>();
        List<String> pathList = new ArrayList<>();
        pathList.add(srcLabel);
        boolean found = dfsHelper(srcLabel, dstLabel, visited, pathList);
        if (found) {
            return new Path(new ArrayList<>(pathList));
        } else {
            return null;
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
        DFS
    }

    //Part 5
    public Path graphSearch(String srcLabel, String dstLabel, Algorithm algo) {
        switch (algo) {
            case BFS:
                if (!graph.containsVertex(srcLabel) || !graph.containsVertex(dstLabel)) {
                    return null; // Return null if either the source or destination is not in the graph
                }

                Queue<String> queue = new LinkedList<>();
                Map<String, String> prev = new HashMap<>();
                Set<String> visited = new HashSet<>();

                queue.add(srcLabel);
                visited.add(srcLabel);
                prev.put(srcLabel, null); // Source node has no predecessor

                while (!queue.isEmpty()) {
                    String current = queue.poll();
                    System.out.println("Visiting Node: " + current);


                    if (current.equals(dstLabel)) {
                        Path foundPath = reconstructPath(prev, dstLabel);
                        System.out.println("Path Found: " + foundPath);
                        return foundPath; // Reconstruct the path if destination is found

                    }

                    for (DefaultEdge edge : graph.outgoingEdgesOf(current)) {
                        String neighbor = graph.getEdgeTarget(edge);
                        if (!visited.contains(neighbor)) {
                            queue.add(neighbor);
                            visited.add(neighbor);
                            prev.put(neighbor, current);
                        }
                    }
                }

                return null;

            case DFS:
                Set<String> visitedd = new HashSet<>();
                List<String> pathList = new ArrayList<>();
                pathList.add(srcLabel);
                boolean found = dfsHelper(srcLabel, dstLabel, visitedd, pathList);
                if (found) {
                    return new Path(new ArrayList<>(pathList));
                } else {
                    return null;
                }
            default:
                throw new IllegalArgumentException("Unsupported search algorithm");
        }
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
