package ConnectFour;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ConnectFour game = new ConnectFour();
        AI ai = new AI();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Connect Four!");
        System.out.println("You are Yellow (Y), AI is Red (R)");
        System.out.println("Enter a column number (0-6) to drop your piece");

        boolean playerFirst = askPlayerFirst(scanner);

        if (!playerFirst) {
            game.switchPlayer(); // Make AI go first
        }

        while (true) {
            game.printBoard();

            if (game.getCurrentPlayer() == 'R') {
                // AI's turn
                System.out.println("AI is thinking...");
                long startTime = System.currentTimeMillis();
                int move = ai.findBestMove(game);
                long endTime = System.currentTimeMillis();

                game.dropPiece(move, 'R');
                System.out.println("AI placed a piece in column: " + move +
                        " (took " + (endTime - startTime) + "ms)");
            } else {
                // Player's turn
                int move = getPlayerMove(scanner, game);
                game.dropPiece(move, 'Y');
            }

            if (game.checkWin(game.getCurrentPlayer())) {
                game.printBoard();
                if (game.getCurrentPlayer() == 'R') {
                    System.out.println("AI wins! Better luck next time.");
                } else {
                    System.out.println("Congratulations! You win!");
                }
                break;
            }

            if (game.isBoardFull()) {
                game.printBoard();
                System.out.println("It's a draw!");
                break;
            }

            game.switchPlayer();
        }

        // Ask to play again
        System.out.println("Play again? (y/n)");
        String playAgain = scanner.next().toLowerCase();
        if (playAgain.startsWith("y")) {
            main(args); // Restart the game
        } else {
            System.out.println("Thanks for playing!");
        }
    }

    private static boolean askPlayerFirst(Scanner scanner) {
        System.out.println("Do you want to go first? (y/n)");
        String response = scanner.next().toLowerCase();
        return response.startsWith("y");
    }

    private static int getPlayerMove(Scanner scanner, ConnectFour game) {
        int move;
        while (true) {
            System.out.print("Your move (column 0-6): ");
            try {
                move = scanner.nextInt();
                if (move >= 0 && move < game.getCols() && game.isValidMove(move)) {
                    return move;
                } else {
                    System.out.println("Invalid move. Column must be between 0-6 and not full.");
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine(); // Clear the input buffer
            }
        }
    }
}