package ru.mail.polis.ads.part5.nikitasob23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Problem5 {
    private static void solve(final FastScanner in, final PrintWriter out) {
        final int n = in.nextInt();
        Permutation permutation = new Permutation(n);

        out.println(permutation.toString());
        while (!permutation.isLastPermutation()) {
            permutation.next();
            out.println(permutation.toString());
        }
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

class Permutation {
    private int numOfItems;
    private List<Integer> permutation;

    public Permutation(int numOfItems) {
        this.numOfItems = numOfItems;
        permutation = getFirstPermutation();
    }

    public List<Integer> get() {
        return permutation;
    }

    public String toString() {
        return permutation.stream()
                .map(item -> item + " ")
                .collect(Collectors.joining());
    }

    public boolean isLastPermutation() {
        return getIndexOfTail() == 0;
    }

    public List<Integer> next() {
        int indexOfTail = getIndexOfTail();
        int indexOfFirstMaxTailItem = getIndexOfFirstMaxTailItem(indexOfTail-1);
        Collections.swap(permutation, indexOfTail-1, indexOfFirstMaxTailItem);
        reverseTail(indexOfTail);
        return permutation;
    }
    
    private void reverseTail(int getIndexOfTail) {
        List<Integer> tail = permutation.subList(getIndexOfTail, permutation.size());
        Collections.reverse(tail);
        for (int permNum = getIndexOfTail, tailNum = 0; 
                permNum < permutation.size(); 
                permNum++, tailNum++) {
            permutation.set(permNum, tail.get(tailNum));
        }
    }

    private int getIndexOfFirstMaxTailItem(int compareIndex) {
        for (int itemNum = permutation.size()-1; itemNum > 0; itemNum--) {
            if (permutation.get(compareIndex) < permutation.get(itemNum)) {
                return itemNum;
            }
        }
        return 0;
    }

    private int getIndexOfTail() {
        for(int itemNum = permutation.size()-1; itemNum > 0; itemNum--) {
            if (permutation.get(itemNum-1) < permutation.get(itemNum)) {
                return itemNum;
            }
        }
        return 0;
    }

    private List<Integer> getFirstPermutation() {
        return IntStream.rangeClosed(1, numOfItems)
                .boxed()
                .collect(Collectors.toList());
    }
}
