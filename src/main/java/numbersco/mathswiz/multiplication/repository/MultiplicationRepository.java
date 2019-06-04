package numbersco.mathswiz.multiplication.repository;

import org.springframework.data.repository.CrudRepository;

import numbersco.mathswiz.multiplication.domain.Multiplication;


/**
 * MultiplicationRepository
 */
public interface MultiplicationRepository extends CrudRepository<Multiplication, Long> {


}
