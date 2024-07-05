package com.tinqinacademy.hotel.services_;

import com.tinqinacademy.hotel.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoomServiceImpl implements RoomService {
    @Override
    public String bookRoom(Test test) {
        log.info("bookRoom");
        return "Room number " + test.getRoomNumber() + " has been booked";
    }

    @Override
    public String checkRoom(Integer roomNumber) {
        log.info("checkRoom");
        return "Room number " + roomNumber + " has been checked";
    }

    @Override
    public RoomOutput addRoom(RoomInput input) {
        log.info("start addRoom input: {}", input);
        RoomOutput result = RoomOutput.builder()
                .roomId(input.getRoomId())
                .roomNumber(input.getRoomNumber())
                .bedCount(input.getBedCount())
                .bedSize(BedSize.getBedSize(input.getBedSize()))
                .floor(input.getFloor())
                .price(input.getPrice())
                .bathroomType(BathroomType.getBathroomType(input.getBathroomType()))
                .build();
        log.info("end addRoom result: {}", result);
        return result;
    }

    @Override
    public String deleteRoom(Integer roomNumber) {
        return "Room number " + roomNumber + " has been deleted";
    }

    @Override
    public String editRoom(Integer roomNumber) {
        return "Room number " + roomNumber + " has been updated";
    }

    @Override
    public String reserveRoom(BookRoom bookRoom) {
        return "Room on floor " + bookRoom.getFloor() + " with bed size " + bookRoom.getBedSize() + " has been reserved";
    }
}
