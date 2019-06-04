package numbersco.mathswiz.multiplication.service;

import numbersco.mathswiz.multiplication.domain.Multiplication;
import numbersco.mathswiz.multiplication.domain.MultiplicationResultAttempt;

/**
 * MultiplicationService
 */
public interface MultiplicationService {

  Multiplication createRandomMultiplication();
  boolean checkAttempt(final MultiplicationResultAttempt resultAttempt);
}
