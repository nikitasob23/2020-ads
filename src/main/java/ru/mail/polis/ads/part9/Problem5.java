package ru.mail.polis.ads.part9.nikitasob23;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class Problem5 {

    private static void solve(final FastScanner in, final PrintWriter out) {
        Graph graph = new Graph(in.nextInt()/*vertexesAmount*/);
        int facesAmount = in.nextInt();
        int startVertex = in.nextInt();
        int finishVertex = in.nextInt();
        for (int vertex = 0; vertex < facesAmount; vertex++) {
            graph.addFace(in.nextInt()/*beginVertex*/, in.nextInt()/*endVertex*/);
        }
        out.println(graph.bfs(startVertex, finishVertex));
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
    private final List<Integer>[] vertexes;

    public Graph(int size) {
        vertexes = new List[size+1];
    }

    public void addFace(int beginVertex, int endVertex) {
        putVertexes(beginVertex, endVertex);
        putVertexes(endVertex, beginVertex);
    }

    public void putVertexes(int beginVertex, int endVertex) {
        if (vertexes[beginVertex] == null) {
            vertexes[beginVertex] = new ArrayList<>();
        }
        vertexes[beginVertex].add(endVertex);
    }

    public String bfs(int startVertex, int finishVertex) {
        boolean[] visited = new boolean[vertexes.length];
        int[] distance = new int[vertexes.length];
        int[] way = new int[vertexes.length];
        Deque<Integer> vertexQueue = new ArrayDeque<>();
        vertexQueue.offer(startVertex);

        while (!vertexQueue.isEmpty()) {
            int vertexNum = vertexQueue.poll();
            visited[vertexNum] = true;
            if (vertexes[vertexNum] != null) {
                for (int neighborNum : vertexes[vertexNum]) {
                    if (!visited[neighborNum]) {
                        setDistanceAndWay(vertexNum, neighborNum, distance, way);
                        vertexQueue.offer(neighborNum);
                    }
                }
            }
        }
        if (way[finishVertex] == 0) {
            return "-1";
        }
        return distance[finishVertex] + "\n" + restoreMinWay(finishVertex, way);
    }

    private String restoreMinWay(int finishVertex, int[] way) {
        StringBuilder sb = new StringBuilder();
        for (int vertexNum = finishVertex; vertexNum > 0; vertexNum = way[vertexNum]) {
            sb.append(" ").append(vertexNum);
        }
        return sb.reverse().toString();
    }

    private void setDistanceAndWay(int vertexNum, int neighborNum, int[] distance, int[] way) {
        int dist = distance[vertexNum] + 1;
        if (distance[neighborNum] == 0 || dist < distance[neighborNum]) {
            distance[neighborNum] = dist;
            way[neighborNum] = vertexNum;
        }
    }
}
