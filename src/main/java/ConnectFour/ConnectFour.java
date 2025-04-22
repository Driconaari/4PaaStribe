package ConnectFour;

public class ConnectFour {
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private final char[][] board;

    public ConnectFour() {
        board = new char[ROWS][COLS];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                board[r][c] = '-';
            }
        }
    }

    public boolean dropPiece(int col, char player) {
        if (col < 0 || col >= COLS || board[0][col] != '-') return false;
        for (int r = ROWS - 1; r >= 0; r--) {
            if (board[r][col] == '-') {
                board[r][col] = player;
                return true;
            }
        }
        return false;
    }

    public void undoMove(int col) {
        for (int r = 0; r < ROWS; r++) {
            if (board[r][col] != '-') {
                board[r][col] = '-';
                break;
            }
        }
    }

    public boolean checkWin(char player) {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (checkDirection(r, c, player, 1, 0) ||
                        checkDirection(r, c, player, 0, 1) ||
                        checkDirection(r, c, player, 1, 1) ||
                        checkDirection(r, c, player, 1, -1)) return true;
            }
        }
        return false;
    }

    private boolean checkDirection(int r, int c, char p, int dr, int dc) {
        int count = 0;
        while (r >= 0 && r < ROWS && c >= 0 && c < COLS) {
            if (board[r][c] == p) {
                count++;
                if (count == 4) return true;
            } else {
                count = 0;
            }
            r += dr;
            c += dc;
        }
        return false;
    }

    public boolean isBoardFull() {
        for (int c = 0; c < COLS; c++) {
            if (board[0][c] == '-') return false;
        }
        return true;
    }

    public void printBoard() {
        for (char[] row : board) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println("0 1 2 3 4 5 6");
    }

    public boolean isValidMove(int col) {
        return col >= 0 && col < COLS && board[0][col] == '-';
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
