package com.tinqinacademy.hotel.core.converters;

import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.persistence.entities.Bed;
import com.tinqinacademy.hotel.persistence.entities.Room;
import com.tinqinacademy.hotel.persistence.enums.BathroomType;
import com.tinqinacademy.hotel.persistence.enums.BedSize;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CreateRoomInputToRoom extends AbstractConverter<CreateRoomInput, Room> {
    @Override
    protected Class<Room> getTargetClass() {
        return Room.class;
    }

    @Override
    protected Room doConvert(CreateRoomInput source) {
        Room result = Room.builder()
                .number(source.getRoomNo())
                .bathroomType(BathroomType.getBathroomType(source.getBathroomType()))
                .price(source.getPrice())
                .floor(source.getFloor())
                .build();

        return result;
    }
}
