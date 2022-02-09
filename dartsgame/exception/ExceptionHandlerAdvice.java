package dartsgame.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(GameNotExistEmptyBodyException.class)
    public ResponseEntity<?> handleGameNotExistEmptyBodyException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of());
    }

    @ExceptionHandler(GamesNotExistEmptyBodyException.class)
    public ResponseEntity<?> handleGamesNotExistEmptyBodyException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleGameExceptions(ResponseStatusException e) {
        return ResponseEntity.status(e.getStatus()).body(Map.of("result", e.getReason()));
    }

}
