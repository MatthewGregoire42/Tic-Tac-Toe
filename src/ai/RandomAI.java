package ai;

import tictactoe.Board;
import java.util.Random;
import java.util.ArrayList;

public class RandomAI {

    private Random randomGenerator;
    public RandomAI() {}

    public int[] chooseMove(Board state) {
        int size = state.getSize();
        Board.Player[][] board = state.getState();
        ArrayList<int[]> legal_moves = new ArrayList<int[]>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (state.isLegalMove(i, j)) {
                    int[] move = {i, j};
                    legal_moves.add(move);
                }
            }
        }
        int index = randomGenerator.nextInt(legal_moves.size());
        return legal_moves.get(index);
    }

}
