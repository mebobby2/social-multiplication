package numbersco.mathswiz.gamification.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;

import numbersco.mathswiz.gamification.domain.LeaderBoardRow;
import numbersco.mathswiz.gamification.repository.ScoreCardRepository;

/**
 * LeaderBoardServiceImplTest
 */
public class LeaderBoardServiceImplTest {
  private LeaderBoardServiceImpl leaderBoardService;

  @Mock
  ScoreCardRepository scoreCardRepository;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    leaderBoardService = new LeaderBoardServiceImpl(scoreCardRepository);
  }

  @Test
  public void retrieveLeaderBoardTest() {
    Long userId = 1L;
    LeaderBoardRow leaderRow1  = new LeaderBoardRow(userId, 300L);
    List<LeaderBoardRow> expectedLeaderBoard = Collections.singletonList(leaderRow1);
    given(scoreCardRepository.findFirst10()).willReturn(expectedLeaderBoard);

    List<LeaderBoardRow> leaderBoard = leaderBoardService.getCurrentLeaderBoard();

    assertThat(leaderBoard).isEqualTo(expectedLeaderBoard);
  }
}
