package dartsgame.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class WrongStatusException extends ResponseStatusException {
    public WrongStatusException() {
        super(HttpStatus.BAD_REQUEST, "Wrong status!");
    }
}
