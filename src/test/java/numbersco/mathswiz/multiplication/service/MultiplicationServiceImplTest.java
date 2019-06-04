package numbersco.mathswiz.multiplication.service;


import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


import numbersco.mathswiz.multiplication.domain.Multiplication;
import numbersco.mathswiz.multiplication.domain.MultiplicationResultAttempt;
import numbersco.mathswiz.multiplication.domain.User;;

/**
 * MultiplicationServiceTest
 */
public class MultiplicationServiceImplTest {
  private MultiplicationServiceImpl multiplicationServiceImpl;

  @Mock
  private RandomGeneratorService randomGeneratorService;

  @Before
public void setUp() {
  MockitoAnnotations.initMocks(this);
  multiplicationServiceImpl = new MultiplicationServiceImpl(randomGeneratorService);
}

  @Test
  public void createRandomMultiplicationTest() {
    given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);

    Multiplication multiplication = multiplicationServiceImpl.createRandomMultiplication();

    assertThat(multiplication.getFactorA()).isEqualTo(50);
    assertThat(multiplication.getFactorB()).isEqualTo(30);
  }

  @Test
  public void checkCorrectAttemptTest() {
    Multiplication multiplication = new Multiplication(50, 60);
    User user = new User("john_doe");
    MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3000, false);

    boolean attemptResult = multiplicationServiceImpl.checkAttempt(attempt);

    assertThat(attemptResult).isTrue();
  }

  @Test
  public void checkWrongAttemptTest() {
    Multiplication multiplication = new Multiplication(50, 60);
    User user = new User("john_doe");
    MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3010, false);

    boolean attemptResult = multiplicationServiceImpl.checkAttempt(attempt);

    assertThat(attemptResult).isFalse();
  }

}
