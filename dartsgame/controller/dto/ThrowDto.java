package dartsgame.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ThrowDto {
    private String first;
    private String second;
    private String third;

    @JsonIgnore
    private Map<Integer, Shot> shots = new HashMap<>();

    // Needs to be called first, before other methods
    public void initShots() {
        shots.put(1, Shot.create(1, first));
        shots.put(2, Shot.create(2, second));
        shots.put(3, Shot.create(3, third));
    }

    public int getShotMultipliedScore(int shotNumber) {
        return getShot(shotNumber).getMultipliedScore();
    }

    public int getShotMultiplier(int shotNumber) {
        return getShot(shotNumber).getMultiplier();
    }

    public Shot getShot(int number) {
        return shots.get(number);
    }
}
