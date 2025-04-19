package ConnectFour;

public class AI {
    private static final int MAX_DEPTH = 5;

    public int findBestMove(ConnectFour game) {
        int bestMove = -1;
        int bestScore = Integer.MIN_VALUE;

        for (int col = 0; col < game.getCols(); col++) {
            if (game.dropPiece(col, 'R')) { // Try a move
                int score = alphaBeta(game, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                game.undoMove(col); // Undo the move

                if (score > bestScore) {
                    bestScore = score;
                    bestMove = col;
                }
            }
        }
        return bestMove;
    }

    private int alphaBeta(ConnectFour game, int depth, int alpha, int beta, boolean isMaximizing) {
        if (game.checkWin('R')) return 1000 - depth;
        if (game.checkWin('Y')) return -1000 + depth;
        if (game.isBoardFull() || depth == MAX_DEPTH) return 0;

        if (isMaximizing) {
            int maxScore = Integer.MIN_VALUE;
            for (int col = 0; col < game.getCols(); col++) {
                if (game.dropPiece(col, 'R')) {
                    int score = alphaBeta(game, depth + 1, alpha, beta, false);
                    game.undoMove(col);
                    maxScore = Math.max(maxScore, score);
                    alpha = Math.max(alpha, score);
                    if (beta <= alpha) break;
                }
            }
            return maxScore;
        } else {
            int minScore = Integer.MAX_VALUE;
            for (int col = 0; col < game.getCols(); col++) {
                if (game.dropPiece(col, 'Y')) {
                    int score = alphaBeta(game, depth + 1, alpha, beta, true);
                    game.undoMove(col);
                    minScore = Math.min(minScore, score);
                    beta = Math.min(beta, score);
                    if (beta <= alpha) break;
                }
            }
            return minScore;
        }
    }
}