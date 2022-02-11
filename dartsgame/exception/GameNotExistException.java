package dartsgame.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GameNotExistException extends ResponseStatusException {
    public GameNotExistException() {
        super(HttpStatus.NOT_FOUND, "Game not found!");
    }
}
