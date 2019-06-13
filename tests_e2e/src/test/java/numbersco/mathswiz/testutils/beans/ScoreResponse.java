package numbersco.mathswiz.testutils.beans;

/**
 * ScoreResponse
 */
public class ScoreResponse {
  private long userId;
  private int score;

  public ScoreResponse() {
  }

  public ScoreResponse(final int score) {
    this.score = score;
  }

  public long getUserId() {
    return userId;
  }

  public int getScore() {
    return score;
  }

}
