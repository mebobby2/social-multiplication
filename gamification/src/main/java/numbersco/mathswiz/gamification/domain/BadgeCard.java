package gamification.src.main.java.numbersco.mathswiz.gamification.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import gamification.src.main.java.numbersco.mathswiz.gamification.domain.Badge;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * BadgeCard
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public final class BadgeCard {
  @Id
  @GeneratedValue
  @Column(name = "BADGE_ID")
  private final Long badgeId;

  private final Long userId;
  private final Long badgeTimestamp;
  private final Badge badge;

  public BadgeCard() {
    this(null, null, 0, null);
  }

  public BadgeCard(final Long userId, final Badge badge) {
    this(null, userId, System.currentTimeMillis(), badge);
  }

}
