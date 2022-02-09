package dartsgame.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class WrongTargetScoreException extends ResponseStatusException {
    public WrongTargetScoreException() {
        super(HttpStatus.BAD_REQUEST, "Wrong target score!");
    }
}
