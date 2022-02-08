package dartsgame.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Wrong target score!")
public class WrongTargetScoreException extends RuntimeException {
    public WrongTargetScoreException() {
        super();
    }
}
