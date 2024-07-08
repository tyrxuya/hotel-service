package com.tinqinacademy.hotel.services_;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.hotel.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.operations.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.operations.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.operations.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.operations.getregisteredvisitors.GetRegisteredVisitorsInput;
import com.tinqinacademy.hotel.operations.getregisteredvisitors.GetRegisteredVisitorsOutput;
import com.tinqinacademy.hotel.operations.hotelvisitor.HotelVisitorOutput;
import com.tinqinacademy.hotel.operations.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.operations.partialupdateroom.PartialUpdateRoomOutput;
import com.tinqinacademy.hotel.operations.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.operations.registervisitor.RegisterVisitorOutput;
import com.tinqinacademy.hotel.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.operations.updateroom.UpdateRoomOutput;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class SystemServiceImpl implements SystemService {

    @Override
    public RegisterVisitorOutput registerVisitor(RegisterVisitorInput input) {
        log.info("start registerVisitor input: {}", input);

        RegisterVisitorOutput result = RegisterVisitorOutput.builder().build();

        log.info("end registerVisitor result: {}", result);

        return result;
    }

    @Override
    public GetRegisteredVisitorsOutput getVisitorsInfo(GetRegisteredVisitorsInput input) {
        log.info("start getVisitorsInfo input: {}", input);

        List<HotelVisitorOutput> visitors = new ArrayList<>();
        HotelVisitorOutput visitor = HotelVisitorOutput.builder()
                .startDate(LocalDate.of(2021, 2, 3))
                .endDate(LocalDate.of(2021, 2, 5))
                .firstName("gosho")
                .lastName("probniq")
                .phoneNo("132412")
                .idNo("0349419491")
                .idIssueAuthority("mvr varna")
                .idIssueDate(LocalDate.of(2015, 5, 5))
                .build();

        visitors.add(visitor);

        visitor = HotelVisitorOutput.builder()
                .startDate(LocalDate.of(2021, 2, 5))
                .endDate(LocalDate.of(2021, 2, 8))
                .firstName("gosho")
                .lastName("probniq2")
                .phoneNo("132412412")
                .idNo("014124349419491")
                .idIssueAuthority("mvr varnaaaaa")
                .idIssueDate(LocalDate.of(2015, 5, 5))
                .build();

        visitors.add(visitor);

        GetRegisteredVisitorsOutput result = GetRegisteredVisitorsOutput.builder()
                .hotelVisitors(visitors)
                .build();

        log.info("end getVisitorsInfo result: {}", result);

        return result;
    }

    @Override
    public CreateRoomOutput createRoom(CreateRoomInput input) {
        log.info("start createRoom input: {}", input);

        CreateRoomOutput result = CreateRoomOutput.builder()
                .roomId("5")
                .build();

        log.info("end createRoom result: {}", result);

        return result;
    }

    @Override
    public UpdateRoomOutput updateRoom(UpdateRoomInput input) {
        log.info("start updateRoom input: {}", input);

        UpdateRoomOutput result = UpdateRoomOutput.builder()
                .roomId("1231231")
                .build();

        log.info("end updateRoom result: {}", result);

        return result;
    }

    @Override
    public PartialUpdateRoomOutput partialUpdateRoom(PartialUpdateRoomInput input) {
        log.info("start partialUpdateRoom input: {}", input);

        PartialUpdateRoomOutput result = PartialUpdateRoomOutput.builder()
                .roomId("1231231")
                .build();

        log.info("end partialUpdateRoom result: {}", result);

        return result;
    }

    @Override
    public DeleteRoomOutput deleteRoom(DeleteRoomInput input) {
        log.info("start deleteRoom input: {}", input);

        DeleteRoomOutput result = DeleteRoomOutput.builder().build();

        log.info("end deleteRoom result: {}", result);

        return result;
    }
}
