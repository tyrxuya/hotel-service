package com.tinqinacademy.hotel.core.converters;

import com.tinqinacademy.hotel.api.operations.createuser.CreateUserInput;
import com.tinqinacademy.hotel.persistence.entities.User;
import org.springframework.stereotype.Component;

@Component
public class CreateUserInputToUser extends AbstractConverter<CreateUserInput, User> {
    @Override
    protected Class<User> getTargetClass() {
        return User.class;
    }

    @Override
    protected User doConvert(CreateUserInput source) {
        User result = User.builder()
                .username(source.getUsername())
                .password(source.getPassword())
                .birthday(source.getBirthday())
                .email(source.getEmail())
                .phone(source.getPhone())
                .build();

        return result;
    }
}
