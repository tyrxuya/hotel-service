package com.tinqinacademy.hotel.api.operations.checkrooms;

import com.tinqinacademy.hotel.api.contracts.base.OperationInput;
import com.tinqinacademy.hotel.persistence.enums.BathroomType;
import com.tinqinacademy.hotel.persistence.enums.BedSize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class CheckRoomsInput implements OperationInput {
    @Schema(example = "2022-05-22")
    private LocalDate startDate;

    @Schema(example = "2022-05-25")
    private LocalDate endDate;

    @Schema(example = "kingSize")
    private BedSize bedSize;

    @Schema(example = "private")
    private BathroomType bathroomType;
}
