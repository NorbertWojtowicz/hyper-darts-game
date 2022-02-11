package dartsgame.controller;

import dartsgame.controller.dto.CancelGameDto;
import dartsgame.controller.dto.RollBackDto;
import dartsgame.controller.dto.TargetScoreDto;
import dartsgame.controller.dto.ThrowDto;
import dartsgame.model.Game;
import dartsgame.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping("/create")
    public ResponseEntity<Game> createGame(Authentication auth,
                                           @RequestBody TargetScoreDto targetScoreDto) {
        return ResponseEntity
                .ok(gameService.createGame(targetScoreDto, auth.getName()));
    }

    @GetMapping("/list")
    public ResponseEntity<List<Game>> getListOfGames() {
        return ResponseEntity
                .ok(gameService.getAllGames());
    }

    @GetMapping("/join/{gameId}")
    public ResponseEntity<Game> joinGame(Authentication auth,
                                         @PathVariable Long gameId) {
        return ResponseEntity
                .ok(gameService.joinGame(gameId, auth.getName()));
    }

    @GetMapping("/status")
    public ResponseEntity<Game> getGameStatus(Authentication auth) {
        return ResponseEntity
                .ok(gameService.getCurrentOrLastGame(auth.getName()));
    }

    @PostMapping("/throws")
    public ResponseEntity<Game> throwDart(Authentication auth,
                                          @RequestBody ThrowDto throwDto) {
        return ResponseEntity
                .ok(gameService.throwDart(throwDto, auth.getName()));
    }

    @PutMapping("/cancel")
    public ResponseEntity<Game> cancelGame(@RequestBody CancelGameDto cancelGameDto) {
        return ResponseEntity
                .ok(gameService.cancelGame(cancelGameDto.getStatus(), cancelGameDto.getGameId()));
    }

    @PutMapping("/revert")
    public ResponseEntity<Game> rollbackToState(@RequestBody RollBackDto rollbackDto) {
        return ResponseEntity
                .ok(gameService.rollbackToState(rollbackDto));
    }
}
