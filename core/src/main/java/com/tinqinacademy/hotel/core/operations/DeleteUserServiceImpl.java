package com.tinqinacademy.hotel.core.operations;

import com.tinqinacademy.hotel.api.contracts.operations.DeleteUserService;
import com.tinqinacademy.hotel.api.operations.deleteuser.DeleteUserInput;
import com.tinqinacademy.hotel.api.operations.deleteuser.DeleteUserOutput;
import com.tinqinacademy.hotel.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteUserServiceImpl implements DeleteUserService {
    private final UserRepository userRepository;

    @Override
    public DeleteUserOutput deleteUser(DeleteUserInput input) {
        log.info("start deleteUser input: {}", input);

        userRepository.deleteById(UUID.fromString(input.getUserId()));

        DeleteUserOutput result = DeleteUserOutput.builder().build();

        log.info("end deleteUser result: {}", result);

        return result;
    }
}
