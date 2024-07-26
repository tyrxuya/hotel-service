package com.tinqinacademy.hotel.core.converters;

import com.tinqinacademy.hotel.api.operations.getroombyid.GetRoomByIdOutput;
import com.tinqinacademy.hotel.persistence.entities.Room;
import com.tinqinacademy.hotel.persistence.enums.BedSize;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoomToGetRoomByIdOutputConverter extends AbstractConverter<Room, GetRoomByIdOutput> {
    @Override
    protected Class<GetRoomByIdOutput> getTargetClass() {
        return GetRoomByIdOutput.class;
    }

    @Override
    protected GetRoomByIdOutput doConvert(Room source) {
        List<BedSize> bedSizes = new ArrayList<>();

        source.getBeds().forEach(bed -> bedSizes.add(bed.getBedSize()));

        GetRoomByIdOutput result = GetRoomByIdOutput.builder()
                .roomId(source.getId())
                .price(source.getPrice())
                .floor(source.getFloor())
                .bathroomType(source.getBathroomType())
                .bedSizes(bedSizes)
                .bedCount(bedSizes.size())
                .build();

        return result;
    }
}
