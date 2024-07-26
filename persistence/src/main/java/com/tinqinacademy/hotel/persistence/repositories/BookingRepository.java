package com.tinqinacademy.hotel.persistence.repositories;

import com.tinqinacademy.hotel.persistence.entities.Booking;
import com.tinqinacademy.hotel.persistence.enums.BathroomType;
import com.tinqinacademy.hotel.persistence.enums.BedSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    Optional<Booking> findByRoomId(UUID roomId);

    @Query(
            value = """
                    SELECT b.room.id FROM Booking b
                    JOIN Room r
                    WHERE (
                        (CAST(:from AS DATE) < b.startDate AND CAST(:to AS DATE) < b.startDate)
                        OR
                        (CAST(:from AS DATE) > b.endDate AND CAST(:to AS DATE) > b.endDate)
                    )
                    AND (:bathroomType = r.bathroomType)
                    AND :bedSize IN (
                        SELECT r FROM Room r
                        JOIN Bed b
                    )
                    """
    )
    List<UUID> searchRooms(LocalDate from,
                           LocalDate to,
                           BedSize bedSize,
                           BathroomType bathroomType);

    @Query(
            value = """
                    SELECT b.room.id FROM Booking b
                    WHERE (
                        (CAST(:from AS DATE) < b.startDate AND CAST(:to AS DATE) < b.startDate)
                        OR
                        (CAST(:from AS DATE) > b.endDate AND CAST(:to AS DATE) > b.endDate)
                    )
            """
    )
    List<UUID> searchRooms(LocalDate from,
                           LocalDate to);
}
