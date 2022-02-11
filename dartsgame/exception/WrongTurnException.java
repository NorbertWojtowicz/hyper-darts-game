package dartsgame.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class WrongTurnException extends ResponseStatusException {
    public WrongTurnException() {
        super(HttpStatus.BAD_REQUEST, "Wrong turn!");
    }
}
