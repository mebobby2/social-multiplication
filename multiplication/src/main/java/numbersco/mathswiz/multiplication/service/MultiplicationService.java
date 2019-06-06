package numbersco.mathswiz.multiplication.service;

import java.util.List;

import numbersco.mathswiz.multiplication.domain.Multiplication;
import numbersco.mathswiz.multiplication.domain.MultiplicationResultAttempt;

/**
 * MultiplicationService
 */
public interface MultiplicationService {

  Multiplication createRandomMultiplication();
  boolean checkAttempt(final MultiplicationResultAttempt resultAttempt);
  List<MultiplicationResultAttempt> getStatsForUser(String userAlias);
  MultiplicationResultAttempt getResultById(final Long resultId);
}
