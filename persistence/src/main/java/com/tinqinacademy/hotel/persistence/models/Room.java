package com.tinqinacademy.hotel.persistence.models;

import com.tinqinacademy.hotel.persistence.enums.BathroomType;
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
public class Room {
    private UUID id;
    private Integer floor;
    private String number;
    private BathroomType bathroomType;
    private BigDecimal price;
    private List<Bed> beds;
}
