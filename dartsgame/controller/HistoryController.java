package dartsgame.controller;

import dartsgame.exception.WrongRequestException;
import dartsgame.model.GameMove;
import dartsgame.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HistoryController {

    @Autowired
    private GameService gameService;

    @GetMapping("api/history/{gameIdStr}")
    public ResponseEntity<List<GameMove>> getMovesForGameId(@PathVariable String gameIdStr) {
        Long gameId = castPathVariableToLong(gameIdStr);
        return ResponseEntity
                .ok(gameService.getMovesForGameId(gameId));
    }

    public Long castPathVariableToLong(String gameIdStr) {
        long gameId;
        try {
            gameId = Long.parseLong(gameIdStr);
        } catch (Exception e) {
            throw new WrongRequestException();
        }
        if (gameId < 0) {
            throw new WrongRequestException();
        }
        return gameId;
    }
}
