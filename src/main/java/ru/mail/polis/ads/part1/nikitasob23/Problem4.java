package ru.mail.polis.ads.part1.nikitasob23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.EmptyStackException;
import java.util.StringTokenizer;

public final class Problem4 {
    private static void solve(final FastScanner in, final PrintWriter out) {
        MyStack<Integer> myStack = new MyStack<>();
        boolean isContinue = true;
        while (isContinue) {
            try {
                CommandsEnum command = CommandsEnum.valueOf(in.next());
                switch (command) {
                    case push:
                        int value = in.nextInt();
                        myStack.push(value);
                        out.println(AnswersEnum.ok);
                        break;
                    case back:
                        out.println(myStack.back());
                        break;
                    case pop:
                        out.println(myStack.pop());
                        break;
                    case size:
                        out.println(myStack.size());
                        break;
                    case clear:
                        myStack.clear();
                        out.println(AnswersEnum.ok);
                        break;
                    case exit:
                        isContinue = false;
                        out.println(AnswersEnum.bye);
                        break;
                    default:
                        out.println(AnswersEnum.error);
                }
            } catch (Exception ex) {
                out.println(AnswersEnum.error);
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

enum CommandsEnum {
    push, back, pop, size, clear, exit
}

enum AnswersEnum {
    ok, error, bye
}

class MyStack<Temp> {
    private Node<Temp> head;
    private int size = 0;

    private class Node<Temp> {
        private Temp inf;
        private Node next;

        public Node(Temp inf) {
            this.inf = inf;
        }

        public Node(Temp inf, Node<Temp> next) {
            this.inf = inf;
            this.next = next;
        }

        public Temp getInf() { return inf; }

        public Node<Temp> getNext() { return next; }
    }

    public void push(Temp newItem) {
        Node<Temp> newNode = new Node(newItem, head);
        head = newNode;
        size++;
    }

    public Temp pop() throws EmptyStackException {
        if (size < 1) {
            throw new EmptyStackException();
        }
        Temp headInf = head.getInf();
        head = head.getNext();
        size--;
        return headInf;
    }

    public Temp back() throws EmptyStackException {
        if (size < 1) {
            throw new EmptyStackException();
        }
        return head.getInf();
    }

    public int size() { return size; }

    public void clear() {
        head = null;
        size = 0;
    }
}
