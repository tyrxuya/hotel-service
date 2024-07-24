package com.tinqinacademy.hotel.persistence.repositories;

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
                SELECT g FROM Guest g
                WHERE (:firstName IS NULL OR g.firstName = :firstName)
                AND (:lastName IS NULL OR g.lastName = :lastName)
                AND (:phone IS NULL OR g.phone = :phone)
                AND (:birthday IS NULL OR g.birthday = :birthday)
                AND (:civilNumber IS NULL OR g.civilNumber = :civilNumber)
                AND (:idIssueAuthority IS NULL OR g.idIssueAuthority = :idIssueAuthority)
                AND (:idIssueDate IS NULL OR g.idIssueDate = :idIssueDate)
                AND (:idValidity IS NULL OR g.idValidity = :idValidity)
                """)
    List<Guest> searchGuests(String firstName,
                             String lastName,
                             String phone,
                             LocalDate birthday,
                             String civilNumber,
                             String idIssueAuthority,
                             LocalDate idIssueDate,
                             LocalDate idValidity);
}
