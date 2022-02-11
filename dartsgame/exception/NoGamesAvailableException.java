package dartsgame.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoGamesAvailableException extends ResponseStatusException {
    public NoGamesAvailableException() {
        super(HttpStatus.NOT_FOUND, "There are no games available!");
    }
}
