package ru.mail.polis.ads.part5.nikitasob23;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Problem1 {
    private static void solve(final FastScanner in, final PrintWriter out) {
        final double n = Double.parseDouble(in.next());
        final Double ACCURACY = 0.0000001;
        double left = 0;
        double right = n;
        double c = 0;

        while (Math.abs(n - c) > ACCURACY) {
            double middle = (left + right) / 2;
            c = Math.pow(middle, 2) + Math.sqrt(middle);
            if (c > n) {
                right = middle;
            } else {
                left = middle;
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
