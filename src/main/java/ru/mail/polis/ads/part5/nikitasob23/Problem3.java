package ru.mail.polis.ads.part5.nikitasob23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Problem3 {
    private static void solve(final FastScanner in, final PrintWriter out) {
        int length = in.nextInt();
        int[] array = new int[length];
        for (int i = 0; i < array.length; i++) {
            array[i] = in.nextInt();
        }

        int[] dynamic = new int[length];
        dynamic[0] = 1;
        for (int i = 1; i < array.length; i++) {
            for (int j = i-1; j >= 0; j--) {
                if (array[j] != 0 && array[i] % array[j] == 0) {
                    dynamic[i] = Integer.max(dynamic[i], dynamic[j]);
                }
            }
            dynamic[i]++;
        }
        Arrays.stream(dynamic)
                .reduce((item1, item2) -> Integer.max(item1, item2)).stream()
                .forEach(out::println);
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
