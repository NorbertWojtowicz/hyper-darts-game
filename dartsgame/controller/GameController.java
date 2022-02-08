package dartsgame.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("api/game")
public class GameController {
    @PostMapping("/create")
    public Map<String, String> createGame(Authentication auth) {
        return new ConcurrentHashMap<>(
                Map.of("status", auth.getName())
        );
    }

    @GetMapping("/list")
    public Map<String, String> getListOfGames(Authentication auth) {
        return new ConcurrentHashMap<>(
                Map.of("status", auth.getName())
        );
    }

    @GetMapping("/join")
    public Map<String, String> joinGame(Authentication auth) {
        return new ConcurrentHashMap<>(
                Map.of("status", auth.getName())
        );
    }

    @GetMapping("/status")
    public Map<String, String> getGameStatus(Authentication auth) {
        return new ConcurrentHashMap<>(
                Map.of("status", auth.getName())
        );
    }

    @PostMapping("/throws")
    public Map<String, String> throwDart(Authentication auth) {
        return new ConcurrentHashMap<>(
                Map.of("status", auth.getName())
        );
    }
}
