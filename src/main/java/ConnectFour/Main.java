package ConnectFour;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ConnectFour game = new ConnectFour();
        AI ai = new AI();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            game.printBoard();

            if (game.getCurrentPlayer() == 'R') {
                // AI's tur
                int move = ai.findBestMove(game);
                game.dropPiece(move, 'R');
                System.out.println("AI placerer brik i kolonne: " + move);
            } else {
                // Modstanderens tur (manuel indtastning)
                System.out.print("Indtast kolonne for modstanderens træk: ");
                int move = scanner.nextInt();
                if (!game.dropPiece(move, 'Y')) {
                    System.out.println("Ugyldigt træk, prøv igen.");
                    continue;
                }
            }

            if (game.checkWin(game.getCurrentPlayer())) {
                game.printBoard();
                System.out.println("Spiller " + game.getCurrentPlayer() + " vandt!");
                break;
            }

            if (game.isBoardFull()) {
                game.printBoard();
                System.out.println("Uafgjort!");
                break;
            }

            game.switchPlayer();
        }
    }
}