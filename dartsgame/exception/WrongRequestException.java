package dartsgame.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class WrongRequestException extends ResponseStatusException {
    public WrongRequestException() {
        super(HttpStatus.BAD_REQUEST, "Wrong request!");
    }
}
