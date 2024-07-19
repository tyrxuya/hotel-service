package com.tinqinacademy.hotel.core;

import com.tinqinacademy.hotel.api.contracts.SystemService;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.api.operations.getregisteredvisitors.GetRegisteredVisitorsInput;
import com.tinqinacademy.hotel.api.operations.getregisteredvisitors.GetRegisteredVisitorsOutput;
import com.tinqinacademy.hotel.api.operations.hotelvisitor.HotelVisitorOutput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomOutput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterVisitorOutput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomOutput;
import com.tinqinacademy.hotel.persistence.contracts.*;
import com.tinqinacademy.hotel.persistence.enums.BathroomType;
import com.tinqinacademy.hotel.persistence.models.Bed;
import com.tinqinacademy.hotel.persistence.models.Guest;
import com.tinqinacademy.hotel.persistence.models.Room;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepositoryImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class SystemServiceImpl implements SystemService {
    private final RoomRepository roomRepository;
    private final BedRepository bedRepository;
    private final BookingRepository bookingRepository;
    private final GuestRepository guestRepository;
    private final UserRepository userRepository;

    @Override
    public RegisterVisitorOutput registerVisitor(RegisterVisitorInput input) {
        log.info("start registerVisitor input: {}", input);

        List<Guest> guests = new ArrayList<>();

        input.getHotelVisitors()
                .forEach(guest ->
                        guests.add(Guest.builder()
                                        .id(UUID.randomUUID())
                                        .firstName(guest.getFirstName())
                                        .lastName(guest.getLastName())
                                        .phone(guest.getPhoneNo())
                                        .birthday(guest.getBirthday())
                                        .civilNumber(guest.getCivilNumber())
                                        .idIssueAuthority(guest.getIdIssueAuthority())
                                        .idIssueDate(guest.getIdIssueDate())
                                        .idValidity(guest.getIdValidity())
                                .build()));

        guestRepository.saveAll(guests);

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

//        CreateRoomOutput result = CreateRoomOutput.builder()
//                .roomId("5")
//                .build();

        List<Bed> beds = new ArrayList<>();

        input.getBedSizes()
                .forEach(bedSize ->
                        beds.add(bedRepository.getBedByBedSize(bedSize.getCode())
                                .orElse(null)));

        Room room = Room.builder()
                .id(UUID.randomUUID())
                .number(input.getRoomNo())
                .price(input.getPrice())
                .floor(input.getFloor())
                .bathroomType(BathroomType.getBathroomType(input.getBathroomType()))
                .beds(beds)
                .build();

        roomRepository.save(room);

        CreateRoomOutput result = CreateRoomOutput.builder()
                .roomId(String.valueOf(room.getId()))
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
