package com.tinqinacademy.hotel.persistence.repositories;

import com.tinqinacademy.hotel.persistence.entities.Booking;
import com.tinqinacademy.hotel.persistence.entities.Room;
import com.tinqinacademy.hotel.persistence.enums.BathroomType;
import com.tinqinacademy.hotel.persistence.enums.BedSize;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID>, BookingRepositoryCustom {
    List<Booking> findBookingsByRoomId(UUID roomId);

//    @Query(
//            value = """
//                    SELECT DISTINCT b.room.id FROM Booking b
//                    LEFT JOIN b.room.beds bb
//                    WHERE
//                    (:bathroomType IS NULL OR b.room.bathroomType = :bathroomType)
//                    AND (:bedSize IS NULL OR bb.bedSize = :bedSize)
//                    AND (:bedCount is NULL OR (SELECT COUNT(bed) FROM b.room.beds bed) = :bedCount)
//                    AND (:from IS NULL OR :to IS NULL OR b.room.id NOT IN (
//                        SELECT b.room.id FROM Booking b
//                        WHERE b.startDate <= CAST(:to AS DATE) AND b.endDate >= CAST(:from AS DATE)
//                    ))
//                    """
//    )

    @Query(
            value = """
                    SELECT b.room.id FROM Booking b
                    WHERE (
                        (CAST(:from AS DATE) >= b.startDate AND CAST(:to AS DATE) <= b.endDate)
                        OR
                        (CAST(:from AS DATE) <= b.endDate)
                    )
            """
    )
    List<UUID> searchRooms(LocalDate from,
                           LocalDate to);

    @Query(value = """
    SELECT COUNT(b) FROM Booking b
    WHERE (b.room.id = :roomId)
    AND (b.startDate = CAST(:startDate AS DATE) AND b.endDate = CAST(:endDate AS DATE))
    OR (b.startDate BETWEEN CAST(:startDate AS DATE) AND CAST(:endDate AS DATE))
    OR (b.endDate BETWEEN CAST(:startDate AS DATE) AND CAST(:endDate AS DATE))
    """)
    Long countByRoomAndDates(UUID roomId, LocalDate startDate, LocalDate endDate);

    Optional<Booking> findByIdAndUserId(UUID id, UUID userId);
}
