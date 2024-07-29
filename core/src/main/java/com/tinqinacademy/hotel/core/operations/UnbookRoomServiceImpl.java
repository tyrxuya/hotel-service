package com.tinqinacademy.hotel.core.operations;

import com.tinqinacademy.hotel.api.contracts.operations.UnbookRoomService;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomOutput;
import com.tinqinacademy.hotel.persistence.repositories.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UnbookRoomServiceImpl implements UnbookRoomService {
    private final BookingRepository bookingRepository;

    @Override
    public UnbookRoomOutput unbookRoom(UnbookRoomInput input) {
        log.info("start unbookRoom input: {}", input);

        bookingRepository.deleteById(input.getBookingId());

        UnbookRoomOutput result = UnbookRoomOutput.builder().build();

        log.info("end unbookRoom result: {}", result);

        return result;
    }
}
