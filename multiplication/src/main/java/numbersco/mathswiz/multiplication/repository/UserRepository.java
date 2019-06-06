package numbersco.mathswiz.multiplication.repository;

import java.util.Optional;

/**
 * UserRepository
 */
import org.springframework.data.repository.CrudRepository;
import numbersco.mathswiz.multiplication.domain.User;


public interface UserRepository extends CrudRepository<User, Long> {
  Optional<User> findByAlias(final String alias);
}
