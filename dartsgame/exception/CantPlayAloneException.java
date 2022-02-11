package dartsgame.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CantPlayAloneException extends ResponseStatusException {
    public CantPlayAloneException() {
        super(HttpStatus.BAD_REQUEST, "You can't play alone!");
    }
}
