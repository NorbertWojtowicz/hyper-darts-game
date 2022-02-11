package dartsgame.service;

import dartsgame.controller.dto.RollBackDto;
import dartsgame.controller.dto.TargetScoreDto;
import dartsgame.controller.dto.ThrowDto;
import dartsgame.exception.*;
import dartsgame.model.GameMove;
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
    private final GamesHistoriesManager gamesHistoriesManager;

    public Game createGame(TargetScoreDto targetScore, String playerOne) {
        Game unfinishedGame = gameRepository.findUnfinishedGameForPlayer(playerOne);
        targetScore.validate();
        checkIfUnfinishedGameExist(unfinishedGame);
        Game newGame = Game.builder()
                .gameStatus("created")
                .playerOne(playerOne)
                .playerTwo("")
                .playerOneScores(targetScore.getTargetScore())
                .playerTwoScores(targetScore.getTargetScore())
                .turn(playerOne)
                .build();
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
        gamesHistoriesManager.addMove(game);
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
        gamesHistoriesManager.addMove(game);
        return game;
    }

    private Game getCurrentGame(String player) {
        return gameRepository.findCurrentGame(player);
    }

    @Transactional
    public Game cancelGame(String status, int gameId) {
        Game game = gameRepository.findGameByGameId(gameId);
        checkIfGameExist(game);
        checkIfGameIsAlreadyOver(game);
        game.cancelGame(status);
        return game;
    }

    private void checkIfGameIsAlreadyOver(Game game) {
        if (game.isGameOver()) {
            throw new GameAlreadyOverException();
        }
    }

    @Transactional
    public Game rollbackToState(RollBackDto rollbackDto) {
        Game rollbackGame = gameRepository.findGameByGameId(rollbackDto.getGameId());
        checkIfGameExist(rollbackGame);
        checkIfMoveIsFound(rollbackDto);
        checkIfGameIsOver(rollbackGame);
        GameMove rollbackGameMove = gamesHistoriesManager.
                getRollbackState(rollbackDto.getGameId(), rollbackDto.getMove());
        rollbackGame = Game.fromGameMove(rollbackGameMove);
        return rollbackGame;
    }

    private void checkIfGameIsOver(Game game) {
        if (game.isGameOver()) {
            throw new GameOverException();
        }
    }

    private void checkIfMoveIsFound(RollBackDto rollbackDto) {
        GameHistory gameHistory = gamesHistoriesManager.getGameHistory(rollbackDto.getGameId());
        if (gameHistory.getMoveNumber() <= rollbackDto.getMove()) {
            throw new MoveNotFoundException();
        }
        if (gameHistory.getMoveNumber() - 1 == rollbackDto.getMove()) {
            throw new NothingToRevertException();
        }
    }

    public List<GameMove> getMovesForGameId(Long gameId) {
        checkIfGameExist(gameRepository.findGameByGameId(gameId));
        return gamesHistoriesManager.getMovesForGameId(gameId);
    }
}
