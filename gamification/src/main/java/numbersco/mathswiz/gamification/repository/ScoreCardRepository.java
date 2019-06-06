package numbersco.mathswiz.gamification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import numbersco.mathswiz.gamification.domain.LeaderBoardRow;
import numbersco.mathswiz.gamification.domain.ScoreCard;

/**
 * ScoreCardRepository
 */
public interface ScoreCardRepository extends CrudRepository<ScoreCard, Long>{
  @Query("SELECT SUM(s.score) " +
         "FROM numbersco.mathswiz.gamification.domain.ScoreCard s " +
         "WHERE s.userId = :userId " +
         "GROUP BY s.userId")
  int getTotalScoreForUser(@Param("userId") final Long userId);

  @Query("SELECT NEW numbersco.mathswiz.gamification.domain.LeaderBoardRow(s.userId, SUM(s.score)) " +
         "FROM numbersco.mathswiz.gamification.domain.ScoreCard s " +
         "GROUP BY s.userId " +
         "ORDER BY SUM(s.score) DESC")
  List<LeaderBoardRow> findFirst10();

  List<ScoreCard> findByUserIdOrderByScoreTimestampDesc(final Long userId);
}
