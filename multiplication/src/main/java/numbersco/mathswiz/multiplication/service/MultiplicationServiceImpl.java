package numbersco.mathswiz.multiplication.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import numbersco.mathswiz.multiplication.domain.Multiplication;
import numbersco.mathswiz.multiplication.domain.MultiplicationResultAttempt;
import numbersco.mathswiz.multiplication.repository.MultiplicationResultAttemptRepository;
import numbersco.mathswiz.multiplication.repository.UserRepository;
import numbersco.mathswiz.multiplication.domain.User;
import numbersco.mathswiz.multiplication.event.EventDispatcher;
import numbersco.mathswiz.multiplication.event.MultiplicationSolvedEvent;

/**
 * MultiplicationServiceImpl
 */
@Service
public class MultiplicationServiceImpl implements MultiplicationService {

  private RandomGeneratorService randomGeneratorService;
  private MultiplicationResultAttemptRepository attemptRepository;
  private UserRepository userRepository;
  private EventDispatcher eventDispatcher;

  @Autowired
  public MultiplicationServiceImpl(
    final RandomGeneratorService randomGeneratorService,
    final MultiplicationResultAttemptRepository attemptRepository,
    final UserRepository userRepository,
    final EventDispatcher eventDispatcher) {
    this.randomGeneratorService = randomGeneratorService;
    this.attemptRepository = attemptRepository;
    this.userRepository = userRepository;
    this.eventDispatcher = eventDispatcher;
  }

  @Override
  public Multiplication createRandomMultiplication() {
    int factorA = randomGeneratorService.generateRandomFactor();
    int factorB = randomGeneratorService.generateRandomFactor();
    return new Multiplication(factorA, factorB);
  }

  @Transactional
  @Override
  public boolean checkAttempt(final MultiplicationResultAttempt attempt) {
    Optional<User> user = userRepository.findByAlias(attempt.getUser().getAlias());

    Assert.isTrue(!attempt.isCorrect(), "You can't send an attempt marked as correct!!");

    boolean isCorrect = attempt.getResultAttempt() ==
      attempt.getMultiplication().getFactorA() *
      attempt.getMultiplication().getFactorB();

    MultiplicationResultAttempt checkedAttempt = new MultiplicationResultAttempt(
      user.orElse(attempt.getUser()), attempt.getMultiplication(), attempt.getResultAttempt(), isCorrect);

    attemptRepository.save(checkedAttempt);

    eventDispatcher.send(new MultiplicationSolvedEvent(
      checkedAttempt.getId(),
      checkedAttempt.getUser().getId(),
      checkedAttempt.isCorrect()));

    return isCorrect;
  }

  @Override
  public List<MultiplicationResultAttempt> getStatsForUser(String userAlias) {
    return attemptRepository.findTop5ByUserAliasOrderByIdDesc(userAlias);
  }

}
