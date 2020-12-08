package ru.mail.polis.ads.part9.nikitasob23;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Deque;
import java.util.StringTokenizer;

public class Problem4 {

    private static void solve(final FastScanner in, final PrintWriter out) {
        int vertexesAmount = in.nextInt();
        int facesAmount = in.nextInt();
        int startVertexId = in.nextInt();
        int endVertexId = in.nextInt();
        Graph graph = new Graph();
        for (int vertex = 0; vertex < facesAmount; vertex++) {
            graph.addFace(in.nextInt(), in.nextInt(), in.nextInt());
        }
        out.println(graph.findMinWay(startVertexId, endVertexId));
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
    private Map<Integer, Map<Integer, Integer>> vertexes = new HashMap<>();

    private class Ways {
        Map<Integer, Integer> min = new HashMap<>();
        Map<Integer, List<Integer>> vertexMap = new HashMap<>();
    }

    public void addFace(int beginVertexId, int endVertexId, int dist) {
        if (!vertexes.containsKey(beginVertexId)) {
            vertexes.put(beginVertexId, new TreeMap<>());
        }
        vertexes.get(beginVertexId).put(endVertexId, dist);
        if (!vertexes.containsKey(endVertexId)) {
            vertexes.put(endVertexId, new TreeMap<>());
        }
        vertexes.get(endVertexId).put(beginVertexId, dist);
    }

    public String findMinWay(int startVertexId, int endVertexId) {
        Map<Integer, Boolean> visited = initVisited();
        Ways ways = initWays(startVertexId);
        Deque<Integer> queue = new LinkedList<>();
        queue.offer(startVertexId);

        while (!queue.isEmpty()) {
            int vertexId = queue.poll();
            visited.put(vertexId, true);
            for (Map.Entry<Integer, Integer> neighbor : vertexes.get(vertexId).entrySet()) {
                int neighborId = neighbor.getKey();
                if (neighborId != vertexId) {
                    findNewMinWay(vertexId, neighborId, ways);
                    if (!visited.get(neighborId) && !queue.contains(neighborId)) {
                        queue.offer(neighborId);
                    }
                }
            }
        }
        return findMinWayResult(ways, endVertexId);
    }

    private Map<Integer, Boolean> initVisited() {
        Map<Integer, Boolean> visited = new HashMap<>();
        for (Map.Entry<Integer, Map<Integer, Integer>> vertex : vertexes.entrySet()) {
            visited.put(vertex.getKey(), false);
        }
        return visited;
    }

    private Ways initWays(int startVertexId) {
        Ways ways = new Ways();
        for (Map.Entry<Integer, Map<Integer, Integer>> vertex : vertexes.entrySet()) {
            int vertexId = vertex.getKey();
            ways.min.put(vertexId, Integer.MAX_VALUE);
            ways.vertexMap.put(vertexId, new LinkedList<>());
        }
        ways.min.put(startVertexId, 0);
        ways.vertexMap.get(startVertexId).add(startVertexId);
        return ways;
    }

    private void findNewMinWay(int vertexId, int neighborId, Ways ways) {
        int newWay = vertexes.get(neighborId).get(vertexId) + ways.min.get(vertexId);
        int minWay = ways.min.get(neighborId);
        if (newWay < minWay) {
            setNewMinWay(vertexId, neighborId, ways, newWay);
        }
        if (minWay != Integer.MAX_VALUE) {
            newWay = minWay + vertexes.get(vertexId).get(neighborId);
            if (newWay < ways.min.get(vertexId)) {
                setNewMinWay(neighborId, vertexId, ways, newWay);
            }
        }
    }

    private void setNewMinWay(int vertexId, int neighborId, Ways ways, int newWay) {
        ways.min.put(neighborId, newWay);
        ways.vertexMap.put(neighborId, new LinkedList<>(ways.vertexMap.get(vertexId)));
        ways.vertexMap.get(neighborId).add(neighborId);
    }

    private String findMinWayResult(Ways ways, int endVertexId) {
        int minWay = ways.min.get(endVertexId);
        if (minWay == Integer.MAX_VALUE) {
            return "-1";
        }
        return minWay + "\n" + ways.vertexMap.get(endVertexId).stream()
                .map(Object::toString)
                .reduce((first, second) -> first + " " + second)
                .get();
    }
}
