package dartsgame.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnfinishedGameException extends ResponseStatusException {
    public UnfinishedGameException() {
        super(HttpStatus.BAD_REQUEST, "You have an unfinished game!");
    }
}
