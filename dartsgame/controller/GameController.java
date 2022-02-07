package dartsgame.controller;

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
    public Map<String, String> createGame() {
        return new ConcurrentHashMap<>(
                Map.of("status", "Under construction!")
        );
    }

    @GetMapping("/list")
    public Map<String, String> getListOfGames() {
        return new ConcurrentHashMap<>(
                Map.of("status", "Under construction!")
        );
    }

    @GetMapping("/join")
    public Map<String, String> joinGame() {
        return new ConcurrentHashMap<>(
                Map.of("status", "Under construction!")
        );
    }

    @GetMapping("/status")
    public Map<String, String> getGameStatus() {
        return new ConcurrentHashMap<>(
                Map.of("status", "Under construction!")
        );
    }

    @PostMapping("/throws")
    public Map<String, String> throwDart() {
        return new ConcurrentHashMap<>(
                Map.of("status", "Under construction!")
        );
    }
}
