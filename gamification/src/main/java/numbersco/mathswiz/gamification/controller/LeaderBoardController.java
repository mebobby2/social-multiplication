package numbersco.mathswiz.gamification.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import numbersco.mathswiz.gamification.domain.LeaderBoardRow;
import numbersco.mathswiz.gamification.service.LeaderBoardService;

/**
 * LeaderBoardController
 */
@RestController
@RequestMapping("/leaders")
public class LeaderBoardController {
  private final LeaderBoardService leaderBoardService;

  public LeaderBoardController(final LeaderBoardService leaderBoardService) {
    this.leaderBoardService = leaderBoardService;
  }

  @GetMapping
  public List<LeaderBoardRow> getLeaderBoard() {
    return leaderBoardService.getCurrentLeaderBoard();
  }

}
