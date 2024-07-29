package com.tinqinacademy.hotel.persistence.entities;

import com.tinqinacademy.hotel.persistence.enums.BathroomType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private Integer floor;

    @Column(nullable = false, updatable = false)
    private String number;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BathroomType bathroomType;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal price;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "rooms_beds",
            joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "bed_id", referencedColumnName = "id"))
    private List<Bed> beds;
}
