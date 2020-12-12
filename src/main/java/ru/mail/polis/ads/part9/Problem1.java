package ru.mail.polis.ads.part9.nikitasob23;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class Problem1 {
    private static void solve(final FastScanner in, final PrintWriter out) {
        int vertexesAmount = in.nextInt();
        int facesAmount = in.nextInt();
        Graph graph = new Graph(vertexesAmount);
        for (int face = 0; face < facesAmount; face++) {
            graph.addFace(in.nextInt(), in.nextInt());
        }
        Stack<Integer> result = graph.getSort();
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
    private List<Integer>[] vertexes;

    private enum Visited {
        RED, BLACK
    }

    public Graph(int size) {
        vertexes = new List[size+1];
    }

    public void addFace(int beginVertex, int endVertex) {
        if (vertexes[beginVertex] == null) {
            vertexes[beginVertex] = new ArrayList<>();
        }
        vertexes[beginVertex].add(endVertex);
    }

    public Stack<Integer> getSort() {
        Visited[] visited = new Visited[vertexes.length];
        Stack<Integer> sortVertexes = new Stack<>();
        for (int vertexNum = 1; vertexNum < visited.length; vertexNum++) {
            if (visited[vertexNum] == null) {
                boolean cycleExists = dfs(vertexNum, visited, sortVertexes);
                if (cycleExists) {
                    return null;
                }
            }
        }
        return sortVertexes;
    }

    private boolean dfs(int vertexNum, Visited[] visited, Stack<Integer> sortVertexes) {
        boolean cycleExists = false;
        visited[vertexNum] = Visited.RED;
        if (vertexes[vertexNum] != null) {
            for (int neighborNum : vertexes[vertexNum]) {
                if (cycleExists || visited[neighborNum] == Visited.RED) {
                    return true;
                }
                if (visited[neighborNum] == null) {
                    cycleExists = dfs(neighborNum, visited, sortVertexes);
                }
            }
        }
        visited[vertexNum] = Visited.BLACK;
        sortVertexes.push(vertexNum);
        return cycleExists;
    }
}
