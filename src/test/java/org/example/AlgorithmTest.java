package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


public class AlgorithmTest {
    private Algorithm graph;

    private void initSmallGraph() {
        int vertices = 4;
        graph = new Algorithm(vertices);

        graph.addEdge(0, 1);
        graph.addEdge(1, 0);

        graph.addEdge(0, 2);
        graph.addEdge(2, 0);

        graph.addEdge(1, 2);
        graph.addEdge(2, 1);

        graph.addEdge(2, 3);
        graph.addEdge(3, 2);
    }

    private static Stream<Object[]> provideOrderForSmallDFS() {
        return Stream.of(
                new Object[] {0, new Integer[] { 0, 1, 2, 3 }},
                new Object[] {1, new Integer[] { 1, 0, 2, 3 }},
                new Object[] {2, new Integer[] { 2, 0, 1, 3 }},
                new Object[] {3, new Integer[] { 3, 2, 0, 1 }}
        );
    }

    private void initBigGraph() {
        int vertices = 8;
        graph = new Algorithm(vertices);

        graph.addEdge(0, 1);
        graph.addEdge(1, 0);

        graph.addEdge(0, 2);
        graph.addEdge(2, 0);

        graph.addEdge(0, 3);
        graph.addEdge(3, 0);

        graph.addEdge(1, 2);
        graph.addEdge(2, 1);

        graph.addEdge(1, 3);
        graph.addEdge(3, 1);

        graph.addEdge(2, 5);
        graph.addEdge(5, 2);

        graph.addEdge(3, 7);
        graph.addEdge(7, 3);

        graph.addEdge(4, 7);
        graph.addEdge(7, 4);

        graph.addEdge(6, 7);
        graph.addEdge(7, 6);
    }

    private static Stream<Object[]> provideOrderForBigDFS() {
        return Stream.of(
                new Object[] {0, new Integer[] { 0, 1, 2, 5, 3, 7, 4, 6 }},
                new Object[] {1, new Integer[] { 1, 0, 2, 5, 3, 7, 4, 6 }},
                new Object[] {2, new Integer[] { 2, 0, 1, 3, 7, 4, 6, 5 }},
                new Object[] {3, new Integer[] { 3, 0, 1, 2, 5, 7, 4, 6 }},
                new Object[] {4, new Integer[] { 4, 7, 3, 0, 1, 2, 5, 6 }},
                new Object[] {5, new Integer[] { 5, 2, 0, 1, 3, 7, 4, 6 }},
                new Object[] {6, new Integer[] { 6, 7, 3, 0, 1, 2, 5, 4 }},
                new Object[] {7, new Integer[] { 7, 3, 0, 1, 2, 5, 4, 6 }}
        );
    }

    @Test
    public void testEdgeAddition() {
        int vertices = 2;
        graph = new Algorithm(vertices);

        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(5, 1));

        graph.addEdge(0, 1);
        assertEquals(1, graph.getRelations().get(0).size());

        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(0, 1));
        assertEquals(1, graph.getRelations().get(0).size());

        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(0, 5));
        assertEquals(1, graph.getRelations().get(0).size());
    }

    private void checkOrder(List<Integer> expectedOrder) {
        assertEquals(expectedOrder, graph.getVisitedOrder());
    }

    private void checkAllVisited() {
        for (boolean v: graph.getVisited()) {
            assertTrue(v);
        }
    }

    @Test
    public void testWrongDFS() {
        initSmallGraph();
        assertThrows(IllegalArgumentException.class, () -> graph.applyDFS(-2));
        assertThrows(IllegalArgumentException.class, () -> graph.applyDFS(8));
    }

    @ParameterizedTest
    @MethodSource("provideOrderForSmallDFS")
    public void testSmallDFS(int firstVertex, Integer[] orderValues) {
        initSmallGraph();
        graph.applyDFS(firstVertex);
        checkAllVisited();
        checkOrder(Arrays.asList(orderValues));
    }

    @ParameterizedTest
    @MethodSource("provideOrderForBigDFS")
    public void testBigDFS(int firstVertex, Integer[] orderValues) {
        initBigGraph();
        graph.applyDFS(firstVertex);
        checkAllVisited();
        checkOrder(Arrays.asList(orderValues));
    }

}
