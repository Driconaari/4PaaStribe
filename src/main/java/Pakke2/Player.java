package Pakke2;

import java.util.Scanner;

public class Player {
    public static long timeLimit; // Tidsgrænse i millisekunder
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

    // Spørger spilleren om de vil starte (ja/nej). Du er altid X, AI er altid O.
    public static void askIfPlayerStarts() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Vil du starte? (ja/nej): ");
            String svar = scanner.nextLine().trim().toLowerCase();

            if (svar.equals("ja")) {
                Rules.currentPlayer = 'X'; // Spiller starter
                System.out.println("Du starter som X.");
                break;
            } else if (svar.equals("nej")) {
                Rules.currentPlayer = 'O'; // AI starter
                System.out.println("AI starter som O.");
                break;
            } else {
                System.out.println("Ugyldigt svar. Skriv 'ja' eller 'nej'.");
            }
        }
    }

    public static void askForTimeLimit() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Indtast tidsgrænsen for et træk i sekunder: ");
        int seconds = scanner.nextInt();
        timeLimit = seconds * 1000L; // Konverter til millisekunder
        System.out.println("Tidsgrænsen er sat til " + seconds + " sekunder.");
    }
}
