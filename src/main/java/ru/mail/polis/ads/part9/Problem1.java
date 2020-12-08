package ru.mail.polis.ads.part9.nikitasob23;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Stack;

public class Problem1 {

    private static void solve(final FastScanner in, final PrintWriter out) {
        int vertexesAmount = in.nextInt();
        int facesAmount = in.nextInt();
        Graph graph = new Graph();
        for (int face = 0; face < facesAmount; face++) {
            graph.addFace(in.nextInt(), in.nextInt());
        }
        Stack<Integer> result = graph.dfs();
        if (result != null) {
            while (!result.isEmpty()) {
                out.print(result.pop() + " ");
            }
        } else {
            out.println(-1);
        }
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

    private enum Visited {
        RED, BLACK
    }

    public void addFace(int beginVertex, int endVertex) {
        if (!vertexes.containsKey(beginVertex)) {
            vertexes.put(beginVertex, new LinkedList<>());
        }
        vertexes.get(beginVertex).add(endVertex);
        if (!vertexes.containsKey(endVertex)) {
            vertexes.put(endVertex, new LinkedList<>());
        }
    }

    public Stack<Integer> dfs() {
        Map<Integer, Visited> visited = initVisited();
        Stack<Integer> stack = new Stack<>();
        for (Map.Entry<Integer, List<Integer>> vertex : vertexes.entrySet()) {
            if (!dfs(visited, stack, vertex.getKey())) {
                return null;
            }
        }
        return stack;
    }

    private boolean dfs(Map<Integer, Visited> visited, Stack<Integer> stack, int vertexId) {
        Visited visitedItem = visited.get(vertexId);
        if (visitedItem != null && visitedItem.equals(Visited.RED)) {
            return false;
        }
        if (visited.get(vertexId) == null) {
            visited.put(vertexId, Visited.RED);
            for (int neighbor : vertexes.get(vertexId)) {
                if (!dfs(visited, stack, neighbor)) {
                    return false;
                }
            }
            visited.put(vertexId, Visited.BLACK);
            stack.push(vertexId);
        }
        return true;
    }

    private Map<Integer, Visited> initVisited() {
        Map<Integer, Visited> visited = new HashMap<>();
        for (Map.Entry<Integer, List<Integer>> vertex : vertexes.entrySet()) {
            visited.put(vertex.getKey(), null);
        }
        return visited;
    }
}
