package Pakke2;

public class Main {
    public static void main(String[] args) {
        Board.initializeBoard();
        Rules.askIfPlayerStarts(); // Spørg om du vil starte

        while (true) {
            Board.displayBoard();

            long startTime = System.currentTimeMillis(); // Start tidtagning

            int col;
            if (Rules.currentPlayer == 'X') {
                col = Player.getPlayerMove();
            } else {
                col = AI.getBestMove();
            }

            long endTime = System.currentTimeMillis(); // Stop tidtagning
            long moveTime = endTime - startTime; // Beregn tid brugt på at lave træk
            System.out.println("Tid brugt på at lave træk: " + moveTime + " ms"); // Udskriv tid brugt på træk

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
