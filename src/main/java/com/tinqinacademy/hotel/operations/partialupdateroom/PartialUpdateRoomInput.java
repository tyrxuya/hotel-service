package com.tinqinacademy.hotel.operations.partialupdateroom;

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
public class PartialUpdateRoomInput {
    @Schema(example = "2")
    private Integer bedCount;

    @Schema(example = "kingSize")
    private BedSize bedSize;

    @Schema(example = "private")
    private BathroomType bathroomType;

    @Schema(example = "5")
    private Integer floor;

    @Schema(example = "500a")
    private String roomNo;

    @Schema(example = "12351235.15")
    private BigDecimal price;
}
