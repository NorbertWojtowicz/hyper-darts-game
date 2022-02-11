package dartsgame.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GameOverException extends ResponseStatusException {
    public GameOverException() {
        super(HttpStatus.BAD_REQUEST, "The game is over!");
    }
}
