package ConnectFour;

import java.util.Scanner;

public class Game {
    private final Scanner scanner;
    private final Board board;
    private final AI ai;

    public Game(Scanner scanner) {
        this.scanner = scanner;
        this.board = new Board();
        this.ai = new AI();
    }

    public void run() {
        System.out.println("\u001B[34mVelkommen til 4 på stribe!\u001B[0m");
        System.out.println("\u001B[33mDu er Gul (Y), AI er Rød (R)\u001B[0m");

        boolean playerStarts = askPlayerFirst();
        char currentPlayer = playerStarts ? 'Y' : 'R';

        while (true) {
            board.print();

            if (currentPlayer == 'R') {
                int move = getAIMove();
                board.dropPiece(move, 'R');
            } else {
                int move = getPlayerMove();
                board.dropPiece(move, 'Y');
            }

            if (board.checkWin(currentPlayer)) {
                board.print();
                announceWinner(currentPlayer);
                break;
            }

            if (board.isFull()) {
                board.print();
                System.out.println("\u001B[36mDet er uafgjort!\u001B[0m");
                break;
            }

            currentPlayer = (currentPlayer == 'R') ? 'Y' : 'R';
        }
    }

    private boolean askPlayerFirst() {
        System.out.print("Vil du starte? (y/n): ");
        return scanner.nextLine().trim().toLowerCase().startsWith("y");
    }

    private int getPlayerMove() {
        while (true) {
            System.out.print("\u001B[33mDin tur (indtast kolonne 0-6): \u001B[0m");
            try {
                int move = Integer.parseInt(scanner.nextLine());
                if (board.isValidMove(move)) {
                    return move;
                }
                System.out.println("\u001B[31mUgyldigt træk, prøv igen.\u001B[0m");
            } catch (Exception e) {
                System.out.println("\u001B[31mIndtast venligst et gyldigt tal.\u001B[0m");
            }
        }
    }

    private int getAIMove() {
        System.out.println("\u001B[35mAI tænker...\u001B[0m");
        long start = System.currentTimeMillis();
        int move = ai.findBestMove(board);
        long end = System.currentTimeMillis();
        System.out.printf("\u001B[31mAI vælger kolonne %d (%d ms)\u001B[0m\n", move, end - start);
        return move;
    }

    private void announceWinner(char player) {
        if (player == 'R') {
            System.out.println("\u001B[31mAI vinder!\u001B[0m");
        } else {
            System.out.println("\u001B[33mTillykke, du vinder!\u001B[0m");
        }
    }
}