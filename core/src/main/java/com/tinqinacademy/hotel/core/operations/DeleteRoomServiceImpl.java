package com.tinqinacademy.hotel.core.operations;

import com.tinqinacademy.hotel.api.contracts.operations.DeleteRoomService;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeleteRoomServiceImpl implements DeleteRoomService {
    private final RoomRepository roomRepository;

    @Override
    public DeleteRoomOutput deleteRoom(DeleteRoomInput input) {
        log.info("start deleteRoom input: {}", input);

        roomRepository.deleteById(input.getRoomId());

        DeleteRoomOutput result = DeleteRoomOutput.builder().build();

        log.info("end deleteRoom result: {}", result);

        return result;
    }
}
