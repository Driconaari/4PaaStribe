package Pakke2;

public class Main {
    public static void main(String[] args) {
        Board.initializeBoard(); // Metoden fra Board klassen

        while (true) {
            Board.displayBoard(); // Metoden fra Board klassen
            int col = Player.getPlayerMove(); // Metoden fra Player klassen

            int row = Rules.dropPiece(col); // Metoden fra Rules Klassen
            if (row == -1) {
                System.out.println("Kolonnen er fuld. Vælg en anden.");
                continue;
            }

            if (Rules.checkWin(row, col)) { // Metoden fra Rules klassen
                Board.displayBoard(); // Metoden fra Board klassen
                System.out.println("Spiller " + Rules.currentPlayer + " vinder!"); // Metoden fra Rules klassen
                break;
            }

            if (Rules.isDraw()) { // Metoden fra Rules klassen
                Board.displayBoard(); // Metoden fra Board klassen
                System.out.println("Spillet ender uafgjort!");
                break;
            }

            Rules.currentPlayer = (Rules.currentPlayer == 'X') ? 'O' : 'X'; // Metoden fra Rules klassen
        }
    }
}
