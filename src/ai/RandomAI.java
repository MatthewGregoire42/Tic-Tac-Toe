package ai;

import tictactoe.Board;
import java.lang.Math;
import java.util.ArrayList;

public class RandomAI {

    public RandomAI() {}

    public int[] chooseMove(Board board) {
        int size = board.getSize();
        ArrayList<int[]> legal_moves = new ArrayList<int[]>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board.isLegalMove(i, j)) {
                    int[] move = {i, j};
                    legal_moves.add(move);
                }
            }
        }

        int[] move = legal_moves.get((int) Math.random()*legal_moves.size());
        return move;
    }

}
