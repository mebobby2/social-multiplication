package numbersco.mathswiz.multiplication.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import numbersco.mathswiz.multiplication.repository.MultiplicationRepository;
import numbersco.mathswiz.multiplication.repository.MultiplicationResultAttemptRepository;
import numbersco.mathswiz.multiplication.repository.UserRepository;

@Profile("test")
@Service
public class AdminServiceImpl implements AdminService {

    private MultiplicationRepository multiplicationRepository;
    private MultiplicationResultAttemptRepository attemptRepository;
    private UserRepository userRepository;

    public AdminServiceImpl(final MultiplicationRepository multiplicationRepository,
                            final UserRepository userRepository,
                            final MultiplicationResultAttemptRepository attemptRepository) {
        this.multiplicationRepository = multiplicationRepository;
        this.userRepository = userRepository;
        this.attemptRepository = attemptRepository;
    }

    @Override
    public void deleteDatabaseContents() {
        attemptRepository.deleteAll();
        multiplicationRepository.deleteAll();
        userRepository.deleteAll();
    }
}
