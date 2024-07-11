package com.tinqinacademy.hotel.core;

import com.tinqinacademy.hotel.api.contracts.HotelService;
import com.tinqinacademy.hotel.api.models.BathroomType;
import com.tinqinacademy.hotel.api.models.BedSize;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.api.operations.checkrooms.CheckRoomsInput;
import com.tinqinacademy.hotel.api.operations.checkrooms.CheckRoomsOutput;
import com.tinqinacademy.hotel.api.operations.getroombyid.GetRoomByIdInput;
import com.tinqinacademy.hotel.api.operations.getroombyid.GetRoomByIdOutput;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomOutput;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class HotelServiceImpl implements HotelService {

    @Override
    public CheckRoomsOutput checkRoomAvailability(CheckRoomsInput input) {
        log.info("start checkRoomAvailability input: {}", input);

        List<String> roomIds = new ArrayList<>();
        roomIds.add("123");
        roomIds.add("456");
        roomIds.add("789");

        CheckRoomsOutput result = CheckRoomsOutput.builder()
                .idList(roomIds)
                .build();

        log.info("end checkRoomAvailability result: {}", result);

        return result;
    }

    @Override
    public GetRoomByIdOutput getRoomInfo(GetRoomByIdInput input) {
        log.info("start getRoomInfo input: {}", input);

        List<LocalDate> datesOccupied = new ArrayList<>();
        datesOccupied.add(LocalDate.of(2020, 1, 1));
        datesOccupied.add(LocalDate.of(2020, 1, 2));
        datesOccupied.add(LocalDate.of(2020, 1, 3));
        datesOccupied.add(LocalDate.of(2020, 1, 4));
        datesOccupied.add(LocalDate.of(2020, 1, 5));
        datesOccupied.add(LocalDate.of(2020, 1, 6));

        GetRoomByIdOutput result = GetRoomByIdOutput.builder()
                .roomId(input.getRoomId())
                .price(BigDecimal.valueOf(123124))
                .floor(5)
                .bedSize(BedSize.getBedSize("kingSize"))
                .bathroomType(BathroomType.getBathroomType("private"))
                .bedCount(10)
                .datesOccupied(datesOccupied)
                .build();

        log.info("end getRoomInfo result: {}", result);

        return result;
    }

    @Override
    public BookRoomOutput bookRoom(BookRoomInput input) {
        log.info("start bookRoom input: {}", input);

        BookRoomOutput result = BookRoomOutput.builder().build();

        log.info("end bookRoom result: {}", result);

        return result;
    }

    @Override
    public UnbookRoomOutput unbookRoom(UnbookRoomInput input) {
        log.info("start unbookRoom input: {}", input);

        UnbookRoomOutput result = UnbookRoomOutput.builder().build();

        log.info("end unbookRoom result: {}", result);

        return result;
    }
}
