package ru.mail.polis.ads.part3.nikitasob23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public final class Problem5 {
    private static List<Integer> coords;

    private static void solve(final FastScanner in, final PrintWriter out) {
        final int numOfCoords = in.nextInt();
        final int numOfCows = in.nextInt();

        coords = new ArrayList<>(numOfCoords);
        while (coords.size() < numOfCoords) {
            coords.add(in.nextInt());
        }

        int left = 0;
        int right = coords.get(coords.size() - 1) - coords.get(0) + 1;
        while (right - left > 1) {
            int middle = (left + right) / 2;
            if (check(middle, numOfCows)) {
                left = middle;
            } else {
                right = middle;
            }
        }
        out.print(left);
    }

    public static boolean check(int value, int cowsAmount) {
        int count = 1;
        int lastCow = coords.get(0);
        for (int coord : coords) {
            if (coord - lastCow >= value) {
                count++;
                lastCow = coord;
            }
        }
        return count >= cowsAmount;
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
