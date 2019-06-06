package numbersco.mathswiz.gamification.service;

import java.util.List;

import numbersco.mathswiz.gamification.domain.LeaderBoardRow;

/**
 * LeaderBoardService
 */
public interface LeaderBoardService {
  List<LeaderBoardRow> getCurrentLeaderBoard();
}
