package numbersco.mathswiz.gamification.service;

import numbersco.mathswiz.gamification.domain.GameStats;

/**
 * GameService
 */
public interface GameService {
  GameStats newAttemptForUser(Long userId, Long attemptId, boolean correct);
  GameStats retrieveStatsForUser(Long userId);
}
