//Name: Tushar Kshirsagar
//PRN: 123B1F050

import java.util.*;

class Edge {
    int to;
    int cost;
    Edge(int to, int cost) {
        this.to = to;
        this.cost = cost;
    }
}

public class Assignment5 {

    public static int[] findShortestPath(List<List<Edge>> graph, int N, int source, int destination) {
        int[] dist = new int[N];
        int[] nextNode = new int[N];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(nextNode, -1);

        dist[destination] = 0;

        for (int i = N - 2; i >= 0; i--) {
            for (Edge e : graph.get(i)) {
                if (dist[e.to] != Integer.MAX_VALUE && e.cost + dist[e.to] < dist[i]) {
                    dist[i] = e.cost + dist[e.to];
                    nextNode[i] = e.to;
                }
            }
        }

        return nextNode;
    }

    public static void printPath(int source, int[] nextNode) {
        int node = source;
        if (nextNode[node] == -1) {
            System.out.println("No path exists from source to destination.");
            return;
        }
        
        System.out.print("Path: ");
        System.out.print(node);
        while (nextNode[node] != -1) {
            node = nextNode[node];
            System.out.print(" -> " + node);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of nodes in multistage graph: ");
        int N = sc.nextInt(); 

        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < N; i++) graph.add(new ArrayList<>());

        System.out.print("Enter number of edges: ");
        int E = sc.nextInt();

        System.out.println("Enter edges as: from to cost");
        for (int i = 0; i < E; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int cost = sc.nextInt();
            graph.get(u).add(new Edge(v, cost));
        }

        System.out.print("Enter source node: ");
        int source = sc.nextInt();

        System.out.print("Enter destination node: ");
        int destination = sc.nextInt();

        long startTime = System.nanoTime();
        int[] nextNode = findShortestPath(graph, N, source, destination);
        long endTime = System.nanoTime();

        System.out.println("\n--- Optimal Delivery Route ---");
        printPath(source, nextNode);

        System.out.println("Execution time: " + (endTime - startTime) / 1_000_000.0 + " ms");

        sc.close();
    }
}

