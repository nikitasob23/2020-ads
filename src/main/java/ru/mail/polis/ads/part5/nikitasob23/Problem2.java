package ru.mail.polis.ads.part5.nikitasob23;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Problem2 {
    private static void solve(final FastScanner in, final PrintWriter out) {
        long h = in.nextInt();
        long w = in.nextInt();
        long n = in.nextInt();
        long max = Math.max(h, w);
        long left = max;
        long right = max * n;

        while (left < right) {
            long middle = (left + right) / 2;
            long count = (middle / h) * (middle / w);
            if (n <= count) {
                right = middle;
            } else {
                left = middle + 1;
            }
        }
        out.println(left);
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
