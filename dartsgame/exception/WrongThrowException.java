package dartsgame.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class WrongThrowException extends ResponseStatusException {
    public WrongThrowException() {
        super(HttpStatus.BAD_REQUEST, "Wrong throws!");
    }
}
