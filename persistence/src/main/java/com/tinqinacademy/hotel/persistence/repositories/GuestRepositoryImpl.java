package com.tinqinacademy.hotel.persistence.repositories;

import com.tinqinacademy.hotel.persistence.contracts.GuestRepository;
import com.tinqinacademy.hotel.persistence.models.Booking;
import com.tinqinacademy.hotel.persistence.models.Guest;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class GuestRepositoryImpl implements GuestRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Guest> findAll() {
        String sql = """
                select * from guest;
                """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Guest.class));
    }

    @Override
    public Optional<Guest> findById(UUID id) {
        String sql = """
                select * from guests
                where id = ?;
                """;
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Guest.class), id));
    }

    @Override
    public void save(Guest entity) {
        String sql = """
                insert into guests 
                (id, 
                first_name, 
                last_name, 
                phone_number, 
                birthday, 
                civil_number, 
                id_issue_authority, 
                id_issue_date, 
                id_validity)
                values (?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;
        jdbcTemplate.update(sql,
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getPhone(),
                entity.getBirthday(),
                entity.getCivilNumber(),
                entity.getIdIssueAuthority(),
                entity.getIdIssueDate(),
                entity.getIdValidity());
    }

    @Override
    public void deleteById(UUID id) {
        String sql = """
                delete from guests
                where id = ?;
                """;
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void saveAll(List<Guest> guests) {
        guests.forEach(this::save);
    }
}
