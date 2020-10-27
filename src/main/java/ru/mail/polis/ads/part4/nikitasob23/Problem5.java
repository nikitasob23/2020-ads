package ru.mail.polis.ads.part4.nikitasob23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public final class Problem5 {

    private static void solve(final FastScanner in, final PrintWriter out) {
        final int LIST_LENGTH = in.nextInt();
        List<Integer> list = new ArrayList<>(LIST_LENGTH);
        while (list.size() < LIST_LENGTH) {
            list.add(in.nextInt());
        }
        NumOfInversion numOfInversion = new NumOfInversion(list);
        out.print(numOfInversion.get());
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

class NumOfInversion {
    private int inversionСounter;

    public NumOfInversion(List<Integer> list) {
        inversionСounter = 0;
        mergeSort(list);
    }

    public int get() {
        return inversionСounter;
    }

    private List<Integer> mergeSort(List<Integer> list) {
        if (list.size() < 2) {
            return list;
        }
        int middle = list.size() / 2;
        List<Integer> leftList = list.subList(0, middle);
        List<Integer> rightList = list.subList(middle, list.size());

        leftList = mergeSort(leftList);
        rightList = mergeSort(rightList);

        return sort(leftList, rightList);
    }

    private List<Integer> sort(List<Integer> list1, List<Integer> list2) {
        int listCounter1 = 0;
        int listCounter2 = 0;
        ArrayList<Integer> resultList = new ArrayList<>(list1.size() + list2.size());
        while (listCounter1 < list1.size() && listCounter2 < list2.size()) {
            if (list1.get(listCounter1) < list2.get(listCounter2)) {
                resultList.add(list1.get(listCounter1++));
            } else {
                inversionСounter += list1.size() - listCounter1;
                resultList.add(list2.get(listCounter2++));
            }
        }
        while (listCounter1 < list1.size()) {
            resultList.add(list1.get(listCounter1++));
        }
        while (listCounter2 < list2.size()) {
            resultList.add((list2.get(listCounter2++)));
        }
        return resultList;
    }
}
