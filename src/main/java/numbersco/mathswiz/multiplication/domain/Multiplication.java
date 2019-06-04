package numbersco.mathswiz.multiplication.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Multiplication
 */

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public final class Multiplication {

  private final int factorA;
  private final int factorB;

  // Empty constructor for JSON (de)serialization
  Multiplication() {
    this(0, 0);
  }
}
