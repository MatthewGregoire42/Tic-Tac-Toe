package tictactoe;

import java.util.HashMap;

public class Board {

    public enum Player {
        X, O;
    }

    public enum AgentType {
        HUMAN, BOT;
    }

    private int size;
    private Player[][] board;
    // Who needs to go next
    private Player turn;
    private HashMap<Player, AgentType> identities;

    public Board(int size) {

        this.size = size;
        this.board = new Player[size][size];
        this.turn = Player.X;

        this.identities = new HashMap<Player, AgentType>();
    }

    private Board(int _size, Player[][] _board, Player _turn) {
        size = _size;
        board = _board;
        turn = _turn;
    }

    public boolean isLegalMove(int x, int y) {
        return board[x][y] == null;
    }

    public void applyMove(int x, int y) {
        if (isLegalMove(x, y)) {
            board[x][y] = turn;
        } else {
            throw new RuntimeException("Attempted illegal move at " + x + ", " + y);
        }
        if (turn == Player.X) {
            turn = Player.O;
        } else {
            turn = Player.X;
        }
    }

    public boolean isOver() {
        Player curr = board[0][0];
        for (int i = 0; i < size; i++) {
            // check for horizontal wins
            curr = board[i][0];
            if (curr != null) {
                for (int j = 1; j < size; j++) {
                    if (curr != board[i][j]) {
                        break;
                    } else if (j + 1 == size) {
                        return true;
                    }
                }
            }
            // check for vertical wins
            curr = board[0][i];
            if (curr != null) {
                for (int j = 1; j < size; j++) {
                    if (curr != board[j][i]) {
                        break;
                    } else if (j + 1 == size) {
                        return true;
                    }
                }
            }
        }
        // check the two diagonals for wins
        curr = board[0][0];
        if (curr != null) {
            for (int i = 0; i < size; i++) {
                if (curr != board[i][i]) {
                    break;
                } else if (i + 1 == size) {
                    return true;
                }
            }
        }
        curr = board[0][size-1];
        if (curr != null) {
            for (int i = 0; i < size; i++) {
                if (curr != board[i][size-1-i]) {
                    break;
                } else if (i + 1 == size) {
                    return true;
                }
            }
        }
        return false;
    }

    public Player[][] getState() {
        return board;
    }

    public int getSize() {
        return size;
    }

    public Player getTurn() {
        return turn;
    }

    public HashMap<Player, AgentType> getIdentities() {
        return identities;
    }

    public void setPlayer(Player key, AgentType value) {
        identities.put(key, value);
    }

    public AgentType whoHasTheTurn() {
        return identities.get(turn);
    }

    public Board copy() {
        return new Board(size, board, turn);
    }

}
