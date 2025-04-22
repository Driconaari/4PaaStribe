package Pakke2;

public class Rules {
    public static char currentPlayer = 'X';

    public static int dropPiece(int col) {
        for (int r = Board.ROWS - 1; r >= 0; r--) {
            if (Board.board[r][col] == ' ') {
                Board.board[r][col] = currentPlayer;
                return r;
            }
        }
        return -1; // Kolonne er fuld
    }

    public static boolean checkWin(int row, int col) {
        return (countConsecutive(row, col, 1, 0) + countConsecutive(row, col, -1, 0) >= 3) || // Lodret
                (countConsecutive(row, col, 0, 1) + countConsecutive(row, col, 0, -1) >= 3) || // Vandret
                (countConsecutive(row, col, 1, 1) + countConsecutive(row, col, -1, -1) >= 3) || // Diagonal /
                (countConsecutive(row, col, 1, -1) + countConsecutive(row, col, -1, 1) >= 3);   // Diagonal \
    }

    public static int countConsecutive(int row, int col, int rowDir, int colDir) {
        int count = 0; // Antal ens symboler vi finder i træk
        char symbol = Board.board[row][col]; // Gemmer symbolet på den brik der lige blev lagt (X eller O)
        int r = row + rowDir; // Starter med at gå et skridt væk fra startposition
        int c = col + colDir; // Starter med at gå et skridt væk fra startposition

        while (r >= 0 && r < Board.ROWS && c >= 0 && c < Board.COLS && Board.board[r][c] == symbol /* Der ligger en brik med samme symbol*/) { // Må ikke gå ud over arrayet
            count++; // Tæller en brik mere
            r += rowDir; // Går videre i samme retning
            c += colDir; // Går videre i samme retning
        }
        return count; // Returnere hvor mange brikker i træk
    }

    public static boolean isDraw() {
        for (int c = 0; c < Board.COLS; c++) {
            if (Board.board[0][c] == ' ') return false;
        }
        return true;
    }
}
