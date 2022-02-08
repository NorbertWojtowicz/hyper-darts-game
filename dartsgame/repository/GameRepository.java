package dartsgame.repository;

import dartsgame.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Game findGameByPlayerOneLikeAndGameStatusNotLike(String playerOne, String status);
    List<Game> findAllByOrderByGameIdDesc();
    Game findGameByGameId(long id);
    Game findGameByPlayerOneLikeOrPlayerTwoLikeAndGameStatusNotLike(String playerOne, String playerTwo, String status);
}
