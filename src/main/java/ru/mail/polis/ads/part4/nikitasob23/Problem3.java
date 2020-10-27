package ru.mail.polis.ads.part4.nikitasob23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public final class Problem3 {

    private static void solve(final FastScanner in, final PrintWriter out) {
        int[] firstLine = new int[in.nextInt()];
        for (int i = 0; i < firstLine.length; i++) {
            firstLine[i] = in.nextInt();
        }
        int[] secondLine = new int[in.nextInt()];
        for (int i = 0; i < secondLine.length; i++) {
            secondLine[i] = in.nextInt();
        }
        out.println(getMinSubsequence(firstLine, secondLine));
    }

    private static int getMinSubsequence(int[]first, int[] second) {
        int[][] matrix = new int[first.length][second.length];
        for (int i = 0; i < first.length; i++) {
            for (int j = 0; j < second.length; j++) {
                if (i > 0) {
                    matrix[i][j] = matrix[i - 1][j];
                }
                if (j > 0 && matrix[i][j] <= matrix[i][j - 1]) {
                    matrix[i][j] = matrix[i][j - 1];
                }
                if (first[i] == second[j]) {
                    matrix[i][j]++;
                }
            }
        }
        return matrix[matrix.length-1][matrix[0].length-1];
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
