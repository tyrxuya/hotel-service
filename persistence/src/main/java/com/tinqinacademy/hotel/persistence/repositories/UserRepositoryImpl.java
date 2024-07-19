package com.tinqinacademy.hotel.persistence.repositories;

import com.tinqinacademy.hotel.persistence.contracts.UserRepository;
import com.tinqinacademy.hotel.persistence.models.User;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<User> findAll() {
        String sql = """
                select * from users;
                """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public Optional<User> findById(UUID id) {
        String sql = """
                select * from users
                where id = ?;
                """;
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id));
    }

    @Override
    public void save(User entity) {
        String sql = """
                insert into users (id, username, user_pass, email, birthday, phone_number)
                values (?, ?, ?, ?, ?, ?);
                """;
        jdbcTemplate.update(sql, entity.getId(), entity.getUsername(), entity.getPassword(), entity.getEmail(), entity.getBirthday(), entity.getPhone());
    }

    @Override
    public void deleteById(UUID id) {
        String sql = """
                delete from users
                where id = ?;
                """;
        jdbcTemplate.update(sql, id);
    }
}
