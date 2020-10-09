package ru.mail.polis.ads.part2.nikitasob23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import static java.util.Collections.swap;

public final class Problem3 {
    private static void solve(final FastScanner in, final PrintWriter out) {
        int arrayListLength = in.nextInt();
        ArrayList<Integer> arrayList = new ArrayList<>(arrayListLength);
        while (arrayList.size() < arrayListLength) {
            arrayList.add(in.nextInt());
        }
        out.print(getBubbleSortReplaceElemQuantity(arrayList));
    }

    private static int getBubbleSortReplaceElemQuantity(ArrayList<Integer> arrayList) {
        int replaceElemCounter = 0;
        boolean isSorted = false;
        while (!isSorted) {
            isSorted = true;
            for (int i = 0; i < arrayList.size()-1; i++) {
                if (arrayList.get(i) > arrayList.get(i+1)) {
                    swap(arrayList, i, i+1);
                    replaceElemCounter++;
                    isSorted = false;
                }
            }
        }
        return replaceElemCounter;
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
