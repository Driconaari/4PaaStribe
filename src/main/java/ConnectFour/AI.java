package ConnectFour;

public class AI {
    private static final int MAX_DEPTH = 7; // Increased depth for better lookahead

    // Piece values for evaluation
    private static final int FOUR_IN_A_ROW = 10000;
    private static final int THREE_IN_A_ROW = 100;
    private static final int TWO_IN_A_ROW = 10;
    private static final int ONE_IN_A_ROW = 1;

    public int findBestMove(ConnectFour game) {
        int bestMove = -1;
        int bestScore = Integer.MIN_VALUE;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        // Try center columns first for better move ordering
        int[] columnOrder = getColumnOrder(game.getCols());

        for (int colIndex = 0; colIndex < game.getCols(); colIndex++) {
            int col = columnOrder[colIndex];
            if (game.isValidMove(col)) {
                game.dropPiece(col, 'R'); // Try a move
                int score = alphaBeta(game, MAX_DEPTH, alpha, beta, false);
                game.undoMove(col); // Undo the move

                if (score > bestScore) {
                    bestScore = score;
                    bestMove = col;
                }

                alpha = Math.max(alpha, bestScore);
            }
        }

        // If no valid move found (shouldn't happen in normal play)
        if (bestMove == -1) {
            for (int col = 0; col < game.getCols(); col++) {
                if (game.isValidMove(col)) {
                    return col;
                }
            }
        }

        return bestMove;
    }

    // Get column order with center columns first (better for Connect Four)
    private int[] getColumnOrder(int cols) {
        int[] order = new int[cols];
        int mid = cols / 2;
        order[0] = mid; // Center column first

        for (int i = 1; i < cols; i++) {
            if (i % 2 == 1) {
                order[i] = mid + (i + 1) / 2;
            } else {
                order[i] = mid - i / 2;
            }

            // Ensure we stay within bounds
            if (order[i] >= cols) order[i] = cols - 1;
            if (order[i] < 0) order[i] = 0;
        }

        return order;
    }

    private int alphaBeta(ConnectFour game, int depth, int alpha, int beta, boolean isMaximizing) {
        // Terminal conditions
        if (game.checkWin('R')) return FOUR_IN_A_ROW + depth; // AI wins
        if (game.checkWin('Y')) return -(FOUR_IN_A_ROW + depth); // Human wins
        if (game.isBoardFull() || depth == 0) return evaluateBoard(game);

        int[] columnOrder = getColumnOrder(game.getCols());

        if (isMaximizing) {
            int maxScore = Integer.MIN_VALUE;
            for (int colIndex = 0; colIndex < game.getCols(); colIndex++) {
                int col = columnOrder[colIndex];
                if (game.isValidMove(col)) {
                    game.dropPiece(col, 'R');
                    int score = alphaBeta(game, depth - 1, alpha, beta, false);
                    game.undoMove(col);
                    maxScore = Math.max(maxScore, score);
                    alpha = Math.max(alpha, score);
                    if (beta <= alpha) break; // Alpha-Beta pruning
                }
            }
            return maxScore;
        } else {
            int minScore = Integer.MAX_VALUE;
            for (int colIndex = 0; colIndex < game.getCols(); colIndex++) {
                int col = columnOrder[colIndex];
                if (game.isValidMove(col)) {
                    game.dropPiece(col, 'Y');
                    int score = alphaBeta(game, depth - 1, alpha, beta, true);
                    game.undoMove(col);
                    minScore = Math.min(minScore, score);
                    beta = Math.min(beta, score);
                    if (beta <= alpha) break; // Alpha-Beta pruning
                }
            }
            return minScore;
        }
    }

    // Evaluate the current board state
    private int evaluateBoard(ConnectFour game) {
        int score = 0;
        char[][] board = game.getBoard();
        int rows = game.getRows();
        int cols = game.getCols();

        // Check horizontal, vertical, and diagonal sequences
        score += evaluateLines(board, rows, cols);

        return score;
    }

    private int evaluateLines(char[][] board, int rows, int cols) {
        int score = 0;

        // Horizontal evaluation
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols - 3; col++) {
                score += evaluateWindow(board[row][col], board[row][col+1],
                        board[row][col+2], board[row][col+3]);
            }
        }

        // Vertical evaluation
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows - 3; row++) {
                score += evaluateWindow(board[row][col], board[row+1][col],
                        board[row+2][col], board[row+3][col]);
            }
        }

        // Diagonal (positive slope) evaluation
        for (int row = 0; row < rows - 3; row++) {
            for (int col = 0; col < cols - 3; col++) {
                score += evaluateWindow(board[row][col], board[row+1][col+1],
                        board[row+2][col+2], board[row+3][col+3]);
            }
        }

        // Diagonal (negative slope) evaluation
        for (int row = 3; row < rows; row++) {
            for (int col = 0; col < cols - 3; col++) {
                score += evaluateWindow(board[row][col], board[row-1][col+1],
                        board[row-2][col+2], board[row-3][col+3]);
            }
        }

        return score;
    }

    private int evaluateWindow(char p1, char p2, char p3, char p4) {
        int redCount = 0;
        int yellowCount = 0;
        int emptyCount = 0;

        // Count pieces in the window
        for (char piece : new char[]{p1, p2, p3, p4}) {
            if (piece == 'R') redCount++;
            else if (piece == 'Y') yellowCount++;
            else emptyCount++;
        }

        // Score the window
        if (redCount == 4) return FOUR_IN_A_ROW;
        if (redCount == 3 && emptyCount == 1) return THREE_IN_A_ROW;
        if (redCount == 2 && emptyCount == 2) return TWO_IN_A_ROW;
        if (redCount == 1 && emptyCount == 3) return ONE_IN_A_ROW;

        if (yellowCount == 4) return -FOUR_IN_A_ROW;
        if (yellowCount == 3 && emptyCount == 1) return -THREE_IN_A_ROW;
        if (yellowCount == 2 && emptyCount == 2) return -TWO_IN_A_ROW;
        if (yellowCount == 1 && emptyCount == 3) return -ONE_IN_A_ROW;

        return 0;
    }

    // Set difficulty by adjusting the search depth
    public void setDifficulty(int level) {
        // Adjust MAX_DEPTH based on difficulty level
        // This would need to be implemented as a non-static field
    }
}