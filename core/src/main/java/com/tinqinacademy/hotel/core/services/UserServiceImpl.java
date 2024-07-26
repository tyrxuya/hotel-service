package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.contracts.UserService;
import com.tinqinacademy.hotel.api.operations.createuser.CreateUserInput;
import com.tinqinacademy.hotel.api.operations.createuser.CreateUserOutput;
import com.tinqinacademy.hotel.api.operations.deleteuser.DeleteUserInput;
import com.tinqinacademy.hotel.api.operations.deleteuser.DeleteUserOutput;
import com.tinqinacademy.hotel.api.operations.getuser.GetUserInput;
import com.tinqinacademy.hotel.api.operations.getuser.GetUserOutput;
import com.tinqinacademy.hotel.persistence.entities.User;
import com.tinqinacademy.hotel.persistence.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {
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

    @Override
    public DeleteUserOutput deleteUser(DeleteUserInput input) {
        log.info("start deleteUser input: {}", input);

        userRepository.deleteById(input.getUserId());

        DeleteUserOutput result = DeleteUserOutput.builder().build();

        log.info("end deleteUser result: {}", result);

        return result;
    }

    @Override
    public GetUserOutput getUser(GetUserInput input) {
        log.info("start getUser input: {}", input);

        User user = userRepository.findById(input.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("user not found"));

        GetUserOutput result = conversionService.convert(user, GetUserOutput.class);

        log.info("end getUser result: {}", result);

        return result;
    }
}
