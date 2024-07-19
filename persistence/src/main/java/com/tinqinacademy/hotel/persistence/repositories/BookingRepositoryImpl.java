package com.tinqinacademy.hotel.persistence.repositories;

import com.tinqinacademy.hotel.persistence.contracts.BookingRepository;
import com.tinqinacademy.hotel.persistence.models.Booking;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class BookingRepositoryImpl implements BookingRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Booking> findAll() {
        String sql = """
                select * from bookings;
                """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Booking.class));
    }

    @Override
    public Optional<Booking> findById(UUID id) {
        String sql = """
                select * from bookings
                where id = ?;
                """;
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Booking.class), id));
    }

    @Override
    public void save(Booking entity) {
        String sql = """
                insert into bookings (id, room_id, user_id, start_date, end_date, booking_price)
                values (?, ?, ?, ?, ?, ?);
                """;
        jdbcTemplate.update(sql, entity.getId(), entity.getRoomId(), entity.getUserId(), entity.getStartDate(), entity.getEndDate(), entity.getPrice());
    }

    @Override
    public void deleteById(UUID id) {
        String sql = """
                delete from bookings
                where id = ?;
                """;
        jdbcTemplate.update(sql, id);
    }
}
