package numbersco.mathswiz.multiplication.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.test.context.junit4.SpringRunner;
import org.assertj.core.util.Lists;
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
import static org.mockito.Matchers.eq;

import numbersco.mathswiz.multiplication.domain.Multiplication;
import numbersco.mathswiz.multiplication.domain.MultiplicationResultAttempt;
import numbersco.mathswiz.multiplication.domain.User;
import numbersco.mathswiz.multiplication.event.EventDispatcher;
import numbersco.mathswiz.multiplication.event.MultiplicationSolvedEvent;
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

  @Mock
  private EventDispatcher eventDispatcher;

  @Before
public void setUp() {
  MockitoAnnotations.initMocks(this);
  multiplicationServiceImpl = new MultiplicationServiceImpl(randomGeneratorService, attempRepository, userRepository, eventDispatcher);
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
    MultiplicationSolvedEvent event = new MultiplicationSolvedEvent(attempt.getId(),
                attempt.getUser().getId(), true);

    given(userRepository.findByAlias("john_doe")).willReturn(Optional.empty());

    boolean attemptResult = multiplicationServiceImpl.checkAttempt(attempt);

    assertThat(attemptResult).isTrue();
    verify(attempRepository).save(verifiedAttempt);
    verify(eventDispatcher).send(eq(event));
  }

  @Test
  public void checkWrongAttemptTest() {
    Multiplication multiplication = new Multiplication(50, 60);
    User user = new User("john_doe");
    MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3010, false);
    MultiplicationSolvedEvent event = new MultiplicationSolvedEvent(attempt.getId(),
                attempt.getUser().getId(), false);

    given(userRepository.findByAlias("john_doe")).willReturn(Optional.empty());

    boolean attemptResult = multiplicationServiceImpl.checkAttempt(attempt);

    assertThat(attemptResult).isFalse();
    verify(attempRepository).save(attempt);
    verify(eventDispatcher).send(eq(event));
  }

  @Test
  public void retrieveStatsTest() {
    Multiplication multiplication = new Multiplication(50, 60);
    User user = new User("john_doe");
    MultiplicationResultAttempt attempt1 = new MultiplicationResultAttempt(user, multiplication, 3010, false);
    MultiplicationResultAttempt attempt2 = new MultiplicationResultAttempt(user, multiplication, 3051, false);
    List<MultiplicationResultAttempt> latestAttempts = Lists.newArrayList(attempt1, attempt2);
    given(attempRepository.findTop5ByUserAliasOrderByIdDesc("john_doe")).willReturn(latestAttempts);

    List<MultiplicationResultAttempt> latestAttemptsResult = multiplicationServiceImpl.getStatsForUser("john_doe");

    assertThat(latestAttemptsResult).isEqualTo(latestAttempts);
  }

}
