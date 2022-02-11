package dartsgame.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NothingToRevertException extends ResponseStatusException {
    public NothingToRevertException() {
        super(HttpStatus.BAD_REQUEST, "There is nothing to revert!");
    }
}
