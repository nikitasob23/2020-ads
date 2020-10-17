package ru.mail.polis.ads.part3.nikitasob23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import static java.util.Collections.swap;

public final class Problem2 {

    private static void solve(final FastScanner in, final PrintWriter out) {
        int numOfCommands = in.nextInt();
        Heap heap = new Heap();

        int commandCounter = 0;
        while (commandCounter < numOfCommands) {
            int command = in.nextInt();
            switch (command) {
                case 0:
                    heap.insert(in.nextInt());
                    commandCounter++;
                    break;
                case 1:
                    out.println(heap.extract());
                    commandCounter++;
                    break;
            }
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

class Heap {
    private final List<Integer> list = new ArrayList<>();

    public int size() {
        return list.size();
    }

    public void insert(int newItem) {
        list.add(newItem);
        swim();
    }

    public int extract() {
        int firstItem = list.get(0);
        int lastItem = list.remove(getIndexOfLastItem());
        if (list.size() > 0) {
            list.set(0, lastItem);
            sink();
        }
        return firstItem;
    }

    private int getIndexOfLastItem() {
        return list.size() - 1;
    }

    private int getParentNumByChildNum(int childNum) {
        return (childNum + 1) / 2 - 1;
    }

    private int getLeftChildNumByParentNum(int parentNum) {
        return (parentNum + 1) * 2 - 1;
    }

    private void swim() {
        int childNum = getIndexOfLastItem();
        for (int parentNum = getParentNumByChildNum(childNum);
             childNum > 0 && list.get(parentNum) < list.get(childNum);
             childNum = parentNum, parentNum = getParentNumByChildNum(parentNum)) {
            swap(list, parentNum, childNum);
        }
    }

    private void sink() {
        int parentNum = 0;
        while (parentNum * 2 < getIndexOfLastItem()) {
            int childNum = getLeftChildNumByParentNum(parentNum);
            if (childNum < getIndexOfLastItem()
                    && list.get(childNum) < list.get(childNum + 1)) {
                childNum++;
            }

            if (list.get(parentNum) < list.get(childNum)) {
                swap(list, parentNum, childNum);
            } else {
                break;
            }
            parentNum = childNum;
        }
    }
}