package numbersco.mathswiz.multiplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
  public boolean checkAttempt(final MultiplicationResultAttempt resultAttempt) {
    return resultAttempt.getResultAttempt() ==
      resultAttempt.getMultiplication().getFactorA() *
      resultAttempt.getMultiplication().getFactorB();
  }
}
