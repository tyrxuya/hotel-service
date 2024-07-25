package com.tinqinacademy.hotel.core.converters;

import com.tinqinacademy.hotel.api.operations.getroombyid.GetRoomByIdOutput;
import com.tinqinacademy.hotel.persistence.entities.Room;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RoomToGetRoomByIdOutputConverter implements Converter<Room, GetRoomByIdOutput> {
    @Override
    public GetRoomByIdOutput convert(Room source) {
        GetRoomByIdOutput result = GetRoomByIdOutput.builder()
                .roomId(source.getId())
                .price(source.getPrice())
                .floor(source.getFloor())
                .bathroomType(source.getBathroomType())
                .build();

        return result;
    }
}
