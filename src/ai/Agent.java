package ai;

import tictactoe.Board;

public interface Agent {
    int[] chooseMove(Board board);
}
