package com.tinqinacademy.hotel.persistence.repositories;

import com.tinqinacademy.hotel.persistence.entities.Booking;
import com.tinqinacademy.hotel.persistence.enums.BathroomType;
import com.tinqinacademy.hotel.persistence.enums.BedSize;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface BookingRepositoryCustom {
    List<Booking> findBookingsByStartDateAndEndDateAndBedSizeAndBathroomTypeAndBedCount(LocalDate from,
                                                                                        LocalDate to,
                                                                                        BedSize bedSize,
                                                                                        BathroomType bathroomType,
                                                                                        Integer bedCount);
}
