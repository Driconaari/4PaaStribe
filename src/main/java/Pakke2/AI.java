package Pakke2;

public class AI {

    private static final int MAX_DEPTH = 5;

    public static int getBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int bestCol = -1;

        for (int col = 0; col < Board.COLS; col++) {
            if (Board.board[0][col] == ' ') {
                int row = Rules.dropPiece(col, 'O');
                int score = minimax(0, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
                Board.board[row][col] = ' ';
                if (score > bestScore) {
                    bestScore = score;
                    bestCol = col;
                }
            }
        }
        return bestCol;
    }

    private static int minimax(int depth, boolean isMaximizing, int alpha, int beta) {
        if (Rules.checkWinSimulated('O')) return 100 - depth;
        if (Rules.checkWinSimulated('X')) return -100 + depth;
        if (Rules.isDraw() || depth == MAX_DEPTH) return 0;

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (int col = 0; col < Board.COLS; col++) {
                if (Board.board[0][col] == ' ') {
                    int row = Rules.dropPiece(col, 'O');
                    int eval = minimax(depth + 1, false, alpha, beta);
                    Board.board[row][col] = ' ';
                    maxEval = Math.max(maxEval, eval);
                    alpha = Math.max(alpha, eval);
                    if (beta <= alpha) break;
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int col = 0; col < Board.COLS; col++) {
                if (Board.board[0][col] == ' ') {
                    int row = Rules.dropPiece(col, 'X');
                    int eval = minimax(depth + 1, true, alpha, beta);
                    Board.board[row][col] = ' ';
                    minEval = Math.min(minEval, eval);
                    beta = Math.min(beta, eval);
                    if (beta <= alpha) break;
                }
            }
            return minEval;
        }
    }
}