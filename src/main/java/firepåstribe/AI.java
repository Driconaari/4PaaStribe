package firepåstribe;

public class AI {

    private static final int MAX_DEPTH = 10; // Hvor mange træk AI prøver at "se ud i fremtiden". Jo højere jo smarter og langsommere
    private static final int[] preferredOrder = {3, 2, 4, 1, 5, 0, 6}; // AI starter fra midten til siderne

    // AI'ens hovedfunktion
    public static int getBestMove() { // Metoden handler om, at finde ud af hvilken kolonne AI skal spille i
        int bestScore = Integer.MIN_VALUE; // Vi starter med en meget lav "bedste score"
        int bestCol = -1; // "ingen kolonne valgt endnu"-værdi.

        for (int col : preferredOrder) { // Gå igennem kolonner i den rækkefølge vi foretrækker
            if (Board.board[0][col] == ' ') { // Tjek om der er plads i kolonnen (øverste felt er tomt)
                int row = Rules.dropPiece(col, 'O'); // Midlertidigt læg AI’ens brik ('O') i kolonnen
                int score = minimax(0, false, Integer.MIN_VALUE, Integer.MAX_VALUE); // Kør minimax-algoritmen for at vurdere hvor godt dette træk er. Nu er det modstanderens tur, alpha og beta bruges til at optimere søgning
                Board.board[row][col] = ' '; // Fjern brikken igen bagefter (det var kun en simulation)
                if (score > bestScore) { // Hvis den score vi fik er bedre end tidligere, gem den som den nye bedste
                    bestScore = score;
                    bestCol = col;
                }
            }
        }
        return bestCol; // Når vi har tjekket alle kolonner, returnerer vi den bedste kolonne at spille i
    }

    // AI'ens tænkehjerne
    private static int minimax(int depth, boolean isMaximizing, int alpha, int beta) { // Rekursive metode, som AI'en bruger til at tænke fremad og analysere spillet
        if (Rules.checkWinSimulated('O')) return 100 - depth; // Hvis AI har vundet – giv høj score (100), og træk depth fra, så hurtige sejre er bedre
        if (Rules.checkWinSimulated('X')) return -100 + depth; // Hvis spilleren har vundet – dårlig score. AI skal undgå det
        if (Rules.isDraw() || depth == MAX_DEPTH) return evaluateBoard('O') - evaluateBoard('X'); // Hvis det er uafgjort, eller vi har tænkt dybt nok, så returner vi heuristikken

        if (isMaximizing) { // AI's tur. Her leder vi efter det bedst mulige træk for AI'en ('O')
            int maxEval = Integer.MIN_VALUE;
            for (int col : preferredOrder) { // Prøv alle kolonner i foretrukken rækkefølge
                if (Board.board[0][col] == ' ') { // Simuler AI’ens træk, kald minimax() igen for modstanderen, og fjern brikken bagefter
                    int row = Rules.dropPiece(col, 'O');
                    int eval = minimax(depth + 1, false, alpha, beta);
                    Board.board[row][col] = ' ';
                    maxEval = Math.max(maxEval, eval); // Gem det højeste resultat, og opdater alpha
                    alpha = Math.max(alpha, eval);
                    if (beta <= alpha) break; // Hvis fundet en bedre mulighed end modstanderen kan acceptere, så stop søgningen (Alpha-Beta pruning).
                }
            }
            return maxEval; // Returner den bedste score vi kunne opnå som AI
        } else { // Spillerens tur (minimizing player). Nu gælder det om at finde det værst mulige træk for AI (spilleren prøver at vinde)
            int minEval = Integer.MAX_VALUE;
            for (int col = 0; col < Board.COLS; col++) { // Gå igennem kolonner
                if (Board.board[0][col] == ' ') { //  Simuler spillerens træk, kald minimax() igen for AI, og fjern brikken bagefter
                    int row = Rules.dropPiece(col, 'X');
                    int eval = minimax(depth + 1, true, alpha, beta);
                    Board.board[row][col] = ' ';
                    minEval = Math.min(minEval, eval); // Returner den værste score (for AI), dvs. spilleren prøver at minimere AI’ens chancer
                    beta = Math.min(beta, eval);
                    if (beta <= alpha) break;
                }
            }
            return minEval;
        }
    }

    // Heuristik til at vurdere brættet
    private static int evaluateBoard(char symbol) {
        int score = 0;
        score += countSequences(symbol, 3) * 50; // Beløn 3 på stribe
        score += countSequences(symbol, 2) * 15; // Beløn 2 på stribe
        for (int r = 0; r < Board.ROWS; r++) {    // Beløn for kontrol af midten
            if (Board.board[r][3] == symbol) score += 6;
        }
        return score;
    }

    // Tæller hvor mange sekvenser der er af længde 'length' for et givet symbol
    private static int countSequences(char symbol, int length) {
        int count = 0;
        for (int r = 0; r < Board.ROWS; r++) {
            for (int c = 0; c < Board.COLS; c++) {
                if (Board.board[r][c] == symbol) {
                    if (checkSequence(r, c, 0, 1, symbol, length)) count++;  // Vandret
                    if (checkSequence(r, c, 1, 0, symbol, length)) count++;  // Lodret
                    if (checkSequence(r, c, 1, 1, symbol, length)) count++;  // Diagonal fra venstre op til højre ned
                    if (checkSequence(r, c, 1, -1, symbol, length)) count++; // Diagonal fra højre op til venstre ned
                }
            }
        }
        return count;
    }

    // Tjekker om der er en sekvens af længde 'length' i en given retning
    private static boolean checkSequence(int row, int col, int rowDir, int colDir, char symbol, int length) {
        int count = 0;
        for (int i = 1; i < length; i++) {
            int r = row + i * rowDir;
            int c = col + i * colDir;
            if (r < 0 || r >= Board.ROWS || c < 0 || c >= Board.COLS) break;
            if (Board.board[r][c] == symbol) {
                count++;
            } else {
                break;
            }
        }
        return count == length - 1;
    }
}