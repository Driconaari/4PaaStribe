package Pakke2;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Board.initializeBoard();
        Player.askForTimeLimit(); // Spørg om tidsgrænse
        Player.askIfPlayerStarts(); // Spørg om du vil starte

        while (true) {
            Board.displayBoard();

            long startTime = System.currentTimeMillis(); // Start tidtagning

            int col;
            if (Rules.currentPlayer == 'X') {
                col = Player.getPlayerMove();
            } else {
                col = AI.getBestMove();
                System.out.println("AI tog et træk kolonne: " + col); //viser bare hvilken kolonne AI valgte
            }

            long endTime = System.currentTimeMillis(); // Stop tidtagning
            long moveTime = endTime - startTime; // Beregn tid brugt på at lave træk
            System.out.println("Tid brugt på at lave træk: " + moveTime + " ms"); // Udskriv tid brugt på træk

            // Kontrollér om trækket overskred tidsgrænsen
            if (moveTime > Player.timeLimit) {
                double exceededTime = (moveTime - Player.timeLimit) / 1000.0; // Konverter til sekunder
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
}
