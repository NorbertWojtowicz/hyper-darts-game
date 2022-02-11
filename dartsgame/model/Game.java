package dartsgame.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dartsgame.controller.dto.Shot;
import dartsgame.controller.dto.ThrowDto;
import dartsgame.exception.WrongStatusException;
import dartsgame.exception.WrongThrowException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long gameId;
    private String playerOne;
    private String playerTwo;
    private String gameStatus;
    private int playerOneScores;
    private int playerTwoScores;
    private String turn;
    @JsonIgnore
    private boolean gameOver;

    public void throwDarts(int playerNumber, ThrowDto throwDto) {
        int playerTemporaryScore = getPlayerScore(playerNumber);
        throwDto.initShots();
        for (int i = 1; i <= 3; i++) {
            playerTemporaryScore -= throwDto.getShotMultipliedScore(i);
            if (playerWins(playerTemporaryScore, throwDto.getShotMultiplier(i))) {
                // (Checkout) Other shots should be 'none', otherwise throws are wrong
                checkOtherShots(i, throwDto);
                finishGameWithWinner(playerNumber);
                return;
            } else if (playerTemporaryScore <= 0) {
                // (Bust) Other shots should be 'none', otherwise throws are wrong
                checkOtherShots(i, throwDto);
            }
        }
        if (playerTemporaryScore > 1) {
            setPlayerScore(playerNumber, playerTemporaryScore);
        }
        setGameStatus("playing");
        setTurn(getOppositePlayerName(playerNumber));
    }

    private int getPlayerScore(int playerNumber) {
        if (playerNumber == 1) {
            return playerOneScores;
        }
        return playerTwoScores;
    }

    private boolean playerWins(int score, int multiplier) {
        return score == 0 && multiplier == 2;
    }

    private void checkOtherShots(int currentShot, ThrowDto throwDto) {
        for (int i = currentShot + 1; i <= 3; i++) {
            validateOtherShot(throwDto.getShot(i));
        }
    }

    private void validateOtherShot(Shot shot) {
        if (shot.getMultipliedScore() != 0) {
            throw new WrongThrowException();
        }
    }

    private void finishGameWithWinner(int playerNumber) {
        setGameStatus(getPlayerName(playerNumber) + " wins!");
        setPlayerScore(playerNumber, 0);
        setGameOver(true);
    }

    private String getPlayerName(int playerNumber) {
        if (playerNumber == 1) {
            return playerOne;
        }
        return playerTwo;
    }

    private String getOppositePlayerName(int playerNumber) {
        if (playerNumber == 2) {
            return playerOne;
        }
        return playerTwo;
    }

    private void setPlayerScore(int playerNumber, int score) {
        if (playerNumber == 1) {
            setPlayerOneScores(score);
        } else {
            setPlayerTwoScores(score);
        }
    }

    public void cancelGame(String status) {
        validateStatus(status);
        setGameStatus(status);
        setGameOver(true);
    }

    private void validateStatus(String status) {
        String statusPrefix = status.split(" wins!")[0];
        // The status field must have the following format: <"Player Name" or "Nobody"> wins!
        if (!statusPrefix.equals(playerOne) && !statusPrefix.equals(playerTwo) && !statusPrefix.equals("Nobody")) {
            throw new WrongStatusException();
        }
    }

    public static Game fromGameMove(GameMove gameMove) {
        return Game.builder()
                .gameId(gameMove.getGameId())
                .playerOne(gameMove.getPlayerOne())
                .playerTwo(gameMove.getPlayerTwo())
                .gameStatus(gameMove.getGameStatus())
                .playerOneScores(gameMove.getPlayerOneScores())
                .playerTwoScores(gameMove.getPlayerTwoScores())
                .turn(gameMove.getTurn())
                .build();
    }
}
