package dartsgame.repository;

import dartsgame.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    @Query("select g from Game g where (g.playerOne like ?1 or g.playerTwo = ?1) and g.gameOver=false")
    Game findCurrentGame(String playerOne);
    List<Game> findAllByOrderByGameIdDesc();
    Game findGameByGameId(long id);
    @Query("select g from Game g where (g.playerOne like ?1 or g.playerTwo = ?1) and g.gameOver=false")
    Game findUnfinishedGameForPlayer(String player);
    @Query("select g from Game g where (g.playerOne like ?1 or g.playerTwo = ?1) and g.gameStatus like '%wins!' order by g.gameId desc")
    List<Game> findLastGames(String player);
}
