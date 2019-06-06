package numbersco.mathswiz.gamification.service;

import java.util.List;

import org.springframework.stereotype.Service;

import numbersco.mathswiz.gamification.domain.LeaderBoardRow;
import numbersco.mathswiz.gamification.repository.ScoreCardRepository;

/**
 * LeaderBoardServiceImpl
 */
@Service
public class LeaderBoardServiceImpl implements LeaderBoardService {
  private ScoreCardRepository scoreCardRepository;

  public LeaderBoardServiceImpl(ScoreCardRepository scoreCardRepository) {
    this.scoreCardRepository = scoreCardRepository;
  }

  @Override
  public List<LeaderBoardRow> getCurrentLeaderBoard() {
    return scoreCardRepository.findFirst10();
  }
}
