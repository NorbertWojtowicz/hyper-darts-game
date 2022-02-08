package dartsgame.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Game not found!")
public class GameNotExistException extends RuntimeException {
    public GameNotExistException() {
        super();
    }
}
