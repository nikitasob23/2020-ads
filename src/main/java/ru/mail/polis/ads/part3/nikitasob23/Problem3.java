package ru.mail.polis.ads.part3.nikitasob23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.List;
import java.util.ArrayList;
import static java.util.Collections.swap;

public final class Problem3 {

    private static void solve(final FastScanner in, final PrintWriter out) {
        HeapList heapList = new HeapList();
        while (true) {
            heapList.add(in.nextInt());
            out.println(heapList.getMedian());
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
        } catch (Exception ex) {

        }
    }
}

class HeapList {
    private Integer median;
    private boolean medianIsItem;
    private final Heap leftHeap;
    private final Heap rightHeap;

    public HeapList() {
        leftHeap = new Heap(Heap.ValueOnTop.Max);
        rightHeap = new Heap(Heap.ValueOnTop.Min);
    }

    public Integer getMedian() {
        return median;
    }

    public int size() {
        if (leftHeap.size() == 0) {
            return medianIsItem ? 1 : 0;
        }

        int heapSize = leftHeap.size() + rightHeap.size();
        return medianIsItem ? ++heapSize : heapSize;
    }

    public void add(int item) {
        if (size() == 0) {
            median = item;
            medianIsItem = true;
            return;
        }

        if (medianIsItem) {
            if (item <= median) {
                leftHeap.insert(item);
                rightHeap.insert(median);
            } else {
                rightHeap.insert(item);
                leftHeap.insert(median);
            }
            median = (leftHeap.peek() + rightHeap.peek()) / 2;
            medianIsItem = false;
        } else {
            if (item < median) {
                leftHeap.insert(item);
                median = leftHeap.extract();
            } else {
                rightHeap.insert(item);
                median = rightHeap.extract();
            }
            medianIsItem = true;
        }
    }
}

class Heap {
    private final List<Integer> list;
    private final boolean isMaxHeap;

    public enum ValueOnTop {
        Max, Min
    }

    public Heap(ValueOnTop valueOnTop) {
        list = new ArrayList<>();
        isMaxHeap = valueOnTop.equals(ValueOnTop.Max);
    }

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

    public int peek() {
        return list.get(0);
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
             childNum > 0 && compare(parentNum, childNum);
             childNum = parentNum, parentNum = getParentNumByChildNum(parentNum)) {
            swap(list, parentNum, childNum);
        }
    }

    private void sink() {
        int parentNum = 0;
        while (parentNum * 2 < getIndexOfLastItem()) {
            int childNum = getLeftChildNumByParentNum(parentNum);
            if (childNum < getIndexOfLastItem()
                    && compare(childNum, childNum + 1)) {
                childNum++;
            }

            if (compare(parentNum, childNum)) {
                swap(list, parentNum, childNum);
            } else {
                break;
            }
            parentNum = childNum;
        }
    }

    private boolean compare(int item1, int item2) {
        if (isMaxHeap) {
            return list.get(item1) < list.get(item2);
        }
        return list.get(item1) > list.get(item2);
    }
}
