package Pakke2;

public class Main {
    public static void main(String[] args) {
        Board.initializeBoard();
        Rules.askIfPlayerStarts(); // Spørg om du vil starte

        while (true) {
            Board.displayBoard();

            int col;
            if (Rules.currentPlayer == 'X') {
                col = Player.getPlayerMove();
            } else {
                col = AI.getBestMove();
            }

            int row = Rules.dropPiece(col, Rules.currentPlayer);
            if (row == -1) {
                System.out.println("Kolonnen er fuld. Vælg en anden.");
                continue;
            }

            if (Rules.checkWin(row, col)) {
                Board.displayBoard();
                System.out.println("Spiller " + Rules.currentPlayer + " vinder!");
                break;
            }

            if (Rules.isDraw()) {
                Board.displayBoard();
                System.out.println("Spillet ender uafgjort!");
                break;
            }

            Rules.currentPlayer = (Rules.currentPlayer == 'X') ? 'O' : 'X';
        }
    }
}
