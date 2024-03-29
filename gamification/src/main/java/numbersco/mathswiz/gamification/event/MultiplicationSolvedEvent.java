package numbersco.mathswiz.gamification.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * Event received when a multiplication has been solved in the system.
 * Provides some context information about the multiplication.
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
class MultiplicationSolvedEvent implements Serializable {

    private final Long multiplicationResultAttemptId;
    private final Long userId;
    private final boolean correct;

    // Empty constructor for JSON deserialization
    MultiplicationSolvedEvent() {
        this.multiplicationResultAttemptId = -1L;
        this.userId = -1L;
        this.correct = false;
    }
}
