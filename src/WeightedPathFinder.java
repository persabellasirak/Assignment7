import java.util.*;

public class WeightedPathFinder {

    // Edge class to hold target node and weight
    static class Edge {
        int target;
        int weight;

        Edge(int target, int weight) {
            this.target = target;
            this.weight = weight;
        }
    }

    static Map<Integer, List<Edge>> graph = new HashMap<>();

    // Recursive DFS to find simple paths of weight 7
    public static void findPaths(int current, int destination, int totalWeight,
                                 int targetWeight, List<Integer> path, Set<Integer> visited) {
        if (totalWeight > targetWeight) return; // too heavy
        if (current == destination && totalWeight == targetWeight) {
            System.out.println(path);
            return;
        }

        visited.add(current);

        if (graph.containsKey(current)) {
            for (Edge edge : graph.get(current)) {
                if (!visited.contains(edge.target)) {
                    path.add(edge.target);
                    findPaths(edge.target, destination, totalWeight + edge.weight, targetWeight, path, visited);
                    path.remove(path.size() - 1);
                }
            }
        }

        visited.remove(current); // backtrack
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input: number of vertices and edges
        System.out.print("Enter number of vertices: ");
        int V = sc.nextInt();
        System.out.print("Enter number of edges: ");
        int E = sc.nextInt();

        // Input: edges (u, v, weight)
        System.out.println("Enter each edge as: from to weight");
        for (int i = 0; i < E; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int weight = sc.nextInt();

            graph.putIfAbsent(u, new ArrayList<>());
            graph.get(u).add(new Edge(v, weight));
        }

        // Input: start and end vertices
        System.out.print("Enter start vertex (u): ");
        int start = sc.nextInt();
        System.out.print("Enter end vertex (w): ");
        int end = sc.nextInt();

        // Find and print all simple paths of length 7
        System.out.println("Paths from " + start + " to " + end + " with total weight 7:");
        List<Integer> path = new ArrayList<>();
        path.add(start);
        findPaths(start, end, 0, 7, path, new HashSet<>());

        sc.close();
    }
}
