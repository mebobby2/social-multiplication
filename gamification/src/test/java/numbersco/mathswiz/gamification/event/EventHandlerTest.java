package numbersco.mathswiz.gamification.event;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import numbersco.mathswiz.gamification.domain.GameStats;
import numbersco.mathswiz.gamification.service.GameService;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

/**
 * EventHandlerTest
 */
public class EventHandlerTest {

  private EventHandler eventHandler;

  @Mock
  private GameService gameService;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    eventHandler = new EventHandler(gameService);
  }

  @Test
  public void multiplicationAttemptReceivedTest() {
    // given
    Long userId = 1L;
    Long attemptId = 8L;
    boolean correct = true;
    GameStats gameStatsExpected = new GameStats();
    MultiplicationSolvedEvent event = new MultiplicationSolvedEvent(attemptId, userId, correct);
    given(gameService.newAttemptForUser(userId, attemptId, correct)).willReturn(gameStatsExpected);

    // when
    eventHandler.handleMultiplicationSolved(event);

    // then
    verify(gameService).newAttemptForUser(userId, attemptId, correct);
  }
}
