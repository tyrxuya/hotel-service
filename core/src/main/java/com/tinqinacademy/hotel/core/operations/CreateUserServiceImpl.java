package com.tinqinacademy.hotel.core.operations;

import com.tinqinacademy.hotel.api.contracts.operations.CreateUserService;
import com.tinqinacademy.hotel.api.operations.createuser.CreateUserInput;
import com.tinqinacademy.hotel.api.operations.createuser.CreateUserOutput;
import com.tinqinacademy.hotel.persistence.entities.User;
import com.tinqinacademy.hotel.persistence.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateUserServiceImpl implements CreateUserService {
    private final UserRepository userRepository;
    private final ConversionService conversionService;

    @Override
    public CreateUserOutput createUser(CreateUserInput input) {
        log.info("start createUser input: {}", input);

        User user = conversionService.convert(input, User.class);

        userRepository.save(user);

        CreateUserOutput result = CreateUserOutput.builder()
                .userId(user.getId())
                .build();

        log.info("end createUser result: {}", result);

        return result;
    }
}
