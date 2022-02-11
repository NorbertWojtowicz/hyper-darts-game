package dartsgame.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CantJoinGameException extends ResponseStatusException {
    public CantJoinGameException() {
        super(HttpStatus.BAD_REQUEST, "You can't join the game!");
    }
}
