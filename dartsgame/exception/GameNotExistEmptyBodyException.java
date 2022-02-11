package dartsgame.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GameNotExistEmptyBodyException extends ResponseStatusException {
    public GameNotExistEmptyBodyException() {
        super(HttpStatus.NOT_FOUND);
    }
}
