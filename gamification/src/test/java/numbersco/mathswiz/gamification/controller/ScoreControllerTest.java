package numbersco.mathswiz.gamification.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import numbersco.mathswiz.gamification.domain.ScoreCard;
import numbersco.mathswiz.gamification.service.GameService;

/**
 * ScoreControllerTest
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ScoreController.class)
public class ScoreControllerTest {
  @MockBean
  private GameService gameService;

  @Autowired
  private MockMvc mvc;

  private JacksonTester<ScoreCard> json;

  @Before
  public void setup() {
    JacksonTester.initFields(this, new ObjectMapper());
  }

  @Test
  public void getScoreForAttemptTest() throws Exception {
    ScoreCard scoreCard = new ScoreCard(1L, 5L, 10L, System.currentTimeMillis(), 100);
    given(gameService.getScoreForAttempt(10L)).willReturn(scoreCard);

    MockHttpServletResponse response = mvc.perform(get("/scores/10").accept(MediaType.APPLICATION_JSON)).andReturn()
        .getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentAsString()).isEqualTo(json.write(scoreCard).getJson());
  }

}
