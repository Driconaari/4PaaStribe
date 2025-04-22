package ConnectFour;

public class Board {
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private final char[][] board;
    private final int[] heights;

    public Board() {
        board = new char[ROWS][COLS];
        heights = new int[COLS];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                board[row][col] = '-';
            }
        }
        for (int col = 0; col < COLS; col++) {
            heights[col] = ROWS;
        }
    }

    public void printBoard() {
        System.out.println("\nCurrent Board:");
        for (int row = 0; row < ROWS; row++) {
            System.out.print("| ");
            for (int col = 0; col < COLS; col++) {
                System.out.print(board[row][col] + " | ");
            }
            System.out.println();
        }
        System.out.print("  ");
        for (int col = 0; col < COLS; col++) {
            System.out.print(col + "   ");
        }
        System.out.println("\n");
    }

    public boolean dropPiece(int col, char player) {
        if (!isValidMove(col)) return false;
        heights[col]--;
        board[heights[col]][col] = player;
        return true;
    }

    public void undoMove(int col) {
        if (heights[col] < ROWS) {
            board[heights[col]][col] = '-';
            heights[col]++;
        }
    }

    public boolean isValidMove(int col) {
        return col >= 0 && col < COLS && heights[col] > 0;
    }

    public boolean isFull() {
        for (int col = 0; col < COLS; col++) {
            if (heights[col] > 0) return false;
        }
        return true;
    }

    public boolean checkWin(char player) {
        // Horizontal, vertical, and diagonal checks
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                if (board[row][col] == player && board[row][col + 1] == player &&
                        board[row][col + 2] == player && board[row][col + 3] == player) {
                    return true;
                }
            }
        }
        for (int col = 0; col < COLS; col++) {
            for (int row = 0; row < ROWS - 3; row++) {
                if (board[row][col] == player && board[row + 1][col] == player &&
                        board[row + 2][col] == player && board[row + 3][col] == player) {
                    return true;
                }
            }
        }
        for (int row = 0; row < ROWS - 3; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                if (board[row][col] == player && board[row + 1][col + 1] == player &&
                        board[row + 2][col + 2] == player && board[row + 3][col + 3] == player) {
                    return true;
                }
            }
        }
        for (int row = 3; row < ROWS; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                if (board[row][col] == player && board[row - 1][col + 1] == player &&
                        board[row - 2][col + 2] == player && board[row - 3][col + 3] == player) {
                    return true;
                }
            }
        }
        return false;
    }

    public char[][] getBoard() {
        return board;
    }

    public int getRows() {
        return ROWS;
    }

    public int getCols() {
        return COLS;
    }
}