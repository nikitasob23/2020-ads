package ru.mail.polis.ads.part9.nikitasob23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class Problem2 {

    private static void solve(final FastScanner in, final PrintWriter out) {
        int vertexesAmount = in.nextInt();
        int facesAmount = in.nextInt();
        Graph graph = new Graph();
        for (int faceNum = 0; faceNum < facesAmount; faceNum++) {
            graph.addFace(in.nextInt(), in.nextInt());
        }

        Integer minCycleVertex = graph.findMinCycleVertex();
        out.println(minCycleVertex == null ? "No" : "Yes\n" + minCycleVertex);
    }

    private static class FastScanner {
        private final BufferedReader reader;
        private StringTokenizer tokenizer;

        FastScanner(final InputStream in) {
            reader = new BufferedReader(new InputStreamReader(in));
        }

        String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return tokenizer.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }

    public static void main(final String[] arg) {
        final FastScanner in = new FastScanner(System.in);
        try (PrintWriter out = new PrintWriter(System.out)) {
            solve(in, out);
        }
    }
}

class Graph {
    private Map<Integer, List<Integer>> vertexes = new HashMap<>();

    private class Cycle {
        int entryItem;
        int minItem;
        boolean inCycle;

        Cycle(int vertexId) {
            entryItem = vertexId;
            minItem = entryItem;
            inCycle = true;
        }
    }

    public void addFace(int beginVertexId, int endVertexId) {
        putVertex(beginVertexId, endVertexId);
        putVertex(endVertexId, beginVertexId);
    }

    public Integer findMinCycleVertex() {
        Map<Integer, Boolean> visited = initVisited();
        Cycle cycle = null;
        while (cycle == null && visited.containsValue(false)) {
            for (Map.Entry<Integer, Boolean> visitedItem : visited.entrySet()) {
                if (!visitedItem.getValue()) {
                    cycle = findMinCycleVertex(visitedItem.getKey(), -1, visited);
                    break;
                }
            }
        }
        return cycle == null ? null : cycle.minItem;
    }

    private Cycle findMinCycleVertex(int vertexId, int prevVertexId, Map<Integer, Boolean> visited) {
        visited.put(vertexId, true);
        Cycle cycle = null;
        for (int neighborId : vertexes.get(vertexId)) {
            if (neighborId != prevVertexId) {
                if (visited.get(neighborId)) {
                    cycle = new Cycle(neighborId);
                    break;
                } else {
                    cycle = findMinCycleVertex(neighborId, vertexId, visited);
                }
                if (cycle != null) {
                    if (cycle.entryItem == neighborId) {
                        cycle.inCycle = false;
                    }
                    if (cycle.inCycle) {
                        cycle.minItem = Math.min(neighborId, cycle.minItem);
                    }
                    break;
                }
            }
        }
        return cycle;
    }

    private void putVertex(int beginVertexId, int endVertexId) {
        if (!vertexes.containsKey(beginVertexId)) {
            vertexes.put(beginVertexId, new LinkedList<>());
        }
        vertexes.get(beginVertexId).add(endVertexId);
    }

    private Map<Integer, Boolean> initVisited() {
        Map<Integer, Boolean> visited = new HashMap<>();
        for (Map.Entry<Integer, List<Integer>> vertex : vertexes.entrySet()) {
            visited.put(vertex.getKey(), false);
        }
        return visited;
    }
}
