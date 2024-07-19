package com.tinqinacademy.hotel.persistence.repositories;

import com.tinqinacademy.hotel.persistence.contracts.BedRepository;
import com.tinqinacademy.hotel.persistence.enums.BedSize;
import com.tinqinacademy.hotel.persistence.models.Bed;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class BedRepositoryImpl implements BedRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Bed> findAll() {
        String sql = """
                 select * from beds;
                 """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Bed.class));
    }

    @Override
    public Optional<Bed> findById(UUID id) {
        String sql = """
                select * from beds
                where id = ?;
                """;
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Bed.class), id));
    }

    @Override
    public void save(Bed entity) {
        String sql = """
                insert into beds
                values(?, ?, ?);
                """;
        jdbcTemplate.update(sql, UUID.randomUUID(), entity.getBedType(), entity.getCapacity());
    }

    @Override
    public void deleteById(UUID id) {
        String sql = """
                delete from beds
                where id = ?;
                """;
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void initializeBeds() {
        List<Bed> beds = findAll();
        if (!beds.isEmpty()) {
            return;
        }
        for (BedSize bedSize : BedSize.values()) {
            Bed bed = Bed.builder()
                    .id(UUID.randomUUID())
                    .bedType(bedSize.getCode())
                    .capacity(bedSize.getCapacity())
                    .build();
            save(bed);
        }
    }

    @Override
    public Optional<Bed> getBedByBedSize(String bedSize) {
        String sql = """
                select * from beds
                where bed_type = ?
                """;
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Bed.class), bedSize));
    }
}
