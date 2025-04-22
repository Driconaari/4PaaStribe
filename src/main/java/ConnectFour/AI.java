package ConnectFour;

public class AI {
    private static final int MAX_DEPTH = 6;
    private static final int WIN_SCORE = 100000;

    public int findBestMove(ConnectFour game) {
        int bestMove = -1;
        int bestScore = Integer.MIN_VALUE;

        for (int col = 0; col < game.getCols(); col++) {
            if (game.isValidMove(col)) {
                game.dropPiece(col, 'R');
                int score = alphaBeta(game, MAX_DEPTH - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                game.undoMove(col);
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = col;
                }
            }
        }

        return bestMove;
    }

    private int alphaBeta(ConnectFour game, int depth, int alpha, int beta, boolean isMaximizing) {
        if (depth == 0 || game.checkWin('R') || game.checkWin('Y') || game.isBoardFull()) {
            return evaluateBoard(game);
        }

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (int col = 0; col < game.getCols(); col++) {
                if (game.isValidMove(col)) {
                    game.dropPiece(col, 'R');
                    int eval = alphaBeta(game, depth - 1, alpha, beta, false);
                    game.undoMove(col);
                    maxEval = Math.max(maxEval, eval);
                    alpha = Math.max(alpha, eval);
                    if (beta <= alpha) break;
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int col = 0; col < game.getCols(); col++) {
                if (game.isValidMove(col)) {
                    game.dropPiece(col, 'Y');
                    int eval = alphaBeta(game, depth - 1, alpha, beta, true);
                    game.undoMove(col);
                    minEval = Math.min(minEval, eval);
                    beta = Math.min(beta, eval);
                    if (beta <= alpha) break;
                }
            }
            return minEval;
        }
    }

    private int evaluateBoard(ConnectFour game) {
        int score = 0;
        char[][] b = game.getBoard();

        // For simplicity, score +1 for each 'R', -1 for each 'Y'
        for (char[] row : b) {
            for (char cell : row) {
                if (cell == 'R') score += 1;
                else if (cell == 'Y') score -= 1;
            }
        }

        if (game.checkWin('R')) return WIN_SCORE;
        if (game.checkWin('Y')) return -WIN_SCORE;

        return score;
    }
}
