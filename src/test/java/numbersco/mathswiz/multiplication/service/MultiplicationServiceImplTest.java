package numbersco.mathswiz.multiplication.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Optional;

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
import numbersco.mathswiz.multiplication.domain.User;
import numbersco.mathswiz.multiplication.repository.MultiplicationResultAttemptRepository;
import numbersco.mathswiz.multiplication.repository.UserRepository;;

/**
 * MultiplicationServiceTest
 */
public class MultiplicationServiceImplTest {
  private MultiplicationServiceImpl multiplicationServiceImpl;

  @Mock
  private RandomGeneratorService randomGeneratorService;

  @Mock
  private MultiplicationResultAttemptRepository attempRepository;

  @Mock
  private UserRepository userRepository;

  @Before
public void setUp() {
  MockitoAnnotations.initMocks(this);
  multiplicationServiceImpl = new MultiplicationServiceImpl(randomGeneratorService, attempRepository, userRepository);
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
    MultiplicationResultAttempt verifiedAttempt = new MultiplicationResultAttempt(user, multiplication, 3000, true);

    given(userRepository.findByAlias("john_doe")).willReturn(Optional.empty());

    boolean attemptResult = multiplicationServiceImpl.checkAttempt(attempt);

    assertThat(attemptResult).isTrue();
    verify(attempRepository).save(verifiedAttempt);
  }

  @Test
  public void checkWrongAttemptTest() {
    Multiplication multiplication = new Multiplication(50, 60);
    User user = new User("john_doe");
    MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3010, false);

    given(userRepository.findByAlias("john_doe")).willReturn(Optional.empty());

    boolean attemptResult = multiplicationServiceImpl.checkAttempt(attempt);

    assertThat(attemptResult).isFalse();
    verify(attempRepository).save(attempt);
  }

}
