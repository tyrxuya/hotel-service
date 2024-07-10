package com.tinqinacademy.hotel.operations.getroombyid;

import com.tinqinacademy.hotel.models.BathroomType;
import com.tinqinacademy.hotel.models.BedSize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class GetRoomByIdOutput {
    @Schema(example = "15")
    private String roomId;

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
