package Pakke2;

import java.util.Scanner;

public class Player {

    public static final Scanner scanner = new Scanner(System.in);


    public static int getPlayerMove() {
        int col;
        while (true) {
            System.out.println("Spiller " + Rules.currentPlayer + ", vælg en kolonne (0-6): ");
            if (scanner.hasNextInt()) {
                col = scanner.nextInt();
                if (col >= 0 && col < Board.COLS) return col;
                else System.out.println("Ugyldig kolonne. Prøv igen.");
            } else {
                scanner.next();
                System.out.println("Indtast venligst et tal.");
            }
        }
    }
}
