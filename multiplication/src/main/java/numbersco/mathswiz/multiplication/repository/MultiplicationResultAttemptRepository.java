package numbersco.mathswiz.multiplication.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import numbersco.mathswiz.multiplication.domain.MultiplicationResultAttempt;

/**
 * MultiplicationResultAttemptRepository
 */
public interface MultiplicationResultAttemptRepository extends CrudRepository<MultiplicationResultAttempt, Long> {
  List<MultiplicationResultAttempt> findTop5ByUserAliasOrderByIdDesc(String userAlias);
}
