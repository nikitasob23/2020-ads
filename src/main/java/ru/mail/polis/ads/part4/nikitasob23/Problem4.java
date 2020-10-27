package ru.mail.polis.ads.part4.nikitasob23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public final class Problem4 {
    private static List<Integer> stepsList;
    private static int maxMove;

    private static void solve(final FastScanner in, final PrintWriter out) {
        final int LIST_LENGTH = in.nextInt() + 2;
        stepsList = new ArrayList<>(LIST_LENGTH);
        stepsList.add(0);
        while (stepsList.size() < LIST_LENGTH - 1) {
            stepsList.add(in.nextInt());
        }
        stepsList.add(0);
        maxMove = in.nextInt();

        out.print(getMostBeneficialMove());
    }

    private static int getMostBeneficialMove() {
        List<Integer> sumList = new ArrayList<>();
        sumList.add(0);
        for (int stepItemNum = 1; stepItemNum < stepsList.size(); stepItemNum++) {
            int maxItemIndex = maxItemIndex(sumList,
                    stepItemNum <= maxMove ? 0 : stepItemNum - maxMove,
                    sumList.size() <= maxMove ? sumList.size() : maxMove);
            sumList.add(sumList.get(maxItemIndex) + stepsList.get(stepItemNum));
        }
        return sumList.get(sumList.size() - 1);
    }

    private static int maxItemIndex(List<Integer> list, int beginIndex, int length) {
        int maxItemIndex = beginIndex;
        int endIndex = beginIndex + length;
        for (int itemNum = beginIndex; itemNum < endIndex; itemNum++) {
            if (list.get(itemNum) > list.get(maxItemIndex)) {
                maxItemIndex = itemNum;
            }
        }
        return maxItemIndex;
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
