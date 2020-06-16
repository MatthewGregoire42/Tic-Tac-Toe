package ai;

import tictactoe.Board;
import tictactoe.Board.*;

import java.util.ArrayList;
import java.util.List;

public class MinimaxAI implements Agent {

    @Override
    public int[] chooseMove(Board board) {
        Player whoseTurn = board.getTurn();
        List<int[]> legalMoves = findLegalMoves(board);

        int[] choice = new int[0];

        if (whoseTurn == Player.X) {
            int maxEval = Integer.MIN_VALUE;
            List<Integer> evals = new ArrayList<>();
            for (int[] move : legalMoves) {
                Board appliedMove = board.copy();
                appliedMove.applyMove(move[0], move[1]);
                int eval = minimax(appliedMove, appliedMove.getTurn());
                evals.add(eval);
                maxEval = Math.max(maxEval, eval);
            }
            choice = legalMoves.get(evals.indexOf(maxEval));
        } else {
            int minEval = Integer.MAX_VALUE;
            List<Integer> evals = new ArrayList<>();
            for (int[] move : legalMoves) {
                Board appliedMove = board.copy();
                appliedMove.applyMove(move[0], move[1]);
                int eval = minimax(appliedMove, appliedMove.getTurn());
                evals.add(eval);
                minEval = Math.min(minEval, eval);
            }
            choice = legalMoves.get(evals.indexOf(minEval));
        }
        return choice;
    }

    // Returns an evaluation of a given board position.
    // X is the maximizing player, and
    // O is the minimizing player.
    private int minimax(Board game, Player player) {

        // Static evaluation of the board position.
        if (game.isOver()) {
            Player won = game.findWinner();
            int eval = 0;
            if (won == Player.X) {
                eval = 1;
            } else if (won == Player.O) {
                eval = -1;
            }
            return eval;
        }

        if (player == Player.X) {
            int maxEval = Integer.MIN_VALUE;
            List<int[]> children = findLegalMoves(game);
            for (int[] move : children) {
                Board appliedMove = game.copy();
                appliedMove.applyMove(move[0], move[1]);
                int eval = minimax(appliedMove, appliedMove.getTurn());
                maxEval = Math.max(maxEval, eval);
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            List<int[]> children = findLegalMoves(game);
            for (int[] move : children) {
                Board appliedMove = game.copy();
                appliedMove.applyMove(move[0], move[1]);
                int eval = minimax(appliedMove, appliedMove.getTurn());
                minEval = Math.min(minEval, eval);
            }
            return minEval;
        }
    }

    private List<int[]> findLegalMoves(Board board) {
        int s = board.getSize();
        List<int[]> output = new ArrayList<int[]>();
        for (int i = 0; i < s; i++) {
            for (int j = 0; j < s; j++) {
                if (board.isLegalMove(i, j)) {
                    int[] move = {i, j};
                    output.add(move);
                }
            }
        }
        return output;
    }
}
