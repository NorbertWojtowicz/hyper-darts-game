package dartsgame.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GamesNotExistEmptyBodyException extends ResponseStatusException {
    public GamesNotExistEmptyBodyException() {
        super(HttpStatus.NOT_FOUND);
    }
}
