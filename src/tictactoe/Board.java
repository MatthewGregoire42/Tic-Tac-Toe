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
    private Player turn; // The player who needs to go next.
    private HashMap<Player, AgentType> identities;

    public Board(int size) {
        this.size = size;
        this.board = new Player[size][size];
        this.turn = Player.X;
        this.identities = new HashMap<Player, AgentType>();
    }

    private Board(int size, Player[][] board, Player turn) {
        this.size = size;
        this.board = board;
        this.turn = turn;
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
        if (turn.equals(Player.X)) {
            turn = Player.O;
        } else {
            turn = Player.X;
        }
    }

    public Player findWinner() {
        Player curr = board[0][0];
        for (int i = 0; i < size; i++) {
            // check for horizontal wins
            curr = board[i][0];
            if (curr != null) {
                for (int j = 1; j < size; j++) {
                    if (curr != board[i][j]) {
                        break;
                    } else if (j + 1 == size) {
                        return curr;
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
                        return curr;
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
                    return curr;
                }
            }
        }
        curr = board[0][size-1];
        if (curr != null) {
            for (int i = 0; i < size; i++) {
                if (curr != board[i][size-1-i]) {
                    break;
                } else if (i + 1 == size) {
                    return curr;
                }
            }
        }
        return null;
    }

    public boolean isOver() {
        Player winner = findWinner();
        if (winner != null) {
            return true;
        } else {
            // If there's no winner and there are still empty
            // squares remaining, the game is not over.
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (board[i][j] == null) {
                        return false;
                    }
                }
            }
            return true;
        }
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

    public void setPlayer(Player key, AgentType value) {
        identities.put(key, value);
    }

    public AgentType whoHasTheTurn() {
        return identities.get(turn);
    }

    public Board copy() {
        Player[][] copiedBoard = new Player[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                copiedBoard[i][j] = board[i][j];
            }
        }
        return new Board(size, copiedBoard, turn);
    }

}
