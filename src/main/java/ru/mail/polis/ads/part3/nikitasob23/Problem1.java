package ru.mail.polis.ads.part3.nikitasob23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public final class Problem1 {

    private static void solve(final FastScanner in, final PrintWriter out) {
        int arrayLength = in.nextInt();
        List<Integer> list = new ArrayList<>(arrayLength);
        while (list.size() < arrayLength) {
            list.add(in.nextInt());
        }
        out.print(isHeap(list) ? "YES" : "NO");
    }

    private static boolean isHeap(List<Integer> list) {
        int parentNum = 0;
        for (int childNum = (parentNum + 1) * 2 - 1;
             childNum < list.size();
             parentNum++,
             childNum = (parentNum + 1) * 2 - 1) {

            if (list.get(parentNum) > list.get(childNum)) {
                return false;
            }

            if (++childNum < list.size()) {
                if (list.get(parentNum) > list.get(childNum)) {
                    return false;
                }
            } else {
                break;
            }
        }
        return true;
    }

    static class FastScanner {
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
