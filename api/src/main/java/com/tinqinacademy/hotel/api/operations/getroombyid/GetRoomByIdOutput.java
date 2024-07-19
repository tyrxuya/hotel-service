package com.tinqinacademy.hotel.api.operations.getroombyid;

import com.tinqinacademy.hotel.persistence.enums.BathroomType;
import com.tinqinacademy.hotel.persistence.enums.BedSize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class GetRoomByIdOutput {
    @Schema(example = "15")
    private UUID roomId;

    @Schema(example = "15124.15")
    private BigDecimal price;

    @Schema(example = "5")
    private Integer floor;

    @Schema(example = "kingSize")
    private BedSize bedSize;

    @Schema(example = "private")
    private BathroomType bathroomType;

    @Schema(example = "2")
    private Integer bedCount;

    private List<LocalDate> datesOccupied;
}
