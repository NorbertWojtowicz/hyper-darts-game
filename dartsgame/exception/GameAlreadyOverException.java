package dartsgame.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GameAlreadyOverException extends ResponseStatusException {
    public GameAlreadyOverException() {
        super(HttpStatus.BAD_REQUEST, "The game is already over!");
    }
}
