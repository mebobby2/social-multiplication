package numbersco.mathswiz.multiplication.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * User
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public final class User {
  @Id
  @GeneratedValue
  @Column(name = "USER_ID")
  private Long id;


  private final String alias;

  // Empty constructor for JSON (de)serialization and JPA (reflection)
  protected User() {
    alias = null;
  }

  public User(final long userId, final String userAlias) {
    this.id = userId;
    this.alias = userAlias;
  }

}
