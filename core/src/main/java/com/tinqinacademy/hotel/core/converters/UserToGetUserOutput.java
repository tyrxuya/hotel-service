package com.tinqinacademy.hotel.core.converters;

import com.tinqinacademy.hotel.api.operations.getuser.GetUserOutput;
import com.tinqinacademy.hotel.persistence.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserToGetUserOutput extends AbstractConverter<User, GetUserOutput> {
    @Override
    protected Class<GetUserOutput> getTargetClass() {
        return GetUserOutput.class;
    }

    @Override
    protected GetUserOutput doConvert(User source) {
        GetUserOutput result = GetUserOutput.builder()
                .username(source.getUsername())
                .password(source.getPassword())
                .email(source.getEmail())
                .birthday(source.getBirthday())
                .phone(source.getPhone())
                .build();

        return result;
    }
}
