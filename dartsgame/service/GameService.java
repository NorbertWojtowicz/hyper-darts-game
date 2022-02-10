package dartsgame.service;

import dartsgame.controller.dto.TargetScoreDto;
import dartsgame.controller.dto.ThrowDto;
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
        Game unfinishedGame = gameRepository.findUnfinishedGameForPlayer(playerOne);
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

    public Game getCurrentOrLastGame(String name) {
        Game currentGame = gameRepository.findCurrentGame(name);
        if (currentGame == null) {
            currentGame = findLastGame(name);
        }
        if (currentGame == null) {
            throw new GameNotExistEmptyBodyException();
        }
        return currentGame;
    }

    private Game findLastGame(String name) {
        List<Game> games = gameRepository.findLastGames(name);
        if (games.size() == 0) {
            return null;
        }
        return games.get(0);
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
        Game unfinishedGame = gameRepository.findUnfinishedGameForPlayer(player);
        checkIfUnfinishedGameExist(unfinishedGame);
    }

    private void checkIfGameExist(Game game) {
        if (game == null) {
            throw new GameNotExistException();
        }
    }

    @Transactional
    public Game throwDart(ThrowDto throwDto, String player) {
        Game game = getCurrentGame(player);
        if (game == null) {
            throw new NoGamesAvailableException();
        }
        if (!game.getTurn().equals(player)) {
            throw new WrongTurnException();
        }
        int playerNumber = game.getPlayerOne().equals(player) ? 1 : 2;
        game.throwDarts(playerNumber, throwDto);
        return game;
    }

    private Game getCurrentGame(String player) {
        return gameRepository.findCurrentGame(player);
    }
}
