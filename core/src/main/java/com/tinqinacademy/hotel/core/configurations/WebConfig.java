package com.tinqinacademy.hotel.core.configurations;

import com.tinqinacademy.hotel.core.converters.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new CreateRoomInputToRoom());
        registry.addConverter(new GuestsToHotelVisitorOutput());
        registry.addConverter(new RoomToGetRoomByIdOutputConverter());
        registry.addConverter(new CreateUserInputToUser());
        registry.addConverter(new UserToGetUserOutput());
    }
}
