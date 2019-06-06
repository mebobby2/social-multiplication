package numbersco.mathswiz.gamification.client;

import numbersco.mathswiz.gamification.client.dto.MultiplicationResultAttempt;

/**
 * MultiplicationResultAttemptClient
 */
public interface MultiplicationResultAttemptClient {
  MultiplicationResultAttempt retrieveMultiplicationResultAttemptById(final Long multiplicationId);
}
