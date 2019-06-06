package numbersco.mathswiz.gamification.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import numbersco.mathswiz.gamification.domain.BadgeCard;

/**
 * BadgeCardRepository
 */
public interface BadgeCardRepository extends CrudRepository<BadgeCard, Long> {
  List<BadgeCard> findByUserIdOrderByBadgeTimestampDesc(final Long userId);
}
