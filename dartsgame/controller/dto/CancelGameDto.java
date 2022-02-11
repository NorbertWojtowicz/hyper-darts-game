package dartsgame.controller.dto;

import lombok.Data;

@Data
public class CancelGameDto {
    private int gameId;
    private String status;
}
