package ai;

import tictactoe.Board;

public interface Agent {
    public int[] chooseMove(Board board);
}
