package com.tinqinacademy.hotel.core.configurations;

import com.tinqinacademy.hotel.core.converters.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final CreateRoomInputToRoom createRoomInputToRoom;
    private final GuestToHotelVisitorOutput guestToHotelVisitorOutput;
    private final RoomToGetRoomByIdOutput roomToGetRoomByIdOutputConverter;
    private final HotelVisitorInputToGuest hotelVisitorInputToGuest;
    private final RoomToDeleteRoomOutput roomToDeleteRoomOutput;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(createRoomInputToRoom);
        registry.addConverter(guestToHotelVisitorOutput);
        registry.addConverter(roomToGetRoomByIdOutputConverter);
        registry.addConverter(hotelVisitorInputToGuest);
        registry.addConverter(roomToDeleteRoomOutput);
    }
}
