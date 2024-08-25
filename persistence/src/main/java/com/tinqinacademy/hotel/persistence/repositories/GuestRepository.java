package com.tinqinacademy.hotel.persistence.repositories;

import com.tinqinacademy.hotel.persistence.entities.Booking;
import com.tinqinacademy.hotel.persistence.entities.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface GuestRepository extends JpaRepository<Guest, UUID> {
    //void saveAll(List<Guest> guests);

    @Query(value = """
                SELECT b FROM Booking b
                JOIN b.guests g
                WHERE (:roomNo IS NULL OR b.room.number = :roomNo)
                AND (:firstName IS NULL OR g.firstName LIKE :firstName)
                AND (:lastName IS NULL OR g.lastName LIKE :lastName)
                AND (:phoneNo IS NULL OR g.phone = :phoneNo)
                AND (:civilNumber IS NULL OR g.civilNumber = :civilNumber)
                AND (CAST(:birthday AS DATE) IS NULL OR g.birthday = :birthday)
                AND (:idIssueAuthority IS NULL OR g.idIssueAuthority = :idIssueAuthority)
                AND (CAST(:idIssueDate AS DATE) IS NULL OR g.idIssueDate = :idIssueDate)
                """)
    List<Booking> searchGuests(String roomNo,
                               String firstName,
                               String lastName,
                               String phoneNo,
                               String civilNumber,
                               LocalDate birthday,
                               String idIssueAuthority,
                               LocalDate idIssueDate);
}
