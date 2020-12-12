package ru.mail.polis.ads.part9.nikitasob23;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

public final class Problem3 {

    private static void solve(final FastScanner in, final PrintWriter out) {
        int vertexAmount = in.nextInt();
        int facesAmount = in.nextInt();
        Graph graph = new Graph(vertexAmount, facesAmount);
        for (int faceNum = 0; faceNum < facesAmount; faceNum++) {
            graph.addFace(in.nextInt(), in.nextInt(), in.nextInt());
        }
        out.println(Arrays.stream(graph.getDistanceList())
                .boxed()
                .map(Objects::toString)
                .reduce((first, second) -> first + " " + second)
                .get());
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
    private final int vertexesAmount;
    private final Face[] faces;
    private int size = 0;
    private final int UNREACHABLE_DIST = 30000;

    class Face {
        int beginVertex;
        int endVertex;
        int dist;

        Face(int beginVertex, int endVertex, int dist) {
            this.beginVertex = beginVertex;
            this.endVertex = endVertex;
            this.dist = dist;
        }
    }

    public Graph(int vertexesAmount, int facesAmount) {
        this.vertexesAmount = vertexesAmount;
        faces = new Face[facesAmount];
    }

    public void addFace(int beginVertex, int endVertex, int dist) {
        faces[size++] = new Face(beginVertex, endVertex, dist);
    }

    public int[] getDistanceList() {
        int[] minDistance = initMinDistance();
        for (int i = 0; i < faces.length; i++) {
            boolean[] visited = new boolean[vertexesAmount+1];
            for (Face face : faces) {
                if (!visited[face.beginVertex] && minDistance[face.beginVertex] != Integer.MAX_VALUE) {
                    int newDist = minDistance[face.beginVertex] + face.dist;
                    if (newDist < minDistance[face.endVertex]) {
                        minDistance[face.endVertex] = newDist;
                        visited[face.endVertex] = true;
                    }
                }
            }
        }
        return Arrays.stream(minDistance)
                .skip(1)
                .map(dist -> dist = dist == Integer.MAX_VALUE ? UNREACHABLE_DIST : dist)
                .toArray();
    }

    private int[] initMinDistance() {
        int[] minDistance = IntStream.range(0, vertexesAmount+1)
                .map(dist -> dist = Integer.MAX_VALUE)
                .toArray();
        minDistance[1] = 0;
        return minDistance;
    }
}
