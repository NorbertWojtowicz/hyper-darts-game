package dartsgame.controller;

import dartsgame.controller.dto.TargetScoreDto;
import dartsgame.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("api/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping("/create")
    public ResponseEntity<?> createGame(Authentication auth, @RequestBody TargetScoreDto targetScoreDto) {
        return gameService.createGame(targetScoreDto, auth.getName());
    }

    @GetMapping("/list")
    public ResponseEntity<?> getListOfGames() {
        return gameService.getAllGames();
    }

    @GetMapping("/join/{gameId}")
    public ResponseEntity<?> joinGame(Authentication auth, @PathVariable Long gameId) {
        return gameService.joinGame(gameId, auth.getName());
    }

    @GetMapping("/status")
    public ResponseEntity<?> getGameStatus(Authentication auth) {
        return gameService.getCurrentGame(auth.getName());
    }

    @PostMapping("/throws")
    public Map<String, String> throwDart(Authentication auth) {
        return new ConcurrentHashMap<>(
                Map.of("status", auth.getName())
        );
    }
}
