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
import com.tinqinacademy.hotel.persistence.entities.Bed;
import com.tinqinacademy.hotel.persistence.enums.BedSize;
import com.tinqinacademy.hotel.persistence.repositories.*;
import com.tinqinacademy.hotel.persistence.enums.BathroomType;
import com.tinqinacademy.hotel.persistence.entities.Guest;
import com.tinqinacademy.hotel.persistence.entities.Room;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

        List<Guest> guests = guestRepository.searchGuests(input.getVisitor().getFirstName(),
                input.getVisitor().getLastName(),
                input.getVisitor().getPhoneNo(),
                input.getVisitor().getBirthday(),
                input.getVisitor().getCivilNumber(),
                input.getVisitor().getIdIssueAuthority(),
                input.getVisitor().getIdIssueDate(),
                input.getVisitor().getIdValidity());

        List<HotelVisitorOutput> visitors = new ArrayList<>();

        guests.forEach(guest -> visitors.add(HotelVisitorOutput.builder()
                .firstName(guest.getFirstName())
                .lastName(guest.getLastName())
                .phoneNo(guest.getPhone())
                .idNo(guest.getCivilNumber())
                .idIssueAuthority(guest.getIdIssueAuthority())
                .idIssueDate(guest.getIdIssueDate())
                .build())
        );

        GetRegisteredVisitorsOutput result = GetRegisteredVisitorsOutput.builder()
                .hotelVisitors(visitors)
                .build();

        log.info("end getVisitorsInfo result: {}", result);

        return result;
    }

    @Override
    public CreateRoomOutput createRoom(CreateRoomInput input) {
        log.info("start createRoom input: {}", input);

        List<BedSize> bedSizes = new ArrayList<>(input.getBedSizes());
        List<Bed> beds = new ArrayList<>();
        bedSizes.forEach(bedSize -> {
            Optional<Bed> bed = bedRepository.findBedByBedSize(bedSize);
            bed.ifPresent(beds::add);
        });

        Room room = Room.builder()
                .number(input.getRoomNo())
                .price(input.getPrice())
                .floor(input.getFloor())
                .bathroomType(input.getBathroomType())
                .beds(beds)
                .build();

        roomRepository.save(room);

        CreateRoomOutput result = CreateRoomOutput.builder()
                .roomId(room.getId())
                .build();

        log.info("end createRoom result: {}", result);

        return result;
    }

    @Override
    public UpdateRoomOutput updateRoom(UpdateRoomInput input) {
        log.info("start updateRoom input: {}", input);

        Room room = roomRepository.findById(input.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("Room not found!"));

        room.setBathroomType(input.getBathroomType());
        room.setFloor(input.getFloor());
        room.setPrice(input.getPrice());
        room.setNumber(input.getRoomNo());

        List<Bed> beds = new ArrayList<>();
        input.getBedSizes().forEach(bedSize -> {
            Optional<Bed> bed = bedRepository.findBedByBedSize(bedSize);
            bed.ifPresent(beds::add);
        });

        room.setBeds(beds);

        roomRepository.save(room);

        //roomRepository.update(room);

        UpdateRoomOutput result = UpdateRoomOutput.builder()
                .roomId(room.getId())
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

        roomRepository.deleteById(input.getRoomId());

        DeleteRoomOutput result = DeleteRoomOutput.builder().build();

        log.info("end deleteRoom result: {}", result);

        return result;
    }
}
