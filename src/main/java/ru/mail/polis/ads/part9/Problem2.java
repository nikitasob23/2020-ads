package ru.mail.polis.ads.part9.nikitasob23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.List;
import java.util.ArrayList;

public class Problem2 {

    private static void solve(final FastScanner in, final PrintWriter out) {
        Graph graph = new Graph(in.nextInt()/*vertexesAmount*/);
        int facesAmount = in.nextInt();
        for (int faceNum = 0; faceNum < facesAmount; faceNum++) {
            graph.addFace(in.nextInt()/*beginVertex*/, in.nextInt()/*endVertex*/);
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
    private List<Integer>[] vertexes;

    private class Cycle {
        boolean inCycle;
        boolean exists;
        int entryItem;
        int minItem;

        void init(int entryItem) {
            inCycle = true;
            exists = true;
            this.entryItem = entryItem;
            minItem = entryItem;
        }
    }

    public Graph(int size) {
        vertexes = new List[size+1];
    }

    public void addFace(int beginVertex, int endVertex) {
        putVertexes(beginVertex, endVertex);
        putVertexes(endVertex, beginVertex);
    }

    private void putVertexes(int beginVertex, int endVertex) {
        if (vertexes[beginVertex] == null) {
            vertexes[beginVertex] = new ArrayList<>();
        }
        if (!vertexes[beginVertex].contains(endVertex)) {
            vertexes[beginVertex].add(endVertex);
        }
    }

    public Integer findMinCycleVertex() {
        boolean[] visited = new boolean[vertexes.length];
        List<Integer> minCycleItemsList = new LinkedList<>();
        Cycle cycle = new Cycle();
        for (int vertexNum = 1; vertexNum < visited.length; vertexNum++) {
            if (!visited[vertexNum] && !cycle.exists) {
                dfs(vertexNum, 0, visited, cycle);
            }
            if (cycle.minItem != 0) {
                minCycleItemsList.add(cycle.minItem);
            }
        }
        return minCycleItemsList.isEmpty() ? null : minCycleItemsList.stream()
                .min(Integer::compareTo)
                .get();
    }

    private void dfs(int vertexNum, int prevVertex, boolean[] visited, Cycle cycle) {
        visited[vertexNum] = true;
        if (vertexes[vertexNum] == null) {
            return;
        }
        for (int neighborNum : vertexes[vertexNum]) {
            if (neighborNum != prevVertex) {
                if (visited[neighborNum]) {
                    cycle.init(neighborNum);
                } else {
                    dfs(neighborNum, vertexNum, visited, cycle);
                }
            }
            if (cycle.inCycle) {
                if (vertexNum < cycle.minItem) {
                    cycle.minItem = vertexNum;
                }
                if (vertexNum == cycle.entryItem) {
                    cycle.inCycle = false;
                }
            }
            if (cycle.exists) {
                break;
            }
        }
    }
}
