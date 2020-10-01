package ru.mail.polis.ads.part1.nikitasob23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public final class Problem2 {
    private static void solve(final FastScanner in, final PrintWriter out) throws IOException {
        int numOfLines = in.nextInt();
        Stack<Node> nodeStack = new Stack<>();
        for (int lineNum = 0; lineNum < numOfLines; lineNum++) {
            char[] inputDataArray = in.next().toCharArray();

            for (char item : inputDataArray) {
                if (Character.isLowerCase(item)) {
                    nodeStack.push(new Node(item));
                } else {
                    Node newNode = new Node(item, nodeStack.pop(), nodeStack.pop());
                    nodeStack.push(newNode);
                }
            }
            if (nodeStack.size() != 1) {
                throw new IOException();
            }

            Stack<Character> resultStack = breadthSearchTree(nodeStack.pop());
            int resultStackSize = resultStack.size();
            for (int charNum = 0; charNum < resultStackSize; charNum++) {
                out.print(resultStack.pop());
            }
            out.println();
        }
    }

    private static Stack<Character> breadthSearchTree(Node root) {
        Stack<Character> resultStack = new Stack<>();
        MyQueue<Node> myQueue = new MyQueue<>();
        myQueue.push(root);
        while (myQueue.size() != 0) {
            Node queueNodeElem = myQueue.pop();
            if (queueNodeElem.getLeft() != null) {
                myQueue.push(queueNodeElem.getRight());
                myQueue.push(queueNodeElem.getLeft());
            }
            resultStack.push(queueNodeElem.getElem());
        }

        return resultStack;
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

class Node {
    private char elem;
    private Node left;
    private Node right;

    Node(char elem) {
        this.elem = elem;
        this.left = null;
        this.right = null;
    }

    Node(char elem, Node left, Node right) {
        this.elem = elem;
        this.left = left;
        this.right = right;
    }

    public char getElem() { return elem; }

    public Node getLeft() { return left; }

    public Node getRight() { return right; }
}

class MyQueue<Temp> {
    private Queue<Temp> queue = new LinkedList<>();

    public Temp pop() throws NoSuchElementException {
        return queue.remove();
    }

    public void push(Temp temp) {
        boolean isException = !queue.offer(temp);
        if (isException) {
            throw new IllegalArgumentException();
        }
    }

    public int size() {
        return queue.size();
    }

    public boolean isExists() {
        return queue.size() != 0;
    }
}
