package numbersco.mathswiz.gamification.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import numbersco.mathswiz.gamification.domain.ScoreCard;
import numbersco.mathswiz.gamification.service.GameService;

/**
 * ScoreController
 */
@RestController
@RequestMapping("/scores")
public class ScoreController {
  private final GameService gameService;

  public ScoreController(final GameService gameService) {
    this.gameService = gameService;
  }

  @GetMapping("/{attemptId}")
  public ScoreCard getScoreForAttempt(@PathVariable("attemptId") final Long attemptId) {
    return gameService.getScoreForAttempt(attemptId);
  }

}
