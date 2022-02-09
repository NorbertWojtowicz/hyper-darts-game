package dartsgame.service;

import dartsgame.controller.dto.TargetScoreDto;
import dartsgame.exception.*;
import dartsgame.repository.GameRepository;
import dartsgame.model.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;

    public Game createGame(TargetScoreDto targetScore, String playerOne) {
        Game unfinishedGame = gameRepository.findGameByPlayerOneLikeOrPlayerTwoLikeAndGameStatusNotLike(playerOne, playerOne, "created");
        targetScore.validate();
        checkIfUnfinishedGameExist(unfinishedGame);
        Game newGame = new Game();
        newGame.setGameStatus("created");
        newGame.setPlayerOne(playerOne);
        newGame.setPlayerTwo("");
        newGame.setPlayersScores(targetScore.getTargetScore());
        newGame.setTurn(playerOne);
        return gameRepository.save(newGame);
    }

    private void checkIfUnfinishedGameExist(Game unfinishedGame) {
        if (unfinishedGame != null) {
            throw new UnfinishedGameException();
        }
    }

    public Game getCurrentGame(String name) {
        Game currentGame = gameRepository.findGameByPlayerOneLikeAndGameStatusNotLike(name, "%wins!");
        if (currentGame == null) {
            throw new GameNotExistEmptyBodyException();
        }
        return currentGame;
    }

    public List<Game> getAllGames() {
        List<Game> games = gameRepository.findAllByOrderByGameIdDesc();
        if (games.size() == 0) {
            throw new GamesNotExistEmptyBodyException();
        }
        return games;
    }

    @Transactional
    public Game joinGame(Long gameId, String player) {
        Game game = gameRepository.findGameByGameId(gameId);
        validateGameAndPlayer(game, player);
        game.setGameStatus("started");
        game.setPlayerTwo(player);
        return game;
    }

    public void validateGameAndPlayer(Game game, String player) {
        checkIfGameExist(game);
        if (game.getPlayerOne().equals(player)) {
            throw new CantPlayAloneException();
        }
        if (!game.getGameStatus().equals("created")) {
            throw new CantJoinGameException();
        }
        Game unfinishedGame = gameRepository.findGameByPlayerOneLikeOrPlayerTwoLikeAndGameStatusNotLike(player, player, "created");
        checkIfUnfinishedGameExist(unfinishedGame);
    }

    private void checkIfGameExist(Game game) {
        if (game == null) {
            throw new GameNotExistException();
        }
    }
}
