package ConnectFour;

public class AI {
    private static final int MAX_DEPTH = 6;
    private static final int WIN_SCORE = 100000;

    public int findBestMove(Board board) {
        int bestMove = -1;
        int bestScore = Integer.MIN_VALUE;

        for (int col = 0; col < board.getCols(); col++) {
            if (board.isValidMove(col)) {
                board.dropPiece(col, 'R');
                int score = alphaBeta(board, MAX_DEPTH - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                board.undoMove(col);
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = col;
                }
            }
        }

        return bestMove;
    }

    private int alphaBeta(Board board, int depth, int alpha, int beta, boolean isMaximizing) {
        if (depth == 0 || board.checkWin('R') || board.checkWin('Y') || board.isFull()) {
            return evaluateBoard(board);
        }

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (int col = 0; col < board.getCols(); col++) {
                if (board.isValidMove(col)) {
                    board.dropPiece(col, 'R');
                    int eval = alphaBeta(board, depth - 1, alpha, beta, false);
                    board.undoMove(col);
                    maxEval = Math.max(maxEval, eval);
                    alpha = Math.max(alpha, eval);
                    if (beta <= alpha) break;
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int col = 0; col < board.getCols(); col++) {
                if (board.isValidMove(col)) {
                    board.dropPiece(col, 'Y');
                    int eval = alphaBeta(board, depth - 1, alpha, beta, true);
                    board.undoMove(col);
                    minEval = Math.min(minEval, eval);
                    beta = Math.min(beta, eval);
                    if (beta <= alpha) break;
                }
            }
            return minEval;
        }
    }

    private int evaluateBoard(Board board) {
        int score = 0;
        char[][] b = board.getBoard();

        // For simplicity, score +1 for each 'R', -1 for each 'Y'
        for (char[] row : b) {
            for (char cell : row) {
                if (cell == 'R') score += 1;
                else if (cell == 'Y') score -= 1;
            }
        }

        if (board.checkWin('R')) return WIN_SCORE;
        if (board.checkWin('Y')) return -WIN_SCORE;

        return score;
    }
}