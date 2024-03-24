package org.example;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public class Algorithm {
    private final HashMap<Integer, LinkedHashSet<Integer>> relations;
    private final LinkedList<Integer> visitedOrder;
    private final boolean[] visited;

    public Algorithm(int vertices) {
        visitedOrder = new LinkedList<>();
        visited = new boolean[vertices];
        relations = new HashMap<>();

        for (int i = 0; i < vertices; i++)
            relations.put(i, new LinkedHashSet<>());
    }

    public HashMap<Integer, LinkedHashSet<Integer>> getRelations() {
        return relations;
    }

    public LinkedList<Integer> getVisitedOrder() {
        return visitedOrder;
    }

    public boolean[] getVisited() {
        return visited;
    }

    public void addEdge(int src, int dst) {
        LinkedHashSet<Integer> relVertices = relations.get(src);

        if (relVertices == null) {
            throw new IllegalArgumentException("Source node is not included in graph");
        }
        if (relations.get(dst) == null) {
            throw new IllegalArgumentException("Destination node is not included in graph");
        }
        if (!relVertices.add(dst)) {
            throw new IllegalArgumentException("Destination node has been already added");
        }
    }

    public void applyDFS(int vertex) throws IllegalArgumentException {

        if (relations.get(vertex) == null) {
            throw new IllegalArgumentException("Start vertex is not included in graph");
        }

        visited[vertex] = true;
        visitedOrder.add(vertex);

        for (int next : relations.get(vertex)){
            if (!visited[next]){
                applyDFS(next);
            }
        }
    }
}