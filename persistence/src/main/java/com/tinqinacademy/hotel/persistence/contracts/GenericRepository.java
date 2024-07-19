package com.tinqinacademy.hotel.persistence.contracts;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GenericRepository<V> {
    List<V> findAll();
    Optional<V> findById(UUID id);
    void save(V entity);
    void deleteById(UUID id);
}
