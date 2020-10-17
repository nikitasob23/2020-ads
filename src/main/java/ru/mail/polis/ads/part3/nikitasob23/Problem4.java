package ru.mail.polis.ads.part3.nikitasob23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public final class Problem4 {
    private static List<Integer> sortedList = new ArrayList<>();

    private static void solve(final FastScanner in, final PrintWriter out) {
        final int arrayLength = in.nextInt();
        final int comparedItemNumber = in.nextInt();
        sortedList = new ArrayList<>(arrayLength);
        while (sortedList.size() < arrayLength) {
            sortedList.add(in.nextInt());
        }

        for (int compareItemNum = 0;
                compareItemNum < comparedItemNumber;
                compareItemNum++) {
            int comparedItem = in.nextInt();
            out.println(binarySearch(comparedItem) ? "YES" : "NO");
        }
    }

    private static boolean binarySearch(int comparedItem) {
        if (comparedItem > sortedList.get(sortedList.size() - 1)
                || comparedItem < sortedList.get(0)) {
            return false;
        }

        for (int firstIndex = 0,
                lastIndex = sortedList.size() - 1,
                middleIndex = lastIndex / 2;
                firstIndex <= lastIndex;
                middleIndex = (firstIndex + lastIndex) / 2) {

            if (comparedItem < sortedList.get(middleIndex)) {
                lastIndex = middleIndex - 1;
            } else if (comparedItem > sortedList.get(middleIndex)) {
                firstIndex = middleIndex + 1;
            } else {
                return true;
            }
        }
        return false;
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
