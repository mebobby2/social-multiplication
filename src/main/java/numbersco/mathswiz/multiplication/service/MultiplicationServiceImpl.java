package numbersco.mathswiz.multiplication.service;

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

/**
 * MultiplicationServiceImpl
 */
@Service
public class MultiplicationServiceImpl implements MultiplicationService {

  private RandomGeneratorService randomGeneratorService;
  private MultiplicationResultAttemptRepository attemptRepository;
  private UserRepository userRepository;

  @Autowired
  public MultiplicationServiceImpl(
    final RandomGeneratorService randomGeneratorService,
    final MultiplicationResultAttemptRepository attemptRepository,
    final UserRepository userRepository) {
    this.randomGeneratorService = randomGeneratorService;
    this.attemptRepository = attemptRepository;
    this.userRepository = userRepository;
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

    return isCorrect;
  }
}
