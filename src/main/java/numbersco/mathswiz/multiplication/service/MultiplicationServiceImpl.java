package numbersco.mathswiz.multiplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import numbersco.mathswiz.multiplication.domain.Multiplication;
import numbersco.mathswiz.multiplication.domain.MultiplicationResultAttempt;

/**
 * MultiplicationServiceImpl
 */
@Service
public class MultiplicationServiceImpl implements MultiplicationService {

  private RandomGeneratorService randomGeneratorService;

  @Autowired
  public MultiplicationServiceImpl(RandomGeneratorService randomGeneratorService) {
    this.randomGeneratorService = randomGeneratorService;
  }

  @Override
  public Multiplication createRandomMultiplication() {
    int factorA = randomGeneratorService.generateRandomFactor();
    int factorB = randomGeneratorService.generateRandomFactor();
    return new Multiplication(factorA, factorB);
  }

  @Override
  public boolean checkAttempt(final MultiplicationResultAttempt attempt) {
    boolean correct = attempt.getResultAttempt() ==
      attempt.getMultiplication().getFactorA() *
      attempt.getMultiplication().getFactorB();

    Assert.isTrue(!attempt.isCorrect(), "You can't send an attempt marked as correct!!");

    MultiplicationResultAttempt checkedAttempt = new MultiplicationResultAttempt(
      attempt.getUser(), attempt.getMultiplication(), attempt.getResultAttempt(), correct);

    return correct;
  }
}
