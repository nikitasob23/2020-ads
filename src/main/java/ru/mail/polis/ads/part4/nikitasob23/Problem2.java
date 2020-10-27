package ru.mail.polis.ads.part4.nikitasob23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import static sun.swing.MenuItemLayoutHelper.max;

public final class Problem2 {
    private static final char MOVE_RIGHT = 'R';
    private static final char MOVE_UP = 'F';

    private static void solve(final FastScanner in, final PrintWriter out) {
        int[][] matrix = getInputMatrix(in);
        matrix = getMatrixOfMoves(matrix);
        out.println(findBestMove(matrix));
    }

    private static int[][] getInputMatrix(final FastScanner in) {
        int numOfRows = in.nextInt();
        int numOfColumns = in.nextInt();
        int[][] matrix = new int[numOfRows][numOfColumns];
        for (int row = 0; row < numOfRows; row++) {
            for (int column = 0; column < numOfColumns; column++) {
                matrix[row][column] = in.nextInt();
            }
        }
        return matrix;
    }
    
    private static int[][] getMatrixOfMoves(int[][] matrix) {
        final int firstColumn = 0;
        for (int row = matrix.length - 2; row >= 0; row--) {
            matrix[row][firstColumn] += matrix[row+1][firstColumn];
        }

        final int lastRow = matrix.length - 1;
        for (int column = 1; column < matrix[0].length; column++) {
            matrix[lastRow][column] += matrix[lastRow][column-1];
        }

        for (int row = matrix.length-2; row >= 0; row--) {
            for (int column = 1; column < matrix[0].length; column++) {
                matrix[row][column] += max(matrix[row+1][column], matrix[row][column-1]);
            }
        }
        return matrix;
    }

    private static String findBestMove(int[][] matrix) {
        StringBuilder move = new StringBuilder();
        int row = 0;
        int column = matrix[0].length - 1;
        while (row <= matrix.length - 2 && column >= 1) {
            if (matrix[row+1][column] <= matrix[row][column-1]) {
                move.append(MOVE_RIGHT);
                column--;
            } else {
                move.append(MOVE_UP);
                row++;
            }
        }
        for (int num = row+1; num < matrix.length; num++) {
            move.append(MOVE_UP);
        }
        for (int num = column-1; num >= 0; num--) {
            move.append(MOVE_RIGHT);
        }
        return move.reverse().toString();
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
