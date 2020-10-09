package ru.mail.polis.ads.part2.nikitasob23;

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
        int arrayListLength = in.nextInt();
        ArrayList<Integer> arrayList = new ArrayList<>(arrayListLength);
        while (arrayList.size() < arrayListLength) {
            arrayList.add(in.nextInt());
        }
        arrayList = (ArrayList<Integer>) merge(arrayList);

        for (int item : arrayList) {
            out.print(item + " ");
        }
    }

    private static<E extends Comparable<E>> List<E> merge(List<E> list) {
        if (list.size() < 2) {
            return list;
        }
        int middle = list.size() / 2;
        List<E> leftList = list.subList(0, middle);
        List<E> rightList = list.subList(middle, list.size());

        leftList = merge(leftList);
        rightList = merge(rightList);

        return mergeSort(leftList, rightList);
    }

    private static<E extends Comparable<E>> List<E> mergeSort(List<E> list1, List<E> list2) {
        int listCounter1 = 0;
        int listCounter2 = 0;
        ArrayList<E> resultList = new ArrayList<>(list1.size() + list2.size());
        while (listCounter1 < list1.size() && listCounter2 < list2.size()) {
            if (list1.get(listCounter1).compareTo(list2.get(listCounter2)) < 0) {
                resultList.add(list1.get(listCounter1++));
            } else {
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
