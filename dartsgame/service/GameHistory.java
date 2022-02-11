package dartsgame.service;

import dartsgame.model.GameMove;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GameHistory {
    private final List<GameMove> gameMoves;
    private int moveNumber;

    public GameHistory(List<GameMove> moves) {
        gameMoves = new ArrayList<>(moves);
        moveNumber = 0;
    }

    public void addMove(GameMove gameMove) {
        gameMove.setMove(moveNumber++);
        gameMoves.add(gameMove);
    }

    public GameMove getMove(int move) {
        return gameMoves.get(move);
    }

    public void removeFollowingMoves(int move) {
        gameMoves.removeIf(gameMove -> gameMove.getMove() > move);
    }
}
