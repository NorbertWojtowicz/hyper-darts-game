package dartsgame.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MoveNotFoundException extends ResponseStatusException {
    public MoveNotFoundException() {
        super(HttpStatus.BAD_REQUEST, "Move not found!");
    }
}
