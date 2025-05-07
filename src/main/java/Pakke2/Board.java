package Pakke2;

public class Board {

    public static final int ROWS = 6;
    public static final int COLS = 7;
    public static final char[][] board = new char[ROWS][COLS]; // Opretter 2D char array

    public static void initializeBoard() {
        for (int r = 0; r < ROWS; r++)
            for (int c = 0; c < COLS; c++)
                board[r][c] = ' ';
    }

    public static void displayBoard() {
        System.out.println();
        for (int r = 0; r < ROWS; r++) {
            System.out.print("|");
            for (int c = 0; c < COLS; c++) {
                System.out.print(" " + board[r][c] + " |");
            }
            System.out.println();
        }
        System.out.println("-----------------------------");
        System.out.println("  0   1   2   3   4   5   6  ");
    }
}
