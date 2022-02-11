package dartsgame.service;

import dartsgame.model.Game;
import dartsgame.model.GameMove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GamesHistoriesManager {
    private final Map<Long, GameHistory> gameHistoryMap = new HashMap<>();

    @Autowired
    private GameMoveService gameMoveService;

    public void addMove(Game game) {
        GameHistory gameHistory = getGameHistory(game.getGameId());
        GameMove gameMove = createGameMove(game);
        gameHistory.addMove(gameMove);
        gameMoveService.save(gameMove);
    }

    public GameHistory getGameHistory(long gameId) {
        GameHistory gameHistory = gameHistoryMap.get(gameId);
        if (gameHistory == null) {
            gameHistory = new GameHistory(gameMoveService.getMovesByGameId(gameId));
            gameHistoryMap.put(gameId, gameHistory);
        }
        return gameHistory;
    }

    private GameMove createGameMove(Game game) {
        return GameMove.builder()
                .gameId(game.getGameId())
                .playerOne(game.getPlayerOne())
                .playerTwo(game.getPlayerTwo())
                .gameStatus(game.getGameStatus())
                .playerOneScores(game.getPlayerOneScores())
                .playerTwoScores(game.getPlayerTwoScores())
                .turn(game.getTurn())
                .build();
    }

    public GameMove getRollbackState(int gameId, int move) {
        return getMove(gameId, move);
    }

    private GameMove getMove(int gameId, int move) {
        GameHistory gameHistory = getGameHistory(gameId);
        removeFollowingMovesFromGameHistory(move, gameHistory, gameId);
        return gameHistory.getMove(move);
    }


    private void removeFollowingMovesFromGameHistory(int move, GameHistory gameHistory, int gameId) {
        gameHistory.removeFollowingMoves(move);
        gameMoveService.removeFollowingMovesInGame(move, gameId);
    }

    public List<GameMove> getMovesForGameId(Long gameId) {
        return gameMoveService.getMovesByGameId(gameId);
    }
}
