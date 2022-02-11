package dartsgame.repository;

import dartsgame.model.GameMove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameMoveRepository extends JpaRepository<GameMove, Long> {
    @Query("select gm from GameMove gm where gm.gameId = ?1 and gm.gameStatus not like 'created' order by gm.move asc")
    List<GameMove> findGameMovesByGameId(long gameId);
    void removeAllByGameIdEqualsAndMoveGreaterThan(long gameId, int move);
}
