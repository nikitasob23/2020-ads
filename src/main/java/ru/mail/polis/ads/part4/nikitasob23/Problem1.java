package ru.mail.polis.ads.part4.nikitasob23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public final class Problem1 {

    private static void solve(final FastScanner in, final PrintWriter out) throws IOException {
        String brackets = in.reader.readLine();
        if (brackets.equals("")) {
            return;
        }
        BracketRecover bracketRecover = new BracketRecover(brackets);
        bracketRecover.recover();
        out.println(bracketRecover.get());
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

class BracketRecover {
    private StringBuilder brackets;
    private int[][] matrix;
    private Integer[][] split;
    private boolean isRecovered;

    public BracketRecover(String brackets) {
        if (brackets.equals("")) {
            throw new IllegalArgumentException();
        }
        this.brackets = new StringBuilder(brackets);
        matrix = new int[brackets.length()][brackets.length()];
        split = new Integer[brackets.length()][brackets.length()];
        isRecovered = false;
    }

    public String get() {
        return brackets.toString();
    }

    public void recover() {
        if (isRecovered) {
            return;
        }
        setDynamicMatrix();
        List<Integer> recoverBracketList = getRecoverBracketsList(0, size()-1, new ArrayList<>());
        recover(recoverBracketList);
        matrix = null;
        split = null;
        isRecovered = true;
    }

    public int size() {
        return brackets.length();
    }

    private void recover(List<Integer> recoverList) {
        int offset = 0;
        for (int bracketNum : recoverList) {
            switch (brackets.charAt(bracketNum+offset)) {
                case '(':
                    brackets.insert(bracketNum+offset+1, ')');
                    break;
                case ')':
                    brackets.insert(bracketNum+offset, '(');
                    break;
                case '[':
                    brackets.insert(bracketNum+offset+1, ']');
                    break;
                case ']':
                    brackets.insert(bracketNum+offset, '[');
                    break;
            }
            offset++;
        }
    }

    private void setDynamicMatrix() {
        for (int main = 0; main < matrix.length; main++) {
            matrix[main][main] = 1;
        }
        for (int startColumn = 1; startColumn < matrix[0].length; startColumn++) {
            for (int row = 0, column = startColumn; column < matrix[0].length; row++, column++) {
                matrix[row][column] = getMinNumOfRecoveries(row, column);
            }
        }
    }

    private int getMinNumOfRecoveries(int row, int column) {
        int min = bracketsIsEquals(row, column) ? matrix[row + 1][column - 1] : Integer.MAX_VALUE;
        for (int offset = 0; row + offset + 1 <= column; offset++) {
            int repairPrice = matrix[row][row + offset] + matrix[row + offset + 1][column];
            if (repairPrice < min) {
                min = repairPrice;
                split[row][column] = row + offset;
            }
        }
        return min;
    }

    private List<Integer> getRecoverBracketsList(int row, int column, List<Integer> recoverList) {
        if (matrix[row][column] == 0) {
            return recoverList;
        }
        if (row == column) {
            recoverList.add(column);
            return recoverList;
        }
        if (split[row][column] == null) {
            recoverList = getRecoverBracketsList(row+1, column-1, recoverList);
        } else {
            recoverList = getRecoverBracketsList(row, split[row][column], recoverList);
            recoverList = getRecoverBracketsList(split[row][column]+1, column, recoverList);
        }
        return recoverList;
    }

    public boolean bracketsIsEquals(int num1, int num2) {
        if (brackets.charAt(num1) == '(' && brackets.charAt(num2) == ')'
                || brackets.charAt(num1) == '[' && brackets.charAt(num2) == ']') {
            return true;
        }
        return false;
    }
}
