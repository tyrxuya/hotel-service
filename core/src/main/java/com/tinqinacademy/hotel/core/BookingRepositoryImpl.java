package com.tinqinacademy.hotel.core;

import com.tinqinacademy.hotel.persistence.entities.Bed;
import com.tinqinacademy.hotel.persistence.entities.Booking;
import com.tinqinacademy.hotel.persistence.entities.Room;
import com.tinqinacademy.hotel.persistence.enums.BathroomType;
import com.tinqinacademy.hotel.persistence.enums.BedSize;
import com.tinqinacademy.hotel.persistence.repositories.BookingRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class BookingRepositoryImpl implements BookingRepositoryCustom {
    private final EntityManager em;

    public BookingRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Booking> findBookingsByStartDateAndEndDateAndBedSizeAndBathroomTypeAndBedCount(LocalDate from,
                                                                                               LocalDate to,
                                                                                               BedSize bedSize,
                                                                                               BathroomType bathroomType,
                                                                                               Integer bedCount) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Booking> cq = cb.createQuery(Booking.class);

        Root<Booking> root = cq.from(Booking.class);
        List<Predicate> predicates = new ArrayList<>();

        Subquery<Long> bedCountSubquery = cq.subquery(Long.class);

        if (from != null && to != null) {
            predicates.add(cb.or(
                    cb.and(
                            cb.greaterThanOrEqualTo(root.get("startDate"), from),
                            cb.greaterThanOrEqualTo(root.get("startDate"), to)
                    ),
                    cb.and(
                            cb.lessThanOrEqualTo(root.get("endDate"), from),
                            cb.lessThanOrEqualTo(root.get("endDate"), to)
                    )
            ));
        }

        if (bedSize != null && !bedSize.equals(BedSize.UNKNOWN)) {
            predicates.add(cb.equal(root.get("room").get("beds").get("bedSize"), bedSize));
        }

        if (bathroomType != null && !bathroomType.equals(BathroomType.UNKNOWN)) {
            predicates.add(cb.equal(root.get("room").get("bathroomType"), bathroomType));
        }

        if (bedCount != null) {
            predicates.add(cb.equal(bedCountSubquery.select(cb.countDistinct(root.get("room").get("beds").get("id"))), bedCount));
        }

        if (predicates.isEmpty()) {
            return em.createQuery(cq).getResultList();
        }

        return em.createQuery(cq.where(predicates.toArray(new Predicate[0]))).getResultList();
    }
}
