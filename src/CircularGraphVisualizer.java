import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

import java.util.*;

public class CircularGraphVisualizer {

    // Helper class to store vertex and its jump distance
    static class Vertex {
        String label;
        int distance;

        Vertex(String label, int distance) {
            this.label = label;
            this.distance = distance;
        }
    }

    public static void main(String[] args) {
        // Sample input
        String input = "[ (I, 2) , (A, 5) , (E, 4) , (F,2) , (T, 2) , (S, 3) ]";

        // Parse the input
        List<Vertex> vertices = parseInput(input);

        // Create and visualize the graph
        Graph graph = new SingleGraph("Circular Directed Graph");
        graph.setStrict(false);
        graph.setAutoCreate(true);

        int n = vertices.size();

        // Add nodes
        for (Vertex v : vertices) {
            Node node = graph.addNode(v.label);
            node.setAttribute("ui.label", v.label);
        }

        // Add edges (circular left and right links)
        for (int i = 0; i < n; i++) {
            Vertex current = vertices.get(i);
            int dist = current.distance;

            int rightIndex = (i + dist) % n;
            int leftIndex = (i - dist + n) % n;

            String from = current.label;
            String toRight = vertices.get(rightIndex).label;
            String toLeft = vertices.get(leftIndex).label;

            graph.addEdge(from + "-" + toRight, from, toRight, true);
            graph.addEdge(from + "-" + toLeft, from, toLeft, true);
        }

        // Style the graph
        graph.setAttribute("ui.stylesheet", "node { fill-color: #77c; size: 35px; text-size: 20px; } edge { arrow-shape: arrow; }");
        graph.display();
    }

    // Method to parse input string into list of Vertex objects
    public static List<Vertex> parseInput(String input) {
        List<Vertex> list = new ArrayList<>();
        input = input.replaceAll("[\\[\\]()]", "").trim();
        if (input.isEmpty()) return list;

        String[] parts = input.split(",");

        for (int i = 0; i < parts.length; i += 2) {
            String label = parts[i].trim();
            String value = parts[i + 1].trim();
            label = label.replaceAll("[^A-Za-z]", "");
            int distance = Integer.parseInt(value.replaceAll("[^0-9]", ""));
            list.add(new Vertex(label, distance));
        }

        return list;
    }
}
