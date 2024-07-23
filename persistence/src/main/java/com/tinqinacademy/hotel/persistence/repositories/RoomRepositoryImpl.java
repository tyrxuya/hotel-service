package com.tinqinacademy.hotel.persistence.repositories;

import com.tinqinacademy.hotel.persistence.contracts.RoomRepository;
import com.tinqinacademy.hotel.persistence.models.Bed;
import com.tinqinacademy.hotel.persistence.models.Room;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class RoomRepositoryImpl implements RoomRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Room> findAll() {
        String sql = """
                select * from rooms r
                join beds_rooms br on r.id = br.room_id;
                """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Room.class));
    }

    @Override
    public Optional<Room> findById(UUID id) {
        String sql = """
                select * from rooms r 
                join beds_rooms br on r.id = br.room_id
                where r.id = ?;
                """;
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Room.class), id));
    }

    @Override
    public void save(Room entity) {
        String sql = """
                insert into rooms(id, room_floor, room_number, bathroom_type, price)
                values (?, ?, ?, ?, ?);
                """;
        jdbcTemplate.update(sql, entity.getId(), entity.getFloor(), entity.getNumber(), entity.getBathroomType().getCode(), entity.getPrice());

        for (Bed bed : entity.getBeds()) {
            sql = """
                insert into beds_rooms (room_id, bed_id)
                values (?, ?);
                """;
            jdbcTemplate.update(sql, entity.getId(), bed.getId());
        }
    }

    @Override
    public void deleteById(UUID id) {
        String sql = """
                delete from rooms
                where id = ?;
                """;
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void update(Room room) {
        String sql = """
                update rooms
                set room_floor = ?, room_number = ?, bathroom_type = ?, price = ?
                where id = ?;
                """;
        jdbcTemplate.update(sql, room.getFloor(), room.getNumber(), room.getBathroomType().getCode(), room.getPrice(), room.getId());

        for (Bed bed : room.getBeds()) {
            sql = """
                update beds_rooms
                set bed_id = ?
                where room_id = ?;
                """;
            jdbcTemplate.update(sql, bed.getId(), room.getId());
        }
    }

    @Override
    public Optional<BigDecimal> getPriceById(UUID id) {
        String sql = """
                select sum(price) from rooms
                where id = ?;
                """;
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(BigDecimal.class), id));
    }
}
