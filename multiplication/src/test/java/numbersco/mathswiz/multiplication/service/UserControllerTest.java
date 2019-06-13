package numbersco.mathswiz.multiplication.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

import numbersco.mathswiz.multiplication.controller.UserController;
import numbersco.mathswiz.multiplication.domain.User;
import numbersco.mathswiz.multiplication.repository.UserRepository;

/**
 * UserControllerTest
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

  @MockBean
  private UserRepository userRepository;

  @Autowired
  private MockMvc mvc;

  private JacksonTester<User> json;

  @Before
  public void setup() {
    JacksonTester.initFields(this, new ObjectMapper());
  }

  @Test
  public void getUserByIdTest() throws Exception {
     // given
     long userId = 1;
     String userAlias = "john";
     given(userRepository.findOne(userId))
             .willReturn(new User(userId, userAlias));

     // when
     MockHttpServletResponse response = mvc.perform(
             get("/users/" + userId)
                     .accept(MediaType.APPLICATION_JSON))
             .andReturn().getResponse();

     // then
     assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
     assertThat(response.getContentAsString())
             .isEqualTo(json.write(new User(userId, userAlias)).getJson());
  }

}
