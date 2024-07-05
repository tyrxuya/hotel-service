package com.tinqinacademy.hotel.operations.createroom;

import com.tinqinacademy.hotel.models.BathroomType;
import com.tinqinacademy.hotel.models.BedSize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class CreateRoomInput {
    @Schema(example = "2")
    private Integer bedCount;

    @Schema(example = "kingSize")
    private BedSize bedSize;

    @Schema(example = "shared")
    private BathroomType bathroomType;

    @Schema(example = "7")
    private Integer floor;

    @Schema(example = "700b")
    private String roomNo;

    @Schema(example = "1838124.15")
    private BigDecimal price;
}
