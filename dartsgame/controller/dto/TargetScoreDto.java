package dartsgame.controller.dto;

import dartsgame.exception.WrongTargetScoreException;
import lombok.Data;

@Data
public class TargetScoreDto {
    private int targetScore;

    public void validate() {
        if (targetScore != 101 &&
                targetScore != 301 &&
                targetScore != 501) {
            throw new WrongTargetScoreException();
        }
    }
}
