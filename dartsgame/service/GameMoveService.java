package dartsgame.service;

import dartsgame.model.GameMove;
import dartsgame.repository.GameMoveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameMoveService {
    private final GameMoveRepository gameMoveRepository;

    public List<GameMove> getMovesByGameId(long gameId) {
        return gameMoveRepository.findGameMovesByGameId(gameId);
    }

    public void removeFollowingMovesInGame(int move, long gameId) {
        gameMoveRepository.removeAllByGameIdEqualsAndMoveGreaterThan(gameId, move);
    }

    public void save(GameMove gameMove) {
        gameMoveRepository.save(gameMove);
    }
}
