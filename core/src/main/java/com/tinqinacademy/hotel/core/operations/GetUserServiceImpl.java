package com.tinqinacademy.hotel.core.operations;

import com.tinqinacademy.hotel.api.contracts.operations.GetUserService;
import com.tinqinacademy.hotel.api.operations.getuser.GetUserInput;
import com.tinqinacademy.hotel.api.operations.getuser.GetUserOutput;
import com.tinqinacademy.hotel.persistence.entities.User;
import com.tinqinacademy.hotel.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetUserServiceImpl implements GetUserService {
    private final UserRepository userRepository;
    private final ConversionService conversionService;

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
