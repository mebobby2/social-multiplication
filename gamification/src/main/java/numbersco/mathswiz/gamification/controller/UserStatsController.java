package numbersco.mathswiz.gamification.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import numbersco.mathswiz.gamification.domain.GameStats;
import numbersco.mathswiz.gamification.service.GameService;

/**
 * UserStatsController
 */
@RestController
@RequestMapping("/stats")
public class UserStatsController {
  private final GameService gameService;

  public UserStatsController(final GameService gameService) {
    this.gameService = gameService;
  }

  @GetMapping
  public GameStats getStatsFOrUser(@RequestParam("userId") final Long userId) {
    return gameService.retrieveStatsForUser(userId);
  }

}
