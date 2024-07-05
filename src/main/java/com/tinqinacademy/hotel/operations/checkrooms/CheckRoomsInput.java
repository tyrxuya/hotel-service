package com.tinqinacademy.hotel.operations.checkrooms;

import com.tinqinacademy.hotel.models.BathroomType;
import com.tinqinacademy.hotel.models.BedSize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class CheckRoomsInput {
    @Schema(example = "2022-05-22")
    private LocalDate startDate;

    @Schema(example = "2022-05-25")
    private LocalDate endDate;

    @Schema(example = "2")
    private Integer bedCount;

    @Schema(example = "kingSize")
    private BedSize bedSize;

    @Schema(example = "private")
    private BathroomType bathroomType;
}
