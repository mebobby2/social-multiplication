package numbersco.mathswiz.multiplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import numbersco.mathswiz.multiplication.domain.Multiplication;
import numbersco.mathswiz.multiplication.service.MultiplicationService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * MultiplicationControllerTest
 */
@RunWith(SpringRunner.class)
@WebMvcTest(MultiplicationController.class)
public class MultiplicationControllerTest {
  @MockBean
  MultiplicationService multiplicationService;

  @Autowired
  private MockMvc mvc;

  private JacksonTester<Multiplication> json;

  @Before
  public void setup() {
    JacksonTester.initFields(this, new ObjectMapper());
  }

  @Test
  public void getRandomMultiplicationTest() throws Exception {
    given(multiplicationService.createRandomMultiplication()).willReturn(new Multiplication(70, 20));

    MockHttpServletResponse response = mvc.perform(
      get("/multiplications/random").accept(MediaType.APPLICATION_JSON))
      .andReturn().getResponse();

    assertThat(response.getContentAsString())
      .isEqualTo(json.write(new Multiplication(70, 20)).getJson());
  }

}
