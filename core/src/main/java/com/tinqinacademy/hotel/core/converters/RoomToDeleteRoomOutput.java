package com.tinqinacademy.hotel.core.converters;

import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.persistence.entities.Room;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoomToDeleteRoomOutput extends AbstractConverter<Room, DeleteRoomOutput> {
    @Override
    protected Class<DeleteRoomOutput> getTargetClass() {
        return DeleteRoomOutput.class;
    }

    @Override
    protected DeleteRoomOutput doConvert(Room source) {
        List<String> bedSizes = new ArrayList<>();

        source.getBeds()
                .forEach(bed -> bedSizes.add(bed.getBedSize().getCode()));

        DeleteRoomOutput result = DeleteRoomOutput.builder()
                .roomNo(source.getNumber())
                .bathroomType(source.getBathroomType().getCode())
                .price(source.getPrice())
                .floor(source.getFloor())
                .bedSizes(bedSizes)
                .build();

        return result;
    }
}
