package dartsgame.service;

import dartsgame.controller.dto.TargetScoreDto;
import dartsgame.repository.GameRepository;
import dartsgame.model.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.status;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;

    public ResponseEntity<?> createGame(TargetScoreDto targetScore, String playerOne) {
        Game unfinishedGame = gameRepository.findGameByPlayerOneLikeOrPlayerTwoLikeAndGameStatusNotLike(playerOne, playerOne, "created");
        if (unfinishedGame != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("result", "You have an unfinished game!"));
        }
        Game newGame = new Game();
        if (!targetScore.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("result", "Wrong target score!"));
        }
        newGame.setGameStatus("created");
        newGame.setPlayerOne(playerOne);
        newGame.setPlayerTwo("");
        newGame.setPlayersScores(targetScore.getTargetScore());
        newGame.setTurn(playerOne);
        return ResponseEntity.ok(gameRepository.save(newGame));
    }

    public ResponseEntity<?> getCurrentGame(String name) {
        Game currentGame = gameRepository.findGameByPlayerOneLikeAndGameStatusNotLike(name, "%wins!");
        if (currentGame == null) {
            return status(HttpStatus.NOT_FOUND).body(Map.of());
        }
        return ResponseEntity.ok(currentGame);
    }

    public ResponseEntity<?> getAllGames() {
        List<Game> games = gameRepository.findAllByOrderByGameIdDesc();
        if (games.size() == 0) {
            return status(HttpStatus.NOT_FOUND).body(List.of());
        }
        return ResponseEntity.ok(games);
    }

    @Transactional
    public ResponseEntity<?> joinGame(Long gameId, String player) {
        Game game = gameRepository.findGameByGameId(gameId);
        ResponseEntity<Map<String, String>> error = validateGameAndPlayer(game, player);
        if (error != null) {
            return error;
        }
        game.setGameStatus("started");
        game.setPlayerTwo(player);
        return ResponseEntity.ok(game);
    }

    public ResponseEntity<Map<String, String>> validateGameAndPlayer(Game game, String player) {
        if (game == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("result", "Game not found!"));
        }
        if (game.getPlayerOne().equals(player)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("result", "You can't play alone!"));
        }
        if (!game.getGameStatus().equals("created")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("result", "You can't join the game!"));
        }
        Game unfinishedGame = gameRepository.findGameByPlayerOneLikeOrPlayerTwoLikeAndGameStatusNotLike(player, player, "created");
        if (unfinishedGame != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("result", "You have an unfinished game!"));
        }
        return null;
    }
}
