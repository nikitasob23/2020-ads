package ru.mail.polis.ads.part1.nikitasob23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public final class Problem3 {
    private static void solve(final FastScanner in, final PrintWriter out) {
        String inputLine = in.next();
        int numOfLeftBracket = 0;
        int numOfRightBracket = 0;
        for (int charNum = 0; charNum < inputLine.length(); charNum++) {
            char bracket = inputLine.charAt(charNum);
            if (bracket == '(') {
                numOfLeftBracket++;
            } else if (bracket == ')') {
                numOfRightBracket++;
            }

            if (numOfLeftBracket < numOfRightBracket) {
                break;
            }
        }
        out.print(numOfLeftBracket == numOfRightBracket ? "YES" : "NO");
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
