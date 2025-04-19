package ConnectFour;

public class ConnectFour {
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private char[][] board;
    private char currentPlayer;

    public ConnectFour() {
        board = new char[ROWS][COLS];
        currentPlayer = 'R'; // Rød starter
        initializeBoard();
    }

    private void initializeBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                board[row][col] = '-';
            }
        }
    }

    public boolean dropPiece(int col, char player) {
        if (col < 0 || col >= COLS || board[0][col] != '-') {
            return false; // Ugyldigt træk
        }
        for (int row = ROWS - 1; row >= 0; row--) {
            if (board[row][col] == '-') {
                board[row][col] = player;
                return true;
            }
        }
        return false;
    }

    public boolean checkWin(char player) {
        // Tjek rækker, kolonner og diagonaler
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (checkDirection(row, col, player, 1, 0) || // Lodret
                        checkDirection(row, col, player, 0, 1) || // Vandret
                        checkDirection(row, col, player, 1, 1) || // Diagonal ned mod højre
                        checkDirection(row, col, player, 1, -1)) { // Diagonal ned mod venstre
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDirection(int row, int col, char player, int dRow, int dCol) {
        int count = 0;
        while (row >= 0 && row < ROWS && col >= 0 && col < COLS) {
            if (board[row][col] == player) {
                count++;
                if (count == 4) return true;
            } else {
                count = 0;
            }
            row += dRow;
            col += dCol;
        }
        return false;
    }

    public boolean isBoardFull() {
        for (int col = 0; col < COLS; col++) {
            if (board[0][col] == '-') return false;
        }
        return true;
    }

    public void printBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println("0 1 2 3 4 5 6"); // Kolonneindeks
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == 'R') ? 'Y' : 'R';
    }

    public int getCols() {
    return COLS;
}

public void undoMove(int col) {
    for (int row = 0; row < ROWS; row++) {
        if (board[row][col] != '-') { // Assuming '-' represents an empty cell
            board[row][col] = '-';
            break;
        }
    }
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
}