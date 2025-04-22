package ConnectFour;

public class ConnectFour {
    private final Board board;
    private char currentPlayer;

    public ConnectFour() {
        board = new Board();
        currentPlayer = 'Y'; // Yellow starts
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == 'Y') ? 'R' : 'Y';
    }

    public void printBoard() {
        board.printBoard();
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean dropPiece(int col) {
        return board.dropPiece(col, currentPlayer);
    }

    public boolean checkWin() {
        return board.checkWin(currentPlayer);
    }

    public boolean isBoardFull() {
        return board.isFull();
    }

    public boolean isValidMove(int col) {
        return board.isValidMove(col);
    }

    public void undoMove(int col) {
        board.undoMove(col);
    }
}