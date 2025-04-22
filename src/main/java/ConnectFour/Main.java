package ConnectFour;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConnectFour board = new ConnectFour();
        AI ai = new AI();

        System.out.println("Velkommen til Connect Four AI vs AI!");
        System.out.print("Vil du lade AI starte? (ja/nej): ");
        String svar = scanner.nextLine().trim().toLowerCase();

        boolean aiStarts = svar.equals("ja");
        char aiChar = aiStarts ? 'R' : 'Y';
        char opponentChar = aiStarts ? 'Y' : 'R';

        while (true) {
            board.printBoard();

            if (aiStarts) {
                // AI's tur
                System.out.println("AI tænker...");
                int aiMove = ai.findBestMove(board);
                board.dropPiece(aiMove, aiChar);
                System.out.println("AI spiller kolonne: " + aiMove);

                if (board.checkWin(aiChar)) {
                    board.printBoard();
                    System.out.println("AI vinder!");
                    break;
                }

                if (board.isBoardFull()) {
                    board.printBoard();
                    System.out.println("Uafgjort!");
                    break;
                }

                aiStarts = false; // Kun første gang
            } else {
                // Modstanderens tur
                System.out.print("Modstanderens træk (" + opponentChar + "): ");
                int opponentMove = scanner.nextInt();
                if (!board.dropPiece(opponentMove, opponentChar)) {
                    System.out.println("Ugyldigt træk. Prøv igen.");
                    continue;
                }

                if (board.checkWin(opponentChar)) {
                    board.printBoard();
                    System.out.println("Modstanderen vinder!");
                    break;
                }

                if (board.isBoardFull()) {
                    board.printBoard();
                    System.out.println("Uafgjort!");
                    break;
                }

                // AI's tur
                System.out.println("AI tænker...");
                int aiMove = ai.findBestMove(board);
                board.dropPiece(aiMove, aiChar);
                System.out.println("AI spiller kolonne: " + aiMove);

                if (board.checkWin(aiChar)) {
                    board.printBoard();
                    System.out.println("AI vinder!");
                    break;
                }

                if (board.isBoardFull()) {
                    board.printBoard();
                    System.out.println("Uafgjort!");
                    break;
                }
            }
        }

        scanner.close();
    }
}
