package ru.mail.polis.ads.part1.nikitasob23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public final class Problem5 {

    private static void solve(final FastScanner in, final PrintWriter out) {
        MyQueue<Integer> MyQueue = new MyQueue<>();
        boolean isContinue = true;
        while (isContinue) {
            try {
                CommandsEnum command = CommandsEnum.valueOf(in.next());
                switch (command) {
                    case push:
                        int value = in.nextInt();
                        MyQueue.push(value);
                        out.println(AnswersEnum.ok);
                        break;
                    case front:
                        out.println(MyQueue.front());
                        break;
                    case pop:
                        out.println(MyQueue.pop());
                        break;
                    case size:
                        out.println(MyQueue.size());
                        break;
                    case clear:
                        MyQueue.clear();
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
    push, front, pop, size, clear, exit
}

enum AnswersEnum {
    ok, error, bye
}

class MyQueue<Temp> {
    private Node<Temp> head;
    private Node<Temp> tail;
    private int size = 0;

    private class Node<Temp> {
        private Temp inf;
        private Node prev;
        private Node next;

        public Node(Temp inf) {
            this.inf = inf;
        }

        public Node<Temp> getNext() { return next; }

        public void setPrev(Node<Temp> node) { prev = node; }

        public void setNext(Node<Temp> node) { next = node; }
    }

    public void push(Temp newItem) throws QueueOutOfBoundsException {
        if (size > 100) {
            throw new QueueOutOfBoundsException();
        }
        if (head == null) {
            head = tail = new Node<>(newItem);
        } else {
            Node<Temp> newNode = new Node<>(newItem);
            newNode.setPrev(tail);
            tail.setNext(newNode);
            tail = tail.getNext();
        }
        size++;
    }

    public Temp pop() throws EmptyQueueException {
        if (size < 1) {
            throw new EmptyQueueException();
        }
        Temp headInf = head.inf;
        head = head.next;
        size--;
        return headInf;
    }

    public Temp front() throws EmptyQueueException {
        if (size < 1) {
            throw new EmptyQueueException();
        }
        return head.inf;
    }

    public int size() { return size; }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }
}

class EmptyQueueException extends Exception {
    EmptyQueueException() {
        super();
    }
}

class QueueOutOfBoundsException extends Exception {
    QueueOutOfBoundsException() {
        super();
    }
}
