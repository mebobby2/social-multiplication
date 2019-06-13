package numbersco.mathswiz.gamification.service;

import numbersco.mathswiz.gamification.domain.GameStats;
import numbersco.mathswiz.gamification.domain.ScoreCard;

/**
 * GameService
 */
public interface GameService {
  GameStats newAttemptForUser(Long userId, Long attemptId, boolean correct);
  GameStats retrieveStatsForUser(Long userId);
  ScoreCard getScoreForAttempt(Long attemptId);
}
