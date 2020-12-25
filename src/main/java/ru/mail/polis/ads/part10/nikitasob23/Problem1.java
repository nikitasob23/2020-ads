package ru.mail.polis.ads.part10.nikitasob23;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public final class Problem1 {
    private static void solve(final FastScanner in, final PrintWriter out) {
        int vertexesAmount = in.nextInt();
        int facesAmount = in.nextInt();
        Graph graph = new Graph(vertexesAmount);
        for (int faceId = 1; faceId <= facesAmount; faceId++) {
            graph.addFace(in.nextInt(), in.nextInt(), faceId);
        }
        List<Integer> bridges = graph.getBridgesList();
        out.println(bridges.size());
        bridges.stream()
                .sorted(Integer::compare)
                .forEach(bridge -> out.print(bridge + " "));
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
    private final List<Vertex>[] vertexes;
    
    private class Vertex {
        int id;
        int faceId;

        public Vertex(int id, int faceId) {
            this.id = id;
            this.faceId = faceId;
        }
    }

    public Graph(int facesAmount) {
        vertexes = new List[facesAmount+1];
    }

    public void addFace(int beginVertex, int endVertex, int faceId) {
        putNeighborVertexes(beginVertex, endVertex, faceId);
        putNeighborVertexes(endVertex, beginVertex, faceId);
    }

    private void putNeighborVertexes(int beginVertex, int endVertex, int faceId) {
        if (vertexes[beginVertex] == null) {
            vertexes[beginVertex] = new ArrayList<>();
        }
        vertexes[beginVertex].add(new Vertex(endVertex, faceId));
    }

    public List<Integer> getBridgesList() {
        boolean[] visited = new boolean[vertexes.length];
        int[] tin = new int[vertexes.length];
        int[] fup = new int[vertexes.length];
        List<Integer> bridges = new LinkedList<>();
        int index = 0;
        for (int vertexId = 1; vertexId < vertexes.length; vertexId++) {
            if (!visited[vertexId]) {
                dfs(vertexId, 0, visited, tin, fup, bridges,  index);
            }
        }
        return bridges;
    }

    private void dfs(int vertexId, int prevId, boolean[] visited, int[] tin, int[] fup, List<Integer> bridges, int index) {
        visited[vertexId] = true;
        tin[vertexId] = fup[vertexId] = ++index;
        int minNeighborFup = Integer.MAX_VALUE;
        for (Vertex neighbor : vertexes[vertexId]) {
            if (neighbor.id != prevId) {
                if (!visited[neighbor.id]) {
                    dfs(neighbor.id, vertexId, visited, tin, fup, bridges, index);
                }
                minNeighborFup = Math.min(minNeighborFup, fup[neighbor.id]);
                if (fup[vertexId] < fup[neighbor.id]) {
                    bridges.add(neighbor.faceId);
                }
            }
        }
        fup[vertexId] = Math.min(fup[vertexId], minNeighborFup);
    }
}
