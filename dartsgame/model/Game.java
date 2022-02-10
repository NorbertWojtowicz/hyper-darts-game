package dartsgame.model;

import dartsgame.controller.dto.Shot;
import dartsgame.controller.dto.ThrowDto;
import dartsgame.exception.WrongThrowException;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
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

    public void setPlayersScores(int playersScores) {
        this.playerOneScores = playersScores;
        this.playerTwoScores = playersScores;
    }

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
}
