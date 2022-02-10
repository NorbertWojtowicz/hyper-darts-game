package dartsgame.controller.dto;

import dartsgame.exception.WrongThrowException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shot {
    private int number;
    private int multiplier;
    private int multipliedScore;

    public static Shot create(int number, String shot) {
        // 'none' means shot didn't happen
        if ("none".equals(shot)) {
            return new Shot(0, 0, 0);
        }
        String[] multiplierScore = shot.split(":");
        int multiplier = Integer.parseInt(multiplierScore[0]);
        int score = Integer.parseInt(multiplierScore[1]);
        validateScoreAndMultiplier(score, multiplier);
        return new Shot(number, multiplier, multiplier * score);
    }

    private static void validateScoreAndMultiplier(int score, int multiplier) {
        if (score == 25 && multiplier != 2) {
            throw new WrongThrowException();
        }
        if (score != 25 && score > 20 || score < 0) {
            throw new WrongThrowException();
        }
        if (multiplier > 3 || multiplier < 1) {
            throw new WrongThrowException();
        }
    }
}
