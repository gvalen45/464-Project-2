import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.nio.dot.DOTImporter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
            System.out.println("Node " + label + " is a duplicate. Cannot add node");
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
            System.out.println("Node " + label + " does not exist. Cannot remove node.");
            return false;
        }
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
            System.out.println("Failed to parse the DOT graph file.");
        }
    }

}
