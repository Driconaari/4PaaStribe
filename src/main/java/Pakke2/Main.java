package Pakke2;

import java.util.Scanner;

public class Main {

    private static long timeLimit; // Tidsgrænse i millisekunder

    public static void main(String[] args) {

        Board.initializeBoard();
        askForTimeLimit(); // Spørg om tidsgrænse
        Player.askIfPlayerStarts(); // Spørg om du vil starte


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

            // Kontrollér om trækket overskred tidsgrænsen
            if (moveTime > timeLimit) {
                double exceededTime = (moveTime - timeLimit) / 1000.0; // Konverter til sekunder
                System.out.printf("Spiller %c har overskredet tidsgrænsen med %.2f sekunder! Turen bliver sprunget over.%n",
                        Rules.currentPlayer, exceededTime);
                Rules.currentPlayer = (Rules.currentPlayer == 'X') ? 'O' : 'X'; // Skift spiller
                continue; // Spring til næste iteration af løkken
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
    private static void askForTimeLimit() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Indtast tidsgrænsen for et træk i sekunder: ");
        int seconds = scanner.nextInt();
        timeLimit = seconds * 1000L; // Konverter til millisekunder
        System.out.println("Tidsgrænsen er sat til " + seconds + " sekunder.");
    }
}
