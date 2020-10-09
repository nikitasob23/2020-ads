package ru.mail.polis.ads.part2.nikitasob23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public final class Problem4 {

    private static void solve(final FastScanner in, final PrintWriter out) throws IOException {
        int minOrder = in.nextInt();
        String inputLine = in.reader.readLine();
        ArrayList<BigInteger> arrayList = stringToBigIntegerList(inputLine);

        BigInteger result = findMinimumByOrder(arrayList, minOrder);
        out.println(result);
    }

    private static BigInteger findMinimumByOrder(List<BigInteger> arrayList, int minOrder) throws IllegalArgumentException {
        if (minOrder < 1) {
            throw new IllegalArgumentException("Wrong minimum order");
        }

        while (true) {
            ArrayList<BigInteger> leftList = new ArrayList<>();
            ArrayList<BigInteger> rightList = new ArrayList<>();
            int rand = (int) (Math.random() * arrayList.size());

            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).compareTo(arrayList.get(rand)) < 0) {
                    rightList.add(arrayList.get(i));
                } else {
                    if (i != rand) {
                        leftList.add((arrayList.get(i)));
                    }
                }
            }
            int minNum = leftList.size() + 1;
            if (minOrder < minNum) {
                arrayList = leftList;
            } else if (minOrder > minNum) {
                arrayList = rightList;
                minOrder -= leftList.size() + 1;
            } else {
                return arrayList.get(rand);
            }
        }
    }

    private static ArrayList<BigInteger> stringToBigIntegerList(String line) {
        ArrayList<BigInteger> resultList = new ArrayList<>();
        ArrayList<String> inputList = new ArrayList<>(Arrays.asList(line.split(" ")));
        for (String item : inputList) {
            resultList.add(new BigInteger((item)));
        }
        return resultList;
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

    public static void main(final String[] arg) throws IOException {
        final FastScanner in = new FastScanner(System.in);
        try (PrintWriter out = new PrintWriter(System.out)) {
            solve(in, out);
        }
    }
}
