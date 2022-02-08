package dartsgame.controller.dto;

import lombok.Data;

@Data
public class TargetScoreDto {
    private int targetScore;

    public boolean isValid() {
        return targetScore == 101 ||
                targetScore == 301 ||
                targetScore == 501;
    }
}
